/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.Ticket;
import com.mycompany.model.TicketDAO;
import com.mycompany.util.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hernan
 */
public class TicketController {
    private TicketDAO ticketDAO;
    private JTable ticketsTable;
    private JFrame ticketsView;
    private JTextField TotalCollectText;
    private JTextField totalsumText;
    private JTable tableTickets;

    public TicketController(JTable ticketsTable, JTextField TotalCollectText, JTextField totalsumText) {
        this.ticketDAO = new TicketDAO();
        this.ticketsView = ticketsView;
        this.ticketsTable = ticketsTable;
        this.TotalCollectText = TotalCollectText;
        this.totalsumText = totalsumText;
        this.tableTickets = tableTickets;
    }
    

    public boolean createTicket(String name, BigDecimal price, Integer age_classification_id, BigDecimal additional_cost, Integer status_id, Integer ticket_booth_id, Integer customer_id) {
        try {
            Ticket ticket = new Ticket(name, price, age_classification_id, additional_cost, status_id, ticket_booth_id, customer_id);
            boolean success = ticketDAO.createTicket(ticket);
            if (success) {
                System.out.println("Ticket creado exitosamente.");
                updateTable();
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ticketsView, e.getMessage());
            return false;
        }
    }

    public Ticket searchTicket(int id) {
        return ticketDAO.getTicketById(id);
    }

    public boolean updateTicket(int id, Ticket ticket) {
        return ticketDAO.updateTicket(id, ticket);
    }

    public boolean deleteTicket(int id) {
        return ticketDAO.deleteTicket(id);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) ticketsTable.getModel();
        model.setRowCount(0);

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getInstance().conectar();
            stmt = conn.createStatement();

            String sql = "SELECT id, name, price, age_classification_id, additional_cost, status_id, ticket_booth_id, customer_id FROM Tickets";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("name");
                row[2] = rs.getBigDecimal("price");
                row[3] = rs.getInt("age_classification_id");
                row[4] = rs.getBigDecimal("additional_cost");
                row[5] = rs.getInt("status_id");
                row[6] = rs.getInt("ticket_booth_id");
                row[7] = rs.getInt("customer_id");
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
    
    public void filterTicketsByStatus(String status) {
    DefaultTableModel model = (DefaultTableModel) ticketsTable.getModel();
    model.setRowCount(0);

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = Conexion.getInstance().conectar();
        
        String sql = "SELECT t.id, t.name, t.price, t.age_classification_id, t.additional_cost, t.status_id, t.ticket_booth_id, t.customer_id " +
                     "FROM Tickets t " +
                     "JOIN TicketStatuses ts ON t.status_id = ts.id " +
                     "WHERE ts.status = ?";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, status);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            Object[] row = new Object[8];
            row[0] = rs.getInt("id");
            row[1] = rs.getString("name");
            row[2] = rs.getBigDecimal("price");
            row[3] = rs.getInt("age_classification_id");
            row[4] = rs.getBigDecimal("additional_cost");
            row[5] = rs.getInt("status_id");
            row[6] = rs.getInt("ticket_booth_id");
            row[7] = rs.getInt("customer_id");
            model.addRow(row);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(ticketsView, "Error al filtrar la tabla: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            Conexion.getInstance().cerrarConexion(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ticketsView, "Error al cerrar los recursos: " + e.getMessage());
        }
    }   
}
    public void calculateSums(String status) {
    String sql = "SELECT SUM(price + additional_cost) AS total_collect, COUNT(*) AS total_tickets FROM Tickets";
    
    // Si se aplica un filtro por estado, agrega la cláusula WHERE
    if (!status.isEmpty()) {
        sql += " WHERE status_id = (SELECT id FROM TicketStatuses WHERE status = ?)";
    }

    try (Connection conn = Conexion.getInstance().conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        // Si hay un filtro, establece el parámetro en la consulta
        if (!status.isEmpty()) {
            pstmt.setString(1, status);
        }

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            // Obtener la suma total y el número de tickets
            BigDecimal totalCollect = rs.getBigDecimal("total_collect");
            int totalTickets = rs.getInt("total_tickets");

            // Actualiza los campos de texto con los resultados
            TotalCollectText.setText(totalCollect != null ? totalCollect.toString() : "0");
            totalsumText.setText(String.valueOf(totalTickets)); // Muestra el total de tickets
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al calcular las sumas: " + e.getMessage());
    }
}


}

