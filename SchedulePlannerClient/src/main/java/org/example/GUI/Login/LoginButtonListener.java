package org.example.GUI.Login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GUI.ToDoList.ToDoListApp;
import org.example.GUI.models.Credentials;
import org.example.GUI.rest.ClientWindow;
import org.example.GUI.utilities.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginButtonListener implements ActionListener {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private ClientWindow parentFrame;

    public LoginButtonListener(JTextField usernameTextField, JPasswordField passwordField, ClientWindow parentFrame) {
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

        ObjectMapper objectMapper = new ObjectMapper();
        Credentials credentials = new Credentials(username, password);
        String credentialsAsString = "";
        try {
            credentialsAsString = objectMapper.writeValueAsString(credentials);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //creating the request entity with the JSON body and headers
            HttpEntity<String> requestEntity = new HttpEntity<>(credentialsAsString, headers);

            //sending the post request ; this throws an error when the response is not accepted
            ResponseEntity<String> response = parentFrame.getRestTemplate().exchange("http://localhost:8081/login", HttpMethod.POST, requestEntity, String.class);

            //access the response body
            String responseBody = response.getBody();
            assert responseBody != null;
            this.processResponseBody(responseBody);
            //HttpStatusCode status = response.getStatusCode();
            //process the response
            // this.processResponseStatus((HttpStatus) status);
        } catch (RestClientException ex) {
            ex.printStackTrace();
        }


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

    private void processResponseBody(String responseBody) {
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

