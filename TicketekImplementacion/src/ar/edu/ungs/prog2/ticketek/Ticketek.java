package ar.edu.ungs.prog2.ticketek;

import java.awt.Point;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 */
public class Ticketek implements ITicketek {

	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Espectaculo> espectaculos;
	private HashMap<String, Sede> sedes;
	private HashMap<String, Entrada> entradasVendidas;

	public Ticketek() {
		this.usuarios = new HashMap<>();
		this.espectaculos = new HashMap<>();
		this.sedes = new HashMap<>();
		this.entradasVendidas = new HashMap<>();
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		//------ Comprobaciones -------
		datoValido(nombre,"nombre");
		datoValido(direccion,"direccion");
		datoValido(capacidadMaxima,"capacidadMaxima");
		yaExisteSede(nombre);
		//------ Comportamiento metodo -------
		Sede nuevaSede = new Estadio(nombre, direccion, capacidadMaxima);
		sedes.put(nombre, nuevaSede);
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		//------ Comprobaciones -------

		datoValido(nombre,"nombre");
		datoValido(direccion,"direccion");
		datoValido(capacidadMaxima,"capacidadMaxima");
		datoValido(asientosPorFila,"asientosPorFila");
		datoValido(sectores,"sectores");
		datoValido(capacidad,"capacidad");
		datoValido(porcentajeAdicional,"porcentajeAdicional");
		yaExisteSede(nombre);
		if (capacidadMaxima <= 0) {
			throw new RuntimeException("la capacidad maxima ingresada es invalida");
		}
		//------ Comportamiento metodo -------

		Sede nuevaSede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad,
				porcentajeAdicional);
		sedes.put(nombre, nuevaSede);
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		//------ Comprobaciones -------

		datoValido(nombre,"nombre");
		datoValido(direccion,"direccion");
		datoValido(capacidadMaxima,"capacidadMaxima");
		datoValido(asientosPorFila,"asientosPorFila");
		datoValido(sectores,"sectores");
		datoValido(capacidad,"capacidad");
		datoValido(porcentajeAdicional,"porcentajeAdicional");
		if(precioConsumicion <= 0) {
			throw new RuntimeException("precioConsumision ingresado es invalida");
		}
		yaExisteSede(nombre);
		//------ Comportamiento metodo -------

