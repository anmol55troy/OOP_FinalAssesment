package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import backend.Competitor;

public class Details extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public Details(Competitor competitor, int correctAnswers, int totalQuestions) {
        setTitle("Quiz Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 600);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Quiz Completed!");
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(50, 30, 600, 50);
        contentPane.add(lblTitle);

        JPanel resultsPanel = new JPanel();
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        resultsPanel.setBounds(50, 100, 600, 350);
        resultsPanel.setLayout(null);
        contentPane.add(resultsPanel);

        JLabel lblFullDetailsTitle = new JLabel(
                "Full Details for CompetitorID " + competitor.getCompetitorID() + ":");
        lblFullDetailsTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblFullDetailsTitle.setBounds(30, 20, 540, 25);
        resultsPanel.add(lblFullDetailsTitle);

        JTextArea txtFullDetails = new JTextArea();
        txtFullDetails.setText(competitor.getFullDetails());
        txtFullDetails.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtFullDetails.setEditable(false);
        txtFullDetails.setBackground(Color.WHITE);
        txtFullDetails.setLineWrap(true);
        txtFullDetails.setWrapStyleWord(true);
        txtFullDetails.setBounds(30, 50, 540, 90);
        resultsPanel.add(txtFullDetails);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(30, 150, 540, 2);
        resultsPanel.add(separator1);

        JLabel lblShortDetailsTitle = new JLabel(
                "Short Details for CompetitorID " + competitor.getCompetitorID() + ":");
        lblShortDetailsTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblShortDetailsTitle.setBounds(30, 165, 540, 25);
        resultsPanel.add(lblShortDetailsTitle);

        JTextArea txtShortDetails = new JTextArea();
        txtShortDetails.setText(competitor.getShortDetails());
        txtShortDetails.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtShortDetails.setEditable(false);
        txtShortDetails.setBackground(Color.WHITE);
        txtShortDetails.setLineWrap(true);
        txtShortDetails.setWrapStyleWord(true);
        txtShortDetails.setBounds(30, 195, 540, 45);
        resultsPanel.add(txtShortDetails);

        JSeparator separator2 = new JSeparator();
        separator2.setBounds(30, 245, 540, 2);
        resultsPanel.add(separator2);

        JLabel lblCorrect = new JLabel("Correct Answers: " + correctAnswers + " / " + totalQuestions);
        lblCorrect.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblCorrect.setBounds(30, 255, 400, 30);
        resultsPanel.add(lblCorrect);

        String message = getPerformanceMessage(correctAnswers, totalQuestions);
        JLabel lblMessage = new JLabel("<html>" + message + "</html>");
        lblMessage.setFont(new Font("Tahoma", Font.ITALIC, 14));
        lblMessage.setBounds(30, 290, 540, 40);
        resultsPanel.add(lblMessage);

        JButton btnBackToDashboard = new JButton("Back to Dashboard");
        btnBackToDashboard.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnBackToDashboard.setBounds(50, 470, 200, 40);
        btnBackToDashboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Dashboard dashboardFrame = new Dashboard(competitor);
                dashboardFrame.setVisible(true);
            }
        });
        contentPane.add(btnBackToDashboard);

        JButton btnViewLeaderboard = new JButton("View Leaderboard");
        btnViewLeaderboard.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnViewLeaderboard.setBounds(270, 470, 180, 40);
        btnViewLeaderboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Leaderboard leaderboardFrame = new Leaderboard(competitor);
                leaderboardFrame.setVisible(true);
            }
        });
        contentPane.add(btnViewLeaderboard);

        JButton btnTakeAnother = new JButton("Take Another Quiz");
        btnTakeAnother.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnTakeAnother.setBounds(470, 470, 180, 40);
        btnTakeAnother.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Quiz quizFrame = new Quiz(competitor);
                quizFrame.setVisible(true);
            }
        });
        contentPane.add(btnTakeAnother);
    }

    private String getPerformanceMessage(int correct, int total) {
        double percentage = (correct * 100.0) / total;
        if (percentage == 100) {
            return "Perfect! You got all questions correct!";
        } else if (percentage >= 80) {
            return "Excellent work! You did very well!";
        } else if (percentage >= 60) {
            return "Good job! Keep practicing to improve further!";
        } else if (percentage >= 40) {
            return "Not bad! Try again to improve your score!";
        } else {
            return "Keep trying! Practice makes perfect!";
        }
    }
}
