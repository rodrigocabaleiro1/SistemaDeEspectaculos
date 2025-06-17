package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;

public class Espectaculo {

	// private String codigo;
	private String nombre;
	private HashMap<String, Funcion> funciones;
	private HashMap<String, Double> precioBase;
	private HashMap<String, Double> recaudacionPorSede; // se nos solicita en O(1)
	private Double recaudacion; // se nos solicita en O(1)

	// METODOS ----------------------------------------------

	public Espectaculo(String nombre) {
		this.nombre = nombre;
		this.funciones = new HashMap<>();
		
		this.recaudacionPorSede = new HashMap<>();
		this.recaudacion = 0.0;
		this.precioBase = new HashMap<>();

	}

	public LinkedList<String> consultarFunciones() {
		return new LinkedList<>(funciones.keySet());
	}

	public Double consultarPrecioBase(String fechaFuncion) {
		return this.precioBase.get(fechaFuncion);
	}

	public boolean fechaLibre(String fecha) {
		return !funciones.containsKey(fecha);
	}

	public void agregarFuncion(Funcion nuevaFuncion, String fecha, double precioBase) {
		funciones.put(fecha, nuevaFuncion);
		this.precioBase.put(fecha,precioBase); // preguntar si el precio base esta en funcion o espectaculo
		this.recaudacionPorSede.put(nuevaFuncion.consultarSede(), 0.0);
	}

	public String consultarSede(String fecha) {
		String sede = this.funciones.get(fecha).consultarSede();

		return sede;
	}

	public int consultarVentasFuncion(String fecha) {
		existeFuncion(fecha); // Si no existe lanza Excepcion
		return this.funciones.get(fecha).consultarEntradasVendidas();
	}

	public Funcion consultarFuncion(String fecha) {
		existeFuncion(fecha);
		return this.funciones.get(fecha);

	}

	public void existeFuncion(String fecha) {
		if (!funciones.containsKey(fecha)) {
			throw new RuntimeException("No hay una funcion en la fecha ingresada.");
		}
	}

	public String consultarNombre() {
		return this.nombre;
	}

	public void venderEntrada(Entrada entrada) {
		existeFuncion(entrada.consultarFecha());
		Funcion funcion = this.funciones.get(entrada.consultarFecha());
		Sede sedeEntrada = entrada.consultarSede();
		if(sedeEntrada instanceof Estadio) {funcion.venderEntrada();}
		if(sedeEntrada instanceof Teatro || sedeEntrada instanceof Miniestadio) {
			
			funcion.venderEntrada(entrada.consultarSector(), entrada.getAsiento());
			}

		double precioVenta = entrada.precio(); // O(1)
		String Sede = funcion.consultarSede();

		this.recaudacion += precioVenta;
		this.recaudacionPorSede.put(Sede,
				this.recaudacionPorSede.getOrDefault(Sede, 0.0) + precioVenta);
	}

	public void anularEntrada(Entrada entrada) {
		existeFuncion(entrada.consultarFecha());	//2op		
		//consultarFecha devuelve una variable y existeFuncion busca si el Hashmap de Funciones contiene elemento
		Funcion funcion = this.funciones.get(entrada.consultarFecha());//2
		funcion.anularEntrada(); //(AF) AF = operaciones de anularEntrada de Funcion

		double precioEntradaAnulada = entrada.precio(); // O(1) 
														// 1 + (P) P = precio() de Entrada

		String Sede = funcion.consultarSede();	// 1
		
		this.recaudacion -= precioEntradaAnulada;	// 2
		this.recaudacionPorSede.put(Sede,
				this.recaudacionPorSede.getOrDefault(Sede, 0.0) - precioEntradaAnulada); // 4
	}																//TOTAL 12 + (AF) + (P)

	// Getter para la recaudación total del espectáculo
	public Double recaudacionTotal() {
		return this.recaudacion;
	}

	// Getter para el mapa de recaudación por sede
	public Double recaudacionPorSede(String sede) {
		existeSede(sede);
		return this.recaudacionPorSede.getOrDefault(sede, 0.0);
	}

	private void existeSede(String sede) {
		if(!this.recaudacionPorSede.containsKey(sede)) {
			throw new RuntimeException("La sede ingresada no existe: " + sede);
		}
		
	}
	@Override 
	public String toString() {
		StringBuilder resultado = new StringBuilder();
		Iterator<String> iterador = consultarFunciones().iterator();
		
		resultado.append("- ").append(this.nombre).append("\n");
		resultado.append("- Recaudacion: - $").append(this.recaudacionTotal()).append("\n");
		resultado.append("- Funciones").append("\n");
		
        while (iterador.hasNext()) {
            Funcion funcion = consultarFuncion(iterador.next());
            resultado.append(funcion.toString()).append("\n");
        }
        
		return resultado.toString();
	} 

	/*
	 * public void mapNull(HashMap <String, Funcion> map) throws RuntimeException{
	 * if(map == null) {
	 * throw new RuntimeException("No hay funciones");
	 * }
	 * }
	 */

}