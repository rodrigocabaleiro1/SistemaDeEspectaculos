package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;

public class Espectaculo {


    //private String codigo;
    private String nombre;
    private HashMap <String,Funcion> funciones;
    private double precioBase;
    private HashMap<String, Double> recaudacionPorSede; //se nos solicita en O(1)
    private Double recaudacion; //se nos solicita en O(1)

// METODOS ----------------------------------------------
    
    public Espectaculo(String nombre) {
    	this.nombre = nombre;
    	this.funciones = new HashMap<>();
    	this.precioBase = 0;
    	this.recaudacionPorSede = new HashMap<>();
    	this.recaudacion = 0.0;
    }
	public LinkedList<String> consultarFunciones() {
		return new LinkedList<>(funciones.keySet());
	}

    public Double consultarPrecioBase() {
        return precioBase;
    }
	public boolean fechaLibre(String fecha) {
		return !funciones.containsKey(fecha);
		}
	public void agregarFuncion(Funcion nuevaFuncion, String fecha, double precioBase) {
		funciones.put(fecha, nuevaFuncion);
		this.precioBase = precioBase; //preguntar si el precio base esta en funcion o espectaculo
	}
	public String consultarSede(String fecha) {
		String sede = this.funciones.get(fecha).consultarSede();
		
		return sede;
	}
	public int consultarVentasFuncion(String fecha) {
		existeFuncion(fecha); //Si no existe lanza Excepcion
		return this.funciones.get(fecha).consultarEntradasVendidas();
	}

	public Funcion consultarFuncion(String fecha) {
		existeFuncion(fecha);
        return this.funciones.get(fecha);    
}
	public void existeFuncion(String fecha) {
		if(!funciones.containsKey(fecha)) {
			throw new RuntimeException("No hay una funcion en la fecha ingresada.");
		}	
	}
	/*public void mapNull(HashMap <String, Funcion> map) throws RuntimeException{
		if(map == null) {
			throw new RuntimeException("No hay funciones");
		}
	}*/
	

}