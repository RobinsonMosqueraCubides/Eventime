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
public class ShopDAO {
    private Conexion conexion;

    public ShopDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createShop(String name, String category, int eventId, int managerId) throws Exception {
        String sql = "INSERT INTO Shops (name, category, event_id, manager_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setInt(3, eventId);
            pstmt.setObject(4, managerId != 0 ? managerId : null, java.sql.Types.INTEGER);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear el comercio: " + e.getMessage());
        }
    }

    public Shop getShopById(int id) {
        String sql = "SELECT * FROM Shops WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                int eventId = rs.getInt("event_id");
                int managerId = rs.getInt("manager_id");

                return new Shop(name, category, eventId, managerId);
            } else {
                return null; 
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el comercio: " + e.getMessage());
            return null;
        }
    }

    public boolean updateShop(int id, Shop shop) {
    String sql = "UPDATE Shops SET name=?, category=?, event_id=?, manager_id=? WHERE id=?";
    try (Connection conn = conexion.conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, shop.getName());
        pstmt.setString(2, shop.getCategory());
        pstmt.setInt(3, shop.getEventId());
        pstmt.setObject(4, shop.getManagerId() != 0 ? shop.getManagerId() : null, java.sql.Types.INTEGER);
        pstmt.setInt(5, id);

        int rowsAffected = pstmt.executeUpdate();
        System.out.println("Filas afectadas: " + rowsAffected);
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al actualizar el comercio: " + e.getMessage());
        e.printStackTrace();  
        return false;
    }
}


    public boolean deleteShop(int id) {
        String sql = "DELETE FROM Shops WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el comercio: " + e.getMessage());
            return false;
        }
    }
}
