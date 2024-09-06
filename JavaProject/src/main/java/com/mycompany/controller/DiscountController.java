/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.Discount;
import com.mycompany.model.DiscountDAO;
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hernan
 */
public class DiscountController {
    private DiscountDAO discountDAO;
    private JTable tableDiscounts;
    private JFrame discountView;

    public DiscountController(JFrame discountView, JTable tableDiscounts) {
        this.discountDAO = new DiscountDAO();
        this.discountView = discountView;
        this.tableDiscounts = tableDiscounts;
    }

    public boolean createDiscount(int shopId, int productId, String discountType, double discountValue, LocalDateTime startDate, LocalDateTime endDate, int eventId) {
        try {
            Discount discount = new Discount(shopId, productId, discountType, discountValue, startDate, endDate, eventId);
            boolean success = discountDAO.createDiscount(discount);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(discountView, "Descuento creado exitosamente.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(discountView, "Error al crear el descuento: " + e.getMessage());
            return false;
        }
    }

    public Discount searchDiscount(int id) {
        return discountDAO.getDiscountById(id);
    }

    public boolean updateDiscount(int id, Discount discount) {
        try {
            boolean success = discountDAO.updateDiscount(id, discount);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(discountView, "Descuento actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(discountView, "Error al actualizar el descuento.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(discountView, "Error al actualizar el descuento: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDiscount(int id) {
        try {
            boolean success = discountDAO.deleteDiscount(id);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(discountView, "Descuento eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(discountView, "Error al eliminar el descuento.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(discountView, "Error al eliminar el descuento: " + e.getMessage());
            return false;
        }
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) tableDiscounts.getModel();
        model.setRowCount(0);

        try (Connection conn = Conexion.getInstance().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Discounts")) {

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("id");
                row[1] = rs.getInt("shop_id");
                row[2] = rs.getInt("product_id");
                row[3] = rs.getString("discount_type");
                row[4] = rs.getDouble("discount_value");
                row[5] = rs.getTimestamp("start_date").toLocalDateTime();
                row[6] = rs.getTimestamp("end_date").toLocalDateTime();
                row[7] = rs.getInt("event_id");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(discountView, "Error al actualizar la tabla: " + e.getMessage());
        }
    }
}

