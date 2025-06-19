package ar.edu.ungs.prog2.ticketek;

public class Estadio extends Sede {

    private String sector;

    public Estadio(String nombre, String direccion, int capacidad) {
        super(nombre, capacidad, direccion);
        this.sector = "campo"; // valor por defecto
    }

    @Override
    public Double calcularCostoEntrada(double costoBase, String sector) {
        return costoBase;
    }

    @Override
    public int consultarCapacidad() {
        return capacidad;
    }
    
    @Override	
	public String toString() {
		StringBuilder resultado = new StringBuilder();
    	resultado.append(" - ").append(super.nombre).append(" - ").append(super.direccion).append(" - ");
    	resultado.append(this.sector).append(": ").append(super.capacidad);
    	resultado.append("\n");
		return resultado.toString();
    	
	}
    


    // métodos adicionales para manejar el sector
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
    public String consultarNombre() {
		// TODO Auto-generated method stub
		return super.nombre;
	}
}
