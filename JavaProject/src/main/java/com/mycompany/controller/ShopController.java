/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.Shop;
import com.mycompany.model.ShopDAO;
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hernan
 */
public class ShopController {
    private ShopDAO shopDAO;
    private JTable shopTable;
    private JFrame shopsView;

    public ShopController(JFrame shopsView, JTable shopTable) {
        this.shopDAO = new ShopDAO();
        this.shopsView = shopsView;
        this.shopTable = shopTable;
    }

    public boolean createShop(String name, String category, int eventId, int managerId) {
        try {
            boolean success = shopDAO.createShop(name, category, eventId, managerId);
            if (success) {
                System.out.println("Comercio creado exitosamente.");
                updateTable(); 
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(shopsView, e.getMessage()); 
            return false;
        }
    }

    public Shop searchShop(int id) {
        return shopDAO.getShopById(id);
    }

    public boolean updateShop(int id, Shop shop) {
        return shopDAO.updateShop(id, shop);
    }

    public boolean deleteShop(int id) {
        return shopDAO.deleteShop(id);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) shopTable.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de recargar los datos

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getInstance().conectar();
            stmt = conn.createStatement();

            String sql = "SELECT id, name, category, event_id, manager_id FROM Shops";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("name");
                row[2] = rs.getString("category");
                row[3] = rs.getInt("event_id");
                row[4] = rs.getInt("manager_id");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(shopsView, "Error al actualizar la tabla: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.getInstance().cerrarConexion(conn);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(shopsView, "Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
}
