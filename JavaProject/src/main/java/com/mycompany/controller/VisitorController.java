/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.Visitor;
import com.mycompany.util.Conexion;
import com.mycompany.view.VisitorDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hernan
 */
public class VisitorController {
    private VisitorDAO visitorDAO;
    private JTable Visitors;
    private JFrame visitorsView;

    public VisitorController(JFrame visitorsView, JTable visitorsTable) {
        this.visitorDAO = new VisitorDAO();
        this.visitorsView = visitorsView;
        this.Visitors = visitorsTable;
    }

    public boolean createVisitor(String name, String identificationDocument, String gender, LocalDate birthDate, String email, String phoneNumber) {
        try {
            Visitor visitor = new Visitor(name, identificationDocument, gender, birthDate, email, phoneNumber);
            boolean success = visitorDAO.createVisitor(visitor);
            if (success) {
                System.out.println("Visitante creado exitosamente.");
                updateTable();
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(visitorsView, e.getMessage());
            return false;
        }
    }

    public Visitor searchVisitor(int id) {
        return visitorDAO.getVisitorById(id);
    }

    public boolean updateVisitor(int id, Visitor visitor) {
        try {
            boolean success = visitorDAO.updateVisitor(id, visitor);
            if (success) {
                System.out.println("Visitante actualizado exitosamente.");
                updateTable();
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(visitorsView, e.getMessage());
            return false;
        }
    }

    public boolean deleteVisitor(int id) {
        return visitorDAO.deleteVisitor(id);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) Visitors.getModel();
    model.setRowCount(0); // Limpia todas las filas actuales

    try (Connection conn = Conexion.getInstance().conectar();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM Visitors")) {

        while (rs.next()) {
            Object[] row = new Object[7];
            row[0] = rs.getInt("id");
            row[1] = rs.getString("name");
            row[2] = rs.getString("identification_document");
            row[3] = rs.getString("gender");
            row[4] = rs.getDate("birth_date");
            row[5] = rs.getString("email");
            row[6] = rs.getString("phone_number");
            model.addRow(row); // Agrega cada fila al modelo de la tabla
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(visitorsView, "Error al actualizar la tabla: " + e.getMessage());
    }
    }
}
