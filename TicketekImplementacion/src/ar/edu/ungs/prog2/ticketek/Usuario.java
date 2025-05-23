package ar.edu.ungs.prog2.ticketek;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Usuario {

    private String mail;
    private String contrasenia;
    private String nombre;
    private String apellido;
    private HashMap<String,String> entradasCompradas; //Hashmap (codigo, fecha)

    public Usuario(String mail, String nombre, String apellido, String contrasenia) {
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.entradasCompradas = new HashMap<>();
    }

    public LinkedList<String> listarEntradasFuturas() {
        LinkedList<String> entradasFuturas = new LinkedList<>();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
        Date fechaActual = new Date(); 
        
        for (String codigo : this.entradasCompradas.keySet()) {	
            try {
                Date fechaEntrada = formato.parse(this.entradasCompradas.get(codigo));
                if(aPosterioraB(fechaEntrada,fechaActual)) {
                	entradasFuturas.add(codigo);
                }
                
            } catch (Exception e) {
                System.out.println("Error al convertir la fecha: " + e.getMessage());
            }
        }
        return entradasFuturas;
    }

    private boolean aPosterioraB(Date fechaEntrada, Date fechaActual) {	
		return fechaEntrada.after(fechaActual);
	}

	public LinkedList<String> listarEntradasCompradas() {
    	
        return new LinkedList<String>(this.entradasCompradas.keySet());
    }

    public void anularEntrada(String codigoEntrada, String contraseniaIngresada) {
        iniciarSesion(contraseniaIngresada);

        if (!this.entradasCompradas.containsKey(codigoEntrada)) {
            throw new IllegalArgumentException("Código de entrada no válido");
        }

        this.entradasCompradas.remove(codigoEntrada);
    }

    public String consultarDatosParaCalcularCosto(String codigoEntrada) {
        if (entradasCompradas.containsKey(codigoEntrada)) {
            return "Costo base + servicio para entrada " + codigoEntrada;
        }
        throw new IllegalArgumentException("Entrada no encontrada.");
    }

    public void comprarEntrada(String codigoEntrada, String fecha) {
        entradasCompradas.put(codigoEntrada, fecha);
    }

    public boolean iniciarSesion(String contraseniaIngresada) {
        return this.contrasenia.equals(contraseniaIngresada);
    }

}
