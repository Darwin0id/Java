/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.darwin.component;

import hr.algebra.utils.FrameUtils;
import hr.darwin.UserFrame;
import hr.darwin.dal.RepositoryFactory;
import hr.algebra.utils.MessageUtils;
import hr.darwin.handler.actor.IActor;
import hr.darwin.handler.director.IDirector;
import hr.darwin.model.Actor;
import hr.darwin.model.ActorTableModel;
import hr.darwin.model.Director;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author darwin
 */
public class EditPerson extends javax.swing.JFrame {

    private List<JTextComponent> validationFields;
    private List<JLabel> errorLabels;
    
    private static final String REDATELJ = "Redatelj";
    private static final String GLUMAC = "Glumac";
    
    private final String[] cbItem = {REDATELJ, GLUMAC};
    
    private IActor actorHandler;
    private IDirector directorHandler;

    private ActorTableModel personTableModel;
    private Actor selectedPerson;
       
    /**
     * Creates new form EditActor
     */
    public EditPerson() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfFirstName = new javax.swing.JTextField();
        lblFirstNameError = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        tfLastName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblLastNameError = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPerson = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cbType = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuEdit = new javax.swing.JMenu();
        miEditMovie = new javax.swing.JMenuItem();
        miEditActor = new javax.swing.JMenuItem();
        miEditGenre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setText("Ime");

        lblFirstNameError.setForeground(new java.awt.Color(255, 0, 0));

