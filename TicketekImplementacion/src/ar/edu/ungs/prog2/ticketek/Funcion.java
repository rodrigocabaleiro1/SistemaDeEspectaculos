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

    public  Funcion(String sede, String fecha) {
    	this.sede = sede;
    	this.fecha = fecha;
    }

	public String consultarSede() {
		return this.sede;
	}

}