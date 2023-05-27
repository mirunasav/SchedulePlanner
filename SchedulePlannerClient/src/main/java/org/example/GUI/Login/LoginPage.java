package org.example.GUI.Login;

import org.example.GUI.rest.ClientWindow;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends ClientWindow {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private RestTemplate restTemplate;

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public LoginPage(RestTemplate restTemplate) {
        // Set up the frame
        setTitle("Schedule Planner");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel for the main page
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Set the background color to white

        // Create a panel for the login/signup container
        JPanel loginSignupContainer = new JPanel(new GridBagLayout());
        loginSignupContainer.setBackground(Color.WHITE); // Set the background color to white

        // Create constraints for component placement
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // Set the padding

        // Create welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Schedule Planner!");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 20)); // Set a bigger font size

        // Create username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(20);
        usernameTextField.setPreferredSize(new Dimension(200, 30)); // Set preferred size for the text field

        // Create password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30)); // Set preferred size for the password field

        // Create register button
        JButton registerButton = new JButton("Register");

        // Create login button
        JButton loginButton = new JButton("Log In");

        // Add welcome label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER; // Set anchor to center
        loginSignupContainer.add(welcomeLabel, constraints);

        // Add username label and text field
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST; // Align labels to the right
        loginSignupContainer.add(usernameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST; // Align text fields to the left
        loginSignupContainer.add(usernameTextField, constraints);

        // Add password label and password field
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST; // Align labels to the right
        loginSignupContainer.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST; // Align text fields to the left
        loginSignupContainer.add(passwordField, constraints);

        // Add the buttons panel to the login/signup container
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align buttons to the left
        buttonsPanel.setBackground(Color.WHITE); // Set the background color to white
        registerButton.addActionListener(new RegisterButtonListener(usernameTextField, passwordField,this));
        loginButton.addActionListener(new LoginButtonListener(usernameTextField, passwordField, this));
        buttonsPanel.add(registerButton);
        buttonsPanel.add(loginButton);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER; // Set anchor to center
        loginSignupContainer.add(buttonsPanel, constraints);

        // Add the login/signup container to the main panel
        mainPanel.add(loginSignupContainer, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
        this.restTemplate =restTemplate;
    }

}
