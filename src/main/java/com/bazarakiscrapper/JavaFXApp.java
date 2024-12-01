package com.bazarakiscrapper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.bazarakiscrapper.controllers.ScraperController;

@Component
public class JavaFXApp extends Application {

    private ConfigurableApplicationContext springContext;
    @Autowired
    private ScraperController ScraperController;

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
        primaryStage.setTitle("Bazaraki Scraper");
        
        Scene scene = new Scene(ScraperController.createRootLayout(), 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}