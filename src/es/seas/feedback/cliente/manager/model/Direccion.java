package es.seas.feedback.cliente.manager.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Ricardo Maximino
 * Ejercicio obligatório del curso java de SEAS año 2016.
 * La aplicación en general visa admintrar los datos de los clientes.
 * Aùn sendo el objetivo principal administrar los datos de los clientes, el
 * sistema dedica una gran parte de sus fucionalidades para los usuarios.
 * Lo desarollo del usuario es muy importante para la seguridad de los datos
 * de los clientes. Es un sistema también implementa internacionalization.
 * 
 * La class Direccion implementa la interfaz Serializable
 * tiene seis variables privada con sus respectivos gets y sets
 * Override el metodo toString.
 * 
 * Tiene dos contructores uno sin argumento y otro con seis parametros, los
 * cuales iniciaran las seis variables privadas.
 */
@Embeddable
public class Direccion implements Serializable {
    private String provincia;
    private String localidade;
    private String direccion;
    private String numero;
    private String cp;
    private String nota;
    
    public Direccion(){}
    public Direccion(String provincia,String localidade,String direccion,String numero,String cp,String nota){
        this.provincia = provincia;
        this.localidade = localidade;
        this.direccion = direccion;
        this.numero = numero;
        this.cp = cp;
        this.nota = nota;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Direccion{" + "provincia=" + provincia + ", localidade=" + localidade + ", numero=" + numero + ", cp=" + cp + ", nota=" +  nota + '}';
    }
}
