package gui;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import backend.*;

public class login extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtCountry;
    private JComboBox<String> comboLevel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login frame = new login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public login() {
        setTitle("Quiz App - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 710, 595);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Quiz App");
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 32));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(200, 56, 300, 75);
        contentPane.add(lblTitle);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblName.setBounds(130, 206, 80, 22);
        contentPane.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(212, 209, 265, 25);
        contentPane.add(txtName);
        txtName.setColumns(10);

        JLabel lblCountry = new JLabel("Country:");
        lblCountry.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblCountry.setBounds(130, 274, 80, 22);
        contentPane.add(lblCountry);

        txtCountry = new JTextField();
        txtCountry.setBounds(212, 274, 265, 25);
        contentPane.add(txtCountry);
        txtCountry.setColumns(10);

        JLabel lblLevel = new JLabel("Level:");
        lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblLevel.setBounds(130, 339, 80, 22);
        contentPane.add(lblLevel);

        comboLevel = new JComboBox<>();
        comboLevel.setModel(new DefaultComboBoxModel<>(new String[] {"BEGINNER", "INTERMEDIATE", "ADVANCED"}));
        comboLevel.setBounds(212, 340, 265, 25);
        contentPane.add(comboLevel);

        JButton btnLogin = new JButton("Log In");
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLogin.setBounds(264, 403, 150, 38);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        contentPane.add(btnLogin);
    }

    /**
     * Handles the login process
     */
    private void handleLogin() {
        String fullName = txtName.getText().trim();
        String country = txtCountry.getText().trim();
        String levelStr = (String) comboLevel.getSelectedItem();

        // Validation
        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (country.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your country", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parse name (assume first and last name separated by space)
        String[] nameParts = fullName.split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        Name name = new Name(firstName, lastName);
        Level level = Level.fromString(levelStr);

        // Check if name already exists
        Manager manager = new Manager();
        if (manager.isNameExists(firstName, lastName)) {
            JOptionPane.showMessageDialog(this, 
                "A competitor with this name already exists. Please use a different name.", 
                "Duplicate Name", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create competitor
        Competitor competitor = null;
        switch (level) {
            case BEGINNER:
                competitor = new BeginnerCompetitor(name, country);
                break;
            case INTERMEDIATE:
                competitor = new IntermediateCompetitor(name, country);
                break;
            case ADVANCED:
                competitor = new AdvancedCompetitor(name, country);
                break;
        }

        // Open dashboard window
        this.dispose();
        Dashboard dashboardFrame = new Dashboard(competitor);
        dashboardFrame.setVisible(true);
    }
}