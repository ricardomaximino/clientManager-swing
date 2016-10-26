package es.seas.feedback.cliente.manager.model;

import es.seas.feedback.cliente.manager.model.dao.hibernate.LocalDateAttributeConverter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Esta es una clase abstracta que representa una persona, esta clase es un POJO.
 * 
 * @author Ricardo Maximino<br><br>
 * 
 * <p>En esta clase se tiene los datos que toda persona tiene, para que las clases
 * contretas que la extenda ahorre trabajo.</p>
 * 
 * <p>Hay once variables globales. Las variables son :</p>
 * <ul>
 * <li>id - creado exclusivamente para utilizar frameworks como hibernate. </li>
 * <li>nif - una String con el número del documento identificativo de la persona. </li>
 * <li>nombre - una String con el nombre de la persona.</li>
 * <li>primerApellido - una String con el primer apellido de la persona.</li>
 * <li>segundoApellido - una String con el segundo apellido de la persona.</li>
 * <li>fechaNacimiento - un LocalDate con la fecha de nacimiento de la persona.</li>
 * <li>activo - un boolean informando se el registro de la persona está activo o no.</li>
 * <li>fechaPrimeraAlta - un LocalDate con la fecha de la primera vez que la persona
 * se a dado de alta.</li>
 * <li>fechaUltimaBaja - un LocalDate con la fecha de la ultima vez que la 
 * persona se a dado de baja.</li>
 * <li>direccion - una Direccion una clase del tipo 
 * es.seas.feedback.cliente.manager.model.Direccion.</li>
 * <li>contactos - una Collection del tipo 
 * es.seas.feedback.cliente.manager.model.Contacto.</li>
 * </ul>
 * 
 */
@MappedSuperclass
public abstract class Persona implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true,nullable = false)
    private String nif;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaNacimiento;
    private boolean activo;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaPrimeraAlta;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaUltimaBaja;
    @Embedded
    private Direccion direcion;
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private Collection<Contacto> contactos;
    
    /**
     * Constructor sin argumento el único implementado,
     * que no hace nada más que crear una instancia
     * desta clase y iniciar la variable contactos.<br><br>
     * 
     * La variable contactos es inicializada con un ArrayList.<br>
     * contactos = new ArrayList&lt;&gt;();<br>
     */
    public Persona(){
        contactos  = new ArrayList<>();
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
     * Retorna el valor de la variable global nif.
     * @return del tipo String.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Configura el valor de la variable global nif.
     * @param nif del tipo String.
     */
    public void setNif(String nif) {
        this.nif = nif;
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
     * @param nombre de tipo String.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el valor de la variable global fechaNacimiento.
     * @return del tipo LocalDate.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Configura el valor de la variable global fechaNacimiento.
     * @param fechaNacimiento del tipo LocalDate.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Retorna el valor de la variable global activo.
     * @return del tipo boolean.
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Configura el valor de la variable global activo.
     * @param activo del tipo boolean.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Retorna el valor de la variable global fechaPrimeraAlta.
     * @return del tipo LocalDate;
     */
    public LocalDate getFechaPrimeraAlta() {
        return fechaPrimeraAlta;
    }

    /**
     * Configura el valor de la variable fechaPrimeraAlta.
     * @param fechaPrimeraAlta del tipo LocalDate.
     */
    public void setFechaPrimeraAlta(LocalDate fechaPrimeraAlta) {
        this.fechaPrimeraAlta = fechaPrimeraAlta;
    }

    /**
     * Retorna el valor de la variable global fechaUltimaBaja.
     * @return de tipo LocalDate.
     */
    public LocalDate getFechaUltimaBaja() {
        return fechaUltimaBaja;
    }

    /**
     * Configura el valor de la variable global fechaUltimaBaja.
     * @param fechaUltimaBaja del tipo LocalDate.
     */
    public void setFechaUltimaBaja(LocalDate fechaUltimaBaja) {
        this.fechaUltimaBaja = fechaUltimaBaja;
    }

    /**
     * Retorna el valor de la variable global direccion.
     * @return del tipo es.seas.feedback.cliente.manager.model.Direccion.
     */
    public Direccion getDirecion() {
        return direcion;
    }

    /**
     * Configura el valor de la variable global direccion.
     * @param direcion del tipo es.seas.feedback.cliente.manager.model.Direccion.
     */
    public void setDirecion(Direccion direcion) {
        this.direcion = direcion;
    }

    /**
     * Retorna el valor de la variable global primerApellido.
     * @return del tipo String.
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Configura el valor de la variable global primerApellido.
     * @param primerApellido del tipo String.
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Retorna el valor de la variable global segundoApellido.
     * @return del tipo String.
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Configura el valor de la variable global segundoApellido.
     * @param segundoApellido del tipo String.
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Retorna el valor de la variable global contactos.
     * @return del tipo Collection&lt;es.seas.feedback.cliente.manager.model.Contacto&gt;
     */
    public Collection<Contacto> getContactos() {
        return contactos;
    }

    /**
     * Configura el valor de la variable global contactos.
     * @param contactos de tipo Collection&lt;es.seas.feedback.cliente.manager.model.Contacto&gt;
     */
    public void setContactos(Collection<Contacto> contactos) {
        this.contactos = contactos;
    }

    /**
     * Este metodo Override the hashCode para que el algoritimo utilice
     * las variables globales nif y nombre.
     * @return del tipo int.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.nif);
        hash = 37 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    /**
     * Este metodo Override the equals para que el metodo utilice tambíen
     * las variables globales nif y nombre para comparar.
     * @param obj del tipo Object.
     * @return del tipo boolean.
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
        final Persona other = (Persona) obj;
        if (!Objects.equals(this.nif, other.nif)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    /**
     * Este metodo Override the toString para que se utilice las variables globales nif, nombre,primerApellido,segundoApellido.
     * @return del tipo String.
     */
    @Override
    public String toString() {
        return "Person{" + "nif=" + nif + ", nombre=" + nombre + ", primer apellido=" + primerApellido + " segungo apellido=" + segundoApellido +"}";
    }
    
}
