/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author hernan
 */
public class DishDAO {

    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        String query = "SELECT id, restaurant_id, name, price, type, preparation_time FROM Dishes";

        try (Connection con = Conexion.getInstance().conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Dish dish = new Dish(
                    rs.getInt("id"),
                    rs.getInt("restaurant_id"),
                    rs.getString("name"),
                    rs.getBigDecimal("price"),
                    rs.getString("type"),
                    rs.getInt("preparation_time")
                );
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public List<DishIngredient> getAllDishIngredients() {
        List<DishIngredient> ingredients = new ArrayList<>();
        String query = "SELECT id, name, available_quantity FROM Ingredients";

        try (Connection con = Conexion.getInstance().conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                DishIngredient ingredient = new DishIngredient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("available_quantity")
                );
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
    
    public void buyDish(int dishId) throws SQLException {
    String getIngredientsQuery = "SELECT ingredient_id, quantity_required FROM DishIngredients WHERE dish_id = ?";
    String updateIngredientsQuery = "UPDATE Ingredients SET available_quantity = available_quantity - ? WHERE id = ?";
    boolean hasSufficientIngredients = true;

    try (Connection con = Conexion.getInstance().conectar();
         PreparedStatement getIngredientsStmt = con.prepareStatement(getIngredientsQuery);
         PreparedStatement updateIngredientsStmt = con.prepareStatement(updateIngredientsQuery)) {

        getIngredientsStmt.setInt(1, dishId);

        try (ResultSet rs = getIngredientsStmt.executeQuery()) {
            // Primero, verificar si hay suficientes ingredientes
            List<Integer> ingredientIds = new ArrayList<>();
            List<Integer> requiredQuantities = new ArrayList<>();

            while (rs.next()) {
                int ingredientId = rs.getInt("ingredient_id");
                int quantityRequired = rs.getInt("quantity_required");
                int currentQuantity = getIngredientQuantity(con, ingredientId);

                if (currentQuantity < quantityRequired) {
                    hasSufficientIngredients = false;
                    break; // No es necesario seguir verificando
                }

                // Guardar datos para usarlos luego si hay suficientes ingredientes
                ingredientIds.add(ingredientId);
                requiredQuantities.add(quantityRequired);
            }

            if (hasSufficientIngredients) {
                for (int i = 0; i < ingredientIds.size(); i++) {
                    updateIngredientsStmt.setInt(1, requiredQuantities.get(i));
                    updateIngredientsStmt.setInt(2, ingredientIds.get(i));
                    updateIngredientsStmt.executeUpdate();
                }
                System.out.println("Compra realizada con éxito.");
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Ingredientes insuficientes, pide otro platillo.");
            }
        }
    } catch (SQLException e) {
        throw new SQLException("Error al realizar la compra", e);
    }
}

private int getIngredientQuantity(Connection con, int ingredientId) throws SQLException {
    String query = "SELECT available_quantity FROM Ingredients WHERE id = ?";
    try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setInt(1, ingredientId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("available_quantity");
            }
        }
    }
    return 0;
}
}