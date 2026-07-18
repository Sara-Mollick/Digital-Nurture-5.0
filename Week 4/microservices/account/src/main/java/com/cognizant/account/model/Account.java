package com.cognizant.account.model;

/**
 * Account Model - POJO representing a bank account.
 * 
 * This simple model represents the "Savings Account" service
 * from the banking enterprise application diagram in the module.
 * 
 * Fields:
 *   - number  : Account number (e.g., "00987987973432")
 *   - type    : Account type (e.g., "savings", "current")
 *   - balance : Current balance amount
 * 
 * Spring Boot automatically converts this POJO to JSON using
 * Jackson (included in spring-boot-starter-web) when returned
 * from a @RestController method.
 */
public class Account {

    private String number;
    private String type;
    private double balance;

    // Default constructor (required for JSON deserialization)
    public Account() {
    }

    // Parameterized constructor
    public Account(String number, String type, double balance) {
        this.number = number;
        this.type = type;
        this.balance = balance;
    }

    // Getters and Setters

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
