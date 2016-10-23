/**
 * 
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
 *
 * @author Ricardo
 */
public class ClienteManager {

    private Integer integer = 0;
    private Map<String, Provincia> mapProvincias;

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Integer getInteger() {
        return integer;
    }

    public void plus() {
        integer++;
    }

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
    private void setProvincias(){
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
    public static void main(String[] args) {
        ClienteManager cm = new ClienteManager();
        cm.bootClienteManager();
    }

    /*public static void main(String args[]) {
        Thread base = new Thread() {
            public void run() {
                int i =0;
                ClienteManager manager = new ClienteManager();
                while (manager.getInteger() < 10) {
                    if (manager.getInteger() == 0) {
                        manager.plus();
                        Thread main = new Thread() {@Override public void run() { manager.bootClienteManager();}};
                        main.start();
                    }else{
                         System.out.print(i);
                    }
                    if (manager.getInteger() == 2) {
                        System.out.println(manager.getInteger());
                        manager.setInteger(0);
                    }
                    i++;
                }
                System.exit(0);
            }
        };
        base.start();
    }*/
}
