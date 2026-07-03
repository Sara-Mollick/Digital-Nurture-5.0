-- ============================================================
-- Exercise 3: Stored Procedures
-- Scenario 1: Process Monthly Interest for Savings Accounts
-- ============================================================
-- Question: Write a stored procedure ProcessMonthlyInterest that
-- calculates and updates the balance of all savings accounts by
-- applying an interest rate of 1% to the current balance.
-- ============================================================

SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
AS
    CURSOR c_savings_accounts IS
        SELECT AccountID, CustomerID, Balance
        FROM Accounts
        WHERE AccountType = 'Savings';

    v_interest    NUMBER;
    v_new_balance NUMBER;
    v_count       NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Monthly Interest Processing Report');
    DBMS_OUTPUT.PUT_LINE('Date: ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Interest Rate: 1%');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('');

    FOR rec IN c_savings_accounts LOOP
        -- Calculate interest (1% of current balance)
        v_interest := rec.Balance * 0.01;
        v_new_balance := rec.Balance + v_interest;

        -- Update account balance
        UPDATE Accounts
        SET Balance = v_new_balance,
            LastModified = SYSDATE
        WHERE AccountID = rec.AccountID;

        v_count := v_count + 1;

        DBMS_OUTPUT.PUT_LINE('Account ID    : ' || rec.AccountID);
        DBMS_OUTPUT.PUT_LINE('Old Balance   : $' || TO_CHAR(rec.Balance, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('Interest Added: $' || TO_CHAR(v_interest, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('New Balance   : $' || TO_CHAR(v_new_balance, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('--------------------------------------------');
    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Total accounts processed: ' || v_count);
    DBMS_OUTPUT.PUT_LINE('============================================');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
        ROLLBACK;
END;
/

-- ============================================================
-- Test: Execute the procedure
-- ============================================================
EXEC ProcessMonthlyInterest;

-- Verify updated balances
SELECT AccountID, CustomerID, AccountType, Balance, LastModified
FROM Accounts
WHERE AccountType = 'Savings';
