-- ============================================================
-- Exercise 4: Functions
-- Scenario 2: Calculate Monthly Loan Installment (EMI)
-- ============================================================
-- Question: Write a function CalculateMonthlyInstallment that
-- takes the loan amount, interest rate, and loan duration in years
-- as input and returns the monthly installment amount.
-- ============================================================
-- Formula (EMI): P * r * (1+r)^n / ((1+r)^n - 1)
--   Where: P = Principal (loan amount)
--          r = Monthly interest rate (annual rate / 12 / 100)
--          n = Total number of months (years * 12)
-- ============================================================

SET SERVEROUTPUT ON;

CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    p_loan_amount    IN NUMBER,
    p_interest_rate  IN NUMBER,
    p_duration_years IN NUMBER
) RETURN NUMBER
AS
    v_monthly_rate   NUMBER;
    v_num_months     NUMBER;
    v_emi            NUMBER;
    v_power_factor   NUMBER;
BEGIN
    -- Handle edge case: zero interest rate
    IF p_interest_rate = 0 THEN
        v_num_months := p_duration_years * 12;
        IF v_num_months > 0 THEN
            RETURN ROUND(p_loan_amount / v_num_months, 2);
        ELSE
            RETURN p_loan_amount;
        END IF;
    END IF;

    -- Calculate monthly interest rate
    v_monthly_rate := p_interest_rate / 12 / 100;

    -- Calculate total number of months
    v_num_months := p_duration_years * 12;

    -- Calculate (1 + r)^n
    v_power_factor := POWER(1 + v_monthly_rate, v_num_months);

    -- Calculate EMI using the formula: P * r * (1+r)^n / ((1+r)^n - 1)
    v_emi := p_loan_amount * v_monthly_rate * v_power_factor / (v_power_factor - 1);

    RETURN ROUND(v_emi, 2);

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error calculating EMI: ' || SQLERRM);
        RETURN -1;
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Calculate EMI for all existing loans
DECLARE
    v_emi NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Loan Monthly Installment Report');
    DBMS_OUTPUT.PUT_LINE('============================================');

    FOR rec IN (
        SELECT l.LoanID, c.Name, l.LoanAmount, l.InterestRate,
               TRUNC(MONTHS_BETWEEN(l.EndDate, l.StartDate) / 12) AS DurationYears
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
    ) LOOP
        v_emi := CalculateMonthlyInstallment(rec.LoanAmount, rec.InterestRate, rec.DurationYears);

        DBMS_OUTPUT.PUT_LINE('Loan ID       : ' || rec.LoanID);
        DBMS_OUTPUT.PUT_LINE('Customer      : ' || rec.Name);
        DBMS_OUTPUT.PUT_LINE('Loan Amount   : $' || TO_CHAR(rec.LoanAmount, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('Interest Rate : ' || rec.InterestRate || '% per annum');
        DBMS_OUTPUT.PUT_LINE('Duration      : ' || rec.DurationYears || ' years');
        DBMS_OUTPUT.PUT_LINE('Monthly EMI   : $' || TO_CHAR(v_emi, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('--------------------------------------------');
    END LOOP;
END;
/

-- Test 2: Direct function call
SELECT CalculateMonthlyInstallment(5000, 5, 5) AS Monthly_EMI FROM DUAL;

-- Test 3: Zero interest rate
SELECT CalculateMonthlyInstallment(12000, 0, 1) AS Monthly_EMI FROM DUAL;
