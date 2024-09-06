/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.Employee;
import com.mycompany.model.EmployeeDAO;
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hernan
 */
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EmployeeController {
    private EmployeeDAO employeeDAO;
    private JTable staffTable;
    private JFrame employeesView;

    public EmployeeController(JFrame employeesView, JTable staffTable) {
        this.employeeDAO = new EmployeeDAO();
        this.employeesView = employeesView;
        this.staffTable = staffTable;
    }

    public boolean createEmployee(String name, String identification, LocalDate birthDate, int roleId, int statusId) {
        try {
            boolean success = employeeDAO.createEmployee(name, identification, birthDate, roleId, statusId);
            if (success) {
                System.out.println("Empleado creado exitosamente.");
                updateTable(); 
            }
            return success;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(employeesView, e.getMessage()); 
            return false;
        }
    }

    public Employee searchEmployee(int id) {
        return employeeDAO.getEmployeeById(id);
    }

    public boolean updateEmployee(int id, Employee employee) {
    try {
        boolean success = employeeDAO.updateEmployee(id, employee);
        if (success) {
            System.out.println("Empleado actualizado exitosamente.");
            updateTable(); 
        } else {
            System.err.println("Error: No se pudo actualizar el empleado.");
        }
        return success;
    } catch (Exception e) {
        System.err.println("Error en el controlador al actualizar el empleado: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}


    public boolean deleteEmployee(int id) {
        return employeeDAO.deleteEmployee(id);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        model.setRowCount(0); 

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getInstance().conectar();
            stmt = conn.createStatement();

            String sql = "SELECT id, name, identification, birth_date, role_id, status_id FROM Employees";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = new Object[6];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("name");
                row[2] = rs.getString("identification");
                row[3] = rs.getDate("birth_date");
                row[4] = rs.getInt("role_id");
                row[5] = rs.getInt("status_id");
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(employeesView, "Error al actualizar la tabla: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.getInstance().cerrarConexion(conn);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(employeesView, "Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
}


