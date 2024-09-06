/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author hernan
 */
public class TriviaRound {
    private int id;
    private int eventId;
    private int roundNumber;
    private int participant1Id;
    private int participant2Id;
    private int winnerId;

    public TriviaRound(int eventId, int roundNumber, int participant1Id, int participant2Id, int winnerId) {
        this.eventId = eventId;
        this.roundNumber = roundNumber;
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
        this.winnerId = winnerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getParticipant1Id() {
        return participant1Id;
    }

    public void setParticipant1Id(int participant1Id) {
        this.participant1Id = participant1Id;
    }

    public int getParticipant2Id() {
        return participant2Id;
    }

    public void setParticipant2Id(int participant2Id) {
        this.participant2Id = participant2Id;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    
}

