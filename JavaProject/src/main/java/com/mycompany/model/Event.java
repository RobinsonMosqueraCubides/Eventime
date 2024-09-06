/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author hernan
 */
import java.time.LocalDateTime;

public class Event {
    private String name;
    private LocalDateTime dateTime;
    private int organizerId;
    private int ageClassificationId;
    private String status;
    
    public Event(String name, LocalDateTime dateTime, int organizerId, int ageClassificationId, String status) {
        this.name = name;
        this.dateTime = dateTime;
        this.organizerId = organizerId;
        this.ageClassificationId = ageClassificationId;
        this.status = status;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public int getOrganizerId() {
        return organizerId;
    }
    
    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }
    
    public int getAgeClassificationId() {
        return ageClassificationId;
    }
    
    public void setAgeClassificationId(int ageClassificationId) {
        this.ageClassificationId = ageClassificationId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
