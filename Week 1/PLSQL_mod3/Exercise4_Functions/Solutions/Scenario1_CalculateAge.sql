-- ============================================================
-- Exercise 4: Functions
-- Scenario 1: Calculate Customer Age
-- ============================================================
-- Question: Write a function CalculateAge that takes a customer's
-- date of birth as input and returns their age in years.
-- ============================================================

SET SERVEROUTPUT ON;

CREATE OR REPLACE FUNCTION CalculateAge (
    p_dob IN DATE
) RETURN NUMBER
AS
    v_age NUMBER;
BEGIN
    -- Calculate age using MONTHS_BETWEEN and TRUNC
    v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
    RETURN v_age;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error calculating age: ' || SQLERRM);
        RETURN -1;
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Calculate age for all customers
DECLARE
    v_age NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Customer Age Report');
    DBMS_OUTPUT.PUT_LINE('============================================');

    FOR rec IN (SELECT CustomerID, Name, DOB FROM Customers) LOOP
        v_age := CalculateAge(rec.DOB);
        DBMS_OUTPUT.PUT_LINE('Customer: ' || rec.Name ||
                             ' | DOB: ' || TO_CHAR(rec.DOB, 'DD-MON-YYYY') ||
                             ' | Age: ' || v_age || ' years');
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('============================================');
END;
/

-- Test 2: Using the function in a SQL query
SELECT CustomerID, Name, DOB, CalculateAge(DOB) AS Age
FROM Customers;

-- Test 3: Direct function call
SELECT CalculateAge(TO_DATE('1960-01-15', 'YYYY-MM-DD')) AS Age FROM DUAL;
