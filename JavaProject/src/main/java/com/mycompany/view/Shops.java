/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.view;

import com.mycompany.controller.ShopController;
import com.mycompany.model.Shop;
import javax.swing.JOptionPane;

/**
 *
 * @author hernan
 */
public class Shops extends javax.swing.JFrame {

    /**
     * Creates new form Shops
     */
    public Shops() {
        initComponents();
        setLocationRelativeTo(null); 
        ShopController shopController = new ShopController(this, ShopTable);
        shopController.updateTable();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        ShopTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Event_Id = new javax.swing.JTextField();
        ManagerId = new javax.swing.JTextField();
        ShopName = new javax.swing.JTextField();
        addShops = new javax.swing.JButton();
        UpdateShops = new javax.swing.JButton();
        DeleteShops = new javax.swing.JButton();
        ShopsOptions = new javax.swing.JComboBox<>();
        back4 = new javax.swing.JButton();
        SearchShops = new javax.swing.JButton();
        textSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(246, 243, 235));

        ShopTable.setBackground(new java.awt.Color(236, 231, 215));
        ShopTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Category", "Event_Id", "Manager_Id"
            }
        ));
        jScrollPane1.setViewportView(ShopTable);

        jLabel1.setFont(new java.awt.Font("Bradley Hand", 0, 48)); // NOI18N
        jLabel1.setText("SHOPS AND RESTAURANTS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jLabel1)
                        .addGap(0, 186, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(246, 243, 235));

        jLabel5.setFont(new java.awt.Font("Bradley Hand", 0, 36)); // NOI18N
        jLabel5.setText("Add Comerce");

        jLabel6.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel6.setText("Name");

        jLabel7.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel7.setText("Event_id");

        jLabel8.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel8.setText("Category");

        jLabel9.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel9.setText("Manager Id");

        addShops.setBackground(new java.awt.Color(232, 224, 194));
        addShops.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        addShops.setText("Add");
        addShops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addShopsActionPerformed(evt);
            }
        });

        UpdateShops.setBackground(new java.awt.Color(232, 224, 194));
        UpdateShops.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        UpdateShops.setText("Update");
        UpdateShops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateShopsActionPerformed(evt);
            }
        });

        DeleteShops.setBackground(new java.awt.Color(232, 224, 194));
        DeleteShops.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        DeleteShops.setText("Delete");
        DeleteShops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteShopsActionPerformed(evt);
            }
        });

        ShopsOptions.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        ShopsOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Shop", "Restaurant" }));

        back4.setBackground(new java.awt.Color(232, 224, 194));
        back4.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        back4.setText("Back");
        back4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back4ActionPerformed(evt);
            }
        });

        SearchShops.setBackground(new java.awt.Color(232, 224, 194));
        SearchShops.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        SearchShops.setText("Search");
        SearchShops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchShopsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(back4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ShopName, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ShopsOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Event_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ManagerId, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(404, 404, 404)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(UpdateShops, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(DeleteShops, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addShops, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(75, 75, 75))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(SearchShops, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addShops, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(ShopName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Event_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(ManagerId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ShopsOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpdateShops, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(back4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(DeleteShops, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(SearchShops, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void back4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back4ActionPerformed
        Software soft = new Software();
        soft.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_back4ActionPerformed

    private void addShopsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addShopsActionPerformed
        try {
        String shopName = ShopName.getText();
        String category = ShopsOptions.getSelectedItem().toString();
        int eventId = Integer.parseInt(Event_Id.getText());
        int managerId = Integer.parseInt(ManagerId.getText());

        if (shopName.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.");
            return;
        }

        ShopController shopController = new ShopController(this, ShopTable);
        boolean success = shopController.createShop(shopName, category, eventId, managerId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Comercio creado exitosamente.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa números válidos para ID de evento y ID de gerente.");
    }
    }//GEN-LAST:event_addShopsActionPerformed

    private void UpdateShopsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateShopsActionPerformed
        try {
        int shopId = Integer.parseInt(textSearch.getText());  
        String shopName = ShopName.getText();                
        String category = ShopsOptions.getSelectedItem().toString(); 
        int eventId = Integer.parseInt(Event_Id.getText());  
        int managerId = Integer.parseInt(ManagerId.getText());  

        if (shopName.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.");
            return;
        }

        ShopController shopController = new ShopController(this, ShopTable);
        Shop shop = new Shop(shopName, category, eventId, managerId);

        boolean success = shopController.updateShop(shopId, shop);

        if (success) {
            JOptionPane.showMessageDialog(this, "Comercio actualizado exitosamente.");
            shopController.updateTable(); 
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el comercio.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa números válidos para ID de comercio, ID de evento y ID de gerente.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
    }//GEN-LAST:event_UpdateShopsActionPerformed

    private void DeleteShopsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteShopsActionPerformed
        try {
        int shopId = Integer.parseInt(textSearch.getText());

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este comercio?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; 
        }

        ShopController shopController = new ShopController(this, ShopTable);
        boolean success = shopController.deleteShop(shopId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Comercio eliminado exitosamente.");
            shopController.updateTable(); 
            ShopName.setText("");
            Event_Id.setText("");
            ManagerId.setText("");
            ShopsOptions.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el comercio.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.");
    }
    }//GEN-LAST:event_DeleteShopsActionPerformed

    private void SearchShopsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchShopsActionPerformed
        try {
        int shopId = Integer.parseInt(textSearch.getText());
        ShopController shopController = new ShopController(this, ShopTable);
        Shop shop = shopController.searchShop(shopId);

        if (shop != null) {
            ShopName.setText(shop.getName());
            ShopsOptions.setSelectedItem(shop.getCategory());
            Event_Id.setText(String.valueOf(shop.getEventId()));
            ManagerId.setText(String.valueOf(shop.getManagerId()));
        } else {
            JOptionPane.showMessageDialog(this, "Comercio no encontrado.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.");
    }
    }//GEN-LAST:event_SearchShopsActionPerformed

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
            java.util.logging.Logger.getLogger(Shops.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Shops.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Shops.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Shops.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Shops().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteShops;
    private javax.swing.JTextField Event_Id;
    private javax.swing.JTextField ManagerId;
    private javax.swing.JButton SearchShops;
    private javax.swing.JTextField ShopName;
    private javax.swing.JTable ShopTable;
    private javax.swing.JComboBox<String> ShopsOptions;
    private javax.swing.JButton UpdateShops;
    private javax.swing.JButton addShops;
    private javax.swing.JButton back4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField textSearch;
    // End of variables declaration//GEN-END:variables
}