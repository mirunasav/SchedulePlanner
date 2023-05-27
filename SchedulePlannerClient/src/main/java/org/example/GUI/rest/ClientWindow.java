package org.example.GUI.rest;

import org.springframework.web.client.RestTemplate;

import javax.swing.*;

public abstract class ClientWindow extends JFrame {
    public abstract RestTemplate getRestTemplate();
}
