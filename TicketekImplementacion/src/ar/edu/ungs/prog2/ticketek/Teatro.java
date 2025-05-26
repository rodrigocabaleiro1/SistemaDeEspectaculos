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
	private int[] capacidadSector; // vamos a asumir que la capacidad del sector son cantidad de filas
	private int[] asientoInicial; // el nro de asiento en el que inicia cada sector]

	public Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		super.nombre = nombre;
		super.direccion = direccion;
		super.capacidad = capacidadMaxima;
		this.asientosPorFila = asientosPorFila;
		this.sectores = sectores;
		this.capacidadSector = capacidad;
		this.porcentajeAdicional = porcentajeAdicional;
		this.asientoInicial = new int[capacidad.length];
		this.asientoInicial[0] = 0;
		for (int x=1; x<sectores.length; x++) {
			this.asientoInicial[x] = capacidad[x-1];
		this.asientoInicial[x] += this.asientoInicial[x-1];}
	}

	public int obtenerIncrementoSector(String sector) {
		for (int i = 0; i < 4; i++) {
			if (this.sectores[i] == sector) {
				return obtenerIncrementoSector(i);
			}
		}
		throw new RuntimeException("El sector '" + sector + "' no existe en el teatro '" + this.nombre + "'.");

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

	@Override // puede dar ArrayIndexOutOfBoundsException si el array tiene menos de 5
				// sectores.
	public int consultarCapacidad() {
		int total = 0;
		for (int capacidad : capacidadSector) {
			total += capacidad;
		}
		return total;
	}

	public int cantidadSectores() {
		return sectores.length;
	}

	public int capacidadSector(int i) {
		return capacidadSector[i];
	}

	public int obtenerAsientosPorFila() {
		// TODO Auto-generated method stub
		return this.asientosPorFila;
	}

	@Override
	public String consultarNombre() {
		// TODO Auto-generated method stub
		return super.nombre;
	}
	public int obtenerAsientoAbsoluto(String sector, int asiento){
		int asientoAb = 0;
		asientoAb=this.asientoInicial[obtenerIndiceSector(sector)] + asiento;
		return asientoAb;
	}

	private int obtenerIndiceSector(String sector) {
		for(int x = 0; x < 4; x++) {
			if(this.sectores[x] == sector) {
				return x;
			}
		}
		throw new RuntimeException("No se ha encontrado el sector");
	}

}