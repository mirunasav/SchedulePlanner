package org.example.GUI.Login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GUI.models.Credentials;
import org.example.GUI.rest.ClientWindow;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class  AuthenticationButtonListener implements ActionListener {
    protected JTextField usernameTextField;
    protected JPasswordField passwordField;
    protected ClientWindow parentFrame;

    public AuthenticationButtonListener(JTextField usernameTextField, JPasswordField passwordField, ClientWindow parentFrame) {
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
            ResponseEntity<String> response = this.buildResponseEntity(requestEntity);
            //access the response body
            String responseBody = response.getBody();
            assert responseBody != null;
            this.processResponseBody(responseBody);
            HttpStatusCode status = response.getStatusCode();
            //process the response
            // this.processResponseStatus((HttpStatus) status);
        } catch (RestClientException ex) {
            ex.printStackTrace();
        }
    }
    protected abstract ResponseEntity <String> buildResponseEntity(HttpEntity<String> requestEntity);
    protected abstract void processResponseBody (String responseBody);

}
