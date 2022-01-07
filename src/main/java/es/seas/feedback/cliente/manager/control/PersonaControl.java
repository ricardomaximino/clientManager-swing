package es.seas.feedback.cliente.manager.control;

import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.view.Controlable;
import java.util.Map;
import javax.swing.JInternalFrame;
import es.seas.feedback.cliente.manager.model.service.ServicioPersona;
import es.seas.feedback.cliente.manager.view.PersonaUtilidades;

/**
 * Esta interfaz fue creada para desvincular los controles de la view.
 * @author Ricardo Maximino<br>
 * Para más informaciones de los metodo, ir a la documentación de alguna implementación.
 * @param <T> del tipo Generics.
 * @see ClienteControl
 * @see UsuarioControl
 */
public interface PersonaControl<T> {
    Controlable getPrincipal();
    void crearFrameAnadir();
    void setServicio(ServicioPersona<T> serv);
    ServicioPersona<T> getServicio();
    void setProvincias(Map<String,Provincia> prov);
    Map<String,Provincia> getProvincias();
    void setPersonaUtilidades(PersonaUtilidades personaUtilidades);
    PersonaUtilidades getPersonaUtilidades();
    boolean guardar(T persona);
    void listarTodos();
    void crearFrameAtualizar(String nif);
    void crearFrameBuscar();
    void closeFrame(JInternalFrame frame);
    void buscar(String nombreDelCampo, String valorDelCampo);
    void refresh();
    
}
