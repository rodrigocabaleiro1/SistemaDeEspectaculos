package ar.edu.ungs.prog2.ticketek;

import java.awt.Point;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Funcion {

    private Sede sede;
    private String fecha;
    private int entradasVendidas;
    private HashMap<String, Integer> entradasVendidasPorSector;
    private HashMap<Integer, String> asientosOcupados; //nro asiento / sector

    public Funcion(Sede sede, String fecha) {
        this.sede = sede;
        this.fecha = fecha;
        this.entradasVendidasPorSector = new HashMap<>();
        this.entradasVendidas =0;
        this.asientosOcupados = new HashMap<>();
    }

    public Sede getSede() {
        return this.sede;
    }

    public String consultarSede() {
        return sede.getNombre(); // supondremos que Sede tiene getNombre()
    }

    public int consultarEntradasVendidas() {
        return this.entradasVendidas;
    }

    public void venderEntrada(String sector, int asiento) {
        this.entradasVendidas++;
        if (sector != null) {
        	this.entradasVendidasPorSector.put(sector, entradasVendidasPorSector.getOrDefault(sector, 0) + 1);
        }
        ocuparAsiento(sector, asiento);
    }

    public int consultarEntradasVendidasSector(String sector) {
        return entradasVendidasPorSector.getOrDefault(sector, 0);
    }

	public void venderEntrada() {
		this.entradasVendidas++;	
	}
	public void ocuparAsiento(String sector, int asiento) {
		int asientoInicialSector = 
	}
	public void desocuparAsiento() {}
	public boolean lugarLibre() {
		return false;
	}
	
}
