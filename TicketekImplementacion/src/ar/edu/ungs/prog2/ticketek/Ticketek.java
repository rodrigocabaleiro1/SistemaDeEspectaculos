
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Ticketek implements ITicketek{


    private HashMap <Usuario> usuarios;
    private HashMap <Espectaculo> espectaculos;
    private HashMap <Sede> sedes;
    private HashMap <Entrada> entradasVendidas;

    public Ticketek() {
        
    }

    public Void registrarSede( String nombre,  int capacidadMaxima,  String direccion,  String tipoDeSede,  int asientosPorFila,  double valorConsumisionLibre) {
    }

    public Void registrarUsuario(void String mail, void String contraseña, void String nombre, void String apellido) {
        // TODO implement here
        return null;
    }

   
    public Void registrarEspectaculo(void String nombre, void Sede[] sedes, void Date[] fechas, void double precioBase) {
        // TODO implement here
        return null;
    }

    /**
     * @param String espectaculo 
     * @param String sede 
     * @param String sector 
     * @param int nrosAsientos 
     * @param String mailUsuario 
     * @param String contraseña 
     * @return
     */
    public Void venderEntradas(void String espectaculo, void String sede, void String sector, void int nrosAsientos, void String mailUsuario, void String contraseña) {
        // TODO implement here
        return null;
    }

    /**
     * @param String espectaculo 
     * @return
     */
    public Void listarSedes(void String espectaculo) {
        // TODO implement here
        return null;
    }

    /**
     * @param String usuario 
     * @return
     */
    public Void listarEntradasFuturas(void String usuario) {
        // TODO implement here
        return null;
    }

    /**
     * @param String usuario 
     * @return
     */
    public Void listarEntradasUsuario(void String usuario) {
        // TODO implement here
        return null;
    }

    /**
     * @param String codigoEntrada 
     * @param String usuario 
     * @param String contraseñaUsuario 
     * @return
     */
    public Void anularEntrada(void String codigoEntrada, void String usuario, void String contraseñaUsuario) {
        // TODO implement here
        return null;
    }

    /**
     * @param String codigoEntrada 
     * @param String sede 
     * @return
     */
    public Void cambiarEntrada(void String codigoEntrada, void String sede) {
        // TODO implement here
        return null;
    }

    /**
     * @param String codigoEntrada 
     * @param String usuario 
     * @return
     */
    public Double calcularCostoEntrada(void String codigoEntrada, void String usuario) {
        // TODO implement here
        return null;
    }

    /**
     * @param String sede 
     * @param String sector 
     * @param String nombreEspectaculo 
     * @return
     */
    public Double calcularValorEntrada(void String sede, void String sector, void String nombreEspectaculo) {
        // TODO implement here
        return null;
    }

    /**
     * @param String espectaculo 
     * @return
     */
    public Double consultarTotalRecaudadoEspectaculo(void String espectaculo) {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void Operation5() {
        // TODO implement here
    }

    /**
     * 
     */
    public void Operation6() {
        // TODO implement here
    }

    /**
     * 
     */
    public void Operation7() {
        // TODO implement here
    }

}