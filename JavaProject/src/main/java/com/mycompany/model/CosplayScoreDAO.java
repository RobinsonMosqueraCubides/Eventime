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
import java.util.ArrayList;
import java.util.List;

public class CosplayScoreDAO {
    private Conexion conexion;

    public CosplayScoreDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createCosplayScore(CosplayScore cosplayScore) throws Exception {
        String sql = "INSERT INTO CosplayScores (participant_id, judge_id, score) VALUES (?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cosplayScore.getParticipantId());
            pstmt.setInt(2, cosplayScore.getJudgeId());
            pstmt.setDouble(3, cosplayScore.getScore());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear el puntaje de cosplay: " + e.getMessage());
        }
    }

    public List<CosplayScore> getAllCosplayScores() {
        String sql = "SELECT * FROM CosplayScores";
        List<CosplayScore> cosplayScores = new ArrayList<>();
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int participantId = rs.getInt("participant_id");
                int judgeId = rs.getInt("judge_id");
                double score = rs.getDouble("score");

                CosplayScore cosplayScore = new CosplayScore(participantId, judgeId, score);
                cosplayScore.setId(id);
                cosplayScores.add(cosplayScore);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los puntajes de cosplay: " + e.getMessage());
        }
        return cosplayScores;
    }

    public boolean updateCosplayScore(int id, CosplayScore cosplayScore) {
        String sql = "UPDATE CosplayScores SET participant_id=?, judge_id=?, score=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cosplayScore.getParticipantId());
            pstmt.setInt(2, cosplayScore.getJudgeId());
            pstmt.setDouble(3, cosplayScore.getScore());
            pstmt.setInt(4, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el puntaje de cosplay: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCosplayScore(int id) {
        String sql = "DELETE FROM CosplayScores WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el puntaje de cosplay: " + e.getMessage());
            return false;
        }
    }
    
}

