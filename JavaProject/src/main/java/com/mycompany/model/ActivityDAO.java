/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author hernan
 */
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityDAO {
    private Conexion conexion;

    public ActivityDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createActivity(Activity activity) throws Exception {
        String sql = "INSERT INTO EventActivities (name, type, number_of_participants, event_id, start_time, manager_id, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activity.getName());
            pstmt.setString(2, activity.getType());
            pstmt.setInt(3, activity.getNumberOfParticipants());
            pstmt.setInt(4, activity.getEventId());
            pstmt.setString(5, activity.getStartTime());
            pstmt.setInt(6, activity.getManagerId());
            pstmt.setInt(7, activity.getCategoryId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear la actividad: " + e.getMessage());
        }
    }

    public Activity getActivityById(int id) {
        String sql = "SELECT * FROM EventActivities WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                int numberOfParticipants = rs.getInt("number_of_participants");
                int eventId = rs.getInt("event_id");
                String startTime = rs.getString("start_time");
                int managerId = rs.getInt("manager_id");
                int categoryId = rs.getInt("category_id");

                return new Activity(name, type, numberOfParticipants, eventId, startTime, managerId, categoryId);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la actividad: " + e.getMessage());
            return null;
        }
    }

    public boolean updateActivity(int id, Activity activity) {
        String sql = "UPDATE EventActivities SET name=?, type=?, number_of_participants=?, event_id=?, start_time=?, manager_id=?, category_id=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activity.getName());
            pstmt.setString(2, activity.getType());
            pstmt.setInt(3, activity.getNumberOfParticipants());
            pstmt.setInt(4, activity.getEventId());
            pstmt.setString(5, activity.getStartTime());
            pstmt.setInt(6, activity.getManagerId());
            pstmt.setInt(7, activity.getCategoryId());
            pstmt.setInt(8, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar la actividad: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteActivity(int id) {
        String sql = "DELETE FROM EventActivities WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar la actividad: " + e.getMessage());
            return false;
        }
    }
}

