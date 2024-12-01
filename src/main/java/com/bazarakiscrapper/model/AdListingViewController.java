package com.bazarakiscrapper.model;

import javafx.scene.control.ListView;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdListingViewController {
    private ListView<AdListing> listView;

    public ListView<AdListing> createAdListingListView(List<AdListing> adListings) {
        listView = new ListView<>();
        listView.getStyleClass().add("ad-listing-listview");
        
        // Custom cell factory for modern, Spotify-like design
        listView.setCellFactory(param -> new ListCell<>() {
            private final VBox container = new VBox(5);
            private final Text titleText = new Text();
            private final Text detailsText = new Text();
            
            {
                // Styling
                titleText.getStyleClass().add("ad-title");
                detailsText.getStyleClass().add("ad-details");
                
                container.getChildren().addAll(titleText, detailsText);
                setGraphic(container);
            }

            @Override
            protected void updateItem(AdListing ad, boolean empty) {
                super.updateItem(ad, empty);
                
                if (empty || ad == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                
                // Format title
                titleText.setText(ad.getTitle());
                
                // Format details
                @SuppressWarnings("deprecation")
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("el", "CY"));
                String details = String.format(
                    "💶 %s | 📍 %s | 📅 %s",
                    currencyFormatter.format(ad.getPrice()),
                    ad.getLocation(),
                    ad.getDatePosted()
                );
                detailsText.setText(details);
                
                // Add click event to open full details
                setOnMouseClicked(event -> showAdDetails(ad));
            }
        });

        listView.getItems().addAll(adListings);
        return listView;
    }

    private void showAdDetails(AdListing ad) {
        // Create a dialog or popup with full ad details
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Ad Details");
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        
        Text titleText = new Text(ad.getTitle());
        titleText.getStyleClass().add("dialog-title");
        
        TextFlow detailsFlow = new TextFlow(
            createStyledText("💶 Price: ", ad.getPrice() + "\n"),
            createStyledText("📍 Location: ", ad.getLocation() + "\n"),
            createStyledText("✨ Features: ", ad.getFeatures() + "\n"),
            createStyledText("📅 Date Posted: ", ad.getDatePosted() + "\n"),
            createStyledText("🔗 Link: ", ad.getUrl() + "\n"),
            createStyledText("📝 Description: ", 
                ad.getDescription().isEmpty() 
                ? "No description available" 
                : ad.getDescription())
        );
        
        content.getChildren().addAll(titleText, detailsFlow);
        
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(content);
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
    }

    private Text createStyledText(String label, String value) {
        Text labelText = new Text(label);
        labelText.getStyleClass().add("dialog-label");
        
        Text valueText = new Text(value);
        valueText.getStyleClass().add("dialog-value");
        
        TextFlow flow = new TextFlow(labelText, valueText);
        return valueText;
    }
}