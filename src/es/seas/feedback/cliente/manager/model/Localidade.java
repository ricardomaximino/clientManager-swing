package es.seas.feedback.cliente.manager.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta clase representa la localidade que será usada en una dirección esta 
 * clase es un POJO. 
 * 
 * @author Ricardo Maximino<br><br>
 * 
 * <p> Esta class representa la localidade que será usada en una dirección. 
 * Realmente el campo localidade de la class Direccion recibe una String
 * he optado crear esta class para ejercitar los conocimeitos.</p>
 * 
 * <p>La class Localidade implementa la interfaz Serializable y
 * tiene tres variables globales con sus respectivos gets y sets.</p>
 * <p>Las variables son:</p>
 * <ul>
 * <li>id - creada exclusivamente para utilizar frameworks como hibernate.</li>
 * <li>nombre - una String con el nombre de la localidade.</li>
 * <li>provincia - una String con el nombre de la Provincia.</li>
 * </ul>
 * 
 * <p>Override los metodos hashCode y equals para que una localidade sea igual
 * la localidade que tenga el mismo nombre y la misma provincia.</p>
 * 
 * <p>Tiene dos contructores uno sin argumento y otro con dos parametros los
 * cuales inicializaran dos variable privadas.</p>
 */
@Entity
@Table(name="Localidades")
public class Localidade implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    private String nombre;
    private String provincia;
    /**
     * Constructor sin argumento inicializará con los valores por defecto.
     * 
     * long id = 0;<br>
     * nombre = null;<br>
     * provincia = null;<br>
     */
    public Localidade(){}
    
    /**
     * Constructor con dos argumento que inicializará dos de las tres variables.
     * 
     * @param nombre El nombre da localidade que representará la instancia que esta sendo creada. Es del tipo String.
     * @param provincia El nombre da provincia a que pertenece tal localidade. Es del tipo String.
     * 
     * <p>public Localidade(String nombre,String provincia){<br>
     * &#32;&#32;&#32;    this.nombre = nombre;<br>
     * &#32;&#32;&#32;    this.provincia = provincia;<br>
     * }</p>
     */
    public Localidade(String nombre,String provincia){
        this.nombre = nombre;
        this.provincia = provincia;
    }

    /**
     * Retorna el valor de la variable global id.
     * @return del tipo String.
     */
    public long getId() {
        return id;
    }

    /**
     * Configura el valor de la variable global id.
     * @param id del tipo long.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el valor de la variable global nombre.
     * @return del tipo String.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Configura el valor de la variable global nombre.
     * @param nombre del tipo String.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el valor de la variable provincia.
     * @return del tipo String.
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Configura el valor de la variable global provincia.
     * @param provincia del tipo String.
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * 
     * Este metodo ha sido Override en conjunto con el metodo equals, para crear un hashCode usando las variables nombre y provincia.
     * 
     * @return Retorna un int que es el resultado de un algoritimo usando las variables nombre y provincia;<br><br>
     * 
     * public int hashCode() {<br>
     * &#32;&#32;&#32;  int hash = 7;<br>
     * &#32;&#32;&#32;  hash = 83 * hash + Objects.hashCode(this.nombre);<br>
     * &#32;&#32;&#32;  hash = 83 * hash + Objects.hashCode(this.provincia);<br>
     * &#32;&#32;&#32;  return hash;<br>
     * }<br>
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.nombre);
        hash = 83 * hash + Objects.hashCode(this.provincia);
        return hash;
    }

    /**
     * 
     * Este metodo ha sido Override en conjunto con el metodo hashCode para se estabelesca como igual
     * una localidade que tenga el mismo nombre y la misma provincia.
     * 
     * @param obj Es el Objeto para compara si son iguales ;
     * @return un valor del tipo boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Localidade other = (Localidade) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        return true;
    }
}
