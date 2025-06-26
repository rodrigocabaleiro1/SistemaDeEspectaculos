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
    private HashMap<Integer, String> asientosOcupados; // nro asiento / sector
    private Espectaculo espectaculo;

    public Funcion(Sede sede, String fecha) {
        if (sede == null) {
            throw new IllegalArgumentException("La sede no puede ser nula");
        }
        if (fecha == null || fecha.isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede ser nula ni vacía");
        }

        this.sede = sede;
        this.fecha = fecha;
        this.entradasVendidas = 0;
        this.entradasVendidasPorSector = new HashMap<>();
        this.asientosOcupados = new HashMap<>();
        this.espectaculo = espectaculo;
    }

    public String consultarSede() {
        return this.sede.getNombre();
    }

    public int consultarEntradasVendidas() {
        return this.entradasVendidas;
    }

    public void venderEntrada(String sector, int asiento) {
        this.entradasVendidas++;

        if (this.entradasVendidasPorSector.containsKey(sector)) {
            this.entradasVendidasPorSector.put(sector, entradasVendidasPorSector.get(sector) + 1);
        } else {
            this.entradasVendidasPorSector.put(sector, 1);
        }

        ocuparAsiento(sector, asiento);
    }

    public int consultarEntradasVendidasSector(String sector) {
        return entradasVendidasPorSector.getOrDefault(sector, 0);
    }

    public void venderEntrada() {
        this.entradasVendidas++;
    }

    public void anularEntrada() {
        this.entradasVendidas--;
    }

    public boolean estaDisponible(String sector, int asiento) {
        int asientoAbsoluto = this.sede.obtenerAsientoAbsoluto(sector, asiento);
        return !asientosOcupados.containsKey(asientoAbsoluto);
    }

    public void ocuparAsiento(String sector, int asiento) {
        int absoluto = this.sede.obtenerAsientoAbsoluto(sector, asiento);
        this.asientosOcupados.put(absoluto, sector);
    }

    public void desocuparAsiento(int asiento) {
        asientosOcupados.remove(asiento);
    }

    public boolean lugarLibre() {
        return false;
    }

    public void venderEntradaConAsiento(String sector, int numeroAsiento) {
        if (asientosOcupados.containsKey(numeroAsiento)) {
            throw new RuntimeException("El asiento ya está ocupado");
        }
        asientosOcupados.put(numeroAsiento, sector);
        entradasVendidas++;
    }

    @Override
    public String toString() {
        return " - (" + this.fecha + ") " + this.sede.consultarNombre() + " - " + this.sede.resumenFuncion(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Funcion otra = (Funcion) obj;
        return Objects.equals(this.fecha, otra.fecha) &&
                Objects.equals(this.sede, otra.sede);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fecha, this.sede);
    }

    // no implemento el equals de Funcion porque es dificil saber si dos funciones
    // son
    // la misma con los datos que disponemos (pueden haber dos funciones iguales de
    // distintos espectaculos)
    // por ende son el mismo objeto si comparten espacio en memoria, ya ponderado
    // por equals de Object
}