        btnAdd.setBackground(new java.awt.Color(236, 97, 97));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Dodaj");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Ažuriraj");
        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(236, 97, 97), 2));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Obriši");
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(236, 97, 97), 2));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel3.setText("Prezime");

        lblLastNameError.setForeground(new java.awt.Color(255, 51, 51));

        tbPerson.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbPerson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPersonMouseClicked(evt);
            }
        });
        tbPerson.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPersonKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbPerson);

        jLabel4.setText("Tip");

        jPanel1.setBackground(new java.awt.Color(236, 97, 97));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        menuEdit.setText("Uredi");

        miEditMovie.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        miEditMovie.setText("Film");
        miEditMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditMovieActionPerformed(evt);
            }
        });
        menuEdit.add(miEditMovie);

        miEditActor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        miEditActor.setText("Glumci");
        menuEdit.add(miEditActor);

        miEditGenre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        miEditGenre.setText("Žanr");
        miEditGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditGenreActionPerformed(evt);
            }
        });
        menuEdit.add(miEditGenre);

        jMenuBar1.add(menuEdit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(0, 682, Short.MAX_VALUE))
                                    .addComponent(tfFirstName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFirstNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(102, 102, 102)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cbType, javax.swing.GroupLayout.Alignment.LEADING, 0, 705, Short.MAX_VALUE)
                                        .addComponent(tfLastName, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addGap(12, 12, 12)
                                .addComponent(lblLastNameError)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFirstNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLastNameError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbPersonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPersonKeyReleased
        showPerson();
    }//GEN-LAST:event_tbPersonKeyReleased

    private void tbPersonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPersonMouseClicked
        showPerson();
    }//GEN-LAST:event_tbPersonMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        init();
    }//GEN-LAST:event_formComponentShown

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (formValid()) {
            try {
                if (cbType.getSelectedIndex() == 1) {  
                    Actor actor = new Actor(tfFirstName.getText().trim(), tfLastName.getText().trim());

                    int actorCreate = actorHandler.createActor(actor);

                    if (actorCreate == 0) {
                        MessageUtils.showErrorMessage("Error", "Glumcina je u bazi!");
                    } else {
                        MessageUtils.showInformationMessage("Message", "Stvoren!");   
                        personTableModel.setPersons(actorHandler.selectEmployee());
                        clearForm();
                    }
                } else {
                    Director director = new Director(tfFirstName.getText().trim(), tfLastName.getText().trim());
                    int actorCreate = directorHandler.createDirector(director);

                    if (actorCreate == 0) {
                        MessageUtils.showErrorMessage("Error", "Redatelj postoji!");
                    } else {
                        MessageUtils.showInformationMessage("Message", "Stvoren!");   
                        personTableModel.setPersons(actorHandler.selectEmployee());
                        clearForm();
                    }
                }
                                    
            } catch (Exception e) {
                Logger.getLogger(EditPerson.class.getName()).log(Level.SEVERE, null, e);
                MessageUtils.showErrorMessage("Error", "Nejde brat!");
            }                    
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (formValid()) {
           try {
                selectedPerson.firstName = tfFirstName.getText().trim();
                selectedPerson.lastName = tfLastName.getText().trim();
                
                int updatePerson = actorHandler.updatePerson(selectedPerson);
            
               switch (updatePerson) {
                   case 1:
                       MessageUtils.showInformationMessage("Message", "Doktor je ažuriran!");
                       personTableModel.setPersons(actorHandler.selectEmployee());
                       clearForm();
                       break;
                   case 2:
                       MessageUtils.showInformationMessage("Message", "Glumac je ažuriran!");
                       personTableModel.setPersons(actorHandler.selectEmployee());
                       clearForm(); 
                       break;
                   default:
                       MessageUtils.showInformationMessage("Message", "Nejde!");
                       break;
               }
            } catch (Exception e) {
                Logger.getLogger(EditPerson.class.getName()).log(Level.SEVERE, null, e);
                MessageUtils.showErrorMessage("Error", "Nejde!");
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void miEditMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditMovieActionPerformed
        UserFrame userSite = new UserFrame();
        userSite.setVisible(true);
        FrameUtils.exit(this);
    }//GEN-LAST:event_miEditMovieActionPerformed

    private void miEditGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditGenreActionPerformed
        EditGenre editGenre = new EditGenre();
        editGenre.setVisible(true);
        FrameUtils.exit(this);
    }//GEN-LAST:event_miEditGenreActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
         if (MessageUtils.showConfirmDialog("Brisanje", "Dal to stvarno želiš?") == JOptionPane.YES_OPTION) {
            try {
                int personDelete = actorHandler.deletePerson(selectedPerson.id);
                if (personDelete == 0) {
                    MessageUtils.showInformationMessage("Message", "Dodanko bananko!");
                    personTableModel.setPersons(actorHandler.selectEmployee());
                    clearForm();
                }
            } catch (Exception e) {
                Logger.getLogger(EditPerson.class.getName()).log(Level.SEVERE, null, e);
                MessageUtils.showErrorMessage("Error", "Ne mogu obrisat osobu!");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(EditPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditPerson().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFirstNameError;
    private javax.swing.JLabel lblLastNameError;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem miEditActor;
    private javax.swing.JMenuItem miEditGenre;
    private javax.swing.JMenuItem miEditMovie;
    private javax.swing.JTable tbPerson;
    private javax.swing.JTextField tfFirstName;
    private javax.swing.JTextField tfLastName;
    // End of variables declaration//GEN-END:variables

    private void init() {
        try {
            cbType.setModel(new DefaultComboBoxModel(cbItem));
            initValidation();
            initRepository();
            initTable();
        } catch (Exception e) {
            Logger.getLogger(EditPerson.class.getName()).log(Level.SEVERE, null, e);
            MessageUtils.showErrorMessage("Smrt", "Ne radi karburator");
            System.exit(1);
        }
    }
    
    private void initValidation() {
        validationFields = Arrays.asList(tfFirstName, tfLastName);
        errorLabels = Arrays.asList(lblFirstNameError, lblLastNameError);
    }

    private void initRepository() throws Exception {
        actorHandler = RepositoryFactory.getActorHandler();
        directorHandler = RepositoryFactory.getDirectorHandler();
    }

    private void initTable() throws Exception {
        tbPerson.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbPerson.setAutoCreateRowSorter(true);
        tbPerson.setRowHeight(25);

    }

    private boolean formValid() {
        boolean ok = true;

        for (int i = 0; i < validationFields.size(); i++) {
            ok &= !validationFields.get(i).getText().trim().isEmpty();
            errorLabels.get(i).setText(validationFields.get(i).getText().trim().isEmpty() ? "X" : "");
        }

        return ok;
    }

    private void clearForm() {
        validationFields.forEach(e -> e.setText(""));
        errorLabels.forEach(e -> e.setText(""));

    }

    private void showPerson() {
        clearForm();
        int selectedRow = tbPerson.getSelectedRow();
        int selectedPersonID = (int) personTableModel.getValueAt(selectedRow, 0); 
        
        try {
            Optional<Actor> optPerson = actorHandler.selectPerson(selectedPersonID);
            if (optPerson.isPresent()) {
                selectedPerson = optPerson.get();
                fillForm(selectedPerson);
            }
        } catch (Exception ex) {
            Logger.getLogger(EditPerson.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Pogreška", "Ne mogu pokazati glumce!");
        }
    }
    
        private void fillForm(Actor person) {
        tfFirstName.setText(person.firstName);
        tfLastName.setText(person.lastName);
        
        if (person.type.equals(REDATELJ)) {
            cbType.setSelectedIndex(0);
        } else {
            cbType.setSelectedIndex(1);
        }       
    }
}
