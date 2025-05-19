package ar.edu.ungs.prog2.ticketek;

public abstract class Sede {

    protected String nombre;
    protected int capacidad;
    protected String direccion;
    
    public Sede() {}

    public Sede(String nombre, int capacidad, String direccion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.direccion = direccion;
    }

    public abstract Double calcularCostoEntrada();
    public abstract int consultarCapacidad();
}
