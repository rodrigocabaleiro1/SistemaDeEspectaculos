package ar.edu.ungs.prog2.ticketek;

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
		if (sedes.containsKey(nombre)) {
			throw new RuntimeException("Ya existe una sede con el nombre ingresado");
		}

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
		if (sedes.containsKey(nombre)) {
			throw new RuntimeException("Ya existe una sede con el nombre ingresado");
		}

		Sede nuevaSede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad,
				porcentajeAdicional);
		sedes.put(nombre, nuevaSede);

	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {

		if (sedes.containsKey(nombre)) {
			throw new RuntimeException("Ya existe una sede con el nombre ingresado");
		}

		Sede nuevaSede = new Miniestadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos,
				precioConsumicion, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, nuevaSede);

	}

	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		if (usuarios.containsKey(email)) {
			throw new RuntimeException("Ya existe un usuario registrado con ese mail.");
		}

		Usuario usuario = new Usuario(email, nombre, apellido, contrasenia);
		usuarios.put(email, usuario);

	}

	@Override
	public void registrarEspectaculo(String nombre) {
		if (espectaculos.containsKey(nombre)) {
			throw new RuntimeException("Ya existe un Espectaculo con el nombre ingresado");
		}

		Espectaculo espectaculo = new Espectaculo(nombre);
		espectaculos.put(nombre, espectaculo);

	}

	@Override
	public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		if (espectaculos.containsKey(nombreEspectaculo)) {
			throw new RuntimeException("¡El Espectaculo ingresado no esta registrado!");
		}
		if (sedes.containsKey(sede)) {
			throw new RuntimeException("¡La Sede ingresada no esta registrada!");
		}
		if (!espectaculos.get(nombreEspectaculo).fechaLibre(fecha)) {
			throw new RuntimeException("¡Ya se realiza una funcion en la fecha ingresada!");
		}

		Funcion nuevaFuncion = new Funcion(sede, fecha);

		espectaculos.get(nombreEspectaculo).agregarFuncion(nuevaFuncion, fecha, precioBase);
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			int cantidadEntradas) {
		if (!this.usuarios.containsKey(email)) {
			throw new RuntimeException("NO existe un usuario registrado con ese mail.");
		}
		if (!this.espectaculos.containsKey(nombreEspectaculo)) {
			throw new RuntimeException("NO existe el espectaculo ingresado");
		}
		if (this.espectaculos.get(nombreEspectaculo).fechaLibre(fecha)) {
			throw new RuntimeException("NO hay una funcion en la fecha ingresada");
		}
		if (this.usuarios.get(email).iniciarSesion(contrasenia)) {
			throw new RuntimeException("¡Contraseña INVALIDA!");
		}

		List<IEntrada> entradas = new LinkedList<IEntrada>();
		Espectaculo espectaculo = this.espectaculos.get(nombreEspectaculo);
		Usuario usuario = usuarios.get(email);
		if (plazasDisponibles(espectaculo, fecha) - cantidadEntradas >= 0) {
			throw new RuntimeException("¡No hay suficientes Plazas Disponibles en esta función!");
		}

		for (int i = 0; i < cantidadEntradas; i++) {

			Sede sede = this.sedes.get(espectaculo.consultarSede(fecha));
			Entrada entrada = new Entrada(espectaculo, fecha, sede);
			entradas.add(entrada);
			usuario.comprarEntrada(entrada.consultarCodigo());
		}
		return entradas;
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		// TODO Auto-generated method stub
		List<IEntrada> entradas = new LinkedList<IEntrada>();
		return entradas;
	}

	@Override
	public String listarFunciones(String nombreEspectaculo) {
		if (!espectaculos.containsKey(nombreEspectaculo)) {
			throw new RuntimeException("El espectáculo no está registrado.");
		}
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		// Suponiendo que Espectaculo tiene un método consultarFunciones() que devuelve
		// LinkedList<String> con las fechas
		LinkedList<String> fechas = espectaculo.consultarFunciones();
		StringBuilder sb = new StringBuilder();

		for (String fecha : fechas) {
			Funcion funcion = espectaculo.consultarFuncion(fecha);
			String nombreSede = funcion.consultarSede();
			Sede sede = sedes.get(nombreSede);

			sb.append("- (").append(fecha).append(") ").append(nombreSede).append(" - ");

			if (sede instanceof Estadio) {
				// Sede no numerada
				int vendidas = funcion.consultarEntradasVendidas();
				int capacidad = sede.consultarCapacidad();
				sb.append(vendidas).append("/").append(capacidad);
			} else {
				// Sede numerada: mostrar por sector
				String[] sectores = null;
				if (sede instanceof Teatro) {
					sectores = ((Teatro) sede).getSectores();
				} else if (sede instanceof Miniestadio) {
					sectores = ((Miniestadio) sede).getSectores();
				}
				for (int i = 0; i < sectores.length; i++) {
					String sector = sectores[i];
					int vendidas = funcion.consultarEntradasVendidas();
					int capacidad = sede.consultarCapacidad();
					sb.append(sector).append(": ").append(vendidas).append("/").append(capacidad);
					if (i < sectores.length - 1) {
						sb.append(" | ");
					}
				}
			}
			sb.append("\n");
		}
		return sb.toString();
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

	private int plazasDisponibles(Espectaculo espectaculo, String fecha) {
		String sede = espectaculo.consultarSede(fecha);
		int capacidad = this.sedes.get(sede).consultarCapacidad();
		int ocupados = espectaculo.consultarVentasFuncion(fecha);
		return capacidad - ocupados;
	}
}