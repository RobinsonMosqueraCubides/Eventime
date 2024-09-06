/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.InventInvenoryItem;
import com.mycompany.model.InventInvenoryItemDAO;
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
public class InventInvenoryItemController {
    private InventInvenoryItemDAO itemDAO;
    private JTable itemsTable;
    private JFrame itemsView;

    public InventInvenoryItemController(JFrame itemsView, JTable itemsTable) {
        this.itemDAO = new InventInvenoryItemDAO();
        this.itemsView = itemsView;
        this.itemsTable = itemsTable;
    }

    public boolean createItem(String name, int quantity, Integer status_id, Integer event_id) {
        try {
            InventInvenoryItem item = new InventInvenoryItem(name, quantity, status_id, event_id);
            boolean success = itemDAO.createItem(item);
            if (success) {
                System.out.println("Ítem creado exitosamente.");
                updateTable();
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(itemsView, e.getMessage());
            return false;
        }
    }

    public InventInvenoryItem searchItem(int id) {
        return itemDAO.getItemById(id);
    }

    public boolean updateItem(int id, InventInvenoryItem item) {
        return itemDAO.updateItem(id, item);
    }

    public boolean deleteItem(int id) {
        return itemDAO.deleteItem(id);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();
        model.setRowCount(0);

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getInstance().conectar();
            stmt = conn.createStatement();

            String sql = "SELECT id, name, quantity, status_id, event_id FROM InventInvenoryItems";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("name");
                row[2] = rs.getInt("quantity");
                row[3] = rs.getInt("status_id");
                row[4] = rs.getInt("event_id");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(itemsView, "Error al actualizar la tabla: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.getInstance().cerrarConexion(conn);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(itemsView, "Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
}

