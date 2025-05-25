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

}