/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import com.mycompany.util.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hernan
 */
public class TicketDAO {
    private Conexion conexion;

    public TicketDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createTicket(Ticket ticket) throws Exception {
        String sql = "INSERT INTO Tickets (name, price, age_classification_id, additional_cost, status_id, ticket_booth_id, customer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getName());
            pstmt.setBigDecimal(2, ticket.getPrice());
            pstmt.setObject(3, ticket.getAge_classification_id(), java.sql.Types.INTEGER);
            pstmt.setBigDecimal(4, ticket.getAdditional_cost());
            pstmt.setObject(5, ticket.getStatus_id(), java.sql.Types.INTEGER);
            pstmt.setObject(6, ticket.getTicket_booth_id(), java.sql.Types.INTEGER);
            pstmt.setObject(7, ticket.getCustomer_id(), java.sql.Types.INTEGER);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear el ticket: " + e.getMessage());
        }
    }

    public Ticket getTicketById(int id) {
        String sql = "SELECT * FROM Tickets WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                Integer age_classification_id = rs.getObject("age_classification_id", Integer.class);
                BigDecimal additional_cost = rs.getBigDecimal("additional_cost");
                Integer status_id = rs.getObject("status_id", Integer.class);
                Integer ticket_booth_id = rs.getObject("ticket_booth_id", Integer.class);
                Integer customer_id = rs.getObject("customer_id", Integer.class);

                return new Ticket(name, price, age_classification_id, additional_cost, status_id, ticket_booth_id, customer_id);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el ticket: " + e.getMessage());
            return null;
        }
    }

    public boolean updateTicket(int id, Ticket ticket) {
        String sql = "UPDATE Tickets SET name=?, price=?, age_classification_id=?, additional_cost=?, status_id=?, ticket_booth_id=?, customer_id=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getName());
            pstmt.setBigDecimal(2, ticket.getPrice());
            pstmt.setObject(3, ticket.getAge_classification_id(), java.sql.Types.INTEGER);
            pstmt.setBigDecimal(4, ticket.getAdditional_cost());
            pstmt.setObject(5, ticket.getStatus_id(), java.sql.Types.INTEGER);
            pstmt.setObject(6, ticket.getTicket_booth_id(), java.sql.Types.INTEGER);
            pstmt.setObject(7, ticket.getCustomer_id(), java.sql.Types.INTEGER);
            pstmt.setInt(8, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el ticket: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTicket(int id) {
        String sql = "DELETE FROM Tickets WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el ticket: " + e.getMessage());
            return false;
        }
    }
}

