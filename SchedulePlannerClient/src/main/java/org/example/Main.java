package org.example;

import org.example.GUI.Login.LoginPage;
import org.example.GUI.rest.RestTemplateFactory;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = RestTemplateFactory.createRestTemplate();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPage(restTemplate);
            }
        });
    }
}