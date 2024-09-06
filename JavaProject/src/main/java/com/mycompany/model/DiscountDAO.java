/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.mycompany.util.Conexion;

/**
 *
 * @author hernan
 */
public class DiscountDAO {
    private Conexion conexion;

    public DiscountDAO() {
        this.conexion = Conexion.getInstance();
    }

    public boolean createDiscount(Discount discount) throws SQLException {
        String sql = "INSERT INTO Discounts (shop_id, product_id, discount_type, discount_value, start_date, end_date, event_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, discount.getShopId());
            pstmt.setInt(2, discount.getProductId());
            pstmt.setString(3, discount.getDiscountType());
            pstmt.setDouble(4, discount.getDiscountValue());
            pstmt.setTimestamp(5, Timestamp.valueOf(discount.getStartDate()));
            pstmt.setTimestamp(6, Timestamp.valueOf(discount.getEndDate()));
            pstmt.setInt(7, discount.getEventId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public Discount getDiscountById(int id) {
        String sql = "SELECT * FROM Discounts WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int shopId = rs.getInt("shop_id");
                int productId = rs.getInt("product_id");
                String discountType = rs.getString("discount_type");
                double discountValue = rs.getDouble("discount_value");
                LocalDateTime startDate = rs.getTimestamp("start_date").toLocalDateTime();
                LocalDateTime endDate = rs.getTimestamp("end_date").toLocalDateTime();
                int eventId = rs.getInt("event_id");

                return new Discount(shopId, productId, discountType, discountValue, startDate, endDate, eventId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el descuento: " + e.getMessage());
            return null;
        }
    }

    public boolean updateDiscount(int id, Discount discount) throws SQLException {
        String sql = "UPDATE Discounts SET shop_id=?, product_id=?, discount_type=?, discount_value=?, start_date=?, end_date=?, event_id=? WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, discount.getShopId());
            pstmt.setInt(2, discount.getProductId());
            pstmt.setString(3, discount.getDiscountType());
            pstmt.setDouble(4, discount.getDiscountValue());
            pstmt.setTimestamp(5, Timestamp.valueOf(discount.getStartDate()));
            pstmt.setTimestamp(6, Timestamp.valueOf(discount.getEndDate()));
            pstmt.setInt(7, discount.getEventId());
            pstmt.setInt(8, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteDiscount(int id) throws SQLException {
        String sql = "DELETE FROM Discounts WHERE id=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
