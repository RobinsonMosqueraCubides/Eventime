/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author hernan
 */
import com.mycompany.controller.TriviaRound;
import com.mycompany.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TriviaRoundDAO {
    private Conexion conexion;

    public TriviaRoundDAO() {
        this.conexion = Conexion.getInstance();
    }

    public List<TriviaRound> getAllTriviaRounds() {
        String sql = "SELECT * FROM TriviaRounds";
        List<TriviaRound> triviaRounds = new ArrayList<>();
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int eventId = rs.getInt("event_id");
                int roundNumber = rs.getInt("round_number");
                int participant1Id = rs.getInt("participant1_id");
                int participant2Id = rs.getInt("participant2_id");
                int winnerId = rs.getInt("winner_id");

                TriviaRound triviaRound = new TriviaRound(eventId, roundNumber, participant1Id, participant2Id, winnerId);
                triviaRound.setId(id);
                triviaRounds.add(triviaRound);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener las rondas de trivia: " + e.getMessage());
        }
        return triviaRounds;
    }

}

