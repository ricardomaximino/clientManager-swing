package es.seas.feedback.cliente.manager.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Esta clase extende Persona y no añade nada diferente, esta clase es un POJO.
 * 
 * @author Ricardo Maximino<br><br>
 * 
 * <p>Todos los metodos definidos en la clase abstrato Persona son las que se utiliza
 * aqui sin tocar nada.</p>
 * 
 * @see Persona
 * 
 */
@Entity
@Table(name="Clientes")
public class Cliente extends Persona {
    
}