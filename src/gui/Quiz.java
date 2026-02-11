package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import backend.*;
import database.DBFetch;

public class Quiz extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Competitor competitor;
    private ArrayList<Question> allQuestions;
    private int currentRound = 1;
    private int currentQuestionInRound = 0;
    private int[][] roundScores; // 5 rounds x 5 questions
    private int correctAnswers = 0;
    private ArrayList<Question> currentRoundQuestions;

    private JLabel lblQuestionNumber;
    private JLabel lblQuestion;
    private JRadioButton radioA, radioB, radioC, radioD;
    private ButtonGroup buttonGroup;
    private JButton btnNext;
    private JProgressBar progressBar;

    /**
     * Create the frame.
     */
    public Quiz(Competitor competitor) {
        this.competitor = competitor;
        this.allQuestions = DBFetch.getRandomQuestions(competitor.getLevel(), 25);
        this.roundScores = new int[5][5];
        this.currentRoundQuestions = new ArrayList<>();

        setTitle("Quiz - " + competitor.getLevel().toString());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Header
        JLabel lblHeader = new JLabel("Quiz - Round 1");
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeader.setBounds(50, 20, 600, 30);
        contentPane.add(lblHeader);

        // Question number
        lblQuestionNumber = new JLabel("Question 1 of 5");
        lblQuestionNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblQuestionNumber.setBounds(50, 60, 200, 20);
        contentPane.add(lblQuestionNumber);

        // Question text
        lblQuestion = new JLabel("");
        lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblQuestion.setVerticalAlignment(SwingConstants.TOP);
        lblQuestion.setBounds(50, 90, 600, 60);
        contentPane.add(lblQuestion);

        // Radio buttons for options
        buttonGroup = new ButtonGroup();

        radioA = new JRadioButton("A");
        radioA.setFont(new Font("Tahoma", Font.PLAIN, 13));
        radioA.setBounds(50, 170, 600, 25);
        radioA.setBackground(Color.WHITE);
        buttonGroup.add(radioA);
        contentPane.add(radioA);

        radioB = new JRadioButton("B");
        radioB.setFont(new Font("Tahoma", Font.PLAIN, 13));
        radioB.setBounds(50, 205, 600, 25);
        radioB.setBackground(Color.WHITE);
        buttonGroup.add(radioB);
        contentPane.add(radioB);

        radioC = new JRadioButton("C");
        radioC.setFont(new Font("Tahoma", Font.PLAIN, 13));
        radioC.setBounds(50, 240, 600, 25);
        radioC.setBackground(Color.WHITE);
        buttonGroup.add(radioC);
        contentPane.add(radioC);

        radioD = new JRadioButton("D");
        radioD.setFont(new Font("Tahoma", Font.PLAIN, 13));
        radioD.setBounds(50, 275, 600, 25);
        radioD.setBackground(Color.WHITE);
        buttonGroup.add(radioD);
        contentPane.add(radioD);

        // Next button
        btnNext = new JButton("Next");
        btnNext.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNext.setBounds(500, 400, 150, 35);
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleNext();
            }
        });
        contentPane.add(btnNext);

        // Load first round questions
        if (!allQuestions.isEmpty()) {
            loadRoundQuestions();
            loadQuestion();
        } else {
            JOptionPane.showMessageDialog(this, 
                "No questions available for this level. Please add questions to the database.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Load 5 questions for current round
     */
    private void loadRoundQuestions() {
        currentRoundQuestions.clear();
        int startIdx = (currentRound - 1) * 5;
        int endIdx = Math.min(startIdx + 5, allQuestions.size());
        
        for (int i = startIdx; i < endIdx; i++) {
            currentRoundQuestions.add(allQuestions.get(i));
        }
        
        currentQuestionInRound = 0;
    }

    /**
     * Load current question
     */
    private void loadQuestion() {
        if (currentQuestionInRound < currentRoundQuestions.size()) {
            Question q = currentRoundQuestions.get(currentQuestionInRound);

            lblQuestionNumber.setText("Question " + (currentQuestionInRound + 1) + " of 5");
            lblQuestion.setText(q.getQuestionText());

            radioA.setText("A. " + q.getOptionA());
            radioB.setText("B. " + q.getOptionB());
            radioC.setText("C. " + q.getOptionC());
            radioD.setText("D. " + q.getOptionD());

            buttonGroup.clearSelection();

            if (currentQuestionInRound == 4) {
                if (currentRound == 5) {
                    btnNext.setText("Finish");
                } else {
                    btnNext.setText("Next Round");
                }
            } else {
                btnNext.setText("Next");
            }
        }
    }

    /**
     * Handle next button click
     */
    private void handleNext() {
        // Check if an answer is selected
        if (!radioA.isSelected() && !radioB.isSelected() && 
            !radioC.isSelected() && !radioD.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select an answer", "Warning", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        // Check answer
        Question currentQuestion = currentRoundQuestions.get(currentQuestionInRound);
        String selectedAnswer = "";

        if (radioA.isSelected()) selectedAnswer = "A";
        else if (radioB.isSelected()) selectedAnswer = "B";
        else if (radioC.isSelected()) selectedAnswer = "C";
        else if (radioD.isSelected()) selectedAnswer = "D";

        // Score the question (5 points for correct, 0 for wrong)
        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            roundScores[currentRound - 1][currentQuestionInRound] = 5;
            correctAnswers++;
        } else {
            roundScores[currentRound - 1][currentQuestionInRound] = 0;
        }

        currentQuestionInRound++;

        if (currentQuestionInRound < 5) {
            // Load next question in same round
            loadQuestion();
        } else {
            // Round finished
            if (currentRound < 5) {
                // Move to next round
                currentRound++;
                JLabel lblHeader = (JLabel) contentPane.getComponent(0);
                lblHeader.setText("Quiz - Round " + currentRound);
                loadRoundQuestions();
                loadQuestion();
            } else {
                // All rounds finished
                showResults();
            }
        }
    }

    /**
     * Show quiz results
     */
    private void showResults() {
        // Flatten scores for storage (take average of each round)
        int[] finalScores = new int[5];
        for (int i = 0; i < 5; i++) {
            int roundTotal = 0;
            for (int j = 0; j < 5; j++) {
                roundTotal += roundScores[i][j];
            }
            finalScores[i] = roundTotal / 5;
        }
        
        competitor.setScores(finalScores);
        
        // Save to database
        backend.Manager manager = new backend.Manager();
        boolean saved = manager.addCompetitorToDatabase(competitor);

        // Show results window
        this.dispose();
        Details detailsFrame = new Details(competitor, correctAnswers, 25);
        detailsFrame.setVisible(true);
    }
}