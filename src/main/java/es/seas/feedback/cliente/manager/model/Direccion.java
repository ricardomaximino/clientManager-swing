package es.seas.feedback.cliente.manager.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Esta representa una dirección y es un POJO con seis variable y sus respectivos gets y sets.
 * 
 * @author Ricardo Maximino<br><br>
 * 
 * Las variables son:
 * <ul>
 * <li>provincia - una String con el nombre de la provincia.</li>
 * <li>localidade - una Stirng con el nombre de la localidade. </li>
 * <li>direccion - una string con el nombre de la calle. </li>
 * <li>numero - una String con el número de la casa, edifício etc. </li>
 * <li>cp - una String con el código postal. </li>
 * <li>nota - una String con todas observaciones necesarias para la 
 * localización de esta dirección.Ejemplo: Apt. 1B 4ªplanta. </li>
 * </ul>

 */
@Embeddable
public class Direccion implements Serializable {
    private String provincia;
    private String localidade;
    private String direccion;
    private String numero;
    private String cp;
    private String nota;
    
    /**
     * Constructor sin argumento que no hace nada más que crear una instancia
     * desta clase.
     */
    public Direccion(){}
    /**
     * Constructor con seis parametros para directamente configuar las variables
     * sin la necesidade de utilizar los metodos sets.
     * @param provincia una String con el nombre de la provincia.
     * @param localidade una String con el nombre de la localidade.
     * @param direccion una String con el nombre de la calle.
     * @param numero una String con el número de la casa, edifício etc.
     * @param cp una String con el código postal.
     * @param nota una String con todas observaciones necesarias para la 
     * localizacion de esta dirección. Ejemplo: Apt. 1B 4ªplanta.
     */
    public Direccion(String provincia,String localidade,String direccion,String numero,String cp,String nota){
        this.provincia = provincia;
        this.localidade = localidade;
        this.direccion = direccion;
        this.numero = numero;
        this.cp = cp;
        this.nota = nota;
    }

    /**
     * Retorna el valor de la variable global provincia.
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
     * Retorna el valor de la variable global localidade.
     * @return del tipo String.
     */
    public String getLocalidade() {
        return localidade;
    }

    /**
     * Configura el valor de la variable global localidade.
     * @param localidade del tipo String
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    /**
     * Retorna el valor de la variable global direccion.
     * @return del tipo String.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Configura el valor de la variable global direccion.
     * @param direccion del tipo String.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Retorna el valor de la variable global numero.
     * @return del tipo String.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Configura el valor de la variable global numero.
     * @param numero del tipo String.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Retorna el valor de la variable global cp.
     * @return del tipo String.
     */
    public String getCp() {
        return cp;
    }

    /**
     * Configura el valor de la variable global cp.
     * @param cp del tipo String.
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * Retorna el valor de la variable global nota.
     * @return del tipo String.
     */
    public String getNota() {
        return nota;
    }

    /**
     * Configura el valor de la variable nota.
     * @param nota del tipo String.
     */
    public void setNota(String nota) {
        this.nota = nota;
    }
    
    /**
     * Este metodo fue Override para retornar los valores de las variables globales.
     * @return del tipo String.<br><br>
     * 
     * <p>Este metodo fue Override para retornar los valores de las variables globales
     * provincia,localidade,numero,cp,nota.</p>
     */
    @Override
    public String toString() {
        return "Direccion{" + "provincia=" + provincia + ", localidade=" + localidade + ", numero=" + numero + ", cp=" + cp + ", nota=" +  nota + '}';
    }
}
