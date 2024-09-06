/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.Prize;
import com.mycompany.model.PrizeDAO;
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
public class PrizeController {
    private PrizeDAO prizeDAO;
    private JTable prizeTable;
    private JFrame prizeView;

    public PrizeController(JFrame prizeView, JTable prizeTable) {
        this.prizeDAO = new PrizeDAO();
        this.prizeView = prizeView;
        this.prizeTable = prizeTable;
    }

    public boolean createPrize(String type, String description, double value, String status, int activityId, int participantId, int generalCategoryId) {
        try {
            Prize prize = new Prize(type, description, value, status, activityId, participantId, generalCategoryId);
            boolean success = prizeDAO.createPrize(prize);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(prizeView, "Premio creado exitosamente.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(prizeView, "Error al crear el premio: " + e.getMessage());
            return false;
        }
    }

    public Prize searchPrize(int id) {
        return prizeDAO.getPrizeById(id);
    }

    public boolean updatePrize(int id, Prize prize) {
        try {
            boolean success = prizeDAO.updatePrize(id, prize);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(prizeView, "Premio actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(prizeView, "Error al actualizar el premio.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(prizeView, "Error al actualizar el premio: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePrize(int id) {
        try {
            boolean success = prizeDAO.deletePrize(id);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(prizeView, "Premio eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(prizeView, "Error al eliminar el premio.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(prizeView, "Error al eliminar el premio: " + e.getMessage());
            return false;
        }
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) prizeTable.getModel();
        model.setRowCount(0);

        try (Connection conn = Conexion.getInstance().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Prizes")) {

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("type");
                row[2] = rs.getString("description");
                row[3] = rs.getDouble("value");
                row[4] = rs.getString("status");
                row[5] = rs.getInt("activity_id");
                row[6] = rs.getInt("participant_id");
                row[7] = rs.getInt("general_category_id");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(prizeView, "Error al actualizar la tabla: " + e.getMessage());
        }
    }
}

