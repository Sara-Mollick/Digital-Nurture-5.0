# Module 3: Spring Core & Maven Hands-on

This project contains the complete, accurate answers for **Hands-on 1 to 6** of Module 3 (Spring Core & Maven).

## Folder Structure

```text
spring-learn/
│
├── pom.xml                                      # Maven project configuration (dependencies, plug-ins)
├── README.md                                    # This documentation
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── cognizant/
    │   │           └── springlearn/
    │   │               ├── Country.java         # Country domain class with logging
    │   │               └── SpringLearnApplication.java # Main application entry point & methods
    │   │
    │   └── resources/
    │       ├── application.properties           # Application properties and logging pattern
    │       ├── country.xml                      # XML Configuration for countries (Hands-on 4, 5, 6)
    │       └── date-format.xml                  # XML Configuration for SimpleDateFormat bean (Hands-on 2)
    │
    └── test/
        └── java/
            └── com/
                └── cognizant/
                    └── springlearn/
                        └── SpringLearnApplicationTests.java # Default test suite
```

---

## Detailed Hands-on Solutions

### [Hands-on 1]: Create a Spring Web Project using Maven
- Created a Spring Boot application using Maven with the Group `com.cognizant`, Artifact `spring-learn`.
- Configured dependencies for `spring-boot-devtools` and `spring-boot-starter-web` in the [pom.xml](pom.xml).

### [Hands-on 2]: Load SimpleDateFormat from Spring Configuration XML
- Created [date-format.xml](src/main/resources/date-format.xml) in `src/main/resources`.
- Defined a bean `dateFormat` of type `java.text.SimpleDateFormat` with constructor injection for the pattern `"dd/MM/yyyy"`.
- Implemented `displayDate()` in [SpringLearnApplication.java](src/main/java/com/cognizant/springlearn/SpringLearnApplication.java) to load the `ApplicationContext`, retrieve the bean, and parse the date `"31/12/2018"`.

### [Hands-on 3]: Incorporate Logging
- Configured log levels and a customized console logging pattern in [application.properties](src/main/resources/application.properties).
- Replaced `System.out.println()` with SLF4J loggers in [SpringLearnApplication.java](src/main/java/com/cognizant/springlearn/SpringLearnApplication.java) to log method start/end (`LOGGER.info`) and debug info (`LOGGER.debug`).

### [Hands-on 4]: Load Country from Spring Configuration XML
- Created [Country.java](src/main/java/com/cognizant/springlearn/Country.java) with properties `code` and `name`. Added logging messages within the default constructor and all getters/setters.
- Defined a `country` bean in [country.xml](src/main/resources/country.xml).
- Implemented `displayCountry()` in [SpringLearnApplication.java](src/main/java/com/cognizant/springlearn/SpringLearnApplication.java) to retrieve and log the country bean.

### [Hands-on 5]: Demonstration of Singleton vs Prototype Scope
- Added code in `displayCountry()` to fetch the `country` bean twice from the same context and assert if they refer to the same instance (`country == anotherCountry`).
- Configured `scope="prototype"` for the `country` bean in [country.xml](src/main/resources/country.xml) so that two separate instances are created and the constructor log prints twice.

### [Hands-on 6]: Load List of Countries
- Defined four separate country beans (`in`, `us`, `de`, `jp`) in [country.xml](src/main/resources/country.xml).
- Defined an `ArrayList` bean (`countryList`) that references these country beans.
- Implemented `displayCountries()` in [SpringLearnApplication.java](src/main/java/com/cognizant/springlearn/SpringLearnApplication.java) to retrieve the list and log the countries.

---

## How to Run

1. Open this folder `C:\Users\Lenovo\.gemini\antigravity\scratch\spring-learn` in Eclipse or any IDE supporting Maven/Spring Boot.
2. Build the project using Maven (or run inside your IDE):
   ```bash
   mvn clean package
   ```
3. Run the application main class `com.cognizant.springlearn.SpringLearnApplication`.
