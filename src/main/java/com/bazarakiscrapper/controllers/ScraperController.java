package com.bazarakiscrapper.controllers;

import com.bazarakiscrapper.model.AdListing;
import com.bazarakiscrapper.services.WebScraperService;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bazarakiscrapper.view.AdListingFormatter;

@Component
public class ScraperController {

    @Autowired
    private WebScraperService webScraperService;

    private TextField priceTextField;
    private Button scrapeButton;
    private TextArea resultArea;

    public VBox getRootNode() {
        priceTextField = new TextField();
        priceTextField.setPromptText("Enter max price");

        scrapeButton = new Button("Scrape Listings");
        scrapeButton.setOnAction(event -> {
            try {
                onScrapeButtonClick();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        resultArea = new TextArea();
        resultArea.setPrefHeight(400);
        resultArea.setPrefWidth(600);
        resultArea.setWrapText(true);

        VBox layout = new VBox(10, priceTextField, scrapeButton, resultArea);
        return layout;
    }

    private void onScrapeButtonClick() throws IOException {
        try {
            // Parse max price from input
            int maxPrice = Integer.parseInt(priceTextField.getText().trim());

            // Scrape listings based on max price
            List<AdListing> result = webScraperService.scrapeListings(maxPrice);

            // Build a string from the List<AdListing> and display it in the TextArea
            String resultText = AdListingFormatter.formatAdListings(result);
            resultArea.setText(resultText);

            // resultArea.setText(resultText.toString()); // Set the compiled string into the TextArea
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid input. Please enter a valid number for the price.");
        }
    }

}