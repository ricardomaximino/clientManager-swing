package es.seas.feedback.cliente.manager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Esta clase representa la provincia que será usada en una dirección esta 
 * clase es un POJO. 
 * 
 * @author Ricardo Maximino<br><br>
 * 
 * <p> Esta class representa la provincia que será usada en una dirección. 
 * Realmente el campo provincia de la class Direccion recibe una String
 * he optado crear esta class para ejercitar los conocimeitos.</p>
 * 
 * <p>La class Provincia implementa la interfaz Serializable y
 * tiene tres variables globales con sus respectivos gets y sets.</p>
 * <p>Las variables son:</p>
 * <ul>
 * <li>id - creada exclusivamente para utilizar frameworks como hibernate.</li>
 * <li>nombre - una String con el nombre de la provincia.</li>
 * <li>localidades - un List&lt;Localidade&gt;.</li>
 * </ul>
 * 
 * <p>Override los metodos hashCode y equals para que una provincia sea igual
 * la provincia que tenga el mismo nombre.</p>
 * 
 * <p>Tiene dos contructores uno sin argumento y otro con dos parametros los
 * cuales inicializaran dos variable privadas.</p>
 */
@Entity
@Table(name="Provincias")
public class Provincia implements Serializable {

    @Id
    private long id;
    private String nombre;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Localidade> localidades;

    /**
     * Constructor sin argumento inicializará con los valores por defecto.
     * 
     * long id = 0;<br>
     * nombre = null;<br>
     * provincia = new ArrayList&lt;&gt;();<br>
     */    
    public Provincia() {
        localidades = new ArrayList<>();
    }

    /**
     * Constructor con dos argumento que inicializará dos de las tres variables.
     * 
     * @param nombre El nombre da provincia que representará la instancia que esta sendo creada. Es del tipo String.
     * @param id El código de la provincia. Es del tipo long.
     * 
     * <p>public Provincia(String nombre,long id){<br>
     * &#32;&#32;&#32;    this();
     * &#32;&#32;&#32;    this.nombre = nombre;<br>
     * &#32;&#32;&#32;    this.id = id;<br>
     * }</p>
     */
    public Provincia(String nombre,long id) {
        this();
        this.nombre = nombre;
        this.id = id;
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
     * Retorna el valor de la variable global localidades.
     * @return del tipo Collection&lt;es.seas.feedback.cliente.manager.modelo.Localidade&gt;.
     */
    public List<Localidade> getLocalidades() {
        return localidades;
    }

    /**
     * Configura el valor de la variable global localidades.
     * @param localidades del tipo Collection&lt;es.seas.feedback.cliente.manager.modelo.Localidade&gt;.
     */
    public void setLocalidades(List<Localidade> localidades) {
        this.localidades = localidades;
    }

    /**
     * Este metodo ha sido Override en conjunto con el metodo equals, para crear un hashCode usando la variable nombre.
     * 
     * @return Retorna un int que es el resultado de un algoritimo usando la variable nombre.<br><br>
     * 
     * public int hashCode() {<br>
     * &#32;&#32;&#32;  int hash = 5;<br>
     * &#32;&#32;&#32;  hash = 53 * hash + Objects.hashCode(this.nombre);<br>
     * &#32;&#32;&#32;  return hash;<br>
     * }<br>
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    /**
     * 
     * Este metodo ha sido Override en conjunto con el metodo hashCode para se estabelesca como igual
     * una provincia que tenga el mismo nombre.
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
        final Provincia other = (Provincia) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
}