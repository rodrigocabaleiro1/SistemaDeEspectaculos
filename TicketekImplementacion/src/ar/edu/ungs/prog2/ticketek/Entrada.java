package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;


public class Entrada implements IEntrada{

    private String codigo;
    private Espectaculo espectaculo;
    private String fecha;
    private Sede ubicacion;


    public Entrada( String nombreEspectaculo,  Date fecha,  String ubicacion) {
       
    }

    public void modificarSede(String sedeNueva) {
        
    }

	@Override
	public double precio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String ubicacion() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return null;
	}

}