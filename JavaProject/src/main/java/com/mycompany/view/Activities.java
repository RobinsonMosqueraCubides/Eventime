/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.view;

import com.mycompany.controller.ActivityController;
import com.mycompany.controller.TriviaRoundController;
import com.mycompany.model.Activity;
import com.mycompany.model.CosplayScore;
import com.mycompany.controller.CosplayScoreController;
import com.mycompany.controller.TriviaController;
import com.mycompany.controller.TriviaQuestionController;
import com.mycompany.model.TriviaModel;
import com.mycompany.model.TriviaQuestion;
import com.mycompany.util.Conexion;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author hernan
 */
public class Activities extends javax.swing.JFrame {
private TriviaQuestionController triviaQuestionController;
private TriviaController triviaController;
private TriviaModel triviaModel;
    /**
     * Creates new form Activities
     */
    
    public Activities() {
        initComponents();
        setLocationRelativeTo(null); 
        ActivityController activityController = new ActivityController(this, ActivityTable);
        activityController.updateTable();
        TriviaRoundController triviaRoundController = new TriviaRoundController(this, TriviaTable);
        triviaRoundController.updateTable();
        cosplayScoreController = new CosplayScoreController(this, CosplayTable); // Inicialización
        cosplayScoreController.updateTable();
        triviaQuestionController = new TriviaQuestionController(this, TriviaQuestionsTable);
        System.out.println("triviaQuestionController inicializado: " + (triviaQuestionController != null));
        triviaQuestionController.updateTable();
        triviaModel = new TriviaModel();
        triviaController = new TriviaController(triviaModel, this);
        triviaQuestionController.updateTable();
        
    }
    
    public void addStartGameListener(ActionListener listener) {
        StartGameButton.addActionListener(listener);
    }

    public void addFinishGameListener(ActionListener listener) {
        FinishGameButton.addActionListener(listener);
    }
    
    public void setQuestions(List<String> questions) {
        JLabel[] questionsLabels = {Question1, Question2, Question3, Question4, Question5, Question6, Question7, Question8, Question9, Question10};
        for (int i = 0; i < questions.size(); i++) {
            questionsLabels[i].setText(questions.get(i));
        }
    }

    public List<String> getQuestions() {
        JLabel[] questionsLabels = {Question1, Question2, Question3, Question4, Question5, Question6, Question7, Question8, Question9, Question10};
        List<String> questions = new ArrayList<>();
        for (JLabel questionLabel : questionsLabels) {
            questions.add(questionLabel.getText());
        }
        return questions;
    }

    public List<String> getAnswers() {
        JTextField[] answersFields = {Answer1, Answer2, Answer3, Answer4, Answer5, Answer6, Answer7, Answer8, Answer9, Answer10};
        List<String> answers = new ArrayList<>();
        for (JTextField answerField : answersFields) {
            answers.add(answerField.getText());
        }
        return answers;
    }

    public void showScore(int score) {
        JOptionPane.showMessageDialog(this, "Tu puntaje es: " + score + "/10");
    }
    
    
    private void saveWinner(int participant1Id, int participant2Id, int winnerId) throws SQLException {
    Connection conexion = null;
    PreparedStatement stmt = null;

    try {
        conexion = Conexion.getInstance().conectar();
        String sql = "INSERT INTO TriviaRounds (event_id, round_number, participant1_id, participant2_id, winner_id) VALUES (?, ?, ?, ?, ?)";
        stmt = conexion.prepareStatement(sql);
        
        stmt.setInt(1,1 /* Event ID corre1spondiente */);
        stmt.setInt(2,1 /* Número de ronda, si aplicable */);
        stmt.setInt(3, participant1Id);
        stmt.setInt(4, participant2Id);
        stmt.setInt(5, winnerId);
        
        stmt.executeUpdate();
    } finally {
        if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conexion != null) Conexion.getInstance().cerrarConexion(conexion);
    }
}
    
    private JPanel createTriviaPanel(List<String> questions, String participantPrompt) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(questions.size() + 2, 2));

    // Campo de texto para el ID del participante
    panel.add(new JLabel(participantPrompt));
    JTextField idField = new JTextField(10);
    panel.add(idField);

    // Preguntas y campos de texto para las respuestas
    for (String question : questions) {
        panel.add(new JLabel(question));
        JTextField answerField = new JTextField(20);
        panel.add(answerField);
    }

    return panel;
}



private List<String> getAnswersFromPanel(JPanel panel, int numberOfQuestions) {
    List<String> answers = new ArrayList<>();

    // Itera sobre los componentes del panel para obtener las respuestas
    for (int i = 1; i < panel.getComponentCount(); i += 2) { // Asume que los JTextField están en posiciones impares
        Component component = panel.getComponent(i);
        if (component instanceof JTextField) {
            JTextField answerField = (JTextField) component;
            answers.add(answerField.getText());
        }
    }

    return answers;
}



private void processResults(int participant1Id, List<String> answers1, int participant2Id, List<String> answers2) throws SQLException {
    // Obtener las preguntas que se usaron en el juego
    List<String> questions = triviaModel.getRandomQuestions(); // Se deben usar las mismas preguntas para ambos participantes

    // Calcular puntajes
    int participant1Score = calculateScore(questions, answers1);
    int participant2Score = calculateScore(questions, answers2);

    // Determinar el ganador
    int winnerId = participant1Score > participant2Score ? participant1Id : participant2Id;

    // Mostrar puntajes y ganador
    JPanel resultPanel = new JPanel();
    resultPanel.add(new JLabel("Puntaje del participante " + participant1Id + ": " + participant1Score));
    resultPanel.add(new JLabel("Puntaje del participante " + participant2Id + ": " + participant2Score));
    resultPanel.add(new JLabel("El ganador es el participante con ID: " + winnerId));
    
    JOptionPane.showMessageDialog(null, resultPanel, "Resultado del Juego", JOptionPane.INFORMATION_MESSAGE);

    // Guardar el ganador en la base de datos
    saveWinner(participant1Id, participant2Id, winnerId);
}


private int calculateScore(List<String> questions, List<String> answers) throws SQLException {
    // Utiliza el método checkAnswers del TriviaModel para calcular el puntaje
    return triviaModel.checkAnswers(questions, answers);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        jButton5 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TypeActivity = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        NameActivity = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        DateActivity = new javax.swing.JTextField();
        ParticipantsNum = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        ManagerId = new javax.swing.JTextField();
        EventId = new javax.swing.JTextField();
        CategoriId = new javax.swing.JTextField();
        Update = new javax.swing.JButton();
        Create = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        back4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ActivityTable = new javax.swing.JTable();
        Search = new javax.swing.JButton();
        SearchText = new javax.swing.JTextField();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        Question1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Question2 = new javax.swing.JLabel();
        Question3 = new javax.swing.JLabel();
        Question4 = new javax.swing.JLabel();
        Question5 = new javax.swing.JLabel();
        Question6 = new javax.swing.JLabel();
        Question7 = new javax.swing.JLabel();
        Question8 = new javax.swing.JLabel();
        Question9 = new javax.swing.JLabel();
        Question10 = new javax.swing.JLabel();
        Answer1 = new javax.swing.JTextField();
        Answer2 = new javax.swing.JTextField();
        Answer3 = new javax.swing.JTextField();
        Answer4 = new javax.swing.JTextField();
        Answer5 = new javax.swing.JTextField();
        Answer6 = new javax.swing.JTextField();
        Answer7 = new javax.swing.JTextField();
        Answer8 = new javax.swing.JTextField();
        Answer9 = new javax.swing.JTextField();
        Answer10 = new javax.swing.JTextField();
        StartGameButton = new javax.swing.JButton();
        FinishGameButton = new javax.swing.JButton();
        StartRealGame = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TriviaTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        CosplayTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        AddButton = new javax.swing.JToggleButton();
        DeleteButton = new javax.swing.JToggleButton();
        EditCosplay = new javax.swing.JToggleButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        JudgeId = new javax.swing.JTextField();
        ParticipantId = new javax.swing.JTextField();
        ScoreCosplay = new javax.swing.JTextField();
        SearchCosplay = new javax.swing.JToggleButton();
        SearchCosplayText = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TriviaQuestionsTable = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        EventIdQuestion = new javax.swing.JTextField();
        QuestionText = new javax.swing.JTextField();
        CategoryIdText = new javax.swing.JTextField();
        CorrectAnswerText = new javax.swing.JTextField();
        CategoryText = new javax.swing.JTextField();
        SearchQuestionText = new javax.swing.JTextField();
        SearchQuestion = new javax.swing.JButton();
        CreateQuestion = new javax.swing.JButton();
        UpdateQuestion = new javax.swing.JButton();
        DeleteQuestion = new javax.swing.JButton();
        ComboBoxDifficult = new javax.swing.JComboBox<>();

        jButton5.setText("jButton5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(246, 243, 235));

        jLabel1.setFont(new java.awt.Font("Bradley Hand", 0, 48)); // NOI18N
        jLabel1.setText("Create Activity");

        TypeActivity.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        TypeActivity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cosplay", "Trivia" }));

        jLabel3.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel3.setText("Name");

        jLabel4.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel4.setText("Date Activity");

        jLabel5.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel5.setText("Type");

        jLabel7.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel7.setText("Participants");

        jLabel6.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel6.setText("Manager id");

        jLabel8.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel8.setText("Category id");

        jLabel9.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel9.setText("Event id");

        Update.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        Create.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Create.setText("Create Activity");
        Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateActionPerformed(evt);
            }
        });

        Delete.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Delete.setText("Delete Activity");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        back4.setBackground(new java.awt.Color(234, 230, 230));
        back4.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        back4.setText("Back");
        back4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back4ActionPerformed(evt);
            }
        });

        ActivityTable.setBackground(new java.awt.Color(236, 231, 215));
        ActivityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Type", "Participants", "Event Id", "Start Time", "Manager Id", "Category Id"
            }
        ));
        jScrollPane2.setViewportView(ActivityTable);

        Search.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        Search.setText("Search");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(115, 115, 115))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(98, 98, 98)
                                        .addComponent(NameActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8))
                                        .addGap(33, 33, 33)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(EventId, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ParticipantsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CategoriId, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(140, 140, 140)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel6)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(Create, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(Delete)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 30, Short.MAX_VALUE)
                                .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(121, 121, 121))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(Search)
                                        .addGap(18, 18, 18)
                                        .addComponent(SearchText))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(DateActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TypeActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ManagerId, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(back4)
                .addGap(481, 481, 481))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchText, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TypeActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NameActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(DateActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ParticipantsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(EventId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ManagerId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(CategoriId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Create, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(back4)
                .addContainerGap())
        );

        jTabbedPane5.addTab("tab1", jPanel1);

        jTabbedPane1.addTab("Management", jTabbedPane5);

        jPanel6.setBackground(new java.awt.Color(246, 243, 235));

        Question1.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question1.setText("Question 1");

        jLabel21.setFont(new java.awt.Font("Bradley Hand", 0, 36)); // NOI18N
        jLabel21.setText("WELCOME TO THE CHALLENGE");

        Question2.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question2.setText("Question 2");

        Question3.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question3.setText("Question 3");

        Question4.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question4.setText("Question 4");

        Question5.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question5.setText("Question 6");

        Question6.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question6.setText("Question 5");

        Question7.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question7.setText("Question 7");

        Question8.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question8.setText("Question 8");

        Question9.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question9.setText("Question 9");

        Question10.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        Question10.setText("Question 10");

        Answer1.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer2.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer3.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer4.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer5.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer6.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer7.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer8.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer9.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        Answer10.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        StartGameButton.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        StartGameButton.setText("Start Game");
        StartGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartGameButtonActionPerformed(evt);
            }
        });

        FinishGameButton.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        FinishGameButton.setText("Finish Game");
        FinishGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinishGameButtonActionPerformed(evt);
            }
        });

        StartRealGame.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        StartRealGame.setText("START!!!!!!");
        StartRealGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartRealGameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jLabel21))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(StartGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                                .addComponent(StartRealGame, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(132, 132, 132)
                                .addComponent(FinishGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Question1)
                                    .addComponent(Question2)
                                    .addComponent(Question3)
                                    .addComponent(Question4)
                                    .addComponent(Question6)
                                    .addComponent(Question5)
                                    .addComponent(Question7)
                                    .addComponent(Question8)
                                    .addComponent(Question9)
                                    .addComponent(Question10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Answer10, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                                        .addComponent(Answer9)
                                        .addComponent(Answer8)
                                        .addComponent(Answer7)
                                        .addComponent(Answer6)
                                        .addComponent(Answer5)
                                        .addComponent(Answer4)
                                        .addComponent(Answer3)
                                        .addComponent(Answer2))
                                    .addComponent(Answer1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(161, 161, 161))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel21)
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StartGameButton)
                    .addComponent(FinishGameButton)
                    .addComponent(StartRealGame))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question1)
                    .addComponent(Answer1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question2)
                    .addComponent(Answer2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question3)
                    .addComponent(Answer3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question4)
                    .addComponent(Answer4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question6)
                    .addComponent(Answer5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question5)
                    .addComponent(Answer6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question7)
                    .addComponent(Answer7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question8)
                    .addComponent(Answer8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question9)
                    .addComponent(Answer9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Question10)
                    .addComponent(Answer10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jTabbedPane3.addTab("tab1", jPanel6);

        jTabbedPane1.addTab("Trivia", jTabbedPane3);

        jPanel4.setBackground(new java.awt.Color(246, 243, 235));

        TriviaTable.setBackground(new java.awt.Color(236, 231, 215));
        TriviaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Event Id", "Round Number", "Participant 1 id", "Participant 2 id", "Winner id"
            }
        ));
        jScrollPane1.setViewportView(TriviaTable);

        jLabel10.setFont(new java.awt.Font("Bradley Hand", 0, 36)); // NOI18N
        jLabel10.setText("TRIVIA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(444, 444, 444))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );

        jPanel5.setBackground(new java.awt.Color(246, 243, 235));

        CosplayTable.setBackground(new java.awt.Color(236, 231, 215));
        CosplayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Participant Id", "Judge Id", "Score"
            }
        ));
        jScrollPane4.setViewportView(CosplayTable);

        jLabel2.setFont(new java.awt.Font("Bradley Hand", 0, 36)); // NOI18N
        jLabel2.setText("COSPLAY");

        AddButton.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        AddButton.setText("Add ");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        DeleteButton.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        EditCosplay.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        EditCosplay.setText("Edit");
        EditCosplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditCosplayActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel11.setText("Participant_Id");

        jLabel13.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel13.setText("Judge_Id");

        jLabel14.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel14.setText("Score");

        SearchCosplay.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N
        SearchCosplay.setText("Search");
        SearchCosplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchCosplayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(74, 74, 74)
                        .addComponent(JudgeId))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(ParticipantId, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ScoreCosplay, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52)
                .addComponent(SearchCosplay)
                .addGap(18, 18, 18)
                .addComponent(SearchCosplayText, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 343, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditCosplay, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(419, 419, 419))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(ScoreCosplay, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(ParticipantId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchCosplay)
                            .addComponent(SearchCosplayText, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EditCosplay)
                            .addComponent(jLabel13)
                            .addComponent(JudgeId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(AddButton)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("tab1", jPanel3);

        jTabbedPane1.addTab("Score", jTabbedPane4);

        jPanel2.setBackground(new java.awt.Color(246, 243, 235));

        TriviaQuestionsTable.setBackground(new java.awt.Color(236, 231, 215));
        TriviaQuestionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Question", "Correct Answer", "Category", "difficulty", "event_id", "category_id"
            }
        ));
        jScrollPane3.setViewportView(TriviaQuestionsTable);

        jLabel12.setFont(new java.awt.Font("Bradley Hand", 0, 48)); // NOI18N
        jLabel12.setText("CreateQuestions");

        jLabel15.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel15.setText("Question");

        jLabel16.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel16.setText("Difficult");

        jLabel17.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel17.setText("Correct Answer");

        jLabel18.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel18.setText("Category");

        jLabel19.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel19.setText("Category id");

        jLabel20.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        jLabel20.setText("Event id");

        EventIdQuestion.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        QuestionText.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        CategoryIdText.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        CorrectAnswerText.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        CategoryText.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        SearchQuestionText.setFont(new java.awt.Font("Bradley Hand", 0, 18)); // NOI18N

        SearchQuestion.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        SearchQuestion.setText("Search");
        SearchQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchQuestionActionPerformed(evt);
            }
        });

        CreateQuestion.setBackground(new java.awt.Color(232, 224, 194));
        CreateQuestion.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        CreateQuestion.setText("Create");
        CreateQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateQuestionActionPerformed(evt);
            }
        });

        UpdateQuestion.setBackground(new java.awt.Color(232, 224, 194));
        UpdateQuestion.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        UpdateQuestion.setText("Update");
        UpdateQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateQuestionActionPerformed(evt);
            }
        });

        DeleteQuestion.setBackground(new java.awt.Color(232, 224, 194));
        DeleteQuestion.setFont(new java.awt.Font("Bradley Hand", 0, 24)); // NOI18N
        DeleteQuestion.setText("Delete");
        DeleteQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteQuestionActionPerformed(evt);
            }
        });

        ComboBoxDifficult.setFont(new java.awt.Font("Bradley Hand", 0, 14)); // NOI18N
        ComboBoxDifficult.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EASY", "INTERMEDIATE", "HARD" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(222, 222, 222))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(18, 18, 18)
                                        .addComponent(CorrectAnswerText))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(18, 18, 18)
                                        .addComponent(QuestionText, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(20, 20, 20)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(CategoryText, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(SearchQuestion))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(ComboBoxDifficult, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(CreateQuestion)
                                        .addGap(67, 67, 67)))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(UpdateQuestion)
                                .addGap(56, 56, 56)
                                .addComponent(DeleteQuestion)
                                .addGap(56, 56, 56))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CategoryIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(EventIdQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SearchQuestionText, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(QuestionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(CategoryIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorrectAnswerText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(EventIdQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(SearchQuestionText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchQuestion)
                    .addComponent(CategoryText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(ComboBoxDifficult, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CreateQuestion)
                            .addComponent(UpdateQuestion)
                            .addComponent(DeleteQuestion))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab1", jPanel2);

        jTabbedPane1.addTab("Create Questions", jTabbedPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void back4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back4ActionPerformed
        Software soft = new Software();
        soft.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_back4ActionPerformed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        try {
        int participantId = Integer.parseInt(ParticipantId.getText());
        int judgeId = Integer.parseInt(JudgeId.getText());
        double score = Double.parseDouble(ScoreCosplay.getText());

        cosplayScoreController.createCosplayScore(participantId, judgeId, score); // Usar la instancia global

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos.");
    }
    }//GEN-LAST:event_AddButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        try {
            int scoreId = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingresa el ID a eliminar:"));

            CosplayScoreController cosplayScoreController = new CosplayScoreController(this, CosplayTable);
            cosplayScoreController.deleteCosplayScore(scoreId);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.");
        }
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void EditCosplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditCosplayActionPerformed
        try {
        // Solicitar ID del puntaje a editar
        int scoreId = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingresa el ID del puntaje a actualizar:"));
        int participantId = Integer.parseInt(ParticipantId.getText());
        
        // Solicitar los puntajes de dos jueces
        int judgeId1 = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingresa el ID del primer juez:"));
        double score1 = Double.parseDouble(JOptionPane.showInputDialog(this, "Ingresa el puntaje del primer juez:"));
        
        int judgeId2 = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingresa el ID del segundo juez:"));
        double score2 = Double.parseDouble(JOptionPane.showInputDialog(this, "Ingresa el puntaje del segundo juez:"));
        
        // Calcular el promedio de los puntajes
        double averageScore = (score1 + score2) / 2.0;

        // Actualizar el puntaje promedio en la base de datos
        CosplayScoreController cosplayScoreController = new CosplayScoreController(this, CosplayTable);
        cosplayScoreController.updateCosplayScore(scoreId, participantId, judgeId1, averageScore);
        
        // Actualizar la tabla con el nuevo puntaje promedio
        DefaultTableModel model = (DefaultTableModel) CosplayTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (Integer.parseInt(model.getValueAt(i, 0).toString()) == scoreId) {
                model.setValueAt(averageScore, i, 3); // Suponiendo que el puntaje se muestra en la cuarta columna
                break;
            }
        }

        JOptionPane.showMessageDialog(this, "Puntaje actualizado exitosamente.");
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos.");
    }
    }//GEN-LAST:event_EditCosplayActionPerformed

    private CosplayScoreController cosplayScoreController;
    private void SearchCosplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchCosplayActionPerformed
        try {
        int scoreId = Integer.parseInt(SearchCosplayText.getText());
        CosplayScore scoreResult = cosplayScoreController.searchCosplayScore(scoreId);

        if (scoreResult != null) {
            ParticipantId.setText(String.valueOf(scoreResult.getParticipantId()));
            JudgeId.setText(String.valueOf(scoreResult.getJudgeId()));
            ScoreCosplay.setText(String.valueOf(scoreResult.getScore()));
        } else {
            JOptionPane.showMessageDialog(this, "Puntaje no encontrado.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.");
    }
    }//GEN-LAST:event_SearchCosplayActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        try {
            int activityId = Integer.parseInt(SearchText.getText());
            ActivityController activityController = new ActivityController(this, ActivityTable);
            Activity activity = activityController.searchActivity(activityId);

            if (activity != null) {
                NameActivity.setText(activity.getName());
                TypeActivity.setSelectedItem(activity.getType());
                ParticipantsNum.setText(String.valueOf(activity.getNumberOfParticipants()));
                EventId.setText(String.valueOf(activity.getEventId()));
                DateActivity.setText(activity.getStartTime());
                ManagerId.setText(String.valueOf(activity.getManagerId()));
                CategoriId.setText(String.valueOf(activity.getCategoryId()));
            } else {
                JOptionPane.showMessageDialog(this, "Actividad no encontrada.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.");
        }
    }//GEN-LAST:event_SearchActionPerformed

    private void CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateActionPerformed
        try {
            String name = NameActivity.getText();
            String type = TypeActivity.getSelectedItem().toString();
            int numberOfParticipants = Integer.parseInt(ParticipantsNum.getText());
            int eventId = Integer.parseInt(EventId.getText());
            String startTime = DateActivity.getText();
            int managerId = Integer.parseInt(ManagerId.getText());
            int categoryId = Integer.parseInt(CategoriId.getText());

            if (name.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.");
                return;
            }

            ActivityController activityController = new ActivityController(this, ActivityTable);
            activityController.createActivity(name, type, numberOfParticipants, eventId, startTime, managerId, categoryId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa números válidos para los campos numéricos.");
        }
    }//GEN-LAST:event_CreateActionPerformed

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
         try {
            int activityId = Integer.parseInt(SearchText.getText());
            String name = NameActivity.getText();
            String type = TypeActivity.getSelectedItem().toString();
            int numberOfParticipants = Integer.parseInt(ParticipantsNum.getText());
            int eventId = Integer.parseInt(EventId.getText());
            String startTime = DateActivity.getText();
            int managerId = Integer.parseInt(ManagerId.getText());
            int categoryId = Integer.parseInt(CategoriId.getText());

            if (name.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos requeridos.");
                return;
            }

            ActivityController activityController = new ActivityController(this, ActivityTable);
            Activity activity = new Activity(name, type, numberOfParticipants, eventId, startTime, managerId, categoryId);

            activityController.updateActivity(activityId, activity);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa números válidos para los campos numéricos.");
        }
    }//GEN-LAST:event_UpdateActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        try {
            int activityId = Integer.parseInt(SearchText.getText());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar esta actividad?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            ActivityController activityController = new ActivityController(this, ActivityTable);
            activityController.deleteActivity(activityId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.");
        }
    }//GEN-LAST:event_DeleteActionPerformed

    private void SearchQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchQuestionActionPerformed
        try {
            int questionId = Integer.parseInt(SearchQuestionText.getText());
            TriviaQuestion triviaQuestion = triviaQuestionController.searchTriviaQuestion(questionId);

            if (triviaQuestion != null) {
                QuestionText.setText(triviaQuestion.getQuestion());
                CorrectAnswerText.setText(triviaQuestion.getCorrectAnswer());
                CategoryText.setText(triviaQuestion.getCategory());
                ComboBoxDifficult.setSelectedItem(triviaQuestion.getDifficulty());
                EventIdQuestion.setText(String.valueOf(triviaQuestion.getEventId()));
                CategoryIdText.setText(String.valueOf(triviaQuestion.getCategoryId()));
            } else {
                JOptionPane.showMessageDialog(this, "Pregunta de trivia no encontrada.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.");
        }
    }//GEN-LAST:event_SearchQuestionActionPerformed

    private void CreateQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateQuestionActionPerformed
        try {
            String question = QuestionText.getText();
            String correctAnswer = CorrectAnswerText.getText();
            String category = CategoryText.getText();
            String difficulty = ComboBoxDifficult.getSelectedItem().toString();
            int eventId = Integer.parseInt(EventIdQuestion.getText());
            int categoryId = Integer.parseInt(CategoryIdText.getText());

            triviaQuestionController.createTriviaQuestion(question, correctAnswer, category, difficulty, eventId, categoryId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos.");
        }
    }//GEN-LAST:event_CreateQuestionActionPerformed

    private void UpdateQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateQuestionActionPerformed
        try {
            int questionId = Integer.parseInt(SearchQuestionText.getText());
            String question = QuestionText.getText();
            String correctAnswer = CorrectAnswerText.getText();
            String category = CategoryText.getText();
            String difficulty = ComboBoxDifficult.getSelectedItem().toString();
            int eventId = Integer.parseInt(EventIdQuestion.getText());
            int categoryId = Integer.parseInt(CategoryIdText.getText());

            TriviaQuestion triviaQuestion = new TriviaQuestion(question, correctAnswer, category, difficulty, eventId, categoryId);
            triviaQuestionController.updateTriviaQuestion(questionId, triviaQuestion);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos.");
        }
    }//GEN-LAST:event_UpdateQuestionActionPerformed

    private void DeleteQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteQuestionActionPerformed
        try {
            int questionId = Integer.parseInt(SearchQuestionText.getText());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar esta pregunta de trivia?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            triviaQuestionController.deleteTriviaQuestion(questionId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID válido.");
        }
    }//GEN-LAST:event_DeleteQuestionActionPerformed

    private void StartGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartGameButtonActionPerformed
        try {
        List<String> questions = triviaModel.getRandomQuestions();
        setQuestions(questions);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }//GEN-LAST:event_StartGameButtonActionPerformed

    private void FinishGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinishGameButtonActionPerformed
        try {
        List<String> answers = getAnswers();
        int score = triviaModel.checkAnswers(getQuestions(), answers);
        showScore(score);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }//GEN-LAST:event_FinishGameButtonActionPerformed

    private void StartRealGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartRealGameActionPerformed
        try {
        // Crear el panel para el primer participante
        List<String> questions = triviaModel.getRandomQuestions();
        JPanel triviaPanel = createTriviaPanel(questions, "Ingrese el ID del primer participante:");
        int result = JOptionPane.showConfirmDialog(this, triviaPanel, "Trivia", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            // Obtener ID del primer participante
            JTextField idField = (JTextField) triviaPanel.getComponent(1);
            int participant1Id = Integer.parseInt(idField.getText());
            // Obtener respuestas del primer participante
            List<String> answers1 = getAnswersFromPanel(triviaPanel, questions.size());
            int score1 = triviaModel.checkAnswers(questions, answers1);
            // Crear el panel para el segundo participante
            triviaPanel = createTriviaPanel(questions, "Ingrese el ID del segundo participante:");
            result = JOptionPane.showConfirmDialog(this, triviaPanel, "Trivia", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                // Obtener ID del segundo participante
                idField = (JTextField) triviaPanel.getComponent(1);
                int participant2Id = Integer.parseInt(idField.getText());
                // Obtener respuestas del segundo participante
                List<String> answers2 = getAnswersFromPanel(triviaPanel, questions.size());
                int score2 = triviaModel.checkAnswers(questions, answers2);

                // Determinar el ganador
                int winnerId;
                String resultMessage;
                if (score1 > score2) {
                    winnerId = participant1Id;
                    resultMessage = "El ganador es el participante con ID: " + winnerId;
                } else if (score2 > score1) {
                    winnerId = participant2Id;
                    resultMessage = "El ganador es el participante con ID: " + winnerId;
                } else {
                    winnerId = -1; // No hay ganador, es un empate
                    resultMessage = "Empate";
                }

                // Mostrar resultados y guardar el ganador
                JOptionPane.showMessageDialog(this, resultMessage);
                if (winnerId != -1) {
                    saveWinner(participant1Id, participant2Id, winnerId);
                } else {
                    saveTie(participant1Id, participant2Id); // Método para manejar empate
                }
            }
        }
    } catch (SQLException | NumberFormatException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al procesar la trivia. " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_StartRealGameActionPerformed

    
    private void saveTie(int participant1Id, int participant2Id) throws SQLException {
    Connection conexion = null;
    PreparedStatement stmt = null;

    try {
        conexion = Conexion.getInstance().conectar();
        String sql = "INSERT INTO TriviaRounds (event_id, round_number, participant1_id, participant2_id, winner_id) VALUES (?, ?, ?, ?, NULL)";
        stmt = conexion.prepareStatement(sql);

        stmt.setInt(1, 1 /* Event ID correspondiente */);
        stmt.setInt(2, 1 /* Número de ronda, si aplicable */);
        stmt.setInt(3, participant1Id);
        stmt.setInt(4, participant2Id);

        stmt.executeUpdate();
    } finally {
        if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conexion != null) Conexion.getInstance().cerrarConexion(conexion);
    }
}
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Activities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Activities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Activities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Activities.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Activities().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ActivityTable;
    private javax.swing.JToggleButton AddButton;
    private javax.swing.JTextField Answer1;
    private javax.swing.JTextField Answer10;
    private javax.swing.JTextField Answer2;
    private javax.swing.JTextField Answer3;
    private javax.swing.JTextField Answer4;
    private javax.swing.JTextField Answer5;
    private javax.swing.JTextField Answer6;
    private javax.swing.JTextField Answer7;
    private javax.swing.JTextField Answer8;
    private javax.swing.JTextField Answer9;
    private javax.swing.JTextField CategoriId;
    private javax.swing.JTextField CategoryIdText;
    private javax.swing.JTextField CategoryText;
    private javax.swing.JComboBox<String> ComboBoxDifficult;
    private javax.swing.JTextField CorrectAnswerText;
    private javax.swing.JTable CosplayTable;
    private javax.swing.JButton Create;
    private javax.swing.JButton CreateQuestion;
    private javax.swing.JTextField DateActivity;
    private javax.swing.JButton Delete;
    private javax.swing.JToggleButton DeleteButton;
    private javax.swing.JButton DeleteQuestion;
    private javax.swing.JToggleButton EditCosplay;
    private javax.swing.JTextField EventId;
    private javax.swing.JTextField EventIdQuestion;
    private javax.swing.JButton FinishGameButton;
    private javax.swing.JTextField JudgeId;
    private javax.swing.JTextField ManagerId;
    private javax.swing.JTextField NameActivity;
    private javax.swing.JTextField ParticipantId;
    private javax.swing.JTextField ParticipantsNum;
    private javax.swing.JLabel Question1;
    private javax.swing.JLabel Question10;
    private javax.swing.JLabel Question2;
    private javax.swing.JLabel Question3;
    private javax.swing.JLabel Question4;
    private javax.swing.JLabel Question5;
    private javax.swing.JLabel Question6;
    private javax.swing.JLabel Question7;
    private javax.swing.JLabel Question8;
    private javax.swing.JLabel Question9;
    private javax.swing.JTextField QuestionText;
    private javax.swing.JTextField ScoreCosplay;
    private javax.swing.JButton Search;
    private javax.swing.JToggleButton SearchCosplay;
    private javax.swing.JTextField SearchCosplayText;
    private javax.swing.JButton SearchQuestion;
    private javax.swing.JTextField SearchQuestionText;
    private javax.swing.JTextField SearchText;
    private javax.swing.JButton StartGameButton;
    private javax.swing.JButton StartRealGame;
    private javax.swing.JTable TriviaQuestionsTable;
    private javax.swing.JTable TriviaTable;
    private javax.swing.JComboBox<String> TypeActivity;
    private javax.swing.JButton Update;
    private javax.swing.JButton UpdateQuestion;
    private javax.swing.JButton back4;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    // End of variables declaration//GEN-END:variables
}
