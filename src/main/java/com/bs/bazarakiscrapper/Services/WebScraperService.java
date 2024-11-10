package com.bs.bazarakiscrapper.services;

import com.bs.bazarakiscrapper.model.AdListing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScraperService {

    @Value("${scraper.target-url}")
    private String targetUrl;

    @Value("${scraper.default-price}")
    private int defaultPrice;

    public List<AdListing> fetchAdListings(int maxPrice) {
        List<AdListing> adListings = new ArrayList<>();

        try {
            Document document = Jsoup.connect(targetUrl).get();
            Elements ads = document.select(".ad-listing");

            for (Element ad : ads) {
                String title = ad.select(".ad-title").text();
                String priceText = ad.select(".ad-price").text();
                int price = parsePrice(priceText);
                String location = ad.select(".ad-location").text();
                String description = ad.select(".ad-description").text();
                String url = ad.select(".ad-link").attr("href");

                // Filter ads based on user-defined max price
                if (price <= maxPrice) {
                    AdListing adListing = new AdListing(title, priceText, location, description, url);
                    adListings.add(adListing);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return adListings;
    }

    private int parsePrice(String priceText) {
        // Implement a method to parse the price text into an integer value.
        // This may depend on the format (e.g., "1,000" or "$1000").
        return Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    }
}