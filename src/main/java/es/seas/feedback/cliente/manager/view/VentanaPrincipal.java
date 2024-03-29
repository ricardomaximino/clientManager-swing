package es.seas.feedback.cliente.manager.view;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import es.seas.feedback.cliente.manager.control.PersonaControl;
import es.seas.feedback.cliente.manager.model.Cliente;
import es.seas.feedback.cliente.manager.model.Usuario;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Esta clase es el frame principal de la aplicación.
 * @author Ricardo Maximino
 */
public class VentanaPrincipal extends javax.swing.JFrame implements Controlable {

    private int x = 0;
    private int y = 0;
    private PersonaControl<Cliente> clienteControl;
    private PersonaControl<Usuario> usuarioControl;

    /**
     * Este es el constructor sin argumentos, que simplemente inicializa los 
     * componentes de JFrame.
     */
    public VentanaPrincipal() {
        initComponents();
    }

    /**
     * Este es el constructor con argumentos, el cual ademas de inicializar los
     * componentes también configura los controles de cliente y usuarios.
     * @param clienteController del tipo 
     * es.seas.feedback.cliente.manager.control.PersonaControl.
     * @param usuarioControl del tipo 
     * es.seas.feedback.cliente.manager.control.PersonaControl.
     */
    public VentanaPrincipal(PersonaControl<Cliente> clienteController, PersonaControl<Usuario> usuarioControl) {
        this();
        this.clienteControl = clienteController;
        this.usuarioControl = usuarioControl;
    }

    /**
     * Este metodo retorna el valor de la variable global desktopPane.
     * @return del tipo javax.swing.JDesktopPane.
     */
    @Override
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    /**
     * Este metodo exibe el JInternalFrame pasado como parametro.
     * @param frame javax.swing.JInternalFrame.
     */
    @Override
    public void showInternalFrame(JInternalFrame frame) {
        if (desktopPane.getAllFrames().length == 0) {
            restartXY();
        }

        frame.setClosable(true);
        frame.setIconifiable(true);
        frame.setResizable(false);

        desktopPane.add(frame);
        frame.setLocation(getPositionX(), getPositionY());
        frame.setVisible(true);
    }

    /**
     * Este metodo configura la variable global clienteControl.
     * @param control del tipo es.seas.feedback.cliente.manager.control.PersonaControl.
     */
    @Override
    public void setClienteControl(PersonaControl<Cliente> control) {
        this.clienteControl = control;
    }

    /**
     * Este metodo configura la variable global usuarioControl.
     * @param control del tipo es.seas.feedback.cliente.manager.control.PersonaControl.
     */
    @Override
    public void setUsuarioControl(PersonaControl<Usuario> control) {
        this.usuarioControl = control;
    }

    private int getPositionX() {
        x += 30;
        if (desktopPane.getWidth() - 800 < x) {
            x = 0;
        }
        return x;
    }

    private int getPositionY() {
        y += 30;
        if (this.desktopPane.getHeight() - 120 < y) {
            y = 0;
        }
        return y;
    }

    private void restartXY() {
        x = y = 0;
    }    
    
