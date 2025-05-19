package ar.edu.ungs.prog2.ticketek;

import java.util.LinkedList;

public class Usuario {

    private String mail;
    private String contrasenia;
    private String nombre;
    private String apellido;
    private LinkedList<String> entradasFechasPasadas;
    private LinkedList<String> entradasFechasFuturas;

    public Usuario(String mail, String nombre, String apellido, String contrasenia) {
        this.mail = mail;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.entradasFechasPasadas = new LinkedList<>();
        this.entradasFechasFuturas = new LinkedList<>();
    }

    public LinkedList<String> listarEntradasFuturas() {
        return new LinkedList<>(entradasFechasFuturas);
    }

    public LinkedList<String> listarEntradasCompradas() {
        LinkedList<String> todas = new LinkedList<>();
        todas.addAll(entradasFechasPasadas);
        todas.addAll(entradasFechasFuturas);
        return todas;
    }

    public Boolean anularEntrada(String codigoEntrada, String contraseniaIngresada) {
        if (!this.contrasenia.equals(contraseniaIngresada)) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        if (!entradasFechasFuturas.contains(codigoEntrada)) {
            throw new IllegalArgumentException("Código de entrada no válido o ya expirado.");
        }

        entradasFechasFuturas.remove(codigoEntrada);
        return true;
    }

    public String consultarDatosParaCalcularCosto(String codigoEntrada) {
        if (entradasFechasFuturas.contains(codigoEntrada) || entradasFechasPasadas.contains(codigoEntrada)) {
            return "Costo base + servicio para entrada " + codigoEntrada;
        }
        throw new IllegalArgumentException("Entrada no encontrada.");
    }

    public void comprarEntrada(String codigoEntrada) {
        entradasFechasFuturas.add(codigoEntrada);
    }

    public boolean iniciarSesion(String contraseniaIngresada) {
        return this.contrasenia.equals(contraseniaIngresada);
    }

}
