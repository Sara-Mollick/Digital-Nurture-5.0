-- ============================================================
-- Exercise 2: Error Handling
-- Scenario 1: Safe Fund Transfer Between Accounts
-- ============================================================
-- Question: Write a stored procedure SafeTransferFunds that
-- transfers funds between two accounts. Ensure that if any error
-- occurs (e.g., insufficient funds), an appropriate error message
-- is logged and the transaction is rolled back.
-- ============================================================

SET SERVEROUTPUT ON;

-- Step 1: Create ErrorLog table to store error messages
BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE ErrorLog (
        ErrorID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        ProcedureName VARCHAR2(100),
        ErrorMessage VARCHAR2(500),
        ErrorDate DATE DEFAULT SYSDATE
    )';
    DBMS_OUTPUT.PUT_LINE('ErrorLog table created.');
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN  -- Table already exists
            DBMS_OUTPUT.PUT_LINE('ErrorLog table already exists.');
        ELSE
            RAISE;
        END IF;
END;
/

-- Step 2: Create the SafeTransferFunds procedure
CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    p_from_account IN NUMBER,
    p_to_account   IN NUMBER,
    p_amount       IN NUMBER
)
AS
    v_source_balance NUMBER;
    v_target_balance NUMBER;
    e_insufficient_funds EXCEPTION;
    e_invalid_amount     EXCEPTION;
BEGIN
    -- Validate transfer amount
    IF p_amount <= 0 THEN
        RAISE e_invalid_amount;
    END IF;

    -- Set savepoint for potential rollback
    SAVEPOINT before_transfer;

    -- Check source account balance
    SELECT Balance INTO v_source_balance
    FROM Accounts
    WHERE AccountID = p_from_account;

    -- Check if sufficient funds exist
    IF v_source_balance < p_amount THEN
        RAISE e_insufficient_funds;
    END IF;

    -- Verify target account exists
    SELECT Balance INTO v_target_balance
    FROM Accounts
    WHERE AccountID = p_to_account;

    -- Debit from source account
    UPDATE Accounts
    SET Balance = Balance - p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_from_account;

    -- Credit to target account
    UPDATE Accounts
    SET Balance = Balance + p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_to_account;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Transfer successful!');
    DBMS_OUTPUT.PUT_LINE('Amount: $' || TO_CHAR(p_amount, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('From Account ' || p_from_account || ' to Account ' || p_to_account);

EXCEPTION
    WHEN e_insufficient_funds THEN
        ROLLBACK TO before_transfer;
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds',
                'Insufficient funds in Account ' || p_from_account ||
                '. Balance: $' || v_source_balance ||
                ', Requested: $' || p_amount);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Insufficient funds. Transfer rolled back.');
        DBMS_OUTPUT.PUT_LINE('Available Balance: $' || v_source_balance);

    WHEN e_invalid_amount THEN
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds',
                'Invalid transfer amount: $' || p_amount || '. Amount must be positive.');
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Transfer amount must be positive.');

    WHEN NO_DATA_FOUND THEN
        ROLLBACK TO before_transfer;
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds',
                'Account not found. From: ' || p_from_account || ', To: ' || p_to_account);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: One or both accounts do not exist. Transfer rolled back.');

    WHEN OTHERS THEN
        ROLLBACK TO before_transfer;
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds', 'Unexpected error: ' || SQLERRM);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Unexpected error occurred. Transfer rolled back.');
        DBMS_OUTPUT.PUT_LINE('Details: ' || SQLERRM);
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Valid transfer
EXEC SafeTransferFunds(1, 2, 200);

-- Test 2: Insufficient funds
EXEC SafeTransferFunds(1, 2, 999999);

-- Test 3: Invalid account
EXEC SafeTransferFunds(999, 2, 100);

-- Test 4: Invalid amount
EXEC SafeTransferFunds(1, 2, -50);

-- Check error log
SELECT * FROM ErrorLog;