		Sede nuevaSede = new Miniestadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos,
				precioConsumicion, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, nuevaSede);
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		//------ Comprobaciones -------

		datoValido(email,"email");
		datoValido(nombre,"nombre");
		datoValido(apellido,"apellido");
		datoValido(contrasenia,"contrasenia");
		yaExisteUsuario(email);
		//------ Comportamiento metodo -------

		Usuario usuario = new Usuario(email, nombre, apellido, contrasenia);
		usuarios.put(email, usuario);
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public void registrarEspectaculo(String nombre) {
		//------ Comprobaciones -------

		datoValido(nombre,"nombre");
		yaExisteEspectaculo(nombre);
		//------ Comportamiento metodo -------

		Espectaculo espectaculo = new Espectaculo(nombre);
		espectaculos.put(nombre, espectaculo);
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		//------ Comprobaciones -------

		datoValido(nombreEspectaculo,"nombreEspectaculo");
		datoValido(fecha,"fecha");
		datoValido(sede,"sede");
		if(precioBase <= 0) {
			throw new RuntimeException("precioConsumision ingresado es invalida");
		}
		espectaculoNoRegistrado(nombreEspectaculo);
		sedeNoRegistrada(sede);
		yaSeRealizaFuncionEnFecha(nombreEspectaculo, fecha);
		//------ Comportamiento metodo -------

		Sede sedeObj = sedes.get(sede); // <- Obtener el objeto sede
		Funcion nuevaFuncion = new Funcion(sedeObj, fecha); // <- Pasar el objeto, no el String

		espectaculos.get(nombreEspectaculo).agregarFuncion(nuevaFuncion, fecha, precioBase);
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			int cantidadEntradas) {
		//------ Comprobaciones -------

		datoValido(cantidadEntradas,"cantidadEntradas");
		datoValido(nombreEspectaculo,"nombreEspectaculo");
		datoValido(fecha,"fecha");
		datoValido(email,"email");
		datoValido(contrasenia,"contrasenia");

		espectaculoNoRegistrado(nombreEspectaculo);
		noHayFuncionEnFecha(nombreEspectaculo, fecha);
		iniciarSesionUsuario(email, contrasenia);
		//------ Comportamiento metodo -------

		List<IEntrada> entradas = new LinkedList<IEntrada>();
		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		Usuario usuario = this.usuarios.get(email);
		Sede sede = this.sedes.get(espectaculo.consultarSede(fecha));

		for (int i = 0; i < cantidadEntradas; i++) {

			if (plazasDisponibles(espectaculo, fecha) < cantidadEntradas) {
				throw new RuntimeException("¡No hay suficientes Plazas Disponibles en esta función!");
			}

			Entrada entrada = new Entrada(espectaculo, fecha, sede, email);
			entradas.add(entrada);
			usuario.comprarEntrada(entrada.consultarCodigo(), entrada.consultarFecha());
			espectaculo.venderEntrada(entrada);
			this.entradasVendidas.put(entrada.consultarCodigo(), entrada);
		}
		return entradas;
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		//------ Comprobaciones -------

		datoValido(nombreEspectaculo,"nombreEspectaculo");
		datoValido(fecha,"fecha");
		datoValido(email,"email");
		datoValido(contrasenia,"contrasenia");
		datoValido(sector,"sector");
		datoValido(asientos,"asientos");
		espectaculoNoRegistrado(nombreEspectaculo);
		noHayFuncionEnFecha(nombreEspectaculo, fecha);
		iniciarSesionUsuario(email, contrasenia);
		//------ Comportamiento metodo -------

		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		Usuario usuario = this.usuarios.get(email);
		List<IEntrada> entradas = new LinkedList<IEntrada>();
		Sede sede = this.sedes.get(espectaculo.consultarSede(fecha));

		for (int i = 0; i < asientos.length; i++) {
			// falta comprobar que haya plazas suficientes

			Entrada entrada = new Entrada(espectaculo, fecha, sede, sector, calcularFilaAsiento(sede, asientos[i]),
					email, asientos[i]);
			entradas.add(entrada);
			usuario.comprarEntrada(entrada.consultarCodigo(), entrada.consultarFecha());
			espectaculo.venderEntrada(entrada);
			this.entradasVendidas.put(entrada.consultarCodigo(), entrada);
		}
		
		return entradas;
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public String listarFunciones(String nombreEspectaculo) {
		//------ Comprobaciones -------

		datoValido(nombreEspectaculo,"nombreEspectaculo");
		//------ Comportamiento metodo -------

		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		if (espectaculo == null)
			return "";

		StringBuilder resultado = new StringBuilder();
		Iterator<String> iterador = espectaculo.consultarFunciones().iterator();

        while (iterador.hasNext()) {
            Funcion funcion = espectaculo.consultarFuncion(iterador.next());
            resultado.append(funcion.toString()).append("\n");
        }
        
		return resultado.toString();
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		//------ Comprobaciones -------

		datoValido(nombreEspectaculo,"nombreEspectaculo");
		espectaculoNoRegistrado(nombreEspectaculo);
		
		//------ Comportamiento metodo -------

		List<IEntrada> entradas = new LinkedList<IEntrada>();
		for (String codigo : this.entradasVendidas.keySet()) {
			String nombreEspectaculoEntrada = this.entradasVendidas.get(codigo).getEspectaculo().consultarNombre();
			if (nombreEspectaculo == nombreEspectaculoEntrada) {
				entradas.add(this.entradasVendidas.get(codigo));
			}
		}

		if (entradas.isEmpty()) {
			throw new RuntimeException("Este espectaculo no vendió ninguna entrada");
		}
		return entradas;
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		//------ Comprobaciones -------

		datoValido(email, "email");
		datoValido(contrasenia, "contraseña");
		iniciarSesionUsuario(email, contrasenia);
		
		//------ Comportamiento metodo -------

		List<IEntrada> entradas = new LinkedList<IEntrada>();
		Usuario usuario = this.usuarios.get(email);
		LinkedList<String> codigosFuturos = usuario.listarEntradasFuturas();
		for (String codigo : codigosFuturos) {
			entradas.add(this.entradasVendidas.get(codigo));
		}

		return entradas;
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		//------ Comprobaciones -------

		datoValido(email, "email");
		datoValido(contrasenia, "contraseña");
		iniciarSesionUsuario(email, contrasenia);
		
		//------ Comportamiento metodo -------

		List<IEntrada> entradas = new LinkedList<IEntrada>();
		Usuario usuario = this.usuarios.get(email);
		LinkedList<String> codigos = usuario.listarEntradasCompradas();
		for (String codigo : codigos) {
			entradas.add(this.entradasVendidas.get(codigo));
		}

		return entradas;
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		// Debe ser en O(1)
		//------ Comprobaciones -------

		datoValido(contrasenia, "contraseña"); 									//(T) T= operaciones de Dato Valido
		Entrada entradaCast = (Entrada) entrada; 								//2
		iniciarSesionUsuario(entradaCast.consultarComprador(), contrasenia);	//(S) S = operaciones de IniciarSesionUsuario
		Usuario usuario = this.usuarios.get(entradaCast.consultarComprador());	//2
		//------ Comportamiento metodo -------

		boolean resultado = usuario.cancelarEntrada(entradaCast.consultarCodigo()); //(c) c= cancelarEntrada
		if (resultado == true) {												// 1
			Espectaculo espectaculo = entradaCast.getEspectaculo();				//1 + (GE) GE = getEspectaculo()
			espectaculo.anularEntrada(entradaCast);								// (A) A = anularEntrada [Espectaculo]
		}

		return resultado;														//1
		//TOTAL							T + S + C + GE + A + 7
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String nuevaFecha, String nuevoSector,
			int asiento) {
		// 1. Validaciones preliminares
		if (entrada == null) {
			throw new IllegalArgumentException("La entrada original no puede ser nula.");
		}
		datoValido(contrasenia, "contraseña");
		datoValido(nuevaFecha, "nueva fecha");
		datoValido(nuevoSector, "nuevo sector");
		if (asiento <= 0) {
			throw new IllegalArgumentException("El número de asiento debe ser positivo.");
		}

		Entrada originalEntrada = (Entrada) entrada;
		String emailComprador = originalEntrada.consultarComprador();

		// 2. Autenticar usuario
		iniciarSesionUsuario(emailComprador, contrasenia);
		Usuario usuario = this.usuarios.get(emailComprador);

		// 3. Verificar validez de la entrada original con el sistema y el usuario
		if (!this.entradasVendidas.containsKey(originalEntrada.consultarCodigo()) ||
				!this.entradasVendidas.get(originalEntrada.consultarCodigo()).equals(originalEntrada)) {
			throw new RuntimeException("La entrada original no es válida o no se encuentra en el sistema.");
		}

		Espectaculo espectaculo = originalEntrada.getEspectaculo();
		String nombreEspectaculo = espectaculo.consultarNombre();

		// 4. Validar nueva función, sede, sector y disponibilidad
		espectaculoNoRegistrado(nombreEspectaculo);
		noHayFuncionEnFecha(nombreEspectaculo, nuevaFecha);

		Funcion funcionNueva = espectaculo.consultarFuncion(nuevaFecha);
		Sede sedeNueva = this.sedes.get(funcionNueva.consultarSede());

		if (!(sedeNueva instanceof Teatro) && !(sedeNueva instanceof Miniestadio)) {
			throw new RuntimeException(
					"El cambio a un sector y asiento específico solo es válido para Teatros o Miniestadios.");
		}

		boolean sectorValidoEnSede = false;
		int capacidadDelSector = -1;

		if (sedeNueva instanceof Teatro teatro) {
			for (int i = 0; i < teatro.cantidadSectores(); i++) {
				if (teatro.consultarSector(i).equals(nuevoSector)) {
					sectorValidoEnSede = true;
					capacidadDelSector = teatro.capacidadSector(i);
					break;
				}
			}
		} else if (sedeNueva instanceof Miniestadio miniestadio) {
			for (int i = 0; i < miniestadio.cantidadSectores(); i++) {
				if (miniestadio.consultarSector(i).equals(nuevoSector)) {
					sectorValidoEnSede = true;
					capacidadDelSector = miniestadio.capacidadSector(i);
					break;
				}
			}
		}

		if (!sectorValidoEnSede) {
			throw new RuntimeException(
					"El sector '" + nuevoSector + "' no es válido para la sede de la nueva función.");
		}

		// Chequeo de capacidad del sector
		if (capacidadDelSector != -1
				&& funcionNueva.consultarEntradasVendidasSector(nuevoSector) >= capacidadDelSector) {
			throw new RuntimeException("El sector '" + nuevoSector + "' está lleno para la nueva fecha y función.");
		}

		// 5. Anular la entrada original
		boolean canceladaPorUsuario = usuario.cancelarEntrada(originalEntrada.consultarCodigo());
		if (!canceladaPorUsuario) {
			throw new RuntimeException(
					"La entrada original no pudo ser cancelada por el usuario (verifique que la poseía).");
		}
		espectaculo.anularEntrada(originalEntrada);
		if (this.entradasVendidas.remove(originalEntrada.consultarCodigo()) == null) {
			throw new RuntimeException(
					"Error interno: La entrada original no se encontró en la lista de vendidas para su remoción, aunque debería haber estado.");
		}
		// 6. Crear y "vender" la nueva entrada
		Point nuevasCoordenadasAsiento = calcularFilaAsiento(sedeNueva, asiento);
		Entrada nuevaEntrada = new Entrada(espectaculo, nuevaFecha, sedeNueva, nuevoSector, nuevasCoordenadasAsiento,
				emailComprador, asiento);

		usuario.comprarEntrada(nuevaEntrada.consultarCodigo(), nuevaEntrada.consultarFecha());
		espectaculo.venderEntrada(nuevaEntrada);
		this.entradasVendidas.put(nuevaEntrada.consultarCodigo(), nuevaEntrada);

		return nuevaEntrada;
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String nuevaFecha) {
		// 1. Validaciones preliminares
		if (entrada == null) {
			throw new IllegalArgumentException("La entrada original no puede ser nula.");
		}
		datoValido(contrasenia, "contraseña");
		datoValido(nuevaFecha, "nueva fecha");

		Entrada originalEntrada = (Entrada) entrada;
		String emailComprador = originalEntrada.consultarComprador();

		// 2. Autenticar usuario
		iniciarSesionUsuario(emailComprador, contrasenia);
		Usuario usuario = this.usuarios.get(emailComprador);

		// 3. Verificar validez de la entrada original
		if (!this.entradasVendidas.containsKey(originalEntrada.consultarCodigo()) ||
				!this.entradasVendidas.get(originalEntrada.consultarCodigo()).equals(originalEntrada)) {
			throw new RuntimeException("La entrada original no es válida o no se encuentra en el sistema.");
		}

		Espectaculo espectaculo = originalEntrada.getEspectaculo();
		String nombreEspectaculo = espectaculo.consultarNombre();

		// 4. Validar nueva función y su Sede
		espectaculoNoRegistrado(nombreEspectaculo);
		noHayFuncionEnFecha(nombreEspectaculo, nuevaFecha);

		Funcion funcionNueva = espectaculo.consultarFuncion(nuevaFecha);
		Sede sedeNueva = this.sedes.get(funcionNueva.consultarSede());

		// 5. Lógica de cambio basada en el tipo de la nueva Sede
		if (sedeNueva instanceof Estadio) {
			// Verificar disponibilidad general para Estadios
			if (plazasDisponibles(espectaculo, nuevaFecha) <= 0) {
				throw new RuntimeException("No hay plazas disponibles en la nueva función del estadio.");
			}

			// Anular la entrada original
			boolean canceladaPorUsuario = usuario.cancelarEntrada(originalEntrada.consultarCodigo());
			if (!canceladaPorUsuario) {
				throw new RuntimeException("La entrada original no pudo ser cancelada por el usuario.");
			}
			espectaculo.anularEntrada(originalEntrada);
			if (this.entradasVendidas.remove(originalEntrada.consultarCodigo()) == null) {
				throw new RuntimeException("Error interno: La entrada original no se encontró para su remoción.");
			}

			// Crear y "vender" la nueva entrada para Estadio
			Entrada nuevaEntrada = new Entrada(espectaculo, nuevaFecha, sedeNueva, emailComprador);

			usuario.comprarEntrada(nuevaEntrada.consultarCodigo(), nuevaEntrada.consultarFecha());
			espectaculo.venderEntrada(nuevaEntrada);
			this.entradasVendidas.put(nuevaEntrada.consultarCodigo(), nuevaEntrada);

			return nuevaEntrada;

		} else if (sedeNueva instanceof Teatro || sedeNueva instanceof Miniestadio) {
			throw new IllegalArgumentException(
					"Para cambiar entrada a un Teatro o Miniestadio, use la versión del método que especifica sector y asiento.");
		} else {
			throw new RuntimeException(
					"Tipo de sede no compatible para el cambio de entrada: " + sedeNueva.getClass().getName());
		}
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha) {
		//------ Comprobaciones -------

		datoValido(nombreEspectaculo, "nombre del espectáculo");
		datoValido(fecha, "fecha");

		espectaculoNoRegistrado(nombreEspectaculo);
		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		noHayFuncionEnFecha(nombreEspectaculo, fecha); // Checks if function exists

		return espectaculo.consultarPrecioBase(fecha);
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
		//------ Comprobaciones -------

		datoValido(nombreEspectaculo, "nombre del espectáculo");
		datoValido(fecha, "fecha");
		datoValido(sector, "sector");

		espectaculoNoRegistrado(nombreEspectaculo);
		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		noHayFuncionEnFecha(nombreEspectaculo, fecha);

		Funcion funcion = espectaculo.consultarFuncion(fecha);
		Sede sede = this.sedes.get(funcion.consultarSede()); // Sede object from Funcion
		double precio = espectaculo.consultarPrecioBase(fecha);

		return sede.calcularCostoEntrada(precio, sector);
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		//------ Comprobaciones -------

		datoValido(nombreEspectaculo, "nombre del espectáculo");
		espectaculoNoRegistrado(nombreEspectaculo);
		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		Double total = espectaculo.recaudacionTotal();
		return total;
	}
	// -------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------

	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		//------ Comprobaciones -------

		datoValido(nombreEspectaculo, "nombre del espectáculo");
		datoValido(nombreSede, "nombre de la sede");
		espectaculoNoRegistrado(nombreEspectaculo);
		sedeNoRegistrada(nombreSede);

		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		return espectaculo.recaudacionPorSede(nombreSede);
	}

	// -------------------------------------------------------------------------------
	// METODOS AUXILIARES
	// -------------------------------------------------------------------------------
	private int plazasDisponibles(Espectaculo espectaculo, String fecha) {
		String sede = espectaculo.consultarSede(fecha);
		int capacidad = this.sedes.get(sede).consultarCapacidad();
		int ocupados = espectaculo.consultarVentasFuncion(fecha);
		return capacidad - ocupados;
	}

	private Point calcularFilaAsiento(Sede sede, int numAsiento) {
		int fila, asiento;
		fila = 0;
		asiento = 0;
		if (sede.getClass() == Teatro.class) {
			Teatro teatro = (Teatro) sede;
			int tamanoFila = teatro.obtenerAsientosPorFila();
			if (numAsiento % tamanoFila == 0) {
				fila = (numAsiento / tamanoFila) - 1;
				asiento = tamanoFila;
			} else {
				fila = numAsiento / tamanoFila;
				asiento = numAsiento % tamanoFila;
			}

		}
		if (sede.getClass() == Miniestadio.class) {
			Miniestadio teatro = (Miniestadio) sede;
			int tamanoFila = teatro.obtenerAsientosPorFila();
			if (numAsiento % tamanoFila == 0) {
				fila = (numAsiento / tamanoFila) - 1;
				asiento = tamanoFila;
			} else {
				fila = numAsiento / tamanoFila;
				asiento = numAsiento % tamanoFila;
			}
		}
		return new Point(fila, asiento);
	}

	// -------------------------------------------------------------------------------
	// COMPROBAR EXISTENCIA DE DATOS
	// -------------------------------------------------------------------------------
	private void yaExisteUsuario(String email) throws RuntimeException {
		if (this.usuarios.containsKey(email)) {
			throw new RuntimeException("Ya existe un usuario registrado con ese mail.");
		}
	}

	private void yaExisteSede(String nombre) throws RuntimeException {
		if (this.sedes.containsKey(nombre)) {
			throw new RuntimeException("Ya existe una sede con el nombre ingresado");
		}
	}

	private void yaExisteEspectaculo(String nombre) throws RuntimeException {
		if (this.espectaculos.containsKey(nombre)) {
			throw new RuntimeException("Ya existe un Espectaculo con el nombre ingresado");
		}
	}

	private void sedeNoRegistrada(String sede) throws RuntimeException {
		if (!this.sedes.containsKey(sede)) {
			throw new RuntimeException("¡La Sede ingresada no esta registrada!");
		}
	} // Evita que se intente operar con una sede que no está registrada en el
		// sistema.

	private void espectaculoNoRegistrado(String nombreEspectaculo) throws RuntimeException {
		if (!this.espectaculos.containsKey(nombreEspectaculo)) {
			throw new RuntimeException("¡El Espectaculo ingresado no esta registrado!");
		}
	} // Evita que se intente operar con un espectáculo inexistente.

	private void iniciarSesionUsuario(String email, String contrasenia) throws RuntimeException {
		usuarioNoRegistrado(email);									// 2 (Op de usuarioNoRegistrado)
		if (!this.usuarios.get(email).iniciarSesion(contrasenia)) {	//1 + (IS) IS = iniciarSesion de clase Usuario
			throw new RuntimeException("¡Contraseña INVALIDA!");	//1
		}															//TOTAL 4 + IS
	}
																						//  2^
	private void usuarioNoRegistrado(String email) throws RuntimeException {		//TOTAL  |
		if (!this.usuarios.containsKey(email)) {										//1	 |
			throw new RuntimeException("NO existe un usuario registrado con ese mail.");//1
		}
	}

	

	private void noHayFuncionEnFecha(String nombreEspectaculo, String fecha) throws RuntimeException {
		if (this.espectaculos.get(nombreEspectaculo).fechaLibre(fecha)) {
			throw new RuntimeException("NO hay una funcion en la fecha ingresada");
		}
	}

	private void yaSeRealizaFuncionEnFecha(String nombreEspectaculo, String fecha) throws RuntimeException {
		if (!espectaculos.get(nombreEspectaculo).fechaLibre(fecha)) {
			throw new RuntimeException("¡Ya se realiza una funcion en la fecha ingresada!");
		}
	}

	private void datoValido(String parametro, String nombreParametro) throws RuntimeException {
		if (parametro == null || parametro.length() == 0 || parametro.isEmpty()) {	// 1 + 1 + 1 + 1 + 1 + 1
			throw new RuntimeException("¡Dato Invalido: " + nombreParametro + "!");	// 1 + 1 + 1
		}																			// TOTAL 9 OP

	}
	private void datoValido(int parametro, String nombreParametro) throws RuntimeException {
		if (parametro < 0) {
			throw new RuntimeException("¡Dato Invalido: " + nombreParametro + "!");
		}

	}
	private void datoValido(int[] parametro, String nombreParametro) {
		if (parametro == null || parametro.length == 0) {
			throw new RuntimeException("¡Dato Invalido: " + nombreParametro + "!");
		}

		
	}

	private void datoValido(String[] parametro, String nombreParametro) {
		if (parametro == null || parametro.length == 0) {
			throw new RuntimeException("¡Dato Invalido: " + nombreParametro + "!");
		}
		
	}
	
	//-----------------------------------------------
	@Override	
	public String toString() {
		StringBuilder resultado = new StringBuilder();
		resultado.append("Bienvenido al sistema de venta de entradas TICKETEK").append("\n");
    	resultado.append(" - ").append("Espectaculos Registrados: ").append(this.espectaculos.size()).append("\n");
    	resultado.append(" - ").append("Usuarios Registrados: ").append(this.usuarios.size()).append("\n");
    	resultado.append(" - ").append("Sedes Registrados: ").append(this.sedes.size()).append("\n");
    	resultado.append(" - ").append("Entradas Vendidas: ").append(this.entradasVendidas.size()).append("\n");
    	
    	double recaudacion = 0;
    	for(String nombre: this.espectaculos.keySet()) {
    		recaudacion += totalRecaudado(nombre); 
    	}
    	
    	resultado.append(" - ").append("Total Recaudado: $").append(recaudacion).append("\n");
    	
		return resultado.toString();
    	
	}
}