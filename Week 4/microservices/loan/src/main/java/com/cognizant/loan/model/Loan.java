package com.cognizant.loan.model;

/**
 * Loan Model - POJO representing a bank loan.
 * 
 * This model represents loans from the banking enterprise application.
 * The bank offers multiple loan types: Personal, Car, Gold, Two Wheeler.
 * 
 * Fields:
 *   - number : Loan account number (e.g., "H00987987972342")
 *   - type   : Loan type (e.g., "car", "personal", "gold")
 *   - loan   : Total loan amount
 *   - emi    : Equated Monthly Installment amount
 *   - tenure : Loan tenure in months
 * 
 * Spring Boot automatically converts this POJO to JSON using
 * Jackson (included in spring-boot-starter-web) when returned
 * from a @RestController method.
 */
public class Loan {

    private String number;
    private String type;
    private double loan;
    private double emi;
    private int tenure;

    // Default constructor (required for JSON deserialization)
    public Loan() {
    }

    // Parameterized constructor
    public Loan(String number, String type, double loan, double emi, int tenure) {
        this.number = number;
        this.type = type;
        this.loan = loan;
        this.emi = emi;
        this.tenure = tenure;
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

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }
}