    private void reset() {
        for (JInternalFrame f : desktopPane.getAllFrames()) {
            f.dispose();
        }
        restartXY();
        clienteControl.refresh();
        usuarioControl.refresh();
        clienteControl.getPersonaUtilidades().refresh();
        ResourceBundle view = ResourceBundle.getBundle("view.messages.view");
        //Cliente
        menuCliente.setText(view.getString("menu_Client"));
        menuItemBuscarCliente.setText(view.getString("menuItem_SearchClient"));
        menuItemListarTodosCliente.setText(view.getString("menuItem_ListAll"));
        menuItemNuevoCliente.setText(view.getString("menuItem_NewClient"));
        //Ventanas
        menuVentanas.setText(view.getString("menu_Windows"));
        menuItemCerrar.setText(view.getString("menuItem_Close"));
        menuItemCerrarTodo.setText(view.getString("menuItem_CloseAll"));
        menuItemCascada.setText(view.getString("menuItem_Waterfall"));
        //Usuario
        menuUsuario.setText(view.getString("menu_User"));
        menuItemBuscarUsuario.setText(view.getString("menuItem_SearchUser"));
        menuItemListarTodosUsuario.setText(view.getString("menuItem_ListAll"));
        menuItemNuevoUsuario.setText(view.getString("menuItem_NewUser"));
        //Lenguas
        menuLengua.setText(view.getString("menu_Languages"));
        menuItemCastellano.setText(view.getString("menuItem_Spanish"));
        menuItemIngles.setText(view.getString("menuItem_English"));
        menuItemPortugues.setText(view.getString("menuItem_Portugues"));
        //Configuraciones
        menuConfiguracion.setText(view.getString("menu_Settings"));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        menuCliente = new javax.swing.JMenu();
        menuItemBuscarCliente = new javax.swing.JMenuItem();
        menuItemListarTodosCliente = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItemNuevoCliente = new javax.swing.JMenuItem();
        menuVentanas = new javax.swing.JMenu();
        menuItemCerrar = new javax.swing.JMenuItem();
        menuItemCerrarTodo = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuItemCascada = new javax.swing.JMenuItem();
        menuUsuario = new javax.swing.JMenu();
        menuItemBuscarUsuario = new javax.swing.JMenuItem();
        menuItemListarTodosUsuario = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItemNuevoUsuario = new javax.swing.JMenuItem();
        menuLengua = new javax.swing.JMenu();
        menuItemCastellano = new javax.swing.JMenuItem();
        menuItemIngles = new javax.swing.JMenuItem();
        menuItemPortugues = new javax.swing.JMenuItem();
        menuConfiguracion = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 650));

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 731, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
        );

        getContentPane().add(desktopPane, java.awt.BorderLayout.CENTER);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("view.messages.view"); // NOI18N
        menuCliente.setText(bundle.getString("menu_Client")); // NOI18N

        menuItemBuscarCliente.setText(bundle.getString("menuItem_SearchClient")); // NOI18N
        menuItemBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBuscarClienteActionPerformed(evt);
            }
        });
        menuCliente.add(menuItemBuscarCliente);

        menuItemListarTodosCliente.setText(bundle.getString("menuItem_ListAll")); // NOI18N
        menuItemListarTodosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemListarTodosClienteActionPerformed(evt);
            }
        });
        menuCliente.add(menuItemListarTodosCliente);
        menuCliente.add(jSeparator1);

        menuItemNuevoCliente.setText(bundle.getString("menuItem_NewClient")); // NOI18N
        menuItemNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevoClienteActionPerformed(evt);
            }
        });
        menuCliente.add(menuItemNuevoCliente);

        menuBar.add(menuCliente);

        menuVentanas.setText(bundle.getString("menu_Windows")); // NOI18N

        menuItemCerrar.setText(bundle.getString("menuItem_Close")); // NOI18N
        menuItemCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCerrarActionPerformed(evt);
            }
        });
        menuVentanas.add(menuItemCerrar);

        menuItemCerrarTodo.setText(bundle.getString("menuItem_CloseAll")); // NOI18N
        menuItemCerrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCerrarTodoActionPerformed(evt);
            }
        });
        menuVentanas.add(menuItemCerrarTodo);
        menuVentanas.add(jSeparator2);

        menuItemCascada.setText(bundle.getString("menuItem_Waterfall")); // NOI18N
        menuItemCascada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCascadaActionPerformed(evt);
            }
        });
        menuVentanas.add(menuItemCascada);

        menuBar.add(menuVentanas);

        menuUsuario.setText(bundle.getString("menu_User")); // NOI18N
        menuUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuUsuarioActionPerformed(evt);
            }
        });

        menuItemBuscarUsuario.setText(bundle.getString("menuItem_SearchUser")); // NOI18N
        menuItemBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBuscarUsuarioActionPerformed(evt);
            }
        });
        menuUsuario.add(menuItemBuscarUsuario);

        menuItemListarTodosUsuario.setText(bundle.getString("menuItem_ListAll")); // NOI18N
        menuItemListarTodosUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemListarTodosUsuarioActionPerformed(evt);
            }
        });
        menuUsuario.add(menuItemListarTodosUsuario);
        menuUsuario.add(jSeparator3);

        menuItemNuevoUsuario.setText(bundle.getString("menuItem_NewUser")); // NOI18N
        menuItemNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevoUsuarioActionPerformed(evt);
            }
        });
        menuUsuario.add(menuItemNuevoUsuario);

        menuBar.add(menuUsuario);

        menuLengua.setText(bundle.getString("menu_Languages")); // NOI18N

        menuItemCastellano.setText(bundle.getString("menuItem_Spanish")); // NOI18N
        menuItemCastellano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCastellanoActionPerformed(evt);
            }
        });
        menuLengua.add(menuItemCastellano);

        menuItemIngles.setText(bundle.getString("menuItem_English")); // NOI18N
        menuItemIngles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemInglesActionPerformed(evt);
            }
        });
        menuLengua.add(menuItemIngles);

        menuItemPortugues.setText(bundle.getString("menuItem_Portugues")); // NOI18N
        menuItemPortugues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPortuguesActionPerformed(evt);
            }
        });
        menuLengua.add(menuItemPortugues);

        menuBar.add(menuLengua);

        menuConfiguracion.setText(bundle.getString("menu_Settings")); // NOI18N
        menuBar.add(menuConfiguracion);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevoClienteActionPerformed
        clienteControl.crearFrameAnadir();
    }//GEN-LAST:event_menuItemNuevoClienteActionPerformed

    private void menuItemBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemBuscarClienteActionPerformed
        clienteControl.crearFrameBuscar();
    }//GEN-LAST:event_menuItemBuscarClienteActionPerformed

    private void menuItemCascadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCascadaActionPerformed
        restartXY();
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            frame.setLocation(getPositionX(), getPositionY());
        }
    }//GEN-LAST:event_menuItemCascadaActionPerformed

    private void menuItemCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCerrarActionPerformed
        JInternalFrame frame = desktopPane.getSelectedFrame();
        if (frame != null) {
            clienteControl.closeFrame(frame);
            usuarioControl.closeFrame(frame);
            frame.dispose();
        }
    }//GEN-LAST:event_menuItemCerrarActionPerformed

    private void menuItemCerrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCerrarTodoActionPerformed
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            clienteControl.closeFrame(frame);
            usuarioControl.closeFrame(frame);
            frame.dispose();
        }
    }//GEN-LAST:event_menuItemCerrarTodoActionPerformed

    private void menuItemListarTodosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemListarTodosClienteActionPerformed
        clienteControl.listarTodos();
    }//GEN-LAST:event_menuItemListarTodosClienteActionPerformed

    private void menuItemBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemBuscarUsuarioActionPerformed
        usuarioControl.crearFrameBuscar();
    }//GEN-LAST:event_menuItemBuscarUsuarioActionPerformed

    private void menuItemListarTodosUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemListarTodosUsuarioActionPerformed
        usuarioControl.listarTodos();
    }//GEN-LAST:event_menuItemListarTodosUsuarioActionPerformed

    private void menuItemNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevoUsuarioActionPerformed
        usuarioControl.crearFrameAnadir();
    }//GEN-LAST:event_menuItemNuevoUsuarioActionPerformed

    private void menuUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUsuarioActionPerformed

    }//GEN-LAST:event_menuUsuarioActionPerformed

    private void menuItemCastellanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCastellanoActionPerformed
        Locale.setDefault(new Locale("es"));
        reset();
    }//GEN-LAST:event_menuItemCastellanoActionPerformed

    private void menuItemInglesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemInglesActionPerformed
        Locale.setDefault(Locale.ENGLISH);
        reset();
    }//GEN-LAST:event_menuItemInglesActionPerformed

    private void menuItemPortuguesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPortuguesActionPerformed
        Locale.setDefault(new Locale("pt"));
        reset();
    }//GEN-LAST:event_menuItemPortuguesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuCliente;
    private javax.swing.JMenu menuConfiguracion;
    private javax.swing.JMenuItem menuItemBuscarCliente;
    private javax.swing.JMenuItem menuItemBuscarUsuario;
    private javax.swing.JMenuItem menuItemCascada;
    private javax.swing.JMenuItem menuItemCastellano;
    private javax.swing.JMenuItem menuItemCerrar;
    private javax.swing.JMenuItem menuItemCerrarTodo;
    private javax.swing.JMenuItem menuItemIngles;
    private javax.swing.JMenuItem menuItemListarTodosCliente;
    private javax.swing.JMenuItem menuItemListarTodosUsuario;
    private javax.swing.JMenuItem menuItemNuevoCliente;
    private javax.swing.JMenuItem menuItemNuevoUsuario;
    private javax.swing.JMenuItem menuItemPortugues;
    private javax.swing.JMenu menuLengua;
    private javax.swing.JMenu menuUsuario;
    private javax.swing.JMenu menuVentanas;
    // End of variables declaration//GEN-END:variables
}
