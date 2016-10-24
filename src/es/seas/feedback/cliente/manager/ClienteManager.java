/**
 * Este es el paquete base de la aplicación.
 */
package es.seas.feedback.cliente.manager;

import es.seas.feedback.cliente.manager.control.ClienteControl;
import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.model.dao.ClienteDAO;
import es.seas.feedback.cliente.manager.model.dao.ProvinciaDAO;
import es.seas.feedback.cliente.manager.model.dao.ProvinciaDAOImpl;
import es.seas.feedback.cliente.manager.model.dao.datos.LectorEscritor;
import es.seas.feedback.cliente.manager.model.service.ServicioCliente;
import es.seas.feedback.cliente.manager.view.VentanaPrincipal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import es.seas.feedback.cliente.manager.model.service.ServicioPersona;
import es.seas.feedback.cliente.manager.control.PersonaControl;
import es.seas.feedback.cliente.manager.control.UsuarioControl;
import es.seas.feedback.cliente.manager.model.Cliente;
import es.seas.feedback.cliente.manager.model.Usuario;
import es.seas.feedback.cliente.manager.model.dao.UsuarioDAO;
import es.seas.feedback.cliente.manager.model.service.ServicioUsuario;
import es.seas.feedback.cliente.manager.view.Controlable;
import es.seas.feedback.cliente.manager.view.PersonaUtilidades;

/**
 * Esta es la class que lanza el la aplicacion.
 *
 * @author Ricardo Maximino<br><br>
 *
 * Esta class tien dos metodos privados y un metodo publico. Los metodos
 * privados ayudan configurar los objetos necesarios para configurar el Frame
 * principal.
 */
public class ClienteManager {

    private Map<String, Provincia> mapProvincias;

    /**
     * Este contructor no lleva argumento y es el único constructor implementado. Realmente este constructor no hace nada más que instanciar la clase.
     */
    public ClienteManager() {
        mapProvincias = null;
    }

    /**
     * Este metodo privados ententa configurar el LookAndFell de la aplicacion
     * para Nimbus
     */
    private void setLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Este metodo privado crea una instancia del ProvinciaDAO y configura la
     * variable global provincias, que es un Map<String,Provincia> con todas las
     * provincias que están el la base de datos. En el caso de que la base de
     * datos esté vazia, el metodo crea una instancia de la class
     * LectorEscritor, la cual lee y registra todas las provincias y sus
     * respectivas localidades el la base de datos y configura la variable
     * provincias.
     */
    private void setProvincias() {
        //Provincias
        ProvinciaDAO provDAO = new ProvinciaDAOImpl();
        mapProvincias = new LinkedHashMap<>();
        List<Provincia> listProvincias = provDAO.getList();
        if (listProvincias.size() > 0) {
            for (Provincia prov : provDAO.getList()) {
                mapProvincias.put(prov.getNombre(), prov);
            }
        } else {
            LectorEscritor lectorEscritor = new LectorEscritor();
            lectorEscritor.leerProvinciasYLocalidades(null, null);
            for (Provincia prov : provDAO.getList()) {
                mapProvincias.put(prov.getNombre(), prov);
            }
        }
        //Provincias
    }

    /**
     * Este metodo configura el Frame principal que implementa la Interface
     * Controlable y extende JFrame.<br>
     * <p>
     * Primeramente instancia todas las classes necesaria para configurar el
     * frame principal, luego se configura todo y finalmente configura la
     * propriedade visible para true.</p>
     * <p>
     * Este metodo llama los metodos privados setLookAndFeel() y setProvincias().<br>
     * Las class que se instancia en este metodo son:</p>
     * <ul>
     * <li>PersonaUtilidades </li>
     * <li>ServicioPersona(Cliente)</li>
     * <li>ServicioPersona(Usuario)</li>
     * <li>Controlable frame principal extende JFrame PersonaControl(Cliente)</li>
     * <li>PersonaControl(Usuario)</li>
     * </ul>
     */
    public void bootClienteManager() {
        setLookAndFeel();
        setProvincias();
        //PersonaUtilidades
        PersonaUtilidades personaUtilidades = new PersonaUtilidades();
        //PersonaUtilidades

        //ServicioCliente
        ServicioPersona<Cliente> servicioCliente = new ServicioCliente(new ClienteDAO());
        //ServicioCliente

        //ServicioUsuario
        ServicioPersona<Usuario> servicioUsuario = new ServicioUsuario(new UsuarioDAO());
        //ServicioUsuario

        //Controlable
        Controlable principal = new VentanaPrincipal();
        //Controlable

        //ClienteControl
        PersonaControl<Cliente> clienteControl = new ClienteControl(servicioCliente, principal, mapProvincias);
        clienteControl.setPersonaUtilidades(personaUtilidades);
        //ClienteControl

        //UsuarioControl
        PersonaControl<Usuario> usuarioControl = new UsuarioControl(servicioUsuario, principal, mapProvincias);
        usuarioControl.setPersonaUtilidades(personaUtilidades);
        //UsuarioControl

        ((JFrame) principal).setExtendedState(JFrame.MAXIMIZED_BOTH);
        ((JFrame) principal).setVisible(true);
    }

    /**
     * 
     * @param args - el argumento del metodo main no esta configurado para modificar la manera que se lanza la aplicación.
     */
    public static void main(String[] args) {
        ClienteManager cm = new ClienteManager();
        cm.bootClienteManager();
    }
}
