/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.util.Conexion;
/**
 *
 * @author hernan
 */
public class OrderDAO {
    private Conexion conexion;

    public OrderDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createOrder(Order order) throws Exception {
        String sql = "INSERT INTO Orders (visitor_id, shop_id, cash_register_id, cashier_id, total_value, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order.getVisitorId());
            pstmt.setInt(2, order.getShopId());
            pstmt.setInt(3, order.getCashRegisterId());
            pstmt.setInt(4, order.getCashierId());
            pstmt.setDouble(5, order.getTotalValue());
            pstmt.setString(6, order.getStatus());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear el pedido: " + e.getMessage());
        }
    }

    public Order getOrderById(int id) {
        String sql = "SELECT * FROM Orders WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int visitorId = rs.getInt("visitor_id");
                int shopId = rs.getInt("shop_id");
                int cashRegisterId = rs.getInt("cash_register_id");
                int cashierId = rs.getInt("cashier_id");
                double totalValue = rs.getDouble("total_value");
                String status = rs.getString("status");

                return new Order(visitorId, shopId, cashRegisterId, cashierId, totalValue, status);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el pedido: " + e.getMessage());
            return null;
        }
    }

    public boolean updateOrder(int id, Order order) {
        String sql = "UPDATE Orders SET visitor_id=?, shop_id=?, cash_register_id=?, cashier_id=?, total_value=?, status=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order.getVisitorId());
            pstmt.setInt(2, order.getShopId());
            pstmt.setInt(3, order.getCashRegisterId());
            pstmt.setInt(4, order.getCashierId());
            pstmt.setDouble(5, order.getTotalValue());
            pstmt.setString(6, order.getStatus());
            pstmt.setInt(7, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteOrder(int id) {
        String sql = "DELETE FROM Orders WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            return false;
        }
    }
}
