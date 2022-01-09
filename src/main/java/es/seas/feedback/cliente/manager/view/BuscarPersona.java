package es.seas.feedback.cliente.manager.view;

import es.seas.feedback.cliente.manager.control.PersonaControl;

/**
 * Este extende JDialog y es la resposable de hacer las búsquedas seleccionadas
 * por el usuario.
 * @author Ricardo Maximino
 */
public class BuscarPersona extends javax.swing.JDialog {

    private PersonaControl<?> control;
    private final PersonaUtilidades personaUtilidades;
    /**
     * Este constructor configura todas las variable globales e inicia lo
     * componentes.
     * @param parent del tipo java.awt.Frame.
     * @param modal del tipo boolean.
     * @param personUtilidades del tipo 
     * es.seas.feedback.cliente.manager.view.PersonaUtilidades.
     * @param control del tipo
     * es.seas.feedback.cliente.manager.control.PersonaControl.
     */
    public BuscarPersona(java.awt.Frame parent, boolean modal,PersonaUtilidades personUtilidades,PersonaControl<?> control) {
        super(parent, modal);
        initComponents();
        this.personaUtilidades = personUtilidades;
        cmbCamposCliente.setModel(personaUtilidades.camposPersonaComboModel());
        this.control = control;
    }

    /**
     * Este metodo retorna el valor de la variable global control.
     * @return del tipo es.seas.feedback.cliente.manager.control.PersonaControl
     */
    public PersonaControl<?> getControl() {
        return control;
    }

    /**
     * Este metodo configura la variable global control.
     * @param control del tipo 
     * es.seas.feedback.cliente.manager.control.PersonaControl.
     */
    public void setControl(PersonaControl<?> control) {
        this.control = control;
    }

    /**
     * Este metodo limpia el JTextField txtBuscar.
     */
    public void clear(){
        txtBuscar.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        lblCampo = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        cmbCamposCliente = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblCampo.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("view.messages.view"); // NOI18N
        lblCampo.setText(bundle.getString("label_SearchFor")); // NOI18N

        txtBuscar.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        btnBuscar.setText(bundle.getString("button_Search")); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        cmbCamposCliente.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        cmbCamposCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCamposClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCampo)
                        .addGap(18, 18, 18)
                        .addComponent(cmbCamposCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCamposCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped

    }//GEN-LAST:event_txtBuscarKeyTyped

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
       if(evt.getKeyCode() == 10){
           this.dispose();
           buscar();
       }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        dispose();  buscar();   
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void cmbCamposClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCamposClienteActionPerformed
        if(cmbCamposCliente.getSelectedItem().toString().equals(personaUtilidades.getCamposPersona()[5])||
                cmbCamposCliente.getSelectedItem().toString().equals(personaUtilidades.getCamposPersona()[4])){
            txtBuscar.setEnabled(false);
        }else{
            txtBuscar.setEnabled(true);
        }
    }//GEN-LAST:event_cmbCamposClienteActionPerformed

    public void buscar(){   
        control.buscar(cmbCamposCliente.getSelectedItem().toString(), txtBuscar.getText());  
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox<String> cmbCamposCliente;
    private javax.swing.JLabel lblCampo;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
