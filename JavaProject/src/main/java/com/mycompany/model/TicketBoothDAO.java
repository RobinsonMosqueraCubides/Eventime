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
public class TicketBoothDAO {
    private Conexion conexion;

    public TicketBoothDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createTicketBooth(int eventId, String location, String contactNumber, int managerId) throws Exception {
        String sql = "INSERT INTO TicketBooths (event_id, location, contact_number, manager_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            pstmt.setString(2, location);
            pstmt.setString(3, contactNumber);
            pstmt.setObject(4, managerId != 0 ? managerId : null, java.sql.Types.INTEGER);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear la taquilla: " + e.getMessage());
        }
    }

    public TicketBooth getTicketBoothById(int id) {
        String sql = "SELECT * FROM TicketBooths WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int eventId = rs.getInt("event_id");
                String location = rs.getString("location");
                String contactNumber = rs.getString("contact_number");
                int managerId = rs.getInt("manager_id");

                return new TicketBooth(eventId, location, contactNumber, managerId);
            } else {
                return null; 
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la taquilla: " + e.getMessage());
            return null;
        }
    }

    public boolean updateTicketBooth(int id, TicketBooth ticketBooth) {
        String sql = "UPDATE TicketBooths SET event_id=?, location=?, contact_number=?, manager_id=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ticketBooth.getEventId());
            pstmt.setString(2, ticketBooth.getLocation());
            pstmt.setString(3, ticketBooth.getContactNumber());
            pstmt.setObject(4, ticketBooth.getManagerId() != 0 ? ticketBooth.getManagerId() : null, java.sql.Types.INTEGER);
            pstmt.setInt(5, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar la taquilla: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTicketBooth(int id) {
        String sql = "DELETE FROM TicketBooths WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar la taquilla: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eventHasTicketBooth(int eventId) {
        String sql = "SELECT COUNT(*) FROM TicketBooths WHERE event_id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Devuelve true si ya existe una taquilla para ese evento
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar la taquilla: " + e.getMessage());
            return false;
        }
    }
}
