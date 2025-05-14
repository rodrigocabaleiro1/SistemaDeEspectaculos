
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Usuario {

    /**
     * Default constructor
     */
    public Usuario() {
    }

    /**
     * 
     */
    private String mail;

    /**
     * 
     */
    private String contraseña;

    /**
     * 
     */
    private String nombre;

    /**
     * 
     */
    private String apellido;

    /**
     * 
     */
    private linkedList<String> entradasFechasPasadas;

    /**
     * 
     */
    private linkedList<String> entradasFechasFuturas;


    /**
     * @param String mail 
     * @param String  contraseña 
     * @param String  nombre 
     * @param String  apellido 
     * @return
     */
    public void Usuario(void String mail, void String  contraseña, void String  nombre, void String  apellido) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public LinkedList<String> listarEntradasFuturas() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public LinkedList<String> listarEntradasCompradas() {
        // TODO implement here
        return null;
    }

    /**
     * @param String codigoEntrada 
     * @param String Contraseña 
     * @return
     */
    public Boolean anularEntrada(void String codigoEntrada, void String Contraseña) {
        // TODO implement here
        return null;
    }

    /**
     * @param String codigoEntrada 
     * @return
     */
    public String consultarDatosParaCalcularCosto(void String codigoEntrada) {
        // TODO implement here
        return "";
    }

    /**
     * @param String entrada 
     * @return
     */
    public Void comprarEntrada(void String entrada) {
        // TODO implement here
        return null;
    }

}