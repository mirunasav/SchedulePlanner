package org.example.GUI.Login;


import org.example.GUI.ToDoList.ToDoListApp;
import org.example.GUI.rest.ClientWindow;
import org.springframework.http.*;
import javax.swing.*;

public class LoginButtonListener extends AuthenticationButtonListener {

    public LoginButtonListener(JTextField usernameTextField, JPasswordField passwordField, ClientWindow parentFrame) {
        super(usernameTextField, passwordField, parentFrame);
    }


    @Override
    protected ResponseEntity<String> buildResponseEntity(HttpEntity<String> requestEntity) {
        return  parentFrame.getRestTemplate().exchange("http://localhost:8081/login", HttpMethod.POST, requestEntity, String.class);
    }

    private void processResponseStatus(HttpStatus status) {
        switch (status) {
            case NOT_FOUND -> { //no such user
                JOptionPane.showMessageDialog(parentFrame, "Incorrect username", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
            case UNAUTHORIZED -> {
                JOptionPane.showMessageDialog(parentFrame, "Incorrect password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
            case INTERNAL_SERVER_ERROR -> {
                JOptionPane.showMessageDialog(parentFrame, "Login failed due to an internal server error", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
            //it will only go on this branch since in the other cases the errors will be already handled
            case ACCEPTED -> {
                // Close the login page
                parentFrame.dispose();
                // Open the to-do list page
                ToDoListApp toDoListApp = new ToDoListApp(parentFrame.getRestTemplate());
                toDoListApp.setVisible(true);
            }
        }
    }

    @Override
    protected void processResponseBody(String responseBody) {
        if (responseBody.equals("You are logged in!")) {
            // Close the login page
            parentFrame.dispose();
            // Open the to-do list page
            ToDoListApp toDoListApp = new ToDoListApp(parentFrame.getRestTemplate());
            toDoListApp.setVisible(true);
            return;
        }
        JOptionPane.showMessageDialog(parentFrame, responseBody, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
}

