package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;

public class Miniestadio extends Sede {

    private double precioConsumicion;
    private int asientosPorFila;
    private String[] sectores;
    private int[] porcentajeAdicional;
    private int[] capacidadSector;
    private int cantidadPuestos;

    

    public Miniestadio (String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional)
    {
    	super.nombre = nombre;
    	super.direccion = direccion;
    	super.capacidad = capacidadMaxima;
    	this.asientosPorFila = asientosPorFila;
    	this.sectores = sectores;
    	this.capacidadSector = capacidad;
    	this.porcentajeAdicional = porcentajeAdicional;
    	this.cantidadPuestos = cantidadPuestos;
    	this.precioConsumicion = precioConsumicion;

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

	public double obtenerPrecioConsumision() {
		// TODO Auto-generated method stub
		return this.precioConsumicion;
	}
	public String consultarSector(int x) {
		return sectores[x];
	}
	public int consultarCapacidad() {
		// TODO Auto-generated method stub
		return capacidadSector[0]+capacidadSector[1]+capacidadSector[2]+capacidadSector[4];
	}

}