package org.example.GUI.Login;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GUI.ToDoList.ToDoListApp;
import org.example.GUI.models.Credentials;
import org.example.GUI.rest.ClientWindow;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;

import javax.swing.*;

public class LoginButtonListener extends AuthenticationButtonListener {

    public LoginButtonListener(JTextField usernameTextField, JPasswordField passwordField, ClientWindow parentFrame) {
        super(usernameTextField, passwordField, parentFrame);
    }


    @Override
    protected ResponseEntity<String> buildResponseEntity(HttpEntity<String> requestEntity) {
        return  parentFrame.getRestTemplate().exchange("http://localhost:6969/login", HttpMethod.POST, requestEntity, String.class);
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
                ToDoListApp toDoListApp = new ToDoListApp(parentFrame.getRestTemplate(),getIdRequest(username));
                toDoListApp.setVisible(true);
            }
        }
    }

    @Override
    protected void processResponseBody(String responseBody) {
        if (responseBody.equals("You are logged in!")) {
            //make getIdRequest
            Long id = this.getIdRequest(username);
            // Close the login page
            parentFrame.dispose();
            // Open the to-do list page
            ToDoListApp toDoListApp = new ToDoListApp(parentFrame.getRestTemplate(),id);
            toDoListApp.setVisible(true);
            return;
        }
        JOptionPane.showMessageDialog(parentFrame, responseBody, "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    private Long getIdRequest (String username){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //creating
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            //sending the post request ; this throws an error when the response is not accepted
            ResponseEntity<String> response = parentFrame.getRestTemplate().exchange("http://localhost:6969/login/getUser/"+username, HttpMethod.GET, requestEntity, String.class);
            //access the response body
            var jsonNode = objectMapper.readValue(response.getBody(), JsonNode.class);
            return jsonNode.get("id").asLong();


        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

