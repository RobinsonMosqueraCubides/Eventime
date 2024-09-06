/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author hernan
 */
import com.mycompany.model.Activity;
import com.mycompany.model.ActivityDAO;
import com.mycompany.model.CosplayScore;
import com.mycompany.model.CosplayScoreDAO;
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ActivityController {
    private ActivityDAO activityDAO;
    private JTable activityTable;
    private JFrame activitiesView;

    public ActivityController(JFrame activitiesView, JTable activityTable) {
        this.activityDAO = new ActivityDAO();
        this.activitiesView = activitiesView;
        this.activityTable = activityTable;
    }

    public boolean createActivity(String name, String type, int numberOfParticipants, int eventId, String startTime, int managerId, int categoryId) {
        try {
            Activity activity = new Activity(name, type, numberOfParticipants, eventId, startTime, managerId, categoryId);
            boolean success = activityDAO.createActivity(activity);
            if (success) {
                updateTable(); 
                JOptionPane.showMessageDialog(activitiesView, "Actividad creada exitosamente.");
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(activitiesView, e.getMessage());
            return false;
        }
    }

    public Activity searchActivity(int id) {
        return activityDAO.getActivityById(id);
    }

    public boolean updateActivity(int id, Activity activity) {
        boolean success = activityDAO.updateActivity(id, activity);
        if (success) {
            updateTable(); 
            JOptionPane.showMessageDialog(activitiesView, "Actividad actualizada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(activitiesView, "Error al actualizar la actividad.");
        }
        return success;
    }

    public boolean deleteActivity(int id) {
        boolean success = activityDAO.deleteActivity(id);
        if (success) {
            updateTable(); 
            JOptionPane.showMessageDialog(activitiesView, "Actividad eliminada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(activitiesView, "Error al eliminar la actividad.");
        }
        return success;
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setRowCount(0); 

        try (Connection conn = Conexion.getInstance().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM EventActivities")) {

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("name");
                row[2] = rs.getString("type");
                row[3] = rs.getInt("number_of_participants");
                row[4] = rs.getInt("event_id");
                row[5] = rs.getString("start_time");
                row[6] = rs.getInt("manager_id");
                row[7] = rs.getInt("category_id");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(activitiesView, "Error al actualizar la tabla: " + e.getMessage());
        }
}

}


