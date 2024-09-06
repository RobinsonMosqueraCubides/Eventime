/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.util.Conexion;

/**
 *
 * @author hernan
 */

public class TriviaModel {

    public List<String> getRandomQuestions() throws SQLException {
        List<String> questions = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexion = Conexion.getInstance().conectar();
            String sql = "SELECT question FROM TriviaQuestions ORDER BY RAND() LIMIT 10";
            stmt = conexion.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                questions.add(rs.getString("question"));
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conexion != null) Conexion.getInstance().cerrarConexion(conexion);
        }

        return questions;
    }

    public int checkAnswers(List<String> questions, List<String> answers) throws SQLException {
        int score = 0;
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexion = Conexion.getInstance().conectar();
            String sql = "SELECT correct_answer FROM TriviaQuestions WHERE question = ?";
            stmt = conexion.prepareStatement(sql);

            for (int i = 0; i < questions.size(); i++) {
                stmt.setString(1, questions.get(i));
                rs = stmt.executeQuery();

                if (rs.next()) {
                    String correctAnswer = rs.getString("correct_answer");
                    if (correctAnswer.equalsIgnoreCase(answers.get(i))) {
                        score++;
                    }
                }
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conexion != null) Conexion.getInstance().cerrarConexion(conexion);
        }

        return score;
    }
}
