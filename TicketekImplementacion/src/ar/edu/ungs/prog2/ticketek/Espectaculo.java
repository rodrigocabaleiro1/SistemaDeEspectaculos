package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;

public class Espectaculo {


    //private String codigo;
    private String nombre;
    private HashMap <String,Funcion> funciones;
    private double precioBase;
    private HashMap<String, Double> RecaudacionPorSede; //se nos solicita en O(1)
    private Double recaudacion; //se nos solicita en O(1)


    public Espectaculo(String nombre) {
    	this.nombre = nombre;
    }
    public LinkedList <String> consultarFunciones() {
        return null;
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
		
		return this.funciones.get(fecha).consultarEntradasVendidas();
	}

}