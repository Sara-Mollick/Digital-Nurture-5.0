-- ============================================================
-- Exercise 6: Cursors
-- Scenario 1: Generate Monthly Statements for All Customers
-- ============================================================
-- Question: Write a PL/SQL block using an explicit cursor
-- GenerateMonthlyStatements that retrieves all transactions for
-- the current month and prints a statement for each customer.
-- ============================================================

SET SERVEROUTPUT ON;

DECLARE
    -- Explicit cursor to fetch transactions for the current month
    CURSOR GenerateMonthlyStatements IS
        SELECT c.CustomerID,
               c.Name AS CustomerName,
               a.AccountID,
               a.AccountType,
               t.TransactionID,
               t.TransactionDate,
               t.Amount,
               t.TransactionType
        FROM Customers c
        JOIN Accounts a ON c.CustomerID = a.CustomerID
        JOIN Transactions t ON a.AccountID = t.AccountID
        WHERE t.TransactionDate >= TRUNC(SYSDATE, 'MM')
          AND t.TransactionDate < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1)
        ORDER BY c.CustomerID, a.AccountID, t.TransactionDate;

    v_rec             GenerateMonthlyStatements%ROWTYPE;
    v_prev_customer   NUMBER := -1;
    v_prev_account    NUMBER := -1;
    v_total_deposits  NUMBER := 0;
    v_total_withdrawals NUMBER := 0;
    v_has_records     BOOLEAN := FALSE;

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('       MONTHLY BANK STATEMENT');
    DBMS_OUTPUT.PUT_LINE('       Period: ' || TO_CHAR(TRUNC(SYSDATE, 'MM'), 'MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('       Generated: ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI'));
    DBMS_OUTPUT.PUT_LINE('============================================================');

    OPEN GenerateMonthlyStatements;

    LOOP
        FETCH GenerateMonthlyStatements INTO v_rec;
        EXIT WHEN GenerateMonthlyStatements%NOTFOUND;

        v_has_records := TRUE;

        -- Print customer header when customer changes
        IF v_rec.CustomerID != v_prev_customer THEN
            IF v_prev_customer != -1 THEN
                -- Print totals for previous customer
                DBMS_OUTPUT.PUT_LINE('');
                DBMS_OUTPUT.PUT_LINE('  Total Deposits   : $' || TO_CHAR(v_total_deposits, '99,999.00'));
                DBMS_OUTPUT.PUT_LINE('  Total Withdrawals: $' || TO_CHAR(v_total_withdrawals, '99,999.00'));
                DBMS_OUTPUT.PUT_LINE('  Net Change       : $' || TO_CHAR(v_total_deposits - v_total_withdrawals, '99,999.00'));
                DBMS_OUTPUT.PUT_LINE('============================================================');
            END IF;

            v_total_deposits := 0;
            v_total_withdrawals := 0;
            v_prev_account := -1;

            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('  Customer: ' || v_rec.CustomerName || ' (ID: ' || v_rec.CustomerID || ')');
            DBMS_OUTPUT.PUT_LINE('------------------------------------------------------------');
        END IF;

        -- Print account header when account changes
        IF v_rec.AccountID != v_prev_account THEN
            DBMS_OUTPUT.PUT_LINE('  Account: ' || v_rec.AccountID || ' (' || v_rec.AccountType || ')');
            DBMS_OUTPUT.PUT_LINE('    Date          | Type       | Amount');
            DBMS_OUTPUT.PUT_LINE('    --------------|------------|----------');
        END IF;

        -- Print transaction details
        DBMS_OUTPUT.PUT_LINE('    ' ||
                             TO_CHAR(v_rec.TransactionDate, 'DD-MON-YYYY') || ' | ' ||
                             RPAD(v_rec.TransactionType, 10) || ' | $' ||
                             TO_CHAR(v_rec.Amount, '99,999.00'));

        -- Track totals
        IF v_rec.TransactionType = 'Deposit' THEN
            v_total_deposits := v_total_deposits + v_rec.Amount;
        ELSIF v_rec.TransactionType = 'Withdrawal' THEN
            v_total_withdrawals := v_total_withdrawals + v_rec.Amount;
        END IF;

        v_prev_customer := v_rec.CustomerID;
        v_prev_account := v_rec.AccountID;
    END LOOP;

    -- Print totals for the last customer
    IF v_has_records THEN
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('  Total Deposits   : $' || TO_CHAR(v_total_deposits, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('  Total Withdrawals: $' || TO_CHAR(v_total_withdrawals, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('  Net Change       : $' || TO_CHAR(v_total_deposits - v_total_withdrawals, '99,999.00'));
    ELSE
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('No transactions found for the current month.');
    END IF;

    CLOSE GenerateMonthlyStatements;

    DBMS_OUTPUT.PUT_LINE('============================================================');
    DBMS_OUTPUT.PUT_LINE('       END OF STATEMENT');
    DBMS_OUTPUT.PUT_LINE('============================================================');

EXCEPTION
    WHEN OTHERS THEN
        IF GenerateMonthlyStatements%ISOPEN THEN
            CLOSE GenerateMonthlyStatements;
        END IF;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
