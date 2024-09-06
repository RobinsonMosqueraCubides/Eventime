/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author hernan
 */
import com.mycompany.model.TriviaRoundDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TriviaRoundController {
    private TriviaRoundDAO triviaRoundDAO;
    private JTable triviaTable;
    private JFrame triviaView;

    public TriviaRoundController(JFrame triviaView, JTable triviaTable) {
        this.triviaRoundDAO = new TriviaRoundDAO();
        this.triviaTable = triviaTable;
        this.triviaView = triviaView;
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) triviaTable.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de recargar los datos

        List<TriviaRound> triviaRounds = triviaRoundDAO.getAllTriviaRounds();
        for (TriviaRound round : triviaRounds) {
            Object[] row = new Object[6];
            row[0] = round.getId();
            row[1] = round.getEventId();
            row[2] = round.getRoundNumber();
            row[3] = round.getParticipant1Id();
            row[4] = round.getParticipant2Id();
            row[5] = round.getWinnerId();
            model.addRow(row);
        }
    }
}
