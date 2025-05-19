package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Teatro extends Sede {

       public Teatro() {
    }
    private int asientosPorFila;
    private String[] sectores;
    private int[] porcentajeAdicional;
    private int[] capacidadSector;

    public Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
    		String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
    	super.nombre = nombre;
    	super.direccion = direccion;
    	super.capacidad = capacidadMaxima;
    	this.asientosPorFila = asientosPorFila;
    	this.sectores = sectores;
    	this.capacidadSector = capacidad;
    	this.porcentajeAdicional = porcentajeAdicional;
    }

    public Double calcularCostoEntrada(int sector) {
        return null;
    }

	@Override
	public Double calcularCostoEntrada() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int obtenerIncrementoSector(int indice) {
		return this.porcentajeAdicional[indice];
	}

	public String consultarSector(int x) {
		return sectores[x];
	}

	@Override
	public int consultarCapacidad() {
		// TODO Auto-generated method stub
		return capacidadSector[0]+capacidadSector[1]+capacidadSector[2]+capacidadSector[4];
	}

	

	

}