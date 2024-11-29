package com.bazarakiscrapper;

import javafx.application.Application;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.bazarakiscrapper.controllers.ScraperController;
// import com.bazarakiscrapper.services.GreetingService;
@Component
public class JavaFXApp extends Application {

    private ConfigurableApplicationContext springContext;
    @Autowired
    private ScraperController ScraperController;
    // @Autowired
    // private GreetingService greetingService;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(SpringApp.class).run();
        // Inject Spring beans into JavaFX application
        springContext.getAutowireCapableBeanFactory().autowireBean(this);
        springContext.getAutowireCapableBeanFactory().autowireBean(ScraperController);
    }

    @Override
    public void stop() {
        springContext.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set up the JavaFX UI and integrate with Spring beans
        primaryStage.setTitle("Web Scraper Application");

        StackPane root = new StackPane();
        root.getChildren().add(ScraperController.getRootNode());
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Greetings app
        // String greeting = greetingService.getGreeting();
        // Label greetingLabel = new Label(greeting);
        // Button changeGreetingButton = new Button("Change Greeting");
        // changeGreetingButton.setOnAction(event -> {
        //     String newGreeting = "Greeting updated!";
        //     greetingLabel.setText(newGreeting);
        // });
        // root.getChildren().addAll(greetingLabel, changeGreetingButton);

        // primaryStage.setScene(scene);
        // primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}