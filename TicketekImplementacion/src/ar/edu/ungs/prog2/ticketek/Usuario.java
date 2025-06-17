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

    private boolean aPosterioraB(Date a, Date b) {	
		return a.after(b);
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
        return this.contrasenia.equals(contraseniaIngresada);	// 1 op
    }

	public boolean cancelarEntrada(String codigo) {
		entradaComprada(codigo);										// 2 op (pregunta si existe codigo en un HashMap)
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");	//1 + 1
        Date fechaActual = new Date(); 									//2

            try {	
                Date fechaEntrada = formato.parse(this.entradasCompradas.get(codigo)); // 3
                if(aPosterioraB(fechaActual, fechaEntrada)) {						// 1op	(a.after(b);)
                	return false;													// 1
                } else {
                	this.entradasCompradas.remove(codigo);
                	return true;
                }
                
            } catch (Exception e) {
                System.out.println("Error al convertir la fecha: " + e.getMessage());
            }
            return false;
	}																	//TOTAL 11 op

	private void entradaComprada(String codigo) {
		// TODO Auto-generated method stub
		if (!this.entradasCompradas.containsKey(codigo)) {
			throw new RuntimeException("La entrada indicada no ha sido comprada por este usuario");
		}
	}
	
	//-------------------------------------------------------------
	//Metodos Faltantes en la primera Correccion
	//-------------------------------------------------------------
	
@Override	
	public String toString() {
		StringBuilder resultado = new StringBuilder();
    	resultado.append(" - (").append(this.mail).append(") ").append(this.nombre).append(" ").append(this.apellido).append(" - ");
		return resultado.toString();
    	
	}
	
	public boolean equals(Usuario otro) {
		if (this.mail == otro.consultarMail()) {return true;}
		return false;
	}
	
	public String consultarMail(){
		return this.mail;
	}

}
