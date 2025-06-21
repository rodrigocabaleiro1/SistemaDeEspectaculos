package ar.edu.ungs.prog2.ticketek;

public abstract class Sede {

    protected String nombre;
    protected int capacidad;
    protected String direccion;

    public Sede() {
    }

    public Sede(String nombre, int capacidad, String direccion) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sede no puede ser nulo ni vacío");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección de la sede no puede ser nula ni vacía");
        }
        if (capacidad < 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor o igual a cero");
        }
    
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.direccion = direccion;
    }
    

    public abstract Double calcularCostoEntrada(double precioBase, String sector);

    public abstract int consultarCapacidad();

    public String getNombre() {
        return this.nombre;
    }

    public boolean esEstadio() {
        return this instanceof Estadio;
    }

	public abstract String consultarNombre();
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Sede) {
			Sede otro = (Sede) obj;
			return this.nombre == otro.consultarNombre();
		
		} else {
			return false;
		}
	}
}
