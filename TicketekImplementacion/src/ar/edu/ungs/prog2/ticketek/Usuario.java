package ar.edu.ungs.prog2.ticketek;

import java.io.*;
import java.util.*;
import java.util.LinkedList;
/**
 * 
 */
public class Usuario {

    
    private String mail;

    private String contraseña;

    private String nombre;

    private String apellido;
    private LinkedList<String> entradasFechasPasadas;
    private LinkedList<String> entradasFechasFuturas;

    public Usuario( String mail,  String  contraseña,  String  nombre,  String  apellido) {
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

    public Void comprarEntrada(String entrada) {
        return null;
    }

}