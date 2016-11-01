package es.seas.feedback.cliente.manager.view;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import es.seas.feedback.cliente.manager.control.PersonaControl;

/**
 * Esta interfaz fue creada para eliminar la dependencia de los Controles.
 * @author Ricardo Maximino
 * <p>Aún que cualquer clase pueda implentar esta interfaz, se espera que sea
 * una clase que extenda JFrame y implemente esta interfaz.</p>
 */
public interface Controlable {
    
    /**
     * Este metodo esta pensado para retornar el valor de la variable global 
     * desktopPane.
     * @return del tipo javax.swing.JDesktopPane.
     */
    JDesktopPane getDesktopPane();
    
    /**
     * Este metodo esta pensado para exibir un JInternalFrame en la pantalla.
     * @param frame del tipo java.swing.JInternalFrame.
     * <p>Este metodo será responsable de llamar el metodo setVisible(true)
     * y el metodo setLocation(x,y)</p>
     */
    void showInternalFrame(JInternalFrame frame);
    
    /**
     * Este metodo esta pensado para configurar la variable global clienteControl.
     * @param control del tipo 
     * es.seas.feedback.cliente.manager.control.PersonaControl.
     */
    void setClienteControl(PersonaControl control);
    
    /**
     * Este metodo esta pensado para configurar la variable global usuarioControl.
     * @param control del tipo 
     * es.seas.feedback.cliente.manager.control.PersonaControl.
     */
    void setUsuarioControl(PersonaControl control);
}
