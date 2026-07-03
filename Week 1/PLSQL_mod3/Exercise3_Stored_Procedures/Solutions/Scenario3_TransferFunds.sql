-- ============================================================
-- Exercise 3: Stored Procedures
-- Scenario 3: Transfer Funds Between Accounts
-- ============================================================
-- Question: Write a stored procedure TransferFunds that transfers
-- a specified amount from one account to another, checking that the
-- source account has sufficient balance before making the transfer.
-- ============================================================

SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE TransferFunds (
    p_source_account IN NUMBER,
    p_target_account IN NUMBER,
    p_amount         IN NUMBER
)
AS
    v_source_balance NUMBER;
    v_target_balance NUMBER;
BEGIN
    -- Fetch source account balance
    SELECT Balance INTO v_source_balance
    FROM Accounts
    WHERE AccountID = p_source_account;

    -- Check for sufficient balance
    IF v_source_balance < p_amount THEN
        DBMS_OUTPUT.PUT_LINE('============================================');
        DBMS_OUTPUT.PUT_LINE('TRANSFER FAILED');
        DBMS_OUTPUT.PUT_LINE('============================================');
        DBMS_OUTPUT.PUT_LINE('Insufficient balance in Account ' || p_source_account);
        DBMS_OUTPUT.PUT_LINE('Available Balance: $' || TO_CHAR(v_source_balance, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('Requested Amount : $' || TO_CHAR(p_amount, '99,999.00'));
        RETURN;
    END IF;

    -- Fetch target account balance (also validates it exists)
    SELECT Balance INTO v_target_balance
    FROM Accounts
    WHERE AccountID = p_target_account;

    -- Debit source account
    UPDATE Accounts
    SET Balance = Balance - p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_source_account;

    -- Credit target account
    UPDATE Accounts
    SET Balance = Balance + p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_target_account;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('TRANSFER SUCCESSFUL');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('From Account  : ' || p_source_account);
    DBMS_OUTPUT.PUT_LINE('To Account    : ' || p_target_account);
    DBMS_OUTPUT.PUT_LINE('Amount        : $' || TO_CHAR(p_amount, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Source Old Bal: $' || TO_CHAR(v_source_balance, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('Source New Bal: $' || TO_CHAR(v_source_balance - p_amount, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('Target Old Bal: $' || TO_CHAR(v_target_balance, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('Target New Bal: $' || TO_CHAR(v_target_balance + p_amount, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('============================================');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: One or both accounts do not exist.');
        ROLLBACK;

    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
        ROLLBACK;
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Valid transfer ($200 from Account 1 to Account 2)
EXEC TransferFunds(1, 2, 200);

-- Test 2: Insufficient funds
EXEC TransferFunds(1, 2, 999999);

-- Test 3: Non-existent account
EXEC TransferFunds(1, 999, 100);

-- Verify balances
SELECT AccountID, CustomerID, AccountType, Balance FROM Accounts;
