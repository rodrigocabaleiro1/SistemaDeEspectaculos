package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;
import java.util.LinkedList;
/**
 * 
 */
public class Usuario {

    
    private String mail;

    private String contrasenia;

    private String nombre;

    private String apellido;
    private LinkedList<Integer> entradasFechasPasadas;
    private LinkedList<Integer> entradasFechasFuturas;

    public Usuario( String mail, String  nombre,  String  apellido, String contrasenia) {
    	
    	this.mail = mail;
    	this.contrasenia = contrasenia;
    	this.nombre = nombre;
    	this.apellido = apellido;
    }

    public LinkedList<String> listarEntradasFuturas() {
        return null;
    }

    public LinkedList<String> listarEntradasCompradas() {
        return null;
    }
    public Boolean anularEntrada( String codigoEntrada,  String Contraseña) {
        return null;
    }

    public String consultarDatosParaCalcularCosto( String codigoEntrada) {
        return "";
    }

    public void comprarEntrada(String codigoEntrada) {
        
    }

}