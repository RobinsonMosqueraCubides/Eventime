/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.TicketBooth;
import com.mycompany.model.TicketBoothDAO;
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
public class TicketBoothController {
    private TicketBoothDAO ticketBoothDAO;
    private JFrame ticketsView; 
    private JTable tableTickets;

    public TicketBoothController(JFrame ticketsView, JTable tableTickets) {
        this.ticketBoothDAO = new TicketBoothDAO();
        this.ticketsView = ticketsView;
        this.tableTickets = tableTickets;
        this.tableTickets = tableTickets;
    }

    public boolean createTicketBooth(int eventId, String location, String contactNumber, int managerId) {
    try {
        // Verifica si ya existe una taquilla para este evento
        if (ticketBoothDAO.eventHasTicketBooth(eventId)) {
            JOptionPane.showMessageDialog(ticketsView, "Este evento ya tiene una taquilla asignada. No se puede añadir otra.");
            return false;
        }

        boolean success = ticketBoothDAO.createTicketBooth(eventId, location, contactNumber, managerId);
        if (success) {
            System.out.println("Taquilla creada exitosamente.");
            updateTable(); 
        }
        return success;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(ticketsView, e.getMessage()); 
        return false;
    }
}


    public TicketBooth searchTicketBooth(int id) {
        return ticketBoothDAO.getTicketBoothById(id);
    }

    public boolean updateTicketBooth(int id, TicketBooth ticketBooth) {
        return ticketBoothDAO.updateTicketBooth(id, ticketBooth);
    }

    public boolean deleteTicketBooth(int id) {
        return ticketBoothDAO.deleteTicketBooth(id);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) tableTickets.getModel();
        model.setRowCount(0); 

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getInstance().conectar();
            stmt = conn.createStatement();

            String sql = "SELECT id, event_id, location, contact_number, manager_id FROM TicketBooths";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getInt("event_id");
                row[2] = rs.getString("location");
                row[3] = rs.getString("contact_number");
                row[4] = rs.getInt("manager_id");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ticketsView, "Error al actualizar la tabla: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.getInstance().cerrarConexion(conn);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(ticketsView, "Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
}
