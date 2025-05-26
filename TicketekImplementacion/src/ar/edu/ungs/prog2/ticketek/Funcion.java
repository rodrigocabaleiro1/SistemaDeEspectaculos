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

    public Funcion(Sede sede, String fecha) {
        this.sede = sede;
        this.fecha = fecha;
        this.entradasVendidasPorSector = new HashMap<>();
        this.entradasVendidas = 0;
        this.asientosOcupados = new HashMap<>();
    }

    public String consultarSede() {
        return this.sede.getNombre();
    }

    public int consultarEntradasVendidas() {
        return this.entradasVendidas;
    }

    public void venderEntrada(String sector, int asiento) {
        this.entradasVendidas++;
      
        	if(this.entradasVendidasPorSector.containsKey(sector)) {
            this.entradasVendidasPorSector.put(sector, entradasVendidasPorSector.get(sector) + 1);
            }else {
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
        // Verifica si ese asiento ya está ocupado
    	if (this.sede.getClass() == Teatro.class) {
    		Teatro teatro = (Teatro) sede;
    		return !asientosOcupados.containsKey(teatro.obtenerAsientoAbsoluto(sector, asiento));        }
    	if (this.sede.getClass() == Miniestadio.class) {
    		Miniestadio miniestadio = (Miniestadio) sede;
    		return !asientosOcupados.containsKey(miniestadio.obtenerAsientoAbsoluto(sector, asiento));        }
        return false;
    }

    public void ocuparAsiento(String sector, int asiento) {
    	if (this.sede.getClass() == Teatro.class) {
    		Teatro teatro = (Teatro) sede;
    		asientosOcupados.put(teatro.obtenerAsientoAbsoluto(sector, asiento), sector);
        }
    	if (this.sede.getClass() == Miniestadio.class) {
    		Miniestadio miniestadio = (Miniestadio) sede;
    		asientosOcupados.put(miniestadio.obtenerAsientoAbsoluto(sector, asiento), sector);
        }
    }

    public void desocuparAsiento(int asiento) {
        asientosOcupados.remove(asiento);
    }

    public boolean lugarLibre() {
        return false;
    }
    @Override
    public String toString() {
    	StringBuilder resultado = new StringBuilder();
    	resultado.append(" - (").append(this.fecha).append(") ").append(this.sede.consultarNombre()).append(" - ");
    	if(this.sede.getClass() == Estadio.class) {
    		resultado.append(this.entradasVendidas).append("/").append(this.sede.capacidad);
    	}
    	if(this.sede.getClass() == Teatro.class) {
    		Teatro teatro = (Teatro) this.sede;
    		for (int x=0; x<teatro.cantidadSectores(); x++) {
    		resultado.append(teatro.consultarSector(x)).append(": ").append(this.entradasVendidasPorSector.getOrDefault(teatro.consultarSector(x),0))
    		.append("/").append(teatro.capacidadSector(x));
    		
    		if(x<teatro.cantidadSectores()-1) {
    			resultado.append(" | ");
    		}
    		}
    	}
    	if(this.sede.getClass() == Miniestadio.class) {
    		Miniestadio miniestadio = (Miniestadio) this.sede;
    		for (int x=0; x<miniestadio.cantidadSectores(); x++) {
    		resultado.append(miniestadio.consultarSector(x)).append(": ").append(this.entradasVendidasPorSector.getOrDefault(miniestadio.consultarSector(x),0))
    		.append("/").append(miniestadio.capacidadSector(x));
    		
    		if(x<miniestadio.cantidadSectores()-1) {
    			resultado.append(" | ");
    		}
    		}
    	}
		return resultado.toString();
    	
    }

}
