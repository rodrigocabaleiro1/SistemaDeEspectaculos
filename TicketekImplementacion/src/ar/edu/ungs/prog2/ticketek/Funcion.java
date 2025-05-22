package ar.edu.ungs.prog2.ticketek;

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

    public Funcion(Sede sede, String fecha) {
        this.sede = sede;
        this.fecha = fecha;
        this.entradasVendidasPorSector = new HashMap<>();
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

    public void venderEntrada(String sector) {
        entradasVendidas++;
        if (sector != null) {
        	entradasVendidasPorSector.put(sector, entradasVendidasPorSector.getOrDefault(sector, 0) + 1);
        }
    }

    public int consultarEntradasVendidasSector(String sector) {
        return entradasVendidasPorSector.getOrDefault(sector, 0);
    }
}
