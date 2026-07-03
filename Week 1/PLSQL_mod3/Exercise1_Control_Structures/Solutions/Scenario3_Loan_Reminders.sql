-- ============================================================
-- Exercise 1: Control Structures
-- Scenario 3: Loan Due Reminders
-- ============================================================
-- Question: Write a PL/SQL block that fetches all loans due in
-- the next 30 days and prints a reminder message for each customer.
-- ============================================================

SET SERVEROUTPUT ON;

DECLARE
    -- Cursor to fetch loans due within 30 days along with customer details
    CURSOR c_due_loans IS
        SELECT l.LoanID,
               l.LoanAmount,
               l.InterestRate,
               l.EndDate,
               c.CustomerID,
               c.Name
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
        ORDER BY l.EndDate;

    v_days_remaining NUMBER;
    v_loan_count     NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('       LOAN DUE REMINDER REPORT');
    DBMS_OUTPUT.PUT_LINE('       Generated on: ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI'));
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('');

    FOR rec IN c_due_loans LOOP
        v_days_remaining := TRUNC(rec.EndDate - SYSDATE);
        v_loan_count := v_loan_count + 1;

        DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
        DBMS_OUTPUT.PUT_LINE('REMINDER #' || v_loan_count);
        DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
        DBMS_OUTPUT.PUT_LINE('Dear ' || rec.Name || ',');
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('This is a reminder that your loan is due soon.');
        DBMS_OUTPUT.PUT_LINE('  Loan ID       : ' || rec.LoanID);
        DBMS_OUTPUT.PUT_LINE('  Loan Amount   : $' || TO_CHAR(rec.LoanAmount, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('  Interest Rate : ' || rec.InterestRate || '%');
        DBMS_OUTPUT.PUT_LINE('  Due Date      : ' || TO_CHAR(rec.EndDate, 'DD-MON-YYYY'));
        DBMS_OUTPUT.PUT_LINE('  Days Remaining: ' || v_days_remaining || ' day(s)');
        DBMS_OUTPUT.PUT_LINE('');

        -- Urgency classification using IF-ELSIF-ELSE
        IF v_days_remaining <= 7 THEN
            DBMS_OUTPUT.PUT_LINE('  ** URGENT: Your loan is due within 7 days! **');
        ELSIF v_days_remaining <= 14 THEN
            DBMS_OUTPUT.PUT_LINE('  * IMPORTANT: Your loan is due within 2 weeks. *');
        ELSE
            DBMS_OUTPUT.PUT_LINE('  Note: Please arrange for payment before the due date.');
        END IF;

        DBMS_OUTPUT.PUT_LINE('');
    END LOOP;

    IF v_loan_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No loans are due within the next 30 days.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('============================================================');
        DBMS_OUTPUT.PUT_LINE('Total reminders sent: ' || v_loan_count);
        DBMS_OUTPUT.PUT_LINE('============================================================');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/
