package com.bs.bazarakiscrapper.model;

// AdListing.java
public class AdListing {
    private String title;
    private String price;
    private String location;
    private String description;
    private String url;

    // Constructors
    public AdListing(String title, String price, String location, String description, String url) {
        this.title = title;
        this.price = price;
        this.location = location;
        this.description = description;
        this.url = url;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @Override
    public String toString() {
        return "AdListing{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
