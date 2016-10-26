package es.seas.feedback.cliente.manager.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Esta clase es un POJO con apenas tres variable con sus respectivos gets y sets.
 * 
 * @author Ricardo Maximino<br><br>
 * 
 * <p>Las variables son :</p>
 * <ul>
 * <li>id - creado exclusivamente para utilizar frameworks como hibernate. </li>
 * <li>descripcion - una String para describir el tipo de contado. </li>
 * <li>contacto - una String para el contacto. Ejemplo: ricardomaximino@hotmail.com
 * o 333-33-33-33</li>
 * </ul>
 * 
 */
@Entity
@Table(name="Contactos")
public class Contacto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String descripcion;
    private String contacto;

    /**
     * Constructor sin argumento que no hace nada más que crear una instancia
     * desta clase.
     */
    public Contacto(){}
    /**
     * Constructor con seis parametros para directamente configuar las variables
     * sin la necesidade de utilizar los metodos sets.
     * 
     * @param descripcion String describindo el tipo de contacto. Ejemplo TELEFONO O EMAIL 
     * @param contacto String con el contacto. Ejemplo 333-33-33-33 o ricardomaximino@hotmail.com
     */
    public Contacto(String descripcion, String contacto){
        this.descripcion = descripcion;
        this.contacto = contacto;
    }

    /**
     * Retorna el valor de la variable global id.
     * @return del tipo long.
     */
    public long getId() {
        return id;
    }

    /**
     * Configura el valor de la variable global id.
     * @param id del tipo long
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Retorna el valor de la variable global descripcion.
     * @return del tipo String.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Configura la variable global descripcion.
     * @param descripcion del tipo String
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Retorna el valor de la variable global contacto.
     * @return del tipo String.
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * Configura la variable global contacto.
     * @param contacto del tipo String.
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * Este metodo es Override para retornar el valor de las variable descripcion
     * y contacto.
     * @return del tipo String.
     */
    @Override
    public String toString() {
        return descripcion + ": " + contacto;
    }
}
