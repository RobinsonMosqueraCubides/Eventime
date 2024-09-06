/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

import com.mycompany.model.ShopStatistics;
import com.mycompany.model.ShopStatisticsDAO;
import com.mycompany.view.Stadistics;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.mycompany.util.Conexion;

/**
 *
 * @author hernan
 */
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ShopStatisticsController {
    private ShopStatisticsDAO statisticsDAO;
    private Stadistics stadisticsView;  // Cambiado a Stadistics

    public ShopStatisticsController(ShopStatisticsDAO statisticsDAO, Stadistics stadisticsView) {
        this.statisticsDAO = statisticsDAO;
        this.stadisticsView = stadisticsView;
    }

    public void updateTableStadistics() {
        List<ShopStatistics> statisticsList = statisticsDAO.getShopStatistics();
        DefaultTableModel model = (DefaultTableModel) stadisticsView.getTableStadistics().getModel();

        // Limpiar la tabla antes de agregar nuevos datos
        model.setRowCount(0);

        // Usar lambda para agregar los datos a la tabla
        statisticsList.forEach(stat -> model.addRow(new Object[]{
                stat.getShopId(),
                stat.getManagerId(),
                stat.getManagerName(),
                stat.getTotalSales()
        }));
    }
}