package org.ies.demo.fornix.clientapp;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientAppApplication {

    public static void main(String[] args) {
        Application.launch(FxApplication.class, args);
    }

}
