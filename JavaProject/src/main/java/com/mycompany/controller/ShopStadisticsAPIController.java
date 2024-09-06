/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author hernan
 */
import com.mycompany.model.ShopStadisticsAPI;
import com.mycompany.model.ShopStadisticsAPIDAO;
import com.mycompany.view.StadisticsAPI;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ShopStadisticsAPIController {
    private ShopStadisticsAPIDAO statisticsDAO;
    private StadisticsAPI stadisticsView;

    public ShopStadisticsAPIController(ShopStadisticsAPIDAO statisticsDAO, StadisticsAPI stadisticsView) {
        this.statisticsDAO = statisticsDAO;
        this.stadisticsView = stadisticsView;
    }

    public void updateTableStadistics() {
        List<ShopStadisticsAPI> statisticsList = statisticsDAO.getShopStatistics();
        DefaultTableModel model = (DefaultTableModel) stadisticsView.getTableStadistics().getModel();

        // Limpiar la tabla antes de agregar nuevos datos
        model.setRowCount(0);

        // Usar API Stream para agregar los datos a la tabla
        statisticsList.stream()
            .map(stat -> new Object[]{
                    stat.getShopId(),
                    stat.getManagerId(),
                    stat.getManagerName(),
                    stat.getTotalSales()
            })
            .forEach(model::addRow);
    }
}