/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.Order;
import com.mycompany.model.OrderDAO;
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
public class OrderController {
    private OrderDAO orderDAO;
    private JTable ordersTable;
    private JFrame ordersView;

    public OrderController(JFrame ordersView, JTable ordersTable) {
        this.orderDAO = new OrderDAO();
        this.ordersView = ordersView;
        this.ordersTable = ordersTable;
    }

    public boolean createOrder(int visitorId, int shopId, int cashRegisterId, int cashierId, double totalValue, String status) {
        try {
            Order order = new Order(visitorId, shopId, cashRegisterId, cashierId, totalValue, status);
            boolean success = orderDAO.createOrder(order);
            if (success) {
                updateTable(); 
                JOptionPane.showMessageDialog(ordersView, "Pedido creado exitosamente.");
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ordersView, e.getMessage());
            return false;
        }
    }

    public Order searchOrder(int id) {
        return orderDAO.getOrderById(id);
    }

    public boolean updateOrder(int id, Order order) {
        boolean success = orderDAO.updateOrder(id, order);
        if (success) {
            updateTable(); 
            JOptionPane.showMessageDialog(ordersView, "Pedido actualizado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(ordersView, "Error al actualizar el pedido.");
        }
        return success;
    }

    public boolean deleteOrder(int id) {
        boolean success = orderDAO.deleteOrder(id);
        if (success) {
            updateTable(); 
            JOptionPane.showMessageDialog(ordersView, "Pedido eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(ordersView, "Error al eliminar el pedido.");
        }
        return success;
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.setRowCount(0); 

        try (Connection conn = Conexion.getInstance().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Orders")) {

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("id");
                row[1] = rs.getInt("visitor_id");
                row[2] = rs.getInt("shop_id");
                row[3] = rs.getInt("cash_register_id");
                row[4] = rs.getInt("cashier_id");
                row[5] = rs.getDouble("total_value");
                row[6] = rs.getString("status");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ordersView, "Error al actualizar la tabla: " + e.getMessage());
        }
    }
}

