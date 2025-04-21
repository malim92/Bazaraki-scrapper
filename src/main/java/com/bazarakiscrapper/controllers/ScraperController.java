package com.bazarakiscrapper.controllers;

import com.bazarakiscrapper.model.AdListing;
import com.bazarakiscrapper.model.AdListingViewController;
import com.bazarakiscrapper.services.WebScraperService;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.List;

import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
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
    private VBox contentArea;

    public BorderPane createRootLayout() {
        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-background");

        // Top section with title and search
        HBox topBar = createTopBar();
        mainLayout.setTop(topBar);

        // Create content area once and keep it as a class member
        contentArea = createContentArea();
        mainLayout.setCenter(contentArea);

        return mainLayout;
    }

    private VBox createContentArea() {
        VBox contentArea = new VBox(20);
        contentArea.getStyleClass().add("content-area");
        contentArea.setPadding(new Insets(20));
        contentArea.setPrefHeight(Double.MAX_VALUE);

        // Result area with scrollpane for better styling
        resultArea = new TextArea();
        resultArea.setWrapText(true);
        resultArea.getStyleClass().add("result-area");

        ScrollPane scrollPane = new ScrollPane(resultArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        contentArea.getChildren().add(scrollPane);

        return contentArea;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.getStyleClass().add("top-bar");
        topBar.setPadding(new Insets(20, 20, 20, 20));

        Label titleLabel = new Label("Bazaraki Scraper");
        titleLabel.getStyleClass().add("app-title");

        HBox searchBox = new HBox(5);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        // Create search icon
        Glyph searchIcon = new FontAwesome().create(FontAwesome.Glyph.SEARCH)
                .size(16)
                .color(javafx.scene.paint.Color.GRAY);

        priceTextField = new CustomTextField();
        priceTextField.setPromptText("Enter max price");
        priceTextField.getStyleClass().add("search-field");

        // Add icon and text field to a horizontal box
        searchBox.getChildren().addAll(searchIcon, priceTextField);
        HBox.setHgrow(priceTextField, Priority.ALWAYS);

        // Scrape button with modern styling
        scrapeButton = new Button("Scrape Listings");
        scrapeButton.getStyleClass().addAll("spotify-button", "primary-button");
        scrapeButton.setOnAction(event -> {
            try {
                onScrapeButtonClick();
            } catch (IOException e) {
                showErrorDialog("Scraping Error", e.getMessage());
            }
        });

        topBar.getChildren().addAll(titleLabel, searchBox, scrapeButton);
        HBox.setHgrow(searchBox, Priority.ALWAYS);

        return topBar;
    }

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
        // resultArea.setPrefHeight(400);
        // resultArea.setPrefWidth(600);
        resultArea.setWrapText(true);

        VBox layout = new VBox(10, priceTextField, scrapeButton, resultArea);
        return layout;
    }

    private void onScrapeButtonClick() throws IOException {
        try {
            // Parse max price from input
            int maxPrice = Integer.parseInt(priceTextField.getText().trim());
            resultArea.setText("Scraping... Please wait.");

            // Run scraping in background thread
            new Thread(() -> {
                try {
                    // Scrape listings based on max price
                    List<AdListing> result = webScraperService.scrapeListings(maxPrice);

                    // Update UI on JavaFX Application Thread
                    Platform.runLater(() -> {
                        AdListingViewController listViewController = new AdListingViewController();
                        ListView<AdListing> adListView = listViewController.createAdListingListView(result);
                        // Replace the TextArea with ListView in your layout
                        contentArea.getChildren().clear();
                        contentArea.getChildren().add(adListView);
                    });
                } catch (Exception e) {
                    Platform.runLater(() -> showErrorDialog("Scraping Failed", e.getMessage()));
                }
            }).start();
            // Scrape listings based on max price

        } catch (NumberFormatException e) {
            resultArea.setText("Invalid input. Please enter a valid number for the price.");
        }
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}