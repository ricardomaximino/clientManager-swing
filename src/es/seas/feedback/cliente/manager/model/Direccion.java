/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 *
 * @author Ricardo
 */
@Embeddable
public class Direccion implements Serializable {
    private String provincia;
    private String localidade;
    private String direccion;
    private String numero;
    private String cp;
    private String nota;
    @Transient
    private final String SIN_NOTA = "NO HAY NINGUNA OBSERVACIÓN ESPECIAL.";
    
    public Direccion(){}
    public Direccion(String provincia,String localidade,String direccion,String numero,String cp,String nota){
        this.provincia = provincia;
        this.localidade = localidade;
        this.direccion = direccion;
        this.numero = numero;
        this.cp = cp;
        this.nota = nota;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Direccion{" + "provincia=" + provincia + ", localidade=" + localidade + ", numero=" + numero + ", cp=" + cp + ", nota=" + (nota.isEmpty()? SIN_NOTA : nota) + '}';
    }
    
    
    
}
