package es.seas.feedback.cliente.manager.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * La class Contacto implementa la interfaz Serializable
 * tiene tres variable privadas con sus respectivos gets y sets
 * Override el metodo toString.
 * 
 * Tiene dos contructores uno sin argumento y otro con dos parametro, los 
 * cuales iniciaran dos variables privadas.
 */
@Entity
public class Contacto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String descripcion;
    private String contacto;

    public Contacto(){}
    public Contacto(String descripcion, String contacto){
        this.descripcion = descripcion;
        this.contacto = contacto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @Override
    public String toString() {
        return descripcion + ": " + contacto;
    }
    
    
    
}
