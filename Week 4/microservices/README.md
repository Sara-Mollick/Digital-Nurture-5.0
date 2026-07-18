# Module 4 — Microservices: Account & Loan Solution

## Context (From Module Theory)

A bank offers many services: Accounts, Loans, Insurance, Forex, Cards, etc.

### The Monolithic Problem
In a monolithic architecture, ALL these services are packaged into a single WAR/EAR.
If the "Account Balance" service has a memory leak during festival season traffic:
- The entire server crashes
- Loan agents can't submit applications
- Insurance agents can't process closures
- Customers can't report stolen cards

### The Microservices Solution (This Exercise)
Each service runs as an **independent Spring Boot application** with its own:
- Source code & pom.xml
- Port number
- Deployment lifecycle

If the Account service crashes, the Loan service **continues running unaffected**.

---

## Project Structure

```
microservices/
├── account/                          ← Port 8080
│   ├── pom.xml
│   └── src/main/java/com/cognizant/account/
│       ├── AccountApplication.java
│       ├── controller/AccountController.java
│       └── model/Account.java
│   └── src/main/resources/application.properties
│
└── loan/                             ← Port 8081
    ├── pom.xml
    └── src/main/java/com/cognizant/loan/
        ├── LoanApplication.java
        ├── controller/LoanController.java
        └── model/Loan.java
    └── src/main/resources/application.properties
```

## How to Build & Run

```bash
# Terminal 1 — Account Service
cd account
mvn clean package
mvn spring-boot:run
# Test: http://localhost:8080/accounts/12345

# Terminal 2 — Loan Service
cd loan
mvn clean package
mvn spring-boot:run
# Test: http://localhost:8081/loans/12345
```

## Key Microservice Advantages Demonstrated

| Advantage         | How This Exercise Shows It                                      |
|-------------------|-----------------------------------------------------------------|
| Independent       | Each service has its own pom.xml, runs on its own port          |
| Decentralized     | No single WAR/EAR — two separate Spring Boot applications      |
| Doing one thing   | Account handles accounts only; Loan handles loans only          |
| Scalable          | Can add more instances of Account without touching Loan         |
| Fault isolation   | If Account crashes on 8080, Loan on 8081 keeps running          |
