package com.cognizant.loan.controller;

import com.cognizant.loan.model.Loan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoanController - REST API for bank loan operations.
 * 
 * This controller demonstrates the "Doing one thing well" principle
 * of microservices. It ONLY handles loan-related operations.
 * Account operations are handled by a completely separate microservice.
 * 
 * MODULE THEORY - FAULT ISOLATION SCENARIO:
 * In the monolithic banking app, when account balance service had a memory
 * leak during festival season, the loan agent could NOT submit loan 
 * applications and missed his monthly target. With microservices, this
 * Loan service runs independently on port 8081. Even if Account service
 * (port 8080) crashes, loan operations continue uninterrupted.
 * 
 * API Specification:
 *   Method   : GET
 *   Endpoint : /loans/{number}
 *   Response : JSON with loan details (dummy data, no backend connectivity)
 * 
 * @RestController = @Controller + @ResponseBody
 *   - Registers this class as a Spring MVC controller
 *   - Automatically serializes return objects to JSON
 */
@RestController
public class LoanController {

    /**
     * Get loan details by loan number.
     * 
     * This is a dummy response without any backend/database connectivity.
     * In a real application, this would connect to a database to fetch
     * actual loan details.
     * 
     * @param number the loan number from the URL path
     * @return Loan object (automatically converted to JSON by Spring)
     * 
     * Sample Request:  GET http://localhost:8081/loans/12345
     * Sample Response: { "number": "H00987987972342", "type": "car",
     *                    "loan": 400000, "emi": 3258, "tenure": 18 }
     */
    @GetMapping("/loans/{number}")
    public Loan getLoanDetails(@PathVariable String number) {
        // Dummy response - no backend connectivity
        // In production, this would query a database
        Loan loan = new Loan();
        loan.setNumber("H00987987972342");
        loan.setType("car");
        loan.setLoan(400000);
        loan.setEmi(3258);
        loan.setTenure(18);
        return loan;
    }
}
