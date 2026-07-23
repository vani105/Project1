package com.example.project1.models;

public class User {
    private String uid = "";
    private String fullName = "";
    private String email = "";
    private String phoneNumber = "";
    private long createdAt = 0;
    private boolean isPro = true;
    private String currency = "Indian Rupee";
    private String region = "India";
    private String language = "English (India)";
    private boolean priceAlerts = true;
    private boolean volAlerts = false;

    public User() {
        // Required for Firestore
    }

    public User(String uid, String fullName, String email, String phoneNumber, long createdAt) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public boolean isPro() { return isPro; }
    public void setPro(boolean pro) { isPro = pro; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public boolean isPriceAlerts() { return priceAlerts; }
    public void setPriceAlerts(boolean priceAlerts) { this.priceAlerts = priceAlerts; }

    public boolean isVolAlerts() { return volAlerts; }
    public void setVolAlerts(boolean volAlerts) { this.volAlerts = volAlerts; }
}
