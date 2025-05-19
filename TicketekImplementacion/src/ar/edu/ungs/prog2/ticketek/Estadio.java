package ar.edu.ungs.prog2.ticketek;

public class Estadio extends Sede {

    private String sector;

    public Estadio(String nombre, String direccion, int capacidad) {
        super(nombre, capacidad, direccion);
        this.sector = "campo"; // valor por defecto
    }

    @Override
    public Double calcularCostoEntrada() {
        double costoBase = 1000.0;

        if ("campo".equalsIgnoreCase(sector)) {
            return costoBase * 1.2;
        } else {
            return costoBase;
        }
    }

    @Override
    public int consultarCapacidad() {
        return capacidad;
    }

    // Getter y setter por si querés cambiar o consultar el sector
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
