package com.example.thecookbook.Classes;
public class SubscriptionClass {
    private int SubscriptionID;
    private String SubscriptionName;
    private String Description;
    private double SubscriptionPrice;
    private int BuyCount;

    public SubscriptionClass(int subscriptionID, String subscriptionName, String description, double subscriptionPrice, int buyCount) {
        SubscriptionID = subscriptionID;
        SubscriptionName = subscriptionName;
        Description = description;
        SubscriptionPrice = subscriptionPrice;
        BuyCount = buyCount;
    }

    public SubscriptionClass() {
    }

    public int getSubscriptionID() {
        return SubscriptionID;
    }

    public void setSubscriptionID(int subscriptionID) {
        SubscriptionID = subscriptionID;
    }

    public String getSubscriptionName() {
        return SubscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        SubscriptionName = subscriptionName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getSubscriptionPrice() {
        return SubscriptionPrice;
    }

    public void setSubscriptionPrice(double subscriptionPrice) {
        SubscriptionPrice = subscriptionPrice;
    }

    public int getBuyCount() {
        return BuyCount;
    }

    public void setBuyCount(int buyCount) {
        BuyCount = buyCount;
    }
}
