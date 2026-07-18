package com.cognizant.account.controller;

import com.cognizant.account.model.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccountController - REST API for bank account operations.
 * 
 * This controller demonstrates the "Doing one thing well" principle
 * of microservices. It ONLY handles account-related operations.
 * Loan operations are handled by a completely separate microservice.
 * 
 * In a monolithic application, this controller would be in the same
 * WAR/EAR as LoanController, InsuranceController, CardController, etc.
 * A memory leak here would bring down ALL controllers.
 * 
 * As a microservice, even if this controller causes issues (e.g., high
 * traffic during festival season), only the Account service is affected.
 * The Loan service on port 8081 continues to operate normally.
 * 
 * API Specification:
 *   Method   : GET
 *   Endpoint : /accounts/{number}
 *   Response : JSON with account details (dummy data, no backend connectivity)
 * 
 * @RestController = @Controller + @ResponseBody
 *   - Registers this class as a Spring MVC controller
 *   - Automatically serializes return objects to JSON
 */
@RestController
public class AccountController {

    /**
     * Get account details by account number.
     * 
     * This is a dummy response without any backend/database connectivity.
     * In a real application, this would connect to a database to fetch
     * actual account details.
     * 
     * @param number the account number from the URL path
     * @return Account object (automatically converted to JSON by Spring)
     * 
     * Sample Request:  GET http://localhost:8080/accounts/12345
     * Sample Response: { "number": "00987987973432", "type": "savings", "balance": 234343 }
     */
    @GetMapping("/accounts/{number}")
    public Account getAccountDetails(@PathVariable String number) {
        // Dummy response - no backend connectivity
        // In production, this would query a database
        Account account = new Account();
        account.setNumber("00987987973432");
        account.setType("savings");
        account.setBalance(234343);
        return account;
    }
}
