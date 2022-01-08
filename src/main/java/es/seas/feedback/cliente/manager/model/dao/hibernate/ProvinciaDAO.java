package es.seas.feedback.cliente.manager.model.dao.hibernate;

import es.seas.feedback.cliente.manager.model.Provincia;
import java.util.List;

/**
 * Esta interfaz fue creada para abstraer depencia de una implementación
 * especifica.
 * @author Ricardo Maximino
 * 
 * <p>Toda la implentación para relacionada a la clase 
 * es.seas.feedback.cliente.manager.Provincia, esta pensada para un manejo
 * interno, sin interacción con el usuario.</p>
 */
public interface ProvinciaDAO {
    /**
     * Este metodo está pensado para guardar una provincia en la base de datos
     * y si presentes todas las localidades de la lista de localidades.
     * @param provincia del tipo es.seas.feedback.cliente.manager.Provincia.
     * @return boolean para certificar se la acción ha sido realizada con
     * éxito o no.
     */
    boolean guardarProvincia(Provincia provincia);
    
    /**
     * Este metodo está pensado para buscar una provincia por el nombre, ya
     * que el nombre de la provincia debe contener una constraint en la base de
     * datos como identificador único.
     * @param nombre del tipo String, identificador único en la tabla.
     * @return del tipo es.seas.feedback.cliente.manager.Provincia.
     */
    Provincia getProvincia(String nombre);
    
    /**
     * Este metodo está pensado para listar todas las provincias de la base de
     * datos.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.Provincia&gt;.
     */
    List<Provincia> getLista();
}
