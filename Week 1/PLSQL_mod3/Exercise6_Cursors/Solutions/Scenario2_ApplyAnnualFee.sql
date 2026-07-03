-- ============================================================
-- Exercise 6: Cursors
-- Scenario 2: Apply Annual Maintenance Fee to All Accounts
-- ============================================================
-- Question: Write a PL/SQL block using an explicit cursor
-- ApplyAnnualFee that deducts an annual maintenance fee from
-- the balance of all accounts.
-- ============================================================

SET SERVEROUTPUT ON;

DECLARE
    -- Define the annual maintenance fee
    v_annual_fee CONSTANT NUMBER := 50;

    -- Explicit cursor for all accounts
    CURSOR ApplyAnnualFee IS
        SELECT AccountID, CustomerID, AccountType, Balance
        FROM Accounts
        FOR UPDATE OF Balance;

    v_rec          ApplyAnnualFee%ROWTYPE;
    v_new_balance  NUMBER;
    v_count        NUMBER := 0;
    v_skipped      NUMBER := 0;

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Annual Maintenance Fee Deduction Report');
    DBMS_OUTPUT.PUT_LINE('Fee Amount: $' || TO_CHAR(v_annual_fee, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('Date: ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('');

    OPEN ApplyAnnualFee;

    LOOP
        FETCH ApplyAnnualFee INTO v_rec;
        EXIT WHEN ApplyAnnualFee%NOTFOUND;

        -- Check if account has sufficient balance for the fee
        IF v_rec.Balance >= v_annual_fee THEN
            v_new_balance := v_rec.Balance - v_annual_fee;

            -- Deduct the fee using WHERE CURRENT OF
            UPDATE Accounts
            SET Balance = v_new_balance,
                LastModified = SYSDATE
            WHERE CURRENT OF ApplyAnnualFee;

            v_count := v_count + 1;

            DBMS_OUTPUT.PUT_LINE('Account ID  : ' || v_rec.AccountID ||
                                 ' (' || v_rec.AccountType || ')');
            DBMS_OUTPUT.PUT_LINE('Old Balance : $' || TO_CHAR(v_rec.Balance, '99,999.00'));
            DBMS_OUTPUT.PUT_LINE('Fee Deducted: $' || TO_CHAR(v_annual_fee, '99,999.00'));
            DBMS_OUTPUT.PUT_LINE('New Balance : $' || TO_CHAR(v_new_balance, '99,999.00'));
            DBMS_OUTPUT.PUT_LINE('--------------------------------------------');
        ELSE
            v_skipped := v_skipped + 1;

            DBMS_OUTPUT.PUT_LINE('Account ID  : ' || v_rec.AccountID ||
                                 ' (' || v_rec.AccountType || ')');
            DBMS_OUTPUT.PUT_LINE('Balance     : $' || TO_CHAR(v_rec.Balance, '99,999.00'));
            DBMS_OUTPUT.PUT_LINE('** SKIPPED: Insufficient balance for fee deduction **');
            DBMS_OUTPUT.PUT_LINE('--------------------------------------------');
        END IF;
    END LOOP;

    CLOSE ApplyAnnualFee;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Summary:');
    DBMS_OUTPUT.PUT_LINE('  Accounts processed : ' || v_count);
    DBMS_OUTPUT.PUT_LINE('  Accounts skipped   : ' || v_skipped);
    DBMS_OUTPUT.PUT_LINE('  Total fee collected: $' || TO_CHAR(v_count * v_annual_fee, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('============================================');

EXCEPTION
    WHEN OTHERS THEN
        IF ApplyAnnualFee%ISOPEN THEN
            CLOSE ApplyAnnualFee;
        END IF;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END;
/

-- Verify updated balances
SELECT AccountID, CustomerID, AccountType, Balance, LastModified
FROM Accounts;
