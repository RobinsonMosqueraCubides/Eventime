/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.TriviaQuestion;
import com.mycompany.model.TriviaQuestionDAO;
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hernan
 */
public class TriviaQuestionController {
    private TriviaQuestionDAO triviaQuestionDAO;
    private JTable triviaTable;
    private JFrame triviaView;

    public TriviaQuestionController(JFrame triviaView, JTable triviaTable) {
        this.triviaQuestionDAO = new TriviaQuestionDAO();
        this.triviaView = triviaView;
        this.triviaTable = triviaTable;
    }

    public boolean createTriviaQuestion(String question, String correctAnswer, String category, String difficulty, int eventId, int categoryId) {
        try {
            TriviaQuestion triviaQuestion = new TriviaQuestion(question, correctAnswer, category, difficulty, eventId, categoryId);
            boolean success = triviaQuestionDAO.createTriviaQuestion(triviaQuestion);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(triviaView, "Pregunta de trivia creada exitosamente.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(triviaView, "Error al crear la pregunta de trivia: " + e.getMessage());
            return false;
        }
    }

    public TriviaQuestion searchTriviaQuestion(int id) {
        return triviaQuestionDAO.getTriviaQuestionById(id);
    }

    public boolean updateTriviaQuestion(int id, TriviaQuestion triviaQuestion) {
        try {
            boolean success = triviaQuestionDAO.updateTriviaQuestion(id, triviaQuestion);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(triviaView, "Pregunta de trivia actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(triviaView, "Error al actualizar la pregunta de trivia.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(triviaView, "Error al actualizar la pregunta de trivia: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTriviaQuestion(int id) {
        try {
            boolean success = triviaQuestionDAO.deleteTriviaQuestion(id);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(triviaView, "Pregunta de trivia eliminada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(triviaView, "Error al eliminar la pregunta de trivia.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(triviaView, "Error al eliminar la pregunta de trivia: " + e.getMessage());
            return false;
        }
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) triviaTable.getModel();
        model.setRowCount(0);

        try (Connection conn = Conexion.getInstance().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM TriviaQuestions")) {

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("question");
                row[2] = rs.getString("correct_answer");
                row[3] = rs.getString("category");
                row[4] = rs.getString("difficulty");
                row[5] = rs.getInt("event_id");
                row[6] = rs.getInt("category_id");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(triviaView, "Error al actualizar la tabla: " + e.getMessage());
        }
    }
}

