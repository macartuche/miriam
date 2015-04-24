/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import ortega.miriam.entidades.Clientes;

/**
 *
 * @author macbookpro
 */
public class ClientForm extends javax.swing.JDialog {

    private Clientes cliente;
    
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 

    /**
     * Creates new form ClientForm
     */
    public ClientForm(java.awt.Frame parent, boolean modal, Clientes cliente) {
        super(parent, modal);
        initComponents();
        this.cliente = cliente;
        fijarCliente();
    }

    private void fijarCliente() {
        if (this.cliente.getEntidadid() != null) {
            this.nombreTxt.setText(this.cliente.getEntidadid().getNombres());
            this.cedulaTxt.setText(this.cliente.getEntidadid().getIdentificacion());
            this.mailTxt.setText(this.cliente.getEntidadid().getCorreo());
            this.telefonotxt.setText(this.cliente.getEntidadid().getTelefono());
            this.direccionTxt.setText(this.cliente.getEntidadid().getDireccion());
        }

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
        direccionTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        telefonotxt = new javax.swing.JTextField();
        mailTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cedulaTxt = new javax.swing.JTextField();
        nombreTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        guardarBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear editar clientes");
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos generales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Abadi MT Condensed Extra Bold", 1, 18))); // NOI18N
        jPanel1.setLayout(null);
        jPanel1.add(direccionTxt);
        direccionTxt.setBounds(160, 150, 200, 28);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Dirección:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 150, 130, 20);

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Teléfono:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 120, 130, 20);
        jPanel1.add(telefonotxt);
        telefonotxt.setBounds(160, 120, 200, 28);
        jPanel1.add(mailTxt);
        mailTxt.setBounds(160, 90, 200, 28);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Correo electrónico:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 90, 130, 20);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Cédula:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 60, 120, 16);
        jPanel1.add(cedulaTxt);
        cedulaTxt.setBounds(160, 60, 200, 28);
        jPanel1.add(nombreTxt);
        nombreTxt.setBounds(160, 30, 200, 28);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombres:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 30, 127, 16);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 380, 200);

        guardarBtn.setText("Guardar");
        guardarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarBtnActionPerformed(evt);
            }
        });
        getContentPane().add(guardarBtn);
        guardarBtn.setBounds(290, 220, 97, 29);

        setBounds(0, 0, 396, 270);
    }// </editor-fold>//GEN-END:initComponents

    private boolean esValido() {
        boolean valido = true;

        boolean cedulaCorrecta = validateCedula(this.cedulaTxt.getText());
        boolean camposLLenos = validarCampos();
        if (!camposLLenos) {
            return false;
        }
        
        if(!validarEmail(this.mailTxt.getText())){
           valido = false;
            JOptionPane.showMessageDialog(this, "El mail es incorrecto ", "Error", JOptionPane.ERROR_MESSAGE); 
        }
        
        if (!cedulaCorrecta) {
            valido = false;
            JOptionPane.showMessageDialog(this, "La cédula es incorrecta ", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return valido;
    }
    
    public static boolean validarEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }

    private boolean validarCampos() {
        boolean correcto = true;
        String nombre = this.nombreTxt.getText();
        String cedula = this.cedulaTxt.getText();
        String correo = this.mailTxt.getText();

        String campos = "";
        if (nombre.trim().isEmpty()) {
            campos += "- Campo nombre requerido \n";
            correcto = false;
        }

        if (cedula.trim().isEmpty()) {
            campos += "- Campo cedula requerido \n";
            correcto = false;
        }

        if (correo.trim().isEmpty()) {
            campos += "- Campo correo requerido \n";
            correcto = false;
        }

        if (!correcto) {
            JOptionPane.showMessageDialog(this, "Los siguientes campos son obligatorios: \n " + campos, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return correcto;

    }

    private boolean validateCedula(String identification) {
        boolean cedulaCorrecta = false;

        try {

            if (identification.length() == 10) {
                int tercerDigito = Integer.parseInt(identification.substring(2, 3));
                if (tercerDigito < 6) {

                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(identification.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (identification.length() - 1); i++) {
                        digito = Integer.parseInt(identification.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
        }
        return cedulaCorrecta;
    }

    private void guardarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarBtnActionPerformed
        if (!esValido()) {
            return;
        }

        cliente.getEntidadid().setNombres(this.nombreTxt.getText());
        cliente.getEntidadid().setIdentificacion(this.cedulaTxt.getText());
        cliente.getEntidadid().setCorreo(this.mailTxt.getText());
        cliente.getEntidadid().setDireccion(this.direccionTxt.getText());
        cliente.getEntidadid().setTelefono(this.telefonotxt.getText());

        //guardar
        if (cliente.getId() == null) {
            ClientePanel.crear(cliente);
        } else {
            ClientePanel.editar(cliente);
        }

        this.dispose();
    }//GEN-LAST:event_guardarBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClientForm dialog = new ClientForm(new javax.swing.JFrame(), true, new Clientes());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cedulaTxt;
    private javax.swing.JTextField direccionTxt;
    private javax.swing.JButton guardarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField mailTxt;
    private javax.swing.JTextField nombreTxt;
    private javax.swing.JTextField telefonotxt;
    // End of variables declaration//GEN-END:variables
}
