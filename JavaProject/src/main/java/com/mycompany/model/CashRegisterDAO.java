/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hernan
 */
public class CashRegisterDAO {
    private Conexion conexion;

    public CashRegisterDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createCashRegister(CashRegister cashRegister) throws SQLException {
        String sql = "INSERT INTO CashRegisters (status, operator_id, opening_amount, closing_amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cashRegister.getStatus());
            pstmt.setInt(2, cashRegister.getOperatorId());
            pstmt.setDouble(3, cashRegister.getOpeningAmount());
            pstmt.setDouble(4, cashRegister.getClosingAmount());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public CashRegister getCashRegisterById(int id) {
        String sql = "SELECT * FROM CashRegisters WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                int operatorId = rs.getInt("operator_id");
                double openingAmount = rs.getDouble("opening_amount");
                double closingAmount = rs.getDouble("closing_amount");

                return new CashRegister(status, operatorId, openingAmount, closingAmount);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la caja registradora: " + e.getMessage());
            return null;
        }
    }

    public boolean updateCashRegister(int id, CashRegister cashRegister) throws SQLException {
        String sql = "UPDATE CashRegisters SET status=?, operator_id=?, opening_amount=?, closing_amount=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cashRegister.getStatus());
            pstmt.setInt(2, cashRegister.getOperatorId());
            pstmt.setDouble(3, cashRegister.getOpeningAmount());
            pstmt.setDouble(4, cashRegister.getClosingAmount());
            pstmt.setInt(5, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteCashRegister(int id) throws SQLException {
        String sql = "DELETE FROM CashRegisters WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
