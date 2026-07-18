package com.cognizant.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Account Microservice - Main Application Class
 * 
 * This is an independent microservice for handling bank account operations.
 * 
 * In a monolithic architecture, this would be part of a single large WAR/EAR
 * along with Loan, Insurance, Cards, and other services. If the account
 * balance service experienced a memory leak (e.g., during festival season
 * high traffic), it would bring down ALL other services.
 * 
 * As a microservice, this application:
 * - Runs independently on its own port (default: 8080)
 * - Has its own embedded Tomcat server
 * - Can be deployed, scaled, and restarted without affecting the Loan service
 * - If this service crashes, the Loan service on port 8081 continues running
 * 
 * @SpringBootApplication combines:
 *   @Configuration  - Marks this as a configuration class
 *   @EnableAutoConfiguration - Auto-configures Spring beans
 *   @ComponentScan  - Scans com.cognizant.account package for components
 */
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
