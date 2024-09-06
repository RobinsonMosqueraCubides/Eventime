package com.mycompany.view;

import com.mycompany.model.Visitor;
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author hernan
 */
public class VisitorDAO {
    private Conexion conexion;

    public VisitorDAO() {
        this.conexion = Conexion.getInstance();
    }
    public boolean createVisitor(Visitor visitor) throws Exception {
        String sql = "INSERT INTO Visitors (name, identification_document, gender, birth_date, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, visitor.getName());
            pstmt.setString(2, visitor.getIdentificationDocument());
            pstmt.setString(3, visitor.getGender());
            pstmt.setDate(4, java.sql.Date.valueOf(visitor.getBirthDate()));
            pstmt.setString(5, visitor.getEmail());
            pstmt.setString(6, visitor.getPhoneNumber());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear el visitante: " + e.getMessage());
        }
    }

    public Visitor getVisitorById(int id) {
        String sql = "SELECT * FROM Visitors WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String identificationDocument = rs.getString("identification_document");
                String gender = rs.getString("gender");
                LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");

                return new Visitor(name, identificationDocument, gender, birthDate, email, phoneNumber);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el visitante: " + e.getMessage());
            return null;
        }
    }

    public boolean updateVisitor(int id, Visitor visitor) {
        String sql = "UPDATE Visitors SET name=?, identification_document=?, gender=?, birth_date=?, email=?, phone_number=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, visitor.getName());
            pstmt.setString(2, visitor.getIdentificationDocument());
            pstmt.setString(3, visitor.getGender());
            pstmt.setDate(4, java.sql.Date.valueOf(visitor.getBirthDate()));
            pstmt.setString(5, visitor.getEmail());
            pstmt.setString(6, visitor.getPhoneNumber());
            pstmt.setInt(7, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el visitante: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteVisitor(int id) {
        String sql = "DELETE FROM Visitors WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el visitante: " + e.getMessage());
            return false;
        }
    }
}

