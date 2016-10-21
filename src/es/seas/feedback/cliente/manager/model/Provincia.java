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
 *
 * @author Ricardo Maximino
 * Ejercicio obligatório del curso java de SEAS año 2016.
 * La aplicación en general visa admintrar los datos de los clientes.
 * Aùn sendo el objetivo principal administrar los datos de los clientes, el
 * sistema dedica una gran parte de sus fucionalidades para los usuarios.
 * Lo desarollo del usuario es muy importante para la seguridad de los datos
 * de los clientes. Es un sistema también implementa internacionalization.
 * 
 * La class Provincia implementa la interfaz Serializable
 * tiene tres viriable privadas con sus respectivos gets y sets
 * Override los metodos hashCode y equals.
 * 
 * Tiene dos contructures uno sin argumento y otro con dos parametros
 * los cuales inicializaran dos variables privadas
 */
@Entity
@Table(name="Provincias")
public class Provincia implements Serializable {

    @Id
    private long id;
    private String nombre;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Localidade> localidades;

    public Provincia() {
        localidades = new ArrayList<>();
    }

    public Provincia(String nombre,long id) {
        this();
        this.nombre = nombre;
        this.id = id;
    }

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

    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidade> localidades) {
        this.localidades = localidades;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

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