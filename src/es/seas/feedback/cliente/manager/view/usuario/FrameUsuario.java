/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.view.usuario;

import es.seas.feedback.cliente.manager.control.UsuarioControl;
import es.seas.feedback.cliente.manager.model.Contacto;
import es.seas.feedback.cliente.manager.model.Direccion;
import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.model.Usuario;
import es.seas.feedback.cliente.manager.view.PersonaUtilidades;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.xml.bind.ValidationException;

/**
 *
 * @author Ricardo
 */
public class FrameUsuario extends javax.swing.JInternalFrame {
    public static final String TELEFONO = "Telefono";
    public static final String EMAIL = "E-mail";
    private UsuarioControl control;
    private Map<String, Provincia> provincias;
    private Usuario usuario;
    private PersonaUtilidades personaUtilidades;
    private final ResourceBundle view = ResourceBundle.getBundle("es.seas.feedback.cliente.manager.view.internationalization.view");

    /**
     * Creates new form Cliente
     *
     * @param prov
     * @param personaUtilidades
     */
    public FrameUsuario(Map<String, Provincia> prov, PersonaUtilidades personaUtilidades) {
        initComponents();
        this.personaUtilidades = personaUtilidades;
        provincias = prov;
        initCombos();
    }

    public UsuarioControl getControl() {
        return control;
    }

    public Usuario getKeyUsuario() {
        return usuario;
    }

    public void setControl(UsuarioControl control) {
        this.control = control;
    }

    public Map<String, Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(Map<String, Provincia> prov) {
        this.provincias = prov;
    }

    public PersonaUtilidades getPersonaUtilidades() {
        return personaUtilidades;
    }

    public void setPersonaUtilidades(PersonaUtilidades personaUtilidades) {
        this.personaUtilidades = personaUtilidades;
    }

    
    
    public void setUsuario(Usuario usu) {
        if (usu != null) {
            this.usuario = usu;
            txtNIF.setText(usu.getNif());
            txtNombre.setText(usu.getNombre());
            txtPrimerApellido.setText(usu.getPrimerApellido());
            txtSegundoApellido.setText(usu.getSegundoApellido());

            cmbDia.setSelectedItem("" + usu.getFechaNacimiento().getDayOfMonth());
            cmbMes.setSelectedItem(personaUtilidades.getMesesDelAño()[usu.getFechaNacimiento().getMonth().getValue() - 1]);
            cmbAño.setSelectedItem("" + usu.getFechaNacimiento().getYear());

            chkActivo.setSelected(usu.isActivo());
            Contacto contacto;
            Iterator<Contacto> iterator = usu.getContactos().iterator();
            while (iterator.hasNext()) {
                contacto = iterator.next();
                if (contacto.getDescripcion().equals(TELEFONO)) {
                    txtTelefono.setText(contacto.getContacto());
                }

                if (contacto.getDescripcion().equals(EMAIL)) {
                    txtEmail.setText(contacto.getContacto());
                }
            }

            cmbProvincia.setSelectedItem(usu.getDirecion().getProvincia());
            cmbLocalidade.setSelectedItem(usu.getDirecion().getLocalidade());

            txtCP.setText(usu.getDirecion().getCp());
            txtDireccion.setText(usu.getDirecion().getDireccion());
            txtNumero.setText(usu.getDirecion().getNumero());

            txtAreaNota.setText(usu.getDirecion().getNota());
        }
    }

    public Usuario getUsuario() throws DateTimeException,ValidationException {
        Usuario newUsuario = new Usuario();
        Direccion direccion = new Direccion();
        Contacto telefono = new Contacto(TELEFONO, txtTelefono.getText());
        Contacto email = new Contacto(EMAIL, txtEmail.getText());

        //nuevo
        if(usuario != null && usuario.getContactos().size()>1){
            for(Contacto c: usuario.getContactos()){
                if(c.getDescripcion().toUpperCase().equals(TELEFONO)){
                    telefono.setId(c.getId());
                }
                if(c.getDescripcion().toUpperCase().equals(EMAIL)){
                    email.setId(c.getId());
                }
            }
        }
        newUsuario.setDirecion(direccion);
        newUsuario.getContactos().add(telefono);
        newUsuario.getContactos().add(email);

        //nuevo
        if(usuario != null) newUsuario.setId(usuario.getId());
        
        newUsuario.setNif(txtNIF.getText());
        newUsuario.setNombre(txtNombre.getText());
        newUsuario.setPrimerApellido(txtPrimerApellido.getText());
        newUsuario.setSegundoApellido(txtSegundoApellido.getText());

        newUsuario.setFechaNacimiento(LocalDate.of(Integer.parseInt(cmbAño.getSelectedItem().toString()), cmbMes.getSelectedIndex() + 1, Integer.parseInt(cmbDia.getSelectedItem().toString())));

        newUsuario.setActivo(chkActivo.isSelected());

        newUsuario.getDirecion().setProvincia((String) cmbProvincia.getSelectedItem());
        newUsuario.getDirecion().setLocalidade((String) cmbLocalidade.getSelectedItem());

        newUsuario.getDirecion().setCp(txtCP.getText());
        newUsuario.getDirecion().setDireccion(txtDireccion.getText());
        newUsuario.getDirecion().setNumero(txtNumero.getText());
        newUsuario.getDirecion().setNota(txtAreaNota.getText());

        if(txtRepitaContraseña.isEnabled()){
            if (String.copyValueOf(txtContraseña.getPassword()).equals(String.copyValueOf(txtRepitaContraseña.getPassword()))) {
                newUsuario.setContraseña(String.copyValueOf(txtContraseña.getPassword()));
                newUsuario.setLevel((int)sprPoderDeAcceso.getValue());
            } else {
                txtRepitaContraseña.setText("");
                txtContraseña.requestFocus();
                throw new ValidationException("");
            }
        }
        return newUsuario;
    }

    public void borrarEsVisible(boolean visible) {
        btnBorrarCliente.setVisible(visible);
    }

    public JTextField getTxtRepiteContraseña() {
        return txtRepitaContraseña;
    }

    public JSpinner getSprPoderDeAcceso() {
        return sprPoderDeAcceso;
    }

    @Override
    public void dispose(){
        control.closeFrame(this);
        super.dispose();
    }
    
      private void initCombos() {
        personaUtilidades.setComboDia(cmbDia);
        personaUtilidades.setComboMes(cmbMes);
        personaUtilidades.setComboAño(80, 33, cmbAño);

        personaUtilidades.setComboProvincia(provincias, "Alicante", cmbProvincia);
        String prov = cmbProvincia.getSelectedItem().toString();
        personaUtilidades.setComboLocalidade(provincias, prov, "Santa Pola", cmbLocalidade);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNIF = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtPrimerApellido = new javax.swing.JTextField();
        txtSegundoApellido = new javax.swing.JTextField();
        cmbDia = new javax.swing.JComboBox<>();
        cmbMes = new javax.swing.JComboBox<>();
        cmbAño = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        chkActivo = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbProvincia = new javax.swing.JComboBox<>();
        cmbLocalidade = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaNota = new javax.swing.JTextArea();
        txtCP = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnBorrarCliente = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        jLabel16 = new javax.swing.JLabel();
        txtRepitaContraseña = new javax.swing.JPasswordField();
        lblVerificaContraseña = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        sprPoderDeAcceso = new javax.swing.JSpinner();

        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 800));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("es/seas/feedback/cliente/manager/view/internationalization/view"); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("border_PersonalData"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText(bundle.getString("label_Id")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText(bundle.getString("label_Name")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText(bundle.getString("label_FirstLastname")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText(bundle.getString("label_Birthday")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText(bundle.getString("lable_SecondLastname")); // NOI18N

        txtNIF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNIFKeyPressed(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });

        txtPrimerApellido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPrimerApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrimerApellidoKeyPressed(evt);
            }
        });

        txtSegundoApellido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSegundoApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSegundoApellidoKeyPressed(evt);
            }
        });

        cmbDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDiaActionPerformed(evt);
            }
        });
        cmbDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDiaKeyPressed(evt);
            }
        });

        cmbMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMesActionPerformed(evt);
            }
        });
        cmbMes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMesKeyPressed(evt);
            }
        });

        cmbAño.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAñoKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText(bundle.getString("label_Phone")); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText(bundle.getString("label_Email")); // NOI18N

        txtTelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyPressed(evt);
            }
        });

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        chkActivo.setText(bundle.getString("check_Active")); // NOI18N
        chkActivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chkActivoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbMes, 0, 148, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(chkActivo)
                        .addGap(198, 198, 198))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail)
                        .addGap(229, 229, 229))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtSegundoApellido)
                                        .addGap(68, 68, 68))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNIF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkActivo))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("border_Direction"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText(bundle.getString("label_Province")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText(bundle.getString("label_Town")); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText(bundle.getString("label_Note")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText(bundle.getString("label_Direction")); // NOI18N

        txtDireccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDireccionKeyPressed(evt);
            }
        });

        txtNumero.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroKeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText(bundle.getString("label_Number")); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText(bundle.getString("label_PostCode")); // NOI18N

        cmbProvincia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinciaActionPerformed(evt);
            }
        });
        cmbProvincia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbProvinciaKeyPressed(evt);
            }
        });

        cmbLocalidade.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbLocalidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbLocalidadeKeyPressed(evt);
            }
        });

        txtAreaNota.setColumns(20);
        txtAreaNota.setRows(5);
        txtAreaNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAreaNotaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtAreaNota);

        txtCP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(19, 19, 19))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(6, 6, 6)
                            .addComponent(cmbLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(6, 6, 6)
                            .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 50, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnAceptar.setText(bundle.getString("button_Accept")); // NOI18N
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText(bundle.getString("button_Cancel")); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnBorrarCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBorrarCliente.setText(bundle.getString("button_Delete")); // NOI18N
        btnBorrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarClienteActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("border_AccessData"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText(bundle.getString("label_Password")); // NOI18N

        txtContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContraseñaKeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText(bundle.getString("label_RepitePassword")); // NOI18N

        txtRepitaContraseña.setEnabled(false);
        txtRepitaContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRepitaContraseñaKeyPressed(evt);
            }
        });

        lblVerificaContraseña.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText(bundle.getString("label_PowerOfAccess")); // NOI18N

        sprPoderDeAcceso.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sprPoderDeAcceso.setEnabled(false);
        sprPoderDeAcceso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sprPoderDeAccesoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRepitaContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sprPoderDeAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(lblVerificaContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sprPoderDeAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtRepitaContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)))
                .addComponent(lblVerificaContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBorrarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar)
                    .addComponent(btnBorrarCliente))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        if (evt.getKeyCode() == 10) {
            txtPrimerApellido.requestFocus();
        }
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtPrimerApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimerApellidoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtSegundoApellido.requestFocus();
        }
    }//GEN-LAST:event_txtPrimerApellidoKeyPressed

    private void txtSegundoApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoApellidoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtNIF.requestFocus();
        }
    }//GEN-LAST:event_txtSegundoApellidoKeyPressed

    private void txtNIFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNIFKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbDia.requestFocus();
        }
    }//GEN-LAST:event_txtNIFKeyPressed

    private void cmbDiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDiaKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbMes.requestFocus();
        }
    }//GEN-LAST:event_cmbDiaKeyPressed

    private void cmbMesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMesKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbAño.requestFocus();
        }
    }//GEN-LAST:event_cmbMesKeyPressed

    private void cmbAñoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAñoKeyPressed
        if (evt.getKeyCode() == 10) {
            chkActivo.requestFocus();
        }
    }//GEN-LAST:event_cmbAñoKeyPressed

    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        if (evt.getKeyCode() == 10) {
            txtEmail.requestFocus();
        }
    }//GEN-LAST:event_txtTelefonoKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        if (evt.getKeyCode() == 10) {
            txtContraseña.requestFocus();
        }
    }//GEN-LAST:event_txtEmailKeyPressed

    private void cmbProvinciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbProvinciaKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbLocalidade.requestFocus();
        }
    }//GEN-LAST:event_cmbProvinciaKeyPressed

    private void cmbLocalidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbLocalidadeKeyPressed
        if (evt.getKeyCode() == 10) {
            txtCP.requestFocus();
        }
    }//GEN-LAST:event_cmbLocalidadeKeyPressed

    private void txtCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPKeyPressed
        if (evt.getKeyCode() == 10) {
            txtDireccion.requestFocus();
        }
    }//GEN-LAST:event_txtCPKeyPressed

    private void txtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyPressed
        if (evt.getKeyCode() == 10) {
            txtNumero.requestFocus();
        }
    }//GEN-LAST:event_txtDireccionKeyPressed

    private void txtNumeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyPressed
        if (evt.getKeyCode() == 10) {
            txtAreaNota.requestFocus();
        }
    }//GEN-LAST:event_txtNumeroKeyPressed

    private void txtAreaNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaNotaKeyPressed
        if (evt.getKeyCode() == 10) {
            btnAceptar.requestFocus();
        }
    }//GEN-LAST:event_txtAreaNotaKeyPressed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {
            Usuario usu = getUsuario();
            if (control.guardar(usu)) {
                JOptionPane.showMessageDialog(this, msgUsuarioGuardado(usu));
                this.dispose();           
            }          
        } catch (DateTimeException ex) {
            JOptionPane.showMessageDialog(this, msgErrorDeFecha());
            cmbDia.requestFocus();
        }catch (ValidationException ex){
            JOptionPane.showMessageDialog(this, msgErrorDeFecha());
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

        private String msgUsuarioGuardado(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append(view.getString("message_Registry"));
        sb.append(usuario.getNombre());
        sb.append(" ");
        sb.append(usuario.getPrimerApellido());
        sb.append(" ");
        sb.append(usuario.getSegundoApellido());
        sb.append(" ");
        sb.append(view.getString("message_was_saved_successfully"));
        return sb.toString();
    }

    private String msgErrorDeFecha() {
        StringBuilder sb = new StringBuilder();
        sb.append(cmbDia.getSelectedItem());
        sb.append(" ");
        sb.append(view.getString("message_of"));
        sb.append(" ");
        sb.append(cmbMes.getSelectedItem());
        sb.append(" ");
        sb.append(view.getString("message_of"));
        sb.append(" ");
        sb.append(cmbAño.getSelectedItem());
        sb.append(", ");
        sb.append(view.getString("message_is_not_a_valid_date"));
        sb.append(".");
        return sb.toString();
    }
    
    private void cmbDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDiaActionPerformed

    }//GEN-LAST:event_cmbDiaActionPerformed

    private void cmbProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciaActionPerformed
        personaUtilidades.setComboLocalidade(provincias, cmbProvincia.getSelectedItem().toString(), null, cmbLocalidade);
    }//GEN-LAST:event_cmbProvinciaActionPerformed

    private void cmbMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMesActionPerformed

    }//GEN-LAST:event_cmbMesActionPerformed

    private void chkActivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chkActivoKeyPressed
        txtTelefono.requestFocus();
    }//GEN-LAST:event_chkActivoKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBorrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarClienteActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this, view.getString("message_Are_you_sure_want_delete_this_registry"));
        try{
            if (respuesta == 0) {
                Usuario usu = getUsuario();
                if (control.getServicio().borrarRegistro(usu)) {
                    JOptionPane.showMessageDialog(this, usu + view.getString("message_was_delete_from_the_database"));
                    dispose();              
                } else {
                    JOptionPane.showMessageDialog(this, usu + view.getString("message_could_not_be_delete_try_later"));
                }
            }
        }catch(DateTimeException | ValidationException ex){
            if( ex instanceof DateTimeException){
                JOptionPane.showMessageDialog(this, msgErrorDeFecha());
                cmbDia.requestFocus();
            }
            if(ex instanceof ValidationException){
                JOptionPane.showMessageDialog(this, view.getString("message_The_fields_password_repitePassword_must_be_equals"));
            }
        
        }
    }//GEN-LAST:event_btnBorrarClienteActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        control.closeFrame(this);
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtContraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaKeyPressed
        if (evt.getKeyCode() == 10) {
            if (txtContraseña.getPassword().length == 0) {
                cmbProvincia.requestFocus();
            } else if (!txtRepitaContraseña.isEnabled()) {
                if (getKeyUsuario().getContraseña().equals(String.copyValueOf(txtContraseña.getPassword()))) {
                    txtRepitaContraseña.setEnabled(true);
                    txtRepitaContraseña.requestFocus();
                    sprPoderDeAcceso.setEnabled(true);
                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(usuario.getLevel(), 0, 5, 1);
                    sprPoderDeAcceso.setModel(spinnerModel);
                    sprPoderDeAcceso.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, view.getString("message_to_change_password_first_enter_the_password"));
                    txtContraseña.requestFocus();
                }
            } else {
                txtRepitaContraseña.requestFocus();
            }
        }
    }//GEN-LAST:event_txtContraseñaKeyPressed

    private void txtRepitaContraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRepitaContraseñaKeyPressed
        if (evt.getKeyCode() == 10) {
            if (String.copyValueOf(txtContraseña.getPassword()).equals(String.copyValueOf(txtRepitaContraseña.getPassword()))) {
                sprPoderDeAcceso.requestFocus();
            } else {
                txtRepitaContraseña.setText("");
                txtContraseña.requestFocus();
                JOptionPane.showMessageDialog(null, view.getString("message_The_fields_password_repitePassword_must_be_equals"));
            }
        }
    }//GEN-LAST:event_txtRepitaContraseñaKeyPressed

    private void sprPoderDeAccesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sprPoderDeAccesoKeyPressed
        if (evt.getKeyCode() == 10) {
            cmbProvincia.requestFocus();
        }
    }//GEN-LAST:event_sprPoderDeAccesoKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBorrarCliente;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox<String> cmbAño;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<String> cmbLocalidade;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JComboBox<String> cmbProvincia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblVerificaContraseña;
    private javax.swing.JSpinner sprPoderDeAcceso;
    private javax.swing.JTextArea txtAreaNota;
    private javax.swing.JTextField txtCP;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNIF;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtPrimerApellido;
    private javax.swing.JPasswordField txtRepitaContraseña;
    private javax.swing.JTextField txtSegundoApellido;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
