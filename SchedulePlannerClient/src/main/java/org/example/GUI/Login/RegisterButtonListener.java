package org.example.GUI.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterButtonListener implements  ActionListener {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JFrame parentFrame;

    public RegisterButtonListener(JTextField usernameTextField, JPasswordField passwordField, JFrame parentFrame) {
        this.usernameTextField = usernameTextField;
        this.passwordField = passwordField;
        this.parentFrame = parentFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
