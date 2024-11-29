package com.bazarakiscrapper.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.bazarakiscrapper.model.AdListing;

public class AdListingFormatter {

    @Value("${scraper.target-url}")
    private static String targetUrl;
    // Method to format a single AdListing nicely
    public static String formatAdListing(AdListing ad) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(ad.getTitle()).append("\n");
        sb.append("Price: €").append(ad.getPrice()).append("\n");
        sb.append("Location: ").append(ad.getLocation()).append("\n");
        sb.append("Features: ").append(ad.getFeatures()).append("\n");
        sb.append("Date Posted: ").append(ad.getDatePosted()).append("\n");
        sb.append("URL: ").append(targetUrl).append(ad.getUrl()).append("\n");
        sb.append("Description: ").append(ad.getDescription().isEmpty() ? "No description available" : ad.getDescription());
        sb.append("\n-----------------------------\n");
        return sb.toString();
    }

    // Method to format a list of AdListings
    public static String formatAdListings(List<AdListing> adListings) {
        StringBuilder resultText = new StringBuilder();
        for (AdListing ad : adListings) {
            resultText.append(formatAdListing(ad)).append("\n");
        }
        return resultText.toString();
    }
}
