/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.CashRegister;
import com.mycompany.model.CashRegisterDAO;
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
public class CashRegisterController {
    private CashRegisterDAO cashRegisterDAO;
    private JTable commerceTable;
    private JFrame commerceView;

    public CashRegisterController(JFrame commerceView, JTable commerceTable) {
        this.cashRegisterDAO = new CashRegisterDAO();
        this.commerceView = commerceView;
        this.commerceTable = commerceTable;
    }

    public boolean createCashRegister(String status, int operatorId, double openingAmount, double closingAmount) {
        try {
            CashRegister cashRegister = new CashRegister(status, operatorId, openingAmount, closingAmount);
            boolean success = cashRegisterDAO.createCashRegister(cashRegister);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(commerceView, "Caja registradora creada exitosamente.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(commerceView, "Error al crear la caja registradora: " + e.getMessage());
            return false;
        }
    }

    public CashRegister searchCashRegister(int id) {
        return cashRegisterDAO.getCashRegisterById(id);
    }

    public boolean updateCashRegister(int id, CashRegister cashRegister) {
        try {
            boolean success = cashRegisterDAO.updateCashRegister(id, cashRegister);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(commerceView, "Caja registradora actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(commerceView, "Error al actualizar la caja registradora.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(commerceView, "Error al actualizar la caja registradora: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCashRegister(int id) {
        try {
            boolean success = cashRegisterDAO.deleteCashRegister(id);
            if (success) {
                updateTable();
                JOptionPane.showMessageDialog(commerceView, "Caja registradora eliminada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(commerceView, "Error al eliminar la caja registradora.");
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(commerceView, "Error al eliminar la caja registradora: " + e.getMessage());
            return false;
        }
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) commerceTable.getModel();
        model.setRowCount(0);

        try (Connection conn = Conexion.getInstance().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM CashRegisters")) {

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("status");
                row[2] = rs.getInt("operator_id");
                row[3] = rs.getDouble("opening_amount");
                row[4] = rs.getDouble("closing_amount");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(commerceView, "Error al actualizar la tabla: " + e.getMessage());
        }
    }
}

