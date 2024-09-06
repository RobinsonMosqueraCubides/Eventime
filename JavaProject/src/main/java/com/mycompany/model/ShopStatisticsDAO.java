/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;
import com.mycompany.util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hernan
 */
public class ShopStatisticsDAO {

    public List<ShopStatistics> getShopStatistics() {
        List<ShopStatistics> statisticsList = new ArrayList<>();
        String query = "SELECT sh.id AS shop_id, sh.manager_id AS manager_id, emp.name AS manager_name, " +
                       "SUM(ord.total_value) AS total_sales " +
                       "FROM Shops sh " +
                       "JOIN Orders ord ON sh.id = ord.shop_id " +
                       "JOIN Employees emp ON sh.manager_id = emp.id " +
                       "GROUP BY sh.id, sh.manager_id, emp.name";

        try (Connection conn = Conexion.getInstance().conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ShopStatistics stats = new ShopStatistics(
                        rs.getInt("shop_id"),
                        rs.getInt("manager_id"),
                        rs.getString("manager_name"),
                        rs.getBigDecimal("total_sales")
                );
                statisticsList.add(stats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statisticsList;
    }
}