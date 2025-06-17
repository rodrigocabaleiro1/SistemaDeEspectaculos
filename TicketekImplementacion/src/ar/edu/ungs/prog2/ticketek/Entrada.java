package ar.edu.ungs.prog2.ticketek;

import java.awt.Point;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		this.sector = "CAMPO";
		this.codigo = String.valueOf(++codigoGlobal);
		this.comprador = comprador;
	}

	public Entrada(Espectaculo espectaculo, String fecha, Sede ubicacion, String sector, Point filaAsiento,
			String comprador, int asiento) {
		validarDatosConstructor(comprador, espectaculo, fecha, ubicacion, sector, filaAsiento);
		this.espectaculo = espectaculo;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.sector = sector;
		this.codigo = String.valueOf(++codigoGlobal);
		this.filaAsiento = filaAsiento;
		this.comprador = comprador;
		this.numeroAsiento = asiento;

	}

	public void modificarSede(Sede sedeNueva) {
		if (sedeNueva == null) {
			throw new RuntimeException("NO ha ingresado la sede nueva.");
		}
		this.ubicacion = sedeNueva;
	}

	@Override
	public double precio() { // debe resolverse en O(1)
		double precio = 0;																//1
		int sectorIndice;
		precio += this.espectaculo.consultarPrecioBase(this.fecha);						//4
		//consultarPrecioBase() {return this.precioBase.get(fechaFuncion);}

		if (this.ubicacion.getClass() == Estadio.class) { //No es peor caso
			return precio;								
		} else {
			double consumisionLibre = 0;
			int incrementoSector = 0;

			if (this.ubicacion.getClass() == Teatro.class) { // no es peor caso
				Teatro ubicacionCasteada = (Teatro) this.ubicacion;
				sectorIndice = obetenerIndiceSector(ubicacionCasteada);
				incrementoSector = ubicacionCasteada.obtenerIncrementoSector(sectorIndice);

			} else if (this.ubicacion.getClass() == Miniestadio.class) { //peor Caso  	3
				Miniestadio ubicacionCasteada = (Miniestadio) this.ubicacion; //2
				sectorIndice = obetenerIndiceSector(ubicacionCasteada); // (OIS) (esta tanto en Teatro como Miniestadio)
				incrementoSector = ubicacionCasteada.obtenerIncrementoSector(sectorIndice); //(INCS)
				consumisionLibre = ubicacionCasteada.obtenerPrecioConsumision(); // 1 (getter en escencia)
			}
			precio = precio + (precio/100)*incrementoSector + consumisionLibre;	// 5

		}
		return precio;							//1
	}																			//TOTAL 15 + (OIS) + (INCS)

	@Override
	public String ubicacion() {
		if (ubicacionEstadio()) {
			return " - " + this.sector;
		}
		StringBuilder ubicacionEntrada = new StringBuilder();
		ubicacionEntrada.append(" - ").append(this.ubicacion.getNombre()).append(" f:")
				.append(this.filaAsiento.x).append(" a:").append(this.filaAsiento.y);
		return ubicacionEntrada.toString();
	}

	@Override
	public String toString() {
		String p = "";
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
        Date fechaActual = new Date();
        try {
			if (formato.parse(this.fecha).before(fechaActual)) {
				p = " P";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("- ").append(this.codigo);
		sb.append(" - ").append(this.espectaculo.consultarNombre());
		sb.append(" - ").append(this.fecha).append(p);
		sb.append(" - ").append(this.ubicacion.getNombre());
		sb.append(ubicacion()).append("\n");
		return sb.toString();
	}

	// ----------------------------------------------------------------
	// SOBRECARGA en los siguientes metodos
	private int obetenerIndiceSector(Teatro teatro) {
		int indice;
		for (int x = 0; x < 4; x++) {
			if (teatro.consultarSector(x).equals(this.sector)) {
				indice = x;
				return indice;
			}
		}

		throw new RuntimeException("No se ha encontrado el sector");
	}

	private int obetenerIndiceSector(Miniestadio miniestadio) {
		int indice;
		for (int x = 0; x < 4; x++) {	//1  + 5 + 5
			if (miniestadio.consultarSector(x).equals(this.sector)) { //1*4
				indice = x;	//1*4
				return indice;//1
			}
		}//TOTAL 20

		throw new RuntimeException("No se ha encontrado el sector");	}

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
		return this.fecha;
	}

	public String consultarComprador() {

		return this.comprador;
	}

	

	

	public int getAsiento() {
		return this.numeroAsiento;
	}
	public Sede consultarSede() {
		return this.ubicacion;
	}
	
	public String consultarSector() {
		return this.sector;
	}
	
	//-------------------------------------------------------------
	//Metodos Faltantes en la primera Correccion
	//-------------------------------------------------------------
	
	public boolean equals(Entrada otra) {
		if(this.codigo == otra.consultarCodigo()) {return true;}
		return false;
	}

}