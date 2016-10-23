/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.view;

import es.seas.feedback.cliente.manager.ClienteManager;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import es.seas.feedback.cliente.manager.control.PersonaControl;

/**
 *
 * @author Ricardo
 */
public interface Controlable {
    JDesktopPane getDesktopPane();
    void showInternalFrame(JInternalFrame frame);
    void setClienteControl(PersonaControl control);
    void setUsuarioControl(PersonaControl control);
}
