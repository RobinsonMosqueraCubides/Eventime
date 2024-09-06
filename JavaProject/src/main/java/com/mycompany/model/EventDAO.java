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
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author hernan
 */
public class EventDAO {
    private Conexion conexion;

    public EventDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createEvent(String name, String dateStr, int organizerId, int ageClassificationId, String status) throws Exception {
        String sql = "INSERT INTO Events (name, date_time, organizer_id, age_classification_id, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, dateStr);
            pstmt.setObject(3, organizerId != 0 ? organizerId : null, java.sql.Types.INTEGER);
            pstmt.setObject(4, ageClassificationId != 0 ? ageClassificationId : null, java.sql.Types.INTEGER);
            pstmt.setString(5, status);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Filas afectadas: " + rowsAffected);
            
            return rowsAffected > 0;

        } catch (SQLException e) {
            String errorMessage = e.getMessage().toLowerCase();

            if (errorMessage.contains("column 'name' cannot be null")) {
                throw new Exception("El campo 'Nombre' es obligatorio.");
            } else if (errorMessage.contains("column 'date_time' cannot be null")) {
                throw new Exception("El campo 'Fecha y Hora' es obligatorio.");
            } else if (errorMessage.contains("column 'status' cannot be null")) {
                throw new Exception("El campo 'Estado' es obligatorio.");
            } else {
                throw new Exception("Error al crear el evento: " + e.getMessage());
            }
        }
   }

   public Event getEventById(int id) {
    String sql = "SELECT * FROM Events WHERE id=?";
    try (Connection conn = conexion.conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
            int organizerId = rs.getInt("organizer_id");
            int ageClassificationId = rs.getInt("age_classification_id");
            String status = rs.getString("status");

            return new Event(name, dateTime, organizerId, ageClassificationId, status);
        } else {
            return null; 
        }

    } catch (SQLException e) {
        System.err.println("Error al obtener el evento: " + e.getMessage());
        return null;
    }
}
   
   public boolean updateEvent(int id, Event event) {
    String sql = "UPDATE Events SET name=?, date_time=?, organizer_id=?, age_classification_id=?, status=? WHERE id=?";
    try (Connection conn = conexion.conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, event.getName());
        pstmt.setTimestamp(2, Timestamp.valueOf(event.getDateTime()));
        pstmt.setObject(3, event.getOrganizerId() != 0 ? event.getOrganizerId() : null, java.sql.Types.INTEGER);
        pstmt.setObject(4, event.getAgeClassificationId() != 0 ? event.getAgeClassificationId() : null, java.sql.Types.INTEGER);
        pstmt.setString(5, event.getStatus());
        pstmt.setInt(6, id);

        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al actualizar el evento: " + e.getMessage());
        return false;
    }
}
   
   public boolean deleteEvent(int id) {
    String sql = "DELETE FROM Events WHERE id=?";
    try (Connection conn = conexion.conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, id);

        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al eliminar el evento: " + e.getMessage());
        return false;
    }
}
}
