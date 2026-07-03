-- ============================================================
-- Exercise 5: Triggers
-- Scenario 3: Enforce Business Rules on Transactions
-- ============================================================
-- Question: Write a trigger CheckTransactionRules that ensures
-- withdrawals do not exceed the balance and deposits are positive
-- before inserting a record into the Transactions table.
-- ============================================================

SET SERVEROUTPUT ON;

CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    -- Fetch the current balance of the account
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = :NEW.AccountID;

    -- Rule 1: Withdrawals must not exceed the account balance
    IF :NEW.TransactionType = 'Withdrawal' THEN
        IF :NEW.Amount > v_balance THEN
            RAISE_APPLICATION_ERROR(-20001,
                'Withdrawal of $' || :NEW.Amount ||
                ' exceeds account balance of $' || v_balance ||
                ' for Account ID ' || :NEW.AccountID);
        END IF;

        IF :NEW.Amount <= 0 THEN
            RAISE_APPLICATION_ERROR(-20003,
                'Withdrawal amount must be positive. Received: $' || :NEW.Amount);
        END IF;
    END IF;

    -- Rule 2: Deposits must be positive
    IF :NEW.TransactionType = 'Deposit' THEN
        IF :NEW.Amount <= 0 THEN
            RAISE_APPLICATION_ERROR(-20002,
                'Deposit amount must be positive. Received: $' || :NEW.Amount);
        END IF;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Transaction Rules Check PASSED for Transaction ' ||
                         :NEW.TransactionID || ' (' || :NEW.TransactionType ||
                         ': $' || :NEW.Amount || ')');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20004,
            'Account ID ' || :NEW.AccountID || ' does not exist.');
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Valid deposit (should succeed)
BEGIN
    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (5, 1, SYSDATE, 100, 'Deposit');
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Test 1 PASSED: Valid deposit accepted.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 1 FAILED: ' || SQLERRM);
        ROLLBACK;
END;
/

-- Test 2: Valid withdrawal (should succeed)
BEGIN
    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (6, 1, SYSDATE, 50, 'Withdrawal');
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Test 2 PASSED: Valid withdrawal accepted.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 2 FAILED: ' || SQLERRM);
        ROLLBACK;
END;
/

-- Test 3: Withdrawal exceeding balance (should FAIL)
BEGIN
    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (7, 1, SYSDATE, 999999, 'Withdrawal');
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Test 3 FAILED: Should have been rejected.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 3 PASSED: Correctly rejected - ' || SQLERRM);
        ROLLBACK;
END;
/

-- Test 4: Negative deposit (should FAIL)
BEGIN
    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (8, 1, SYSDATE, -100, 'Deposit');
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Test 4 FAILED: Should have been rejected.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Test 4 PASSED: Correctly rejected - ' || SQLERRM);
        ROLLBACK;
END;
/
