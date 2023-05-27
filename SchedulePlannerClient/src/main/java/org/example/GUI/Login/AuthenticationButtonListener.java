package org.example.GUI.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class  AuthenticationButtonListener implements ActionListener {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JFrame parentFrame;

    public AuthenticationButtonListener(JTextField usernameTextField, JPasswordField passwordField, JFrame parentFrame) {
        this.usernameTextField = usernameTextField;
        this.passwordField = passwordField;
        this.parentFrame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameTextField.getText();
        if (username.equals("")) {
            JOptionPane.showMessageDialog(parentFrame, "Username cannot be empty", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String password = new String(passwordField.getPassword());
        if (password.equals("")) {
            JOptionPane.showMessageDialog(parentFrame, "Password cannot be empty", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Clear the text fields after login
        usernameTextField.setText("");
        passwordField.setText("");

        this.sendRequest(username, password);
    }
    private void sendRequest(String username, String password) {

    }

}
