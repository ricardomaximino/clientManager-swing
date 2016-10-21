/**
 * @see 
 * El pacote modelos es donde estan todas las class necessaria para implementar
 * las reglas de negocio.
 */
package es.seas.feedback.cliente.manager.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta class representa la localidade que será usada en una dirección. 
 * Realmente el campo localidade de la class Direccion recibe una String
 * he optado crear esta class para ejercitar los conocimeitos.
 * 
 * @author Ricardo Maximino
 * 
 * La class Localidade implementa la interfaz Serializable
 * tiene tres variables privadas con sus respectivos gets y sets
 * 
 * private long id;
 * private String nombre;
 * private String provincia;
 * 
 * public void setId(long id){
 *    this.id = id;
 * }
 * 
 * public long getId(){
 *    return id;
 * }
 * ...
 * Esta class no procesa ningún dato es solamente un POJO
 * 
 * Override los metodos hashCode y equals para que una localidade sea igual
 * tiene que tener el mismo nombre y la misma provincia.
 * 
 * Tiene dos contructores uno sin argumento y otro con dos parametros los
 * cuales inicializaran dos variable privadas.
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
     * private long id = 0;
     * private String nombre = null;
     * private String provincia = null;
     */
    public Localidade(){}
    
    /**
     * Constructor con dos argumento que inicializará dos de las tres variables.
     * 
     * @param nombre El nombre da localidade que representará la instancia que esta sendo creada. Es del tipo String.
     * @param provincia El nombre da provincia a que pertenece tal localidade. Es del tipo String.
     * 
     * public Localidade(String nombre,String provincia){
     *     this.nombre = nombre;
     *     this.provincia = provincia;
     * }
     */
    public Localidade(String nombre,String provincia){
        this.nombre = nombre;
        this.provincia = provincia;
    }

    /**
     * Retorna el valor de la variable id como long
     * @return 
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * 
     * Este metodo ha sido Override en conjunto con el metodo equals, para crear un hashCode usando las variables nombre y provincia.
     * 
     * @return Retorna un int que es el resultado de un algoritimo usando las variables nombre y provincia;
     * 
     * public int hashCode() {
     *   int hash = 7;
     *   hash = 83 * hash + Objects.hashCode(this.nombre);
     *   hash = 83 * hash + Objects.hashCode(this.provincia);
     *   return hash;
     * }
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
     * @return boolean true será si es not null, instanceOf Localidade, nombre y provincia son iguales. Sino retornará false.
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
