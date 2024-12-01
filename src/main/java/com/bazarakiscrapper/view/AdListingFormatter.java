package com.bazarakiscrapper.view;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;

import com.bazarakiscrapper.model.AdListing;

public class AdListingFormatter {
    @Value("${scraper.target-url}")
    private static String targetUrl;

    // Currency formatter
    @SuppressWarnings("deprecation")
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("el", "CY"));

    public static String formatAdListing(AdListing ad) {
        // Use Unicode symbols and consistent formatting
        StringBuilder sb = new StringBuilder();
        sb.append("🏠 ").append(ad.getTitle()).append("\n");
        sb.append("💶 Price: ").append(currencyFormatter.format(ad.getPrice())).append("\n");
        sb.append("📍 Location: ").append(ad.getLocation()).append("\n");
        
        // Conditional formatting for features
        if (!ad.getFeatures().isEmpty()) {
            sb.append("✨ Features: ").append(ad.getFeatures()).append("\n");
        }
        
        sb.append("📅 Date Posted: ").append(ad.getDatePosted()).append("\n");
        
        // Clickable-style URL
        sb.append("🔗 Link: ").append(targetUrl).append(ad.getUrl()).append("\n");
        
        // Description with fallback
        sb.append("📝 Description: ").append(
            ad.getDescription().isEmpty() 
            ? "No description available" 
            : ad.getDescription()
        ).append("\n");
        
        sb.append("━".repeat(40)).append("\n");
        return sb.toString();
    }

    public static String formatAdListings(List<AdListing> adListings) {
        if (adListings.isEmpty()) {
            return "🔍 No listings found matching your criteria.";
        }
        
        StringBuilder resultText = new StringBuilder();
        resultText.append("🔎 Found ").append(adListings.size()).append(" listings:\n\n");
        
        for (AdListing ad : adListings) {
            resultText.append(formatAdListing(ad)).append("\n");
        }
        return resultText.toString();
    }
}