package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;
/**
 * 
 */
public class Ticketek implements ITicketek{


    private HashMap <String, Usuario> usuarios;
    private HashMap <String, Espectaculo> espectaculos;
    private HashMap <String, Sede> sedes;
    private HashMap <String, Entrada> entradasVendidas;

    public Ticketek() {
    }

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		if (nombre == null || nombre.isEmpty()) {
	        throw new IllegalArgumentException("El nombre no puede estar vacío");
	    }
		if(direccion == null || direccion.isEmpty()) {
			throw new IllegalArgumentException("La dirección no puede estar vacía");
		}
		if(capacidadMaxima <= 0) {
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
		if(direccion == null || direccion.isEmpty()) {
			throw new IllegalArgumentException("La dirección no puede estar vacía");
		}
		if(capacidadMaxima <= 0) {
			throw new RuntimeException("la capacidad maxima ingresada es invalida");
		}
		if (sedes.containsKey(nombre)) {
			throw new RuntimeException("Ya existe una sede con el nombre ingresado");
		}
		
		Sede nuevaSede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, nuevaSede);

		
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		
		if (sedes.containsKey(nombre)) {
			throw new RuntimeException("Ya existe una sede con el nombre ingresado");
		}
		
		Sede nuevaSede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, nuevaSede);
		
	}

	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarEspectaculo(String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			int cantidadEntradas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listarFunciones(String nombreEspectaculo) {
		// TODO Auto-generated method stub
		return null;
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

    
}