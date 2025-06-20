package ar.edu.ungs.prog2.ticketek;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class Miniestadio extends Sede {

	private double precioConsumicion;
	private int asientosPorFila;
	private String[] sectores;
	private int[] porcentajeAdicional;
	private int[] capacidadSector;
	private int cantidadPuestos;
	private int[] asientoInicial; // asiento inicial absoluto de cada sector

	public Miniestadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		super(nombre, capacidadMaxima, direccion);

		if (precioConsumicion < 0) {
			throw new IllegalArgumentException("El precio de consumición debe ser mayor o igual a cero");
		}
		if (cantidadPuestos < 0) {
			throw new IllegalArgumentException("La cantidad de puestos debe ser mayor o igual a cero");
		}
		if (asientosPorFila <= 0) {
			throw new IllegalArgumentException("Los asientos por fila deben ser mayor a cero");
		}
		if (sectores == null || capacidad == null || porcentajeAdicional == null) {
			throw new IllegalArgumentException("Los arrays no pueden ser nulos");
		}
		if (sectores.length != capacidad.length || sectores.length != porcentajeAdicional.length) {
			throw new IllegalArgumentException("Los arrays deben tener la misma longitud");
		}

		this.precioConsumicion = precioConsumicion;
		this.cantidadPuestos = cantidadPuestos;
		this.asientosPorFila = asientosPorFila;
		this.sectores = sectores;
		this.capacidadSector = capacidad;
		this.porcentajeAdicional = porcentajeAdicional;

		this.asientoInicial = new int[capacidad.length];
		this.asientoInicial[0] = 0;
		for (int i = 1; i < capacidad.length; i++) {
			this.asientoInicial[i] = this.asientoInicial[i - 1] + capacidad[i - 1];
		}
	}

	public String[] getSectores() {
		return this.sectores;
	}

	public Double calcularCostoEntrada(int sector) {
		return null;
	}

	@Override
	public Double calcularCostoEntrada(double precioBase, String sector) {
		int porcentaje = obtenerIncrementoSector(sector);
		double precioConsumision = obtenerPrecioConsumision();
		return precioBase * (1 + (porcentaje / 100.0)) + precioConsumision;
	}

	public int obtenerIncrementoSector(int indice) {
		if (indice < 0 || indice > 3) {
			throw new RuntimeException("El indice ingresado es invalido");
		}
		return this.porcentajeAdicional[indice];
	}

	public double obtenerPrecioConsumision() {
		// TODO Auto-generated method stub
		return this.precioConsumicion;
	}

	public String consultarSector(int x) {
		if (x < 0 || x > 3) {
			throw new RuntimeException("El indice ingresado es invalido");
		}
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
		if (i < 0 || i > 3) {
			throw new RuntimeException("El indice ingresado es invalido");
		}
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
		return this.asientosPorFila;
	}

	@Override
	public String consultarNombre() {
		return super.nombre;
	}

	public int obtenerAsientoAbsoluto(String sector, int asiento) {
		int asientoAb = 0;
		asientoAb = this.asientoInicial[obtenerIndiceSector(sector)] + asiento;
		return asientoAb;
	}

	private int obtenerIndiceSector(String sector) {
		for (int x = 0; x < 4; x++) {
			if (this.sectores[x] == sector) {
				return x;
			}
		}
		throw new RuntimeException("No se ha encontrado el sector");
	}

	@Override
	public String toString() {
		StringBuilder resultado = new StringBuilder();
		resultado.append(" - ").append(super.nombre).append(" - ").append(super.direccion).append(" - ");
		for (int x = 0; x < cantidadSectores(); x++) {
			resultado.append(consultarSector(x)).append(": ").append(capacidadSector(x));

			if (x < cantidadSectores() - 1) {
				resultado.append(" | ");
			}
		}
		resultado.append("\n");
		return resultado.toString();

	}

}