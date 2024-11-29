package com.bazarakiscrapper.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.bazarakiscrapper.model.AdListing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScraperService {

    @Value("${scraper.target-url}")
    private String targetUrl;

    @Value("${scraper.default-price}")
    private int defaultPrice;

    public List<AdListing> scrapeListings(int maxPrice) throws IOException {

        System.out.println("Scrapping...");
        Document doc = Jsoup.connect(targetUrl)
                .timeout(60000) // Timeout set to 60 seconds
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .get();
        System.out.println("Done...");

        // Select the ad elements from the HTML (This will depend on the website
        // structure)
        Elements ads = doc.select(".advert");
        List<AdListing> adListings = new ArrayList<>();
        System.out.println("Test 1...");

        for (Element ad : ads) {
            // Create a new AdListing object using the no-argument constructor
            AdListing adListing = new AdListing();

            // Extract the ad title
            String title = ad.select(".advert__content-title").text();
            adListing.setTitle(title);

            // Extract the ad price (handle missing values)
            String priceText = ad.select(".advert__content-price span").text();
            int price = 0;
            if (!priceText.isEmpty()) {
                try {
                    price = parsePrice(priceText, adListing);
                } catch (NumberFormatException e) {
                    // Handle parsing error or invalid price
                    System.out.println("Error parsing price: " + priceText);
                }
            }
            adListing.setPrice(price);


            // Extract the ad link
            String adUrl = ad.select(".advert__content-title").attr("href");
            adListing.setUrl(adUrl);

            // Extract the location
            String location = ad.select(".advert__content-place").text();
            adListing.setLocation(location);

            // Extract other details (features, date posted, etc.)
            String features = ad.select(".advert__content-features").text();
            adListing.setFeatures(features);

            String description = ad.select(".advert__content-description").text();
            adListing.setDescription(description);

            String datePosted = ad.select(".advert__content-date").text();
            adListing.setDatePosted(datePosted);

            // If the price is within the maxPrice, add the listing to the results
            if (price <= maxPrice) {
                adListings.add(adListing);
            }
        }

        return adListings;
    }

    private int parsePrice(String priceText,AdListing adListing) {

        String[] prices = priceText.split(" ");
    
        int parsedPrice = Integer.parseInt(prices[0].replaceAll("[^0-9]", ""));
    
        Boolean isDiscounted = false;
        isDiscounted = prices.length > 1;
        adListing.setIsDiscounted(isDiscounted);

        return parsedPrice;
    }

    // public String getTargetUrl() {
    //     return scraperProperties.getTargetUrl();
    // }

    // public int getDefaultPrice() {
    //     return scraperProperties.getDefaultPrice();
    // }
}