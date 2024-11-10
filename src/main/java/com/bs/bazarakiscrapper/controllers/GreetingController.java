package com.bs.bazarakiscrapper.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bs.bazarakiscrapper.services.GreetingService;

@Component
public class GreetingController {

    private final GreetingService greetingService;
    private Label greetingLabel;
    private StackPane root;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
        initializeUI();
    }

    private void initializeUI() {
        String greeting = greetingService.getGreeting();
        greetingLabel = new Label(greeting);

        Button changeGreetingButton = new Button("Change Greeting");
        changeGreetingButton.setOnAction(event -> greetingLabel.setText("Greeting updated!"));

        root = new StackPane();
        root.getChildren().addAll(greetingLabel, changeGreetingButton);
    }

    public StackPane getView() {
        return root;
    }
}