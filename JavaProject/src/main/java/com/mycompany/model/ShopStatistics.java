/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.math.BigDecimal;

/**
 *
 * @author hernan
 */
public class ShopStatistics {
    private int shopId;
    private int managerId;
    private String managerName;
    private BigDecimal totalSales;

    public ShopStatistics(int shopId, int managerId, String managerName, BigDecimal totalSales) {
        this.shopId = shopId;
        this.managerId = managerId;
        this.managerName = managerName;
        this.totalSales = totalSales;
    }

    public int getShopId() {
        return shopId;
    }

    public int getManagerId() {
        return managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }
}
