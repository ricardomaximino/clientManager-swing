package es.seas.feedback.cliente.manager.model;

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
 * La class Cliente es simplesment la class contreta de la class abstrata Persona.
 */
@Entity
@Table(name="Cliente")
public class Cliente extends Persona {
    
}