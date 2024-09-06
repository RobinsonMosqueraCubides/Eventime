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
import java.time.LocalDate;

/**
 *
 * @author hernan
 */
public class EmployeeDAO {
    private Conexion conexion;

    public EmployeeDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createEmployee(String name, String identification, LocalDate birthDate, int roleId, int statusId) throws Exception {
        String sql = "INSERT INTO Employees (name, identification, birth_date, role_id, status_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, identification);
            pstmt.setDate(3, java.sql.Date.valueOf(birthDate));
            pstmt.setObject(4, roleId != 0 ? roleId : null, java.sql.Types.INTEGER);
            pstmt.setObject(5, statusId != 0 ? statusId : null, java.sql.Types.INTEGER);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("Error al crear el empleado: " + e.getMessage());
        }
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM Employees WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String identification = rs.getString("identification");
                LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
                int roleId = rs.getInt("role_id");
                int statusId = rs.getInt("status_id");

                return new Employee(name, identification, birthDate, roleId, statusId);
            } else {
                return null; 
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el empleado: " + e.getMessage());
            return null;
        }
    }

    public boolean updateEmployee(int id, Employee employee) {
    String sql = "UPDATE Employees SET name=?, identification=?, birth_date=?, role_id=?, status_id=? WHERE id=?";
    try (Connection conn = conexion.conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, employee.getName());
        pstmt.setString(2, employee.getIdentification());
        pstmt.setDate(3, java.sql.Date.valueOf(employee.getBirthDate()));
        pstmt.setObject(4, employee.getRoleId() != 0 ? employee.getRoleId() : null, java.sql.Types.INTEGER);
        pstmt.setObject(5, employee.getStatusId() != 0 ? employee.getStatusId() : null, java.sql.Types.INTEGER);
        pstmt.setInt(6, id);

        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("Error al actualizar el empleado: " + e.getMessage());
        e.printStackTrace();  
        return false;
    }
    }

    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM Employees WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el empleado: " + e.getMessage());
            return false;
        }
    }
}

