/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author hernan
 */
public class Order {
    private int id;
    private int visitorId;
    private int shopId;
    private int cashRegisterId;
    private int cashierId;
    private double totalValue;
    private String status;

    public Order(int visitorId, int shopId, int cashRegisterId, int cashierId, double totalValue, String status) {
        this.visitorId = visitorId;
        this.shopId = shopId;
        this.cashRegisterId = cashRegisterId;
        this.cashierId = cashierId;
        this.totalValue = totalValue;
        this.status = status;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getCashRegisterId() {
        return cashRegisterId;
    }

    public void setCashRegisterId(int cashRegisterId) {
        this.cashRegisterId = cashRegisterId;
    }

    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
