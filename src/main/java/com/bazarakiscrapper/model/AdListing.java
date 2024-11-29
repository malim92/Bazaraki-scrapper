package com.bazarakiscrapper.model;

// AdListing.java
public class AdListing {
    private String title;
    private int price;
    private String url;
    private String location;
    private String features;
    private String description;
    private String datePosted;
    private Boolean isDiscounted;

    public AdListing() {
        this.title = "";
        this.price = 0;
        this.url = "";
        this.location = "";
        this.features = "";
        this.description = "";
        this.datePosted = "";
        this.isDiscounted = false;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getLocation() { return location; }
    public Boolean getIsDiscounted() { return isDiscounted; }
    public void setLocation(String location) { this.location = location; }

    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDatePosted() { return datePosted; }
    public void setDatePosted(String datePosted) { this.datePosted = datePosted; }
    public void setIsDiscounted(Boolean isDiscounted) { this.isDiscounted = isDiscounted; }

    // Optional: You can override toString() to help with logging or debugging
    @Override
    public String toString() {
        return "AdListing{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", location='" + location + '\'' +
                ", features='" + features + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
