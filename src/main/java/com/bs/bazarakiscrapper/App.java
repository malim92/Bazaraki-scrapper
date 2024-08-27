package com.bs.bazarakiscrapper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.bs.bazarakiscrapper.Services.GreetingService;

@Component
public class App extends Application {

    private ConfigurableApplicationContext springContext;

    @Autowired
    private GreetingService greetingService;

@Override
    public void init() {
        springContext = new SpringApplicationBuilder(SpringApp.class).run();
        springContext.getAutowireCapableBeanFactory().autowireBean(this); // Manually inject dependencies
    }

    @Override
    public void stop() {
        springContext.close();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Spring Boot JavaFX Application");

        String greeting = greetingService.getGreeting();
        Label greetingLabel = new Label(greeting);
        Button changeGreetingButton = new Button("Change Greeting");
        changeGreetingButton.setOnAction(event -> {
            String newGreeting = "Greeting updated!";
            greetingLabel.setText(newGreeting);
        });
        StackPane root = new StackPane();
        root.getChildren().addAll(greetingLabel, changeGreetingButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}