package es.seas.feedback.cliente.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
 * La class Usuario extende la class abstrata Persona añade dos variables
 * privadas con sus respectivos gets y sets
 */
@Entity
@Table(name="Usuarios")
public class Usuario extends Persona{
    private String contraseña;
    @Column(name="controle" ,nullable = false)
    private int level=0;

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
}
