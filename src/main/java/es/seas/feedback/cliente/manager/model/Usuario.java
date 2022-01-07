package es.seas.feedback.cliente.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Esta clase extende Persona y añade dos variables globales y sus respectivos 
 * gets y sets, esta clase es un POJO.
 * 
 * @author Ricardo Maximino<br><br>
 * 
 * <p>Todos los metodos definidos en la clase abstrato Persona son las que se utiliza
 * aqui de la misma manera sin hacer ningun Override.</p>
 * 
 * @see Persona
 * 
 */
@Entity
@Table(name="Usuarios")
public class Usuario extends Persona{
    private String contraseña;
    @Column(name="controle" ,nullable = false)
    private int level=0;

    /**
     * Retorna el valor de la variable global contraseña.
     * @return del tipo String.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Configura el valor de la variable global contraseña.
     * @param contraseña del tipo String.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Retorna el valor de la variable global level.
     * @return del tipo int.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Configura el valor de la variable global level.
     * @param level del tipo int.
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
