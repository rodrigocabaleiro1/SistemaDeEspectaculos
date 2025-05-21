package ar.edu.ungs.prog2.ticketek;

import java.awt.Point;
import java.io.*;
import java.util.*;

public class Entrada implements IEntrada {

	private static int codigoGlobal;
	private String codigo;
	private Espectaculo espectaculo;
	private String fecha;
	private Sede ubicacion;
	private String sector;
	private Point filaAsiento;

	public Entrada(Espectaculo espectaculo, String fecha, Sede ubicacion) {
		this.espectaculo = espectaculo;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.sector = null;
		this.codigo = String.valueOf(++codigoGlobal);
	}

	public Entrada(Espectaculo espectaculo, String fecha, Sede ubicacion, String sector, Point filaAsiento) {
		this.espectaculo = espectaculo;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.sector = sector;
		this.codigo = String.valueOf(++codigoGlobal);
		this.filaAsiento = filaAsiento;
	}

	public void modificarSede(Sede sedeNueva) {
		if (sedeNueva== null) {
			throw new RuntimeException("NO ha ingresado la sede nueva.");
		}
		this.ubicacion = sedeNueva;
	}

	@Override
	public double precio() { // debe resolverse en O(1)
		double precio = 0;
		int sectorIndice;
		precio += this.espectaculo.consultarPrecioBase();
		if (this.ubicacion.getClass() == Estadio.class) {
			return precio;
		} else {
			double consumisionLibre = 0;
			int incrementoSector = 0;

			if (this.ubicacion.getClass() == Teatro.class) {
				Teatro ubicacionCasteada = (Teatro) this.ubicacion;
				sectorIndice = obetenerIndiceSector(ubicacionCasteada);
				incrementoSector = ubicacionCasteada.obtenerIncrementoSector(sectorIndice);

			} else if (this.ubicacion.getClass() == Miniestadio.class) {
				Miniestadio ubicacionCasteada = (Miniestadio) this.ubicacion;
				sectorIndice = obetenerIndiceSector(ubicacionCasteada);
				incrementoSector = ubicacionCasteada.obtenerIncrementoSector(sectorIndice);
				consumisionLibre = ubicacionCasteada.obtenerPrecioConsumision();
			}
			precio += (incrementoSector / 100) * precio + consumisionLibre;

		}
		return precio;
	}

	@Override
	public String ubicacion() {
		if (this.ubicacion != null) {
			return this.ubicacion.getNombre();
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(codigo);
		sb.append("} - {").append(espectaculo != null ? espectaculo.consultarNombre() : "N/A");
		sb.append("} - {").append(fecha);
		sb.append("} - {").append(ubicacion != null ? ubicacion.getNombre() : "N/A");
		sb.append("} - {").append(sector != null ? sector : "General");
		sb.append("}");
		return sb.toString();
	}

	// ----------------------------------------------------------------
	// SOBRECARGA en los siguientes dos metodos
	private int obetenerIndiceSector(Teatro teatro) {
		int indice = 40;
		for (int x = 0; x < 4; x++) {
			if (teatro.consultarSector(x) == this.sector) {
				indice = x;
			}
		}

		return indice;
	}

	private int obetenerIndiceSector(Miniestadio miniestadio) {
		int indice = 40;
		for (int x = 0; x < 4; x++) {
			if (miniestadio.consultarSector(x) == this.sector) {
				indice = x;
			}
		}

		return indice;
	}

	// ------------------------------------------------------------------------
	public String consultarCodigo() {

		return this.codigo;
	}

	public Espectaculo getEspectaculo() {
		return this.espectaculo;
	}

}