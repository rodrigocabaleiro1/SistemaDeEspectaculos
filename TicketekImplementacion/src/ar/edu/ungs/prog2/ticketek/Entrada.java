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
	private Point filaAsiento; // x:fila/ y:asiento
	private String comprador;
	private int numeroAsiento; // absoluto de todo el teatro/miniestadio

	public Entrada(Espectaculo espectaculo, String fecha, Sede ubicacion, String comprador) {
		validarDatosConstructor(comprador, espectaculo, fecha, ubicacion);
		this.espectaculo = espectaculo;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.sector = null;
		this.codigo = String.valueOf(++codigoGlobal);
		this.comprador = comprador;
	}

	public Entrada(Espectaculo espectaculo, String fecha, Sede ubicacion, String sector, Point filaAsiento,
			String comprador) {
		validarDatosConstructor(comprador, espectaculo, fecha, ubicacion, sector, filaAsiento);
		this.espectaculo = espectaculo;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.sector = sector;
		this.codigo = String.valueOf(++codigoGlobal);
		this.filaAsiento = filaAsiento;
		this.comprador = comprador;

	}

	public void modificarSede(Sede sedeNueva) {
		if (sedeNueva == null) {
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
		if (ubicacionEstadio()) {
			return this.ubicacion.getNombre();
		}
		StringBuilder ubicacionEntrada = new StringBuilder();
		ubicacionEntrada.append("- ").append(this.ubicacion.getNombre()).append(" f:")
				.append(this.filaAsiento.x).append(" a:").append(this.filaAsiento.y);
		return ubicacionEntrada.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("- ").append(codigo);
		sb.append(" - ").append(espectaculo != null ? espectaculo.consultarNombre() : "N/A");
		sb.append(" - ").append(fecha);
		sb.append(" - ").append(ubicacion != null ? ubicacion.getNombre() : "N/A");
		sb.append(ubicacion());
		sb.append("");
		return sb.toString();
	}

	// ----------------------------------------------------------------
	// SOBRECARGA en los siguientes metodos
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

	private void validarDatosConstructor(String comprador, Espectaculo espectaculo, String fecha, Sede ubicacion) {
		if (espectaculo == null || fecha == null || ubicacion == null || comprador == null) {
			throw new RuntimeException(
					"No se puede crear una entrada: Todos los datos de entrada deben estar definidos.");
		}

	}

	private void validarDatosConstructor(String comprador, Espectaculo espectaculo, String fecha, Sede ubicacion,
			String sector, Point filaAsiento) {
		if (espectaculo == null || fecha == null || ubicacion == null || sector == null || filaAsiento == null
				|| comprador == null) {
			throw new RuntimeException(
					"No se puede crear una entrada: Todos los datos de entrada deben estar definidos.");
		}

	}

	// ------------------------------------------------------------------------
	public String consultarCodigo() {

		return this.codigo;
	}

	public Espectaculo getEspectaculo() {
		return this.espectaculo;
	}

	private boolean ubicacionEstadio() { // Comprueba si la sede es estadio o no
		return this.ubicacion instanceof Estadio;
	}

	public String consultarFecha() {
		// TODO Auto-generated method stub
		return this.fecha;
	}

	public String consultarComprador() {

		return this.comprador;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public void setAsiento(int asiento) {
		this.numeroAsiento = asiento;
	}

	public int getAsiento() {
		return this.numeroAsiento;
	}

}