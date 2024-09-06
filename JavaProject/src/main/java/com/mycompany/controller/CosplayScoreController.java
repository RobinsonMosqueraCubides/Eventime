/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author hernan
 */
import com.mycompany.model.CosplayScore;
import com.mycompany.model.CosplayScoreDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CosplayScoreController {
    private CosplayScoreDAO cosplayScoreDAO;
    private JTable cosplayTable;
    private JFrame cosplayView;

    public CosplayScoreController(JFrame cosplayView, JTable cosplayTable) {
        this.cosplayScoreDAO = new CosplayScoreDAO();
        this.cosplayTable = cosplayTable;
        this.cosplayView = cosplayView;
    }

    public boolean createCosplayScore(int participantId, int judgeId, double score) {
        try {
            CosplayScore cosplayScore = new CosplayScore(participantId, judgeId, score);
            boolean success = cosplayScoreDAO.createCosplayScore(cosplayScore);
            if (success) {
                updateTable(); 
                JOptionPane.showMessageDialog(cosplayView, "Puntaje de cosplay creado exitosamente.");
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cosplayView, e.getMessage());
            return false;
        }
    }

    public boolean updateCosplayScore(int id, int participantId, int judgeId, double score) {
        CosplayScore cosplayScore = new CosplayScore(participantId, judgeId, score);
        boolean success = cosplayScoreDAO.updateCosplayScore(id, cosplayScore);
        if (success) {
            updateTable(); 
            JOptionPane.showMessageDialog(cosplayView, "Puntaje de cosplay actualizado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(cosplayView, "Error al actualizar el puntaje de cosplay.");
        }
        return success;
    }

    public boolean deleteCosplayScore(int id) {
        boolean success = cosplayScoreDAO.deleteCosplayScore(id);
        if (success) {
            updateTable(); 
            JOptionPane.showMessageDialog(cosplayView, "Puntaje de cosplay eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(cosplayView, "Error al eliminar el puntaje de cosplay.");
        }
        return success;
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) cosplayTable.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de recargar los datos

        List<CosplayScore> cosplayScores = cosplayScoreDAO.getAllCosplayScores();
        for (CosplayScore score : cosplayScores) {
            Object[] row = new Object[4];
            row[0] = score.getId();
            row[1] = score.getParticipantId();
            row[2] = score.getJudgeId();
            row[3] = score.getScore();
            model.addRow(row);
        }
    }
    public CosplayScore searchCosplayScore(int id) {
    CosplayScoreDAO cosplayScoreDAO = new CosplayScoreDAO(); // Crear instancia
    List<CosplayScore> cosplayScores = cosplayScoreDAO.getAllCosplayScores(); // Usar la instancia

    for (CosplayScore score : cosplayScores) {
        if (score.getId() == id) {
            return score;
        }
    }
    return null; // Si no encuentra el puntaje
}
}

