/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model;

import es.seas.feedback.cliente.manager.model.dao.hibernate.LocalDateAttributeConverter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
 *
 * @author Ricardo
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
    
    
    public Persona(){
        contactos  = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaPrimeraAlta() {
        return fechaPrimeraAlta;
    }

    public void setFechaPrimeraAlta(LocalDate fechaPrimeraAlta) {
        this.fechaPrimeraAlta = fechaPrimeraAlta;
    }

    public LocalDate getFechaUltimaBaja() {
        return fechaUltimaBaja;
    }

    public void setFechaUltimaBaja(LocalDate fechaUltimaBaja) {
        this.fechaUltimaBaja = fechaUltimaBaja;
    }

    public Direccion getDirecion() {
        return direcion;
    }

    public void setDirecion(Direccion direcion) {
        this.direcion = direcion;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Collection<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(Collection<Contacto> contactos) {
        this.contactos = contactos;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.nif);
        hash = 37 * hash + Objects.hashCode(this.nombre);
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
        final Persona other = (Persona) obj;
        if (!Objects.equals(this.nif, other.nif)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "nif=" + nif + ", nombre=" + nombre + ", primer apellido=" + primerApellido + " segungo apellido=" + segundoApellido +"}";
    }
    
}
