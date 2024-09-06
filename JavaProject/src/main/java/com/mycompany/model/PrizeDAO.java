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
public class PrizeDAO {
    private Conexion conexion;

    public PrizeDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createPrize(Prize prize) throws SQLException {
        String sql = "INSERT INTO Prizes (type, description, value, status, activity_id, participant_id, general_category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prize.getType());
            pstmt.setString(2, prize.getDescription());
            pstmt.setDouble(3, prize.getValue());
            pstmt.setString(4, prize.getStatus());
            pstmt.setInt(5, prize.getActivityId());
            pstmt.setInt(6, prize.getParticipantId());
            pstmt.setInt(7, prize.getGeneralCategoryId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public Prize getPrizeById(int id) {
        String sql = "SELECT * FROM Prizes WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("type");
                String description = rs.getString("description");
                double value = rs.getDouble("value");
                String status = rs.getString("status");
                int activityId = rs.getInt("activity_id");
                int participantId = rs.getInt("participant_id");
                int generalCategoryId = rs.getInt("general_category_id");

                return new Prize(type, description, value, status, activityId, participantId, generalCategoryId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el premio: " + e.getMessage());
            return null;
        }
    }

    public boolean updatePrize(int id, Prize prize) throws SQLException {
        String sql = "UPDATE Prizes SET type=?, description=?, value=?, status=?, activity_id=?, participant_id=?, general_category_id=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prize.getType());
            pstmt.setString(2, prize.getDescription());
            pstmt.setDouble(3, prize.getValue());
            pstmt.setString(4, prize.getStatus());
            pstmt.setInt(5, prize.getActivityId());
            pstmt.setInt(6, prize.getParticipantId());
            pstmt.setInt(7, prize.getGeneralCategoryId());
            pstmt.setInt(8, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deletePrize(int id) throws SQLException {
        String sql = "DELETE FROM Prizes WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
