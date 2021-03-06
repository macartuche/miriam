/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.ui;
 
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ortega.miriam.controladores.EntidadJpaController;
import ortega.miriam.controladores.UsuariosJpaController;
import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import ortega.miriam.entidades.Entidad;
import ortega.miriam.entidades.Usuarios;

/**
 *
 */
public class UsuarioPanel extends javax.swing.JPanel {

    public static UsuariosJpaController controlador;

    public static EntidadJpaController controladorEntidad;

    /**
     * Creates new form RolPanel
     */
    public UsuarioPanel() {
        initComponents();
        controlador = new UsuariosJpaController();
        controladorEntidad = new EntidadJpaController();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager1 = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("facturacionMueblesDesktopPU").createEntityManager();
        getRowsQuery = java.beans.Beans.isDesignTime() ? null : entityManager1.createQuery("Select u from Usuarios u");
        rowCountQuery = java.beans.Beans.isDesignTime() ? null : entityManager1.createQuery("Select count(u) from Usuarios u");
        list1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : getRowsQuery.getResultList();
        list1=getList();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        nuevoBtn1 = new javax.swing.JButton();
        editarBtn1 = new javax.swing.JButton();
        eliminarBtn1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        identificacionTxt = new javax.swing.JTextField();
        nombresTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Abadi MT Condensed Extra Bold", 0, 24))); // NOI18N
        jPanel1.setLayout(null);

        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list1, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${entidadid.nombres}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${entidadid.identificacion}"));
        columnBinding.setColumnName("Identificación");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${entidadid.correo}"));
        columnBinding.setColumnName("Correo");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${username}"));
        columnBinding.setColumnName("Usuario");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(240);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(20, 230, 720, 200);

        nuevoBtn1.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        nuevoBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ortega/miriam/imagenes/add1.png"))); // NOI18N
        nuevoBtn1.setText("Nuevo");
        nuevoBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoBtn1ActionPerformed(evt);
            }
        });
        jPanel1.add(nuevoBtn1);
        nuevoBtn1.setBounds(380, 170, 120, 50);

        editarBtn1.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        editarBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ortega/miriam/imagenes/edit.png"))); // NOI18N
        editarBtn1.setText("Editar");
        editarBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarBtn1ActionPerformed(evt);
            }
        });
        jPanel1.add(editarBtn1);
        editarBtn1.setBounds(500, 170, 120, 50);

        eliminarBtn1.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        eliminarBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ortega/miriam/imagenes/delete.png"))); // NOI18N
        eliminarBtn1.setText("Eliminar");
        eliminarBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarBtn1ActionPerformed(evt);
            }
        });
        jPanel1.add(eliminarBtn1);
        eliminarBtn1.setBounds(620, 170, 120, 50);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Abadi MT Condensed Extra Bold", 0, 18))); // NOI18N
        jPanel2.setLayout(null);

        jLabel1.setText("Identificación:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(20, 50, 110, 16);
        jPanel2.add(identificacionTxt);
        identificacionTxt.setBounds(130, 40, 160, 40);
        jPanel2.add(nombresTxt);
        nombresTxt.setBounds(370, 40, 170, 40);

        jLabel2.setText("Nombres:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(300, 50, 90, 16);

        jButton1.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ortega/miriam/imagenes/search.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(540, 40, 110, 50);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(30, 40, 710, 110);

        add(jPanel1);
        jPanel1.setBounds(10, 10, 760, 460);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private final Usuarios getIndex() {
        int index = jTable1.getSelectedRow();
        Usuarios user = null;
        if (index != -1) { 
            user = list1.get(index);
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        return user;
    }
    private void nuevoBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoBtn1ActionPerformed
        Usuarios user = new Usuarios();
        user.setEntidadid(new Entidad());
        abrirFormulario(user);
    }//GEN-LAST:event_nuevoBtn1ActionPerformed

    private void editarBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarBtn1ActionPerformed
        Usuarios user = getIndex();
        if (user != null) {
            abrirFormulario(user);
        }
    }//GEN-LAST:event_editarBtn1ActionPerformed

    private void eliminarBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBtn1ActionPerformed
        Usuarios user = getIndex();
        if (user != null) {
            String message = "Desea eliminar el registro?";
            String title = "Confirmar eliminación";
            int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    String tipoMensaje = "";
                    String tituloMensaje = "";
                    int tipoMens = 0;
                    controlador.destroy(user.getId());
                    tipoMensaje = "El registro ha sido actualizado con éxito";
                    tituloMensaje = "Información";
                    tipoMens = JOptionPane.INFORMATION_MESSAGE;
                    
                    JOptionPane.showMessageDialog(null, tipoMensaje, tituloMensaje, tipoMens);
                    actualizar();
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(UsuarioPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_eliminarBtn1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String identificacion = this.identificacionTxt.getText();
        String nombres = this.nombresTxt.getText();
        StringBuilder consulta = new StringBuilder();
        StringBuilder consultaCont = new StringBuilder();
        
        consulta.append("Select u from Usuarios u where 1=1 ");
        consultaCont.append("Select count(u) from Usuarios u where 1=1 ");
        
        HashMap<String, Object> parametros = new HashMap<>();
        HashMap<String, Object> parametrosCount = new HashMap<>();
        if (identificacion.length() >= 8) {
            consulta.append(" and lower(u.entidadid.identificacion) like :identificacion ");
            consultaCont.append(" and lower(u.entidadid.identificacion) like :identificacion ");
            parametros.put("identificacion", "%" + identificacion.toLowerCase() + "%");
            parametrosCount.put("identificacion", "%" + identificacion.toLowerCase() + "%");
            
        }
        
        
        if(!nombres.isEmpty()){
            consulta.append(" and lower(u.entidadid.nombres) like :nombre ");
            consultaCont.append(" and lower(u.entidadid.nombres) like :nombre");
            parametros.put("nombre", "%" + nombres.toLowerCase() + "%");
            parametrosCount.put("nombre", "%" + nombres.toLowerCase() + "%");
        }

          
        getRowsQuery = entityManager1.createQuery(consulta.toString());
        for (String key : parametros.keySet()) {
            getRowsQuery.setParameter(key, parametros.get(key));
        }
        rowCountQuery =  entityManager1.createQuery(consultaCont.toString());
        for (String key : parametros.keySet()) {
            rowCountQuery.setParameter(key, parametros.get(key));
        }
        actualizar();   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void abrirFormulario(final Usuarios usuario) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UsuarioForm dialog = new UsuarioForm(new javax.swing.JFrame(), true, usuario);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {

                    }
                });
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }

    public static void crear(Usuarios user) {
        String tipoMensaje = "";
        String tituloMensaje = "";
        int tipoMens = 0;
        controladorEntidad.create(user.getEntidadid());
        controlador.create(user);
        tipoMensaje = "El registro ha sido actualizado con éxito";
        tituloMensaje = "Información";
        tipoMens = JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(null, tipoMensaje, tituloMensaje, tipoMens);
        actualizar();
    }

    public static void editar(Usuarios user) {
        String tipoMensaje = "";
        String tituloMensaje = "";
        int tipoMens = 0;
        try {
            controladorEntidad.edit(user.getEntidadid());
            controlador.edit(user);
            tipoMensaje = "El registro ha sido actualizado con éxito";
            tituloMensaje = "Información";
            tipoMens = JOptionPane.INFORMATION_MESSAGE;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RolForm.class.getName()).log(Level.SEVERE, null, ex);
            tipoMensaje = "Existe un problema al modificar el registro";
            tituloMensaje = "Error";
            tipoMens = JOptionPane.ERROR_MESSAGE;
        } catch (Exception ex) {
            Logger.getLogger(RolForm.class.getName()).log(Level.SEVERE, null, ex);
            tipoMensaje = "Existe un problema al modificar el registro";
            tituloMensaje = "Error";
            tipoMens = JOptionPane.ERROR_MESSAGE;
        }
        JOptionPane.showMessageDialog(null, tipoMensaje, tituloMensaje, tipoMens);
        actualizar();
    }

    public static void actualizar() {

        List<Usuarios> users = getList();
        for (Usuarios user : users) {
            entityManager1.refresh(user);
        }
        list1 = users;

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list1, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${entidadid.nombres}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${entidadid.identificacion}"));
        columnBinding.setColumnName("Identificación");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${entidadid.correo}"));
        columnBinding.setColumnName("Correo");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${username}"));
        columnBinding.setColumnName("Usuario");
        columnBinding.setColumnClass(String.class);
//        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
//        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) { 
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(240);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editarBtn1;
    private javax.swing.JButton eliminarBtn1;
    public static javax.persistence.EntityManager entityManager1;
    public static javax.persistence.Query getRowsQuery;
    private javax.swing.JTextField identificacionTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    public static java.util.List<Usuarios> list1;
    private javax.swing.JTextField nombresTxt;
    private javax.swing.JButton nuevoBtn1;
    private static javax.persistence.Query rowCountQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public static List<Usuarios> getList() {
        List<Usuarios> roles = new ResultListJPA<>(rowCountQuery, getRowsQuery);
        return roles;
    }

}
