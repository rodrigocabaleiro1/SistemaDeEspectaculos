package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Funcion {

    private String sede;
    private String fecha;
    //private Date fecha;
    private int entradasVendidas; //Es un contador

    public  Funcion(String sede, String fecha) {
    	this.sede = sede;
    	this.fecha = fecha;
    }

	public String consultarSede() {
		return this.sede;
	}
	
	public int consultarEntradasVendidas(){
		return this.entradasVendidas;
	}
	public void venderEntrada() {
		entradasVendidas ++;
	}

}