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
public class Ticket {
    private int id;
    private String name;
    private BigDecimal price;
    private Integer age_classification_id;
    private BigDecimal additional_cost;
    private Integer status_id;
    private Integer ticket_booth_id;
    private Integer customer_id;

    public Ticket(String name, BigDecimal price, Integer age_classification_id, BigDecimal additional_cost, Integer status_id, Integer ticket_booth_id, Integer customer_id) {
        this.name = name;
        this.price = price;
        this.age_classification_id = age_classification_id;
        this.additional_cost = additional_cost;
        this.status_id = status_id;
        this.ticket_booth_id = ticket_booth_id;
        this.customer_id = customer_id;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAge_classification_id() {
        return age_classification_id;
    }

    public void setAge_classification_id(Integer age_classification_id) {
        this.age_classification_id = age_classification_id;
    }

    public BigDecimal getAdditional_cost() {
        return additional_cost;
    }

    public void setAdditional_cost(BigDecimal additional_cost) {
        this.additional_cost = additional_cost;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Integer getTicket_booth_id() {
        return ticket_booth_id;
    }

    public void setTicket_booth_id(Integer ticket_booth_id) {
        this.ticket_booth_id = ticket_booth_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }
}

