package com.cognizant.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Loan Microservice - Main Application Class
 * 
 * This is an independent microservice for handling bank loan operations
 * (Personal Loan, Car Loan, Gold Loan, Two Wheeler Loan, etc.).
 * 
 * KEY CONCEPT FROM MODULE:
 * In the monolithic banking application, when the Account Balance service
 * experienced a memory leak during festival season, it brought down the
 * entire server — including Loan processing. A loan agent was unable to
 * submit a loan application and missed his monthly target.
 * 
 * With microservices architecture:
 * - This Loan service runs on port 8081 (independent of Account on 8080)
 * - If Account service crashes, this Loan service CONTINUES RUNNING
 * - The loan agent can still submit loan applications
 * - Each service can be restarted independently
 * 
 * This demonstrates the "Independent" and "Decentralized" advantages
 * of microservices as described in the module theory.
 */
@SpringBootApplication
public class LoanApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanApplication.class, args);
    }
}
