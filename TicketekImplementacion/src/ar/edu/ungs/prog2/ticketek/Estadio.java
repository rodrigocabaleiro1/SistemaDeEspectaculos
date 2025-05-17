package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;


public class Estadio extends Sede {
	private String sector;


    public  Estadio(String nombre, String direccion, int capacidad) {
    	this.sector = "campo";
    	super.nombre = nombre;
    	super.direccion = direccion;
    	super.capacidad = capacidad;
    }

@Override    
    public Double calcularCostoEntrada() {
        return null;
    }

}