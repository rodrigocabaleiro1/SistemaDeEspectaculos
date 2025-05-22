package ar.edu.ungs.prog2.ticketek;

import java.awt.Point;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Ticketek implements ITicketek {

	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Espectaculo> espectaculos;
	private HashMap<String, Sede> sedes;
	private HashMap<Integer, Entrada> entradasVendidas;

	public Ticketek() {
		this.usuarios = new HashMap<>();
		this.espectaculos = new HashMap<>();
		this.sedes = new HashMap<>();
		this.entradasVendidas = new HashMap<>();
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		if (nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacío");
		}
		if (direccion == null || direccion.isEmpty()) {
			throw new IllegalArgumentException("La dirección no puede estar vacía");
		}
		if (capacidadMaxima <= 0) {
			throw new RuntimeException("la capacidad maxima ingresada es invalida");
		}
		yaExisteSede(nombre);

		Sede nuevaSede = new Estadio(nombre, direccion, capacidadMaxima);
		sedes.put(nombre, nuevaSede);
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {

		if (nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacío");
		}
		if (direccion == null || direccion.isEmpty()) {
			throw new IllegalArgumentException("La dirección no puede estar vacía");
		}
		if (capacidadMaxima <= 0) {
			throw new RuntimeException("la capacidad maxima ingresada es invalida");
		}
		yaExisteSede(nombre);

		Sede nuevaSede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad,
				porcentajeAdicional);
		sedes.put(nombre, nuevaSede);

	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {

		yaExisteSede(nombre);

		Sede nuevaSede = new Miniestadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos,
				precioConsumicion, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, nuevaSede);
	}

	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {

		yaExisteUsuario(email);
		Usuario usuario = new Usuario(email, nombre, apellido, contrasenia);
		usuarios.put(email, usuario);
	}

	@Override
	public void registrarEspectaculo(String nombre) {
		yaExisteEspectaculo(nombre);

		Espectaculo espectaculo = new Espectaculo(nombre);
		espectaculos.put(nombre, espectaculo);

	}

	@Override
	public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		espectaculoNoRegistrado(nombreEspectaculo);
		sedeNoRegistrada(sede);
		yaSeRealizaFuncionEnFecha(nombreEspectaculo, fecha);

		Sede sedeObj = sedes.get(sede); // <- Obtener el objeto sede
		Funcion nuevaFuncion = new Funcion(sedeObj, fecha); // <- Pasar el objeto, no el String

		espectaculos.get(nombreEspectaculo).agregarFuncion(nuevaFuncion, fecha, precioBase);
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			int cantidadEntradas) {

		espectaculoNoRegistrado(nombreEspectaculo);
		noHayFuncionEnFecha(nombreEspectaculo, fecha);
		iniciarSesionUsuario(email, contrasenia);

		List<IEntrada> entradas = new LinkedList<IEntrada>();
		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		Usuario usuario = this.usuarios.get(email);
		Sede sede = this.sedes.get(espectaculo.consultarSede(fecha));

		for (int i = 0; i < cantidadEntradas; i++) {

			if (plazasDisponibles(espectaculo, fecha) < cantidadEntradas) {
				throw new RuntimeException("¡No hay suficientes Plazas Disponibles en esta función!");
			}
			
			Entrada entrada = new Entrada(espectaculo, fecha, sede);
			entradas.add(entrada);
			usuario.comprarEntrada(entrada.consultarCodigo());
			espectaculo.venderEntrada(fecha); //deberia aumentar el contador de entradas vendidas
		}
		return entradas;
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		// TODO Auto-generated method stub
		
		espectaculoNoRegistrado(nombreEspectaculo);
		noHayFuncionEnFecha(nombreEspectaculo, fecha);
		iniciarSesionUsuario(email, contrasenia);
		
		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		Usuario usuario = this.usuarios.get(email);
		List<IEntrada> entradas = new LinkedList<IEntrada>();
		Sede sede = this.sedes.get(espectaculo.consultarSede(fecha));
		
		for (int i = 0; i < asientos.length; i++) {
			//falta comprobar que haya plazas suficientes
	
			Entrada entrada = new Entrada(espectaculo, fecha, sede, sector, calcularFilaAsiento(sede, asientos[i]));
			entradas.add(entrada);
			usuario.comprarEntrada(entrada.consultarCodigo());
			espectaculo.venderEntrada(fecha); //deberia aumentar el contador de entradas vendidas
		}
		
		return entradas;
	}
	

	@Override
	public String listarFunciones(String nombreEspectaculo) {
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		if (espectaculo == null)
			return "";

		StringBuilder resultado = new StringBuilder();
		for (String fecha : espectaculo.consultarFunciones()) {
			Funcion funcion = espectaculo.consultarFuncion(fecha);
			String sedeNombre = funcion.consultarSede();
			Sede sede = sedes.get(sedeNombre);
			resultado.append(" - (").append(fecha).append(") ").append(sede.getNombre()).append(" - ");

			if (sede.esEstadio()) {
				resultado.append(funcion.consultarEntradasVendidas())
						.append("/")
						.append(sede.consultarCapacidad())
						.append("\n");
			} else if (sede instanceof Teatro teatro) {
				for (int i = 0; i < teatro.cantidadSectores(); i++) {
					if (i > 0)
						resultado.append(" | ");
					String nombreSector = teatro.consultarSector(i);
					int vendidos = funcion.consultarEntradasVendidasSector(nombreSector);
					resultado.append(nombreSector)
							.append(": ")
							.append(vendidos)
							.append("/")
							.append(teatro.capacidadSector(i));
				}
				resultado.append("\n");
			} else if (sede instanceof Miniestadio mini) {
				for (int i = 0; i < mini.cantidadSectores(); i++) {
					if (i > 0)
						resultado.append(" | ");
					String nombreSector = mini.consultarSector(i);
					int vendidos = funcion.consultarEntradasVendidasSector(nombreSector);
					resultado.append(nombreSector)
							.append(": ")
							.append(vendidos)
							.append("/")
							.append(mini.capacidadSector(i));
				}
				resultado.append("\n");
			}
		}

		return resultado.toString();
	}

	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		// TODO Auto-generated method stub
		return 0;
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
		if(sede.getClass() == Teatro.class) {
			Teatro teatro = (Teatro) sede;
			int tamanoFila = teatro.obtenerAsientosPorFila();
			if (numAsiento % tamanoFila == 0) {
				fila = (numAsiento / tamanoFila) -1;
				asiento = tamanoFila;
			}else {
				fila = numAsiento / tamanoFila;
				asiento = numAsiento % tamanoFila;
			}
			
		}
		if(sede.getClass() == Miniestadio.class) {
			Miniestadio teatro = (Miniestadio) sede;
			int tamanoFila = teatro.obtenerAsientosPorFila();
			if (numAsiento % tamanoFila == 0) {
				fila = (numAsiento / tamanoFila) -1;
				asiento = tamanoFila;
			}else {
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

	private void usuarioNoRegistrado(String email) throws RuntimeException {
		if (!this.usuarios.containsKey(email)) {
			throw new RuntimeException("NO existe un usuario registrado con ese mail.");
		}
	}

	private void iniciarSesionUsuario(String email, String contrasenia) throws RuntimeException {
		usuarioNoRegistrado(email);
		if (!this.usuarios.get(email).iniciarSesion(contrasenia)) {
			throw new RuntimeException("¡Contraseña INVALIDA!");
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
}