package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import backend.*;

public class Dashboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Competitor competitor;

    public Dashboard(Competitor competitor) {
        this.competitor = competitor;
        
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 710, 595);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title
        JLabel lblTitle = new JLabel("Welcome to Quiz Application!");
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 32));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(50, 56, 600, 75);
        contentPane.add(lblTitle);

        // User info panel
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Color.WHITE);
        userPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        userPanel.setBounds(220, 185, 250, 75);
        userPanel.setLayout(null);
        contentPane.add(userPanel);

        JLabel lblName = new JLabel("Name: " + competitor.getName().toString());
        lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblName.setBounds(20, 10, 342, 30);
        userPanel.add(lblName);

        JLabel lblDetails = new JLabel("Country: " + competitor.getCountry() + " | Level: " + competitor.getLevel());
        lblDetails.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDetails.setBounds(20, 45, 490, 20);
        userPanel.add(lblDetails);

        // Question label
        JLabel lblQuestion = new JLabel("What would you like to do?");
        lblQuestion.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuestion.setBounds(50, 270, 600, 30);
        contentPane.add(lblQuestion);

        // Play Quiz Button
        JButton btnPlayQuiz = new JButton("Play Quiz");
        btnPlayQuiz.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnPlayQuiz.setBackground(Color.WHITE);
        btnPlayQuiz.setBounds(220, 320, 250, 38);
        btnPlayQuiz.setFocusPainted(false);
        btnPlayQuiz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openQuiz();
            }
        });
        contentPane.add(btnPlayQuiz);

        // View Leaderboard Button
        JButton btnLeaderboard = new JButton("View Leaderboard");
        btnLeaderboard.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLeaderboard.setBackground(Color.WHITE);
        btnLeaderboard.setBounds(220, 370, 250, 38);
        btnLeaderboard.setFocusPainted(false);
        btnLeaderboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLeaderboard();
            }
        });
        contentPane.add(btnLeaderboard);

        // View Competitor Details Button
        JButton btnViewDetails = new JButton("View Competitor Details");
        btnViewDetails.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnViewDetails.setBackground(Color.WHITE);
        btnViewDetails.setBounds(220, 420, 250, 38);
        btnViewDetails.setFocusPainted(false);
        btnViewDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewCompetitorDetails();
            }
        });
        contentPane.add(btnViewDetails);

        // Logout Button
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setBounds(270, 480, 150, 38);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        contentPane.add(btnLogout);
    }

    private void openQuiz() {
        this.dispose();
        Quiz quizFrame = new Quiz(competitor);
        quizFrame.setVisible(true);
    }

    private void openLeaderboard() {
        Leaderboard leaderboardFrame = new Leaderboard(competitor);
        leaderboardFrame.setVisible(true);
    }

    private void viewCompetitorDetails() {
        String input = JOptionPane.showInputDialog(this, "Enter Competitor ID:", "Search", JOptionPane.PLAIN_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            try {
                int competitorId = Integer.parseInt(input.trim());
                Manager manager = new Manager();
                manager.loadCompetitorsFromDatabase();
                Competitor found = manager.findCompetitor(competitorId);

                if (found != null) {
                    String fullDetails = found.getFullDetails();
                    String shortDetails = found.getShortDetails();
                    
                    String combinedDetails = fullDetails + "\n\n" + 
                                           "Short Details for CompetitorID " + found.getCompetitorID() + ":\n" +
                                           shortDetails;
                    
                    JTextArea textArea = new JTextArea(combinedDetails);
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(450, 200));
                    JOptionPane.showMessageDialog(this, scrollPane, "Details", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Competitor not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            this.dispose();
            login loginFrame = new login();
            loginFrame.setVisible(true);
        }
    }
}