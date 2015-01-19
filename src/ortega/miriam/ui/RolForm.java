/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.ui;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import facturacionmueblesdesktop.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ortega.miriam.controladores.RolJpaController;
import ortega.miriam.entidades.Rol;

/**
 *
 * @author macbookpro
 */
public class RolForm extends javax.swing.JDialog {

    RolJpaController controlador;
    public Rol rol;

    /**
     * Creates new form RolDialog
     */
    public RolForm(java.awt.Frame parent, boolean modal, Rol rol) {
        super(parent, modal);
        this.rol = rol;
        initComponents();
        controlador = new RolJpaController();
        fijarRol();
    }

    private void fijarRol() {
        rolNombre.setText(rol.getNombre());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rolNombre = new javax.swing.JTextField();
        guardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear/editar Rol");

        guardar.setText("Guardar");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rolNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guardar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rolNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean esValido() {
        boolean valido = true;
        Rol buscar = new Rol();
        buscar.setNombre(this.rolNombre.getText());
        List<Rol> roles = controlador.find(buscar);
        if (roles.size() > 0) {
            valido = false;
            JOptionPane.showMessageDialog(this, "Ya existe un registro similar ", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return valido;
    }
    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed

        if (!esValido()) {
            return;
        }

        rol.setNombre(this.rolNombre.getText());
        
        //guardar
        if (rol.getId() == null) {
            RolPanel.crear(rol); 
        } else {
            RolPanel.editar(rol); 
        }
 
        this.dispose(); 
    }//GEN-LAST:event_guardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guardar;
    private javax.swing.JTextField rolNombre;
    // End of variables declaration//GEN-END:variables
}