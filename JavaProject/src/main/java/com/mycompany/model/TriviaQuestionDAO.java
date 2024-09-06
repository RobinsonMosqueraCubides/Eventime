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
public class TriviaQuestionDAO {
    private Conexion conexion;

    public TriviaQuestionDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createTriviaQuestion(TriviaQuestion triviaQuestion) throws SQLException {
        String sql = "INSERT INTO TriviaQuestions (question, correct_answer, category, difficulty, event_id, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, triviaQuestion.getQuestion());
            pstmt.setString(2, triviaQuestion.getCorrectAnswer());
            pstmt.setString(3, triviaQuestion.getCategory());
            pstmt.setString(4, triviaQuestion.getDifficulty());
            pstmt.setInt(5, triviaQuestion.getEventId());
            pstmt.setInt(6, triviaQuestion.getCategoryId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public TriviaQuestion getTriviaQuestionById(int id) {
        String sql = "SELECT * FROM TriviaQuestions WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String question = rs.getString("question");
                String correctAnswer = rs.getString("correct_answer");
                String category = rs.getString("category");
                String difficulty = rs.getString("difficulty");
                int eventId = rs.getInt("event_id");
                int categoryId = rs.getInt("category_id");

                return new TriviaQuestion(question, correctAnswer, category, difficulty, eventId, categoryId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la pregunta de trivia: " + e.getMessage());
            return null;
        }
    }

    public boolean updateTriviaQuestion(int id, TriviaQuestion triviaQuestion) throws SQLException {
        String sql = "UPDATE TriviaQuestions SET question=?, correct_answer=?, category=?, difficulty=?, event_id=?, category_id=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, triviaQuestion.getQuestion());
            pstmt.setString(2, triviaQuestion.getCorrectAnswer());
            pstmt.setString(3, triviaQuestion.getCategory());
            pstmt.setString(4, triviaQuestion.getDifficulty());
            pstmt.setInt(5, triviaQuestion.getEventId());
            pstmt.setInt(6, triviaQuestion.getCategoryId());
            pstmt.setInt(7, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteTriviaQuestion(int id) throws SQLException {
        String sql = "DELETE FROM TriviaQuestions WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
