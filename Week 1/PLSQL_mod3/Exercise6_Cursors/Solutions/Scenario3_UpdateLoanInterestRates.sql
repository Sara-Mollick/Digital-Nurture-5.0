-- ============================================================
-- Exercise 6: Cursors
-- Scenario 3: Update Loan Interest Rates Based on New Policy
-- ============================================================
-- Question: Write a PL/SQL block using an explicit cursor
-- UpdateLoanInterestRates that fetches all loans and updates their
-- interest rates based on the new policy.
-- ============================================================
-- New Policy Rules:
--   - Loan Amount > $10,000 : Reduce interest rate by 0.5%
--   - Loan Amount > $5,000  : Reduce interest rate by 0.25%
--   - Loan Amount <= $5,000 : Increase interest rate by 0.5%
-- ============================================================

SET SERVEROUTPUT ON;

DECLARE
    -- Explicit cursor to fetch all loans with customer details
    CURSOR UpdateLoanInterestRates IS
        SELECT l.LoanID, l.CustomerID, l.LoanAmount,
               l.InterestRate, l.StartDate, l.EndDate,
               c.Name AS CustomerName
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        FOR UPDATE OF l.InterestRate;

    v_rec          UpdateLoanInterestRates%ROWTYPE;
    v_old_rate     NUMBER;
    v_new_rate     NUMBER;
    v_adjustment   NUMBER;
    v_count        NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('       LOAN INTEREST RATE UPDATE REPORT');
    DBMS_OUTPUT.PUT_LINE('       New Policy Implementation');
    DBMS_OUTPUT.PUT_LINE('       Date: ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Policy Rules:');
    DBMS_OUTPUT.PUT_LINE('  Loan > $10,000 : Rate reduced by 0.50%');
    DBMS_OUTPUT.PUT_LINE('  Loan > $5,000  : Rate reduced by 0.25%');
    DBMS_OUTPUT.PUT_LINE('  Loan <= $5,000 : Rate increased by 0.50%');
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');

    OPEN UpdateLoanInterestRates;

    LOOP
        FETCH UpdateLoanInterestRates INTO v_rec;
        EXIT WHEN UpdateLoanInterestRates%NOTFOUND;

        v_old_rate := v_rec.InterestRate;

        -- Apply policy rules
        IF v_rec.LoanAmount > 10000 THEN
            v_adjustment := -0.5;
        ELSIF v_rec.LoanAmount > 5000 THEN
            v_adjustment := -0.25;
        ELSE
            v_adjustment := 0.5;
        END IF;

        v_new_rate := v_old_rate + v_adjustment;

        -- Ensure interest rate doesn't go below 1%
        IF v_new_rate < 1 THEN
            v_new_rate := 1;
        END IF;

        -- Update the interest rate
        UPDATE Loans
        SET InterestRate = v_new_rate
        WHERE CURRENT OF UpdateLoanInterestRates;

        v_count := v_count + 1;

        DBMS_OUTPUT.PUT_LINE('Loan ID      : ' || v_rec.LoanID);
        DBMS_OUTPUT.PUT_LINE('Customer     : ' || v_rec.CustomerName);
        DBMS_OUTPUT.PUT_LINE('Loan Amount  : $' || TO_CHAR(v_rec.LoanAmount, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('Old Rate     : ' || TO_CHAR(v_old_rate, '99.00') || '%');
        DBMS_OUTPUT.PUT_LINE('Adjustment   : ' || TO_CHAR(v_adjustment, 'S9.00') || '%');
        DBMS_OUTPUT.PUT_LINE('New Rate     : ' || TO_CHAR(v_new_rate, '99.00') || '%');
        DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
    END LOOP;

    CLOSE UpdateLoanInterestRates;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Total loans updated: ' || v_count);
    DBMS_OUTPUT.PUT_LINE('============================================================');

EXCEPTION
    WHEN OTHERS THEN
        IF UpdateLoanInterestRates%ISOPEN THEN
            CLOSE UpdateLoanInterestRates;
        END IF;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END;
/

-- Verify updated interest rates
SELECT l.LoanID, c.Name, l.LoanAmount, l.InterestRate, l.StartDate, l.EndDate
FROM Loans l
JOIN Customers c ON l.CustomerID = c.CustomerID;
