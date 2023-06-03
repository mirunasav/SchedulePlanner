package org.example.GUI.Login;

import org.example.GUI.ToDoList.ToDoListApp;
import org.example.GUI.rest.ClientWindow;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.swing.*;


public class RegisterButtonListener extends AuthenticationButtonListener{

    public RegisterButtonListener(JTextField usernameTextField, JPasswordField passwordField, ClientWindow parentFrame) {
        super(usernameTextField, passwordField, parentFrame);
    }

    @Override
    protected ResponseEntity<String> buildResponseEntity(HttpEntity<String> requestEntity) {
        return  parentFrame.getRestTemplate().exchange("http://localhost:8081/login/register", HttpMethod.POST, requestEntity, String.class);
    }

    @Override
    protected void processResponseBody(String responseBody) {
        JOptionPane.showMessageDialog(parentFrame, "You are registered!", "Register Successful", JOptionPane.INFORMATION_MESSAGE);
    }
}