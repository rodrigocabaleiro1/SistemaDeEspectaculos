package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;

public abstract class Sede {



    protected String nombre;
    protected int capacidad;
    protected String direccion;
    
    public Sede() {}

    public Sede(String nombre, int capacidad, String direccion) {
        
    }

    public abstract Double calcularCostoEntrada() ;

	public abstract int consultarCapacidad();
    

}