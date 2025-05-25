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

	public Miniestadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
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

	public String[] getSectores() {
		return this.sectores;
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

	@Override // puede dar ArrayIndexOutOfBoundsException si el array tiene menos de 5 sectores.
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

	public int obtenerIncrementoSector(String sector) {
		for (int i = 0; i < 4; i++) {
			if (this.sectores[i] == sector) {
				return obtenerIncrementoSector(i);
			}
		}
		throw new RuntimeException("El sector '" + sector + "' no existe en el teatro '" + this.nombre + "'.");

	}
	public int obtenerAsientosPorFila() {
		// TODO Auto-generated method stub
		return this.asientosPorFila;
	}


}