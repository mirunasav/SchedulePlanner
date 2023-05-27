package org.example.GUI.utilities;

import org.example.GUI.exceptions.NotFoundException;
import org.example.GUI.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import javax.swing.*;
import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    public RestTemplateResponseErrorHandler(){

    }
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().is4xxClientError() ||
                response.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().is5xxServerError()){
            //JOptionPane.showMessageDialog(parentFrame, "Login failed due to an internal server error", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (response.getStatusCode().is4xxClientError()){
            if(response.getStatusCode() == HttpStatus.NOT_FOUND)
                // JOptionPane.showMessageDialog(parentFrame, "Incorrect username", "Login Error", JOptionPane.ERROR_MESSAGE);
            if(response.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return;
               // JOptionPane.showMessageDialog(parentFrame, "Incorrect password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
