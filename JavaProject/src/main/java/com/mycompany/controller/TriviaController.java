/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author hernan
 */
import com.mycompany.model.TriviaModel;
import com.mycompany.view.Activities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import com.mycompany.util.Conexion;

public class TriviaController {
    private TriviaModel model;
    private Activities view;

    public TriviaController(TriviaModel model, Activities view) {
        this.model = model;
        this.view = view;

        // La lógica de los botones debe estar en los métodos correspondientes de la vista (Activities)
        this.view.addStartGameListener(new StartGameListener());
        this.view.addFinishGameListener(new FinishGameListener());
    }

    class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<String> questions = model.getRandomQuestions();
                view.setQuestions(questions); 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    class FinishGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<String> answers = view.getAnswers(); 
                int score = model.checkAnswers(view.getQuestions(), answers); 
                view.showScore(score); 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

