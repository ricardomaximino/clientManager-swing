/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.control;

import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.view.Controlable;
import java.util.Map;
import javax.swing.JInternalFrame;
import es.seas.feedback.cliente.manager.model.service.ServicioPersona;
import es.seas.feedback.cliente.manager.view.PersonaUtilidades;

/**
 *
 * @author Ricardo
 */
public interface PersonaControl<T> {
    void setPrincipal(Controlable princip);
    Controlable getPrincipal();
    void setServicio(ServicioPersona serv);
    ServicioPersona getServicio();
    void setProvincias(Map<String,Provincia> prov);
    Map<String,Provincia> getProvincias();
    void setPersonaUtilidades(PersonaUtilidades personaUtilidades);
    PersonaUtilidades getPersonaUtilidades();
    boolean guardar(T persona);
    void listarTodos();
    void crearFrameAñadir();
    void crearFrameAtualizar(String nif);
    void crearFrameBuscar();
    void closeFrame(JInternalFrame frame);
    void buscar(String nombreDelCampo, String valorDelCampo);
    void refresh();
    
}
