package com.bs.bazarakiscrapper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.bs.bazarakiscrapper.controllers.GreetingController;

@Component
public class JavaFXApp extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(SpringApp.class).run();
    }

    @Override
    public void stop() {
        springContext.close();
    }

    @Override
    public void start(Stage primaryStage) {
        GreetingController controller = springContext.getBean(GreetingController.class);
        primaryStage.setTitle("Spring Boot JavaFX Application");
        primaryStage.setScene(new Scene(controller.getView(), 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}