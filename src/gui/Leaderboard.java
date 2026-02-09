package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import backend.*;

public class Leaderboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private Competitor currentCompetitor;
    private JLabel lblTotalCompetitors;
    private JLabel lblHighestScorer;
    private JLabel lblFrequency;

    /**
     * Create the frame.
     */
    public Leaderboard(Competitor competitor) {
        this.currentCompetitor = competitor;
        setTitle("Leaderboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title
        JLabel lblTitle = new JLabel("Leaderboard");
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(50, 20, 800, 50);
        contentPane.add(lblTitle);

        // Table
        String[] columnNames = {"Rank", "ID", "Name", "Level", "Country", "Scores", "Overall Score"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 80, 800, 320);
        contentPane.add(scrollPane);

        // Statistical Summary Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistical Summary"));
        statsPanel.setBounds(50, 410, 800, 110);
        statsPanel.setLayout(null);
        contentPane.add(statsPanel);

        lblTotalCompetitors = new JLabel("");
        lblTotalCompetitors.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTotalCompetitors.setBounds(10, 20, 780, 20);
        statsPanel.add(lblTotalCompetitors);

        lblHighestScorer = new JLabel("");
        lblHighestScorer.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblHighestScorer.setBounds(10, 40, 780, 20);
        statsPanel.add(lblHighestScorer);

        lblFrequency = new JLabel("");
        lblFrequency.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFrequency.setBounds(10, 60, 780, 40);
        statsPanel.add(lblFrequency);

        // Buttons
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnBack.setBounds(350, 530, 200, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Dashboard dashboardFrame = new Dashboard(currentCompetitor);
                dashboardFrame.setVisible(true);
            }
        });
        contentPane.add(btnBack);

        // Load initial data
        loadLeaderboard();
    }

    /**
     * Load leaderboard data
     */
    private void loadLeaderboard() {
        tableModel.setRowCount(0); // Clear existing data

        Manager manager = new Manager();
        manager.loadCompetitorsFromDatabase();

        CompetitorList competitorList = manager.getCompetitorList();
        var competitors = competitorList.getAllCompetitors();

        // Sort by overall score (descending)
        competitors.sort((c1, c2) -> Double.compare(c2.getOverallScore(), c1.getOverallScore()));

        int rank = 1;
        for (Competitor c : competitors) {

            String scoresStr = "";
            int[] scores = c.getScores();
            if (scores != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < scores.length; i++) {
                    sb.append(scores[i]);
                    if (i < scores.length - 1) {
                        sb.append(", ");
                    }
                }
                scoresStr = sb.toString();
            }

            Object[] row = {
                rank++,
                c.getCompetitorID(),
                c.getName().toString(),
                c.getLevel().toString(),
                c.getCountry(),
                scoresStr,
                String.format("%.2f", c.getOverallScore())
            };

            tableModel.addRow(row);
        }

        // Update statistics
        updateStatistics(competitorList);

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "No competitors found. Take a quiz to appear on the leaderboard!", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Update statistical summary
     */
    private void updateStatistics(CompetitorList competitorList) {
        var competitors = competitorList.getAllCompetitors();
        
        // Total competitors
        lblTotalCompetitors.setText("• Total number of competitors: " + competitors.size());

        // Highest scorer
        Competitor top = competitorList.getTopCompetitor();
        if (top != null) {
            lblHighestScorer.setText("• Competitor with the highest score: " + 
                top.getName().toString() + " with an overall score of " + 
                String.format("%.1f", top.getOverallScore()));
        }

        // Frequency of scores
        var frequency = competitorList.getScoreFrequency();
        if (!frequency.isEmpty()) {
            StringBuilder scoreStr = new StringBuilder();
            StringBuilder freqStr = new StringBuilder();
            
            frequency.keySet().stream().sorted().forEach(score -> {
                scoreStr.append(score).append("  ");
                freqStr.append(frequency.get(score)).append("  ");
            });
            
            lblFrequency.setText("<html>• Frequency of individual scores:<br>" +
                "&nbsp;&nbsp;&nbsp;Score:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + scoreStr.toString() + "<br>" +
                "&nbsp;&nbsp;&nbsp;Frequency:&nbsp;" + freqStr.toString() + "</html>");
        }
    }
}