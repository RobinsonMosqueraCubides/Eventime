/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hernan
 */
public class InventInvenoryItemDAO {
    private Conexion conexion;

    public InventInvenoryItemDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createItem(InventInvenoryItem item) throws Exception {
        String sql = "INSERT INTO InventInvenoryItems (name, quantity, status_id, event_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setObject(3, item.getStatus_id(), java.sql.Types.INTEGER);
            pstmt.setObject(4, item.getEvent_id(), java.sql.Types.INTEGER);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear el ítem: " + e.getMessage());
        }
    }

    public InventInvenoryItem getItemById(int id) {
        String sql = "SELECT * FROM InventInvenoryItems WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                Integer status_id = rs.getObject("status_id", Integer.class);
                Integer event_id = rs.getObject("event_id", Integer.class);

                return new InventInvenoryItem(name, quantity, status_id, event_id);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el ítem: " + e.getMessage());
            return null;
        }
    }

    public boolean updateItem(int id, InventInvenoryItem item) {
        String sql = "UPDATE InventInvenoryItems SET name=?, quantity=?, status_id=?, event_id=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setObject(3, item.getStatus_id(), java.sql.Types.INTEGER);
            pstmt.setObject(4, item.getEvent_id(), java.sql.Types.INTEGER);
            pstmt.setInt(5, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el ítem: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteItem(int id) {
        String sql = "DELETE FROM InventInvenoryItems WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el ítem: " + e.getMessage());
            return false;
        }
    }
}

