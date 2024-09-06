/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author hernan
 */
import com.mycompany.model.Event;
import com.mycompany.model.EventDAO;
import com.mycompany.view.Events;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class EventController {
    private EventDAO eventDAO;
    private Events Events; 
    private JTable tableTickets;

    public EventController(Events eventsView) {
        this.eventDAO = new EventDAO();
        this.Events = eventsView;
        this.tableTickets = tableTickets;
    }

    public boolean createEvent(String name, String dateStr, int organizerId, int ageClassificationId, String status) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime dateTime;

    try {
        dateTime = LocalDateTime.parse(dateStr, formatter);
    } catch (DateTimeParseException e) {
        JOptionPane.showMessageDialog(Events, "Formato de fecha incorrecto. Use 'yyyy-MM-dd HH:mm:ss'.");
        return false;
    }

    try {
        boolean success = eventDAO.createEvent(name, dateStr, organizerId, ageClassificationId, status);
        if (success) {
            System.out.println("Evento creado exitosamente.");
            Events.updateTable(); 
        }
        return success;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(Events, e.getMessage()); 
        return false;
    }
}

    public Event searchEvent(int id) {
    return eventDAO.getEventById(id);
}

    public boolean updateEvent(int id, Event event) {
    return eventDAO.updateEvent(id, event);
}

    public boolean deleteEvent(int id) {
    return eventDAO.deleteEvent(id);
}

}
