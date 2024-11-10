package com.bs.bazarakiscrapper.controllers;


import com.bs.bazarakiscrapper.model.AdListing;
import com.bs.bazarakiscrapper.services.WebScraperService;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScraperController {

    @Autowired
    private WebScraperService webScraperService;

    private TextField priceTextField;
    private Button scrapeButton;
    private TextArea resultArea;

    @FXML
    public void initialize() {
        priceTextField = new TextField();
        priceTextField.setPromptText("Enter max price");

        scrapeButton = new Button("Scrape Listings");
        scrapeButton.setOnAction(event -> onScrapeButtonClick());

        resultArea = new TextArea();
        resultArea.setPrefHeight(400);
        resultArea.setPrefWidth(600);
        resultArea.setWrapText(true);

        VBox layout = new VBox(10, priceTextField, scrapeButton, resultArea);
        Scene scene = new Scene(layout, 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Web Scraper");
        stage.setScene(scene);
        stage.show();
    }

    private void onScrapeButtonClick() {
        try {
            int maxPrice = Integer.parseInt(priceTextField.getText().trim());
            List<AdListing> adListings = webScraperService.fetchAdListings(maxPrice);

            resultArea.clear();
            for (AdListing ad : adListings) {
                resultArea.appendText(ad.toString() + "\n");
            }
        } catch (NumberFormatException e) {
            resultArea.setText("Please enter a valid number for the price.");
        }
    }
}