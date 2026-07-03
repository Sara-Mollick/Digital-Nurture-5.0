-- ============================================================
-- Exercise 4: Functions
-- Scenario 3: Check Sufficient Balance
-- ============================================================
-- Question: Write a function HasSufficientBalance that takes an
-- account ID and an amount as input and returns a boolean indicating
-- whether the account has at least the specified amount.
-- ============================================================

SET SERVEROUTPUT ON;

CREATE OR REPLACE FUNCTION HasSufficientBalance (
    p_account_id IN NUMBER,
    p_amount     IN NUMBER
) RETURN BOOLEAN
AS
    v_balance NUMBER;
BEGIN
    -- Fetch the account balance
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = p_account_id;

    -- Return TRUE if balance is sufficient
    RETURN (v_balance >= p_amount);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Account ID ' || p_account_id || ' not found.');
        RETURN FALSE;

    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        RETURN FALSE;
END;
/

-- ============================================================
-- Test Cases
-- ============================================================
-- Note: Since BOOLEAN cannot be used directly in SQL queries,
-- we test using PL/SQL anonymous blocks.

DECLARE
    v_result BOOLEAN;
BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Sufficient Balance Check Tests');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('');

    -- Test 1: Account 1, check for $500 (should be TRUE)
    v_result := HasSufficientBalance(1, 500);
    DBMS_OUTPUT.PUT_LINE('Test 1: Account 1, Amount $500');
    IF v_result THEN
        DBMS_OUTPUT.PUT_LINE('Result: SUFFICIENT BALANCE');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Result: INSUFFICIENT BALANCE');
    END IF;
    DBMS_OUTPUT.PUT_LINE('');

    -- Test 2: Account 1, check for $999999 (should be FALSE)
    v_result := HasSufficientBalance(1, 999999);
    DBMS_OUTPUT.PUT_LINE('Test 2: Account 1, Amount $999,999');
    IF v_result THEN
        DBMS_OUTPUT.PUT_LINE('Result: SUFFICIENT BALANCE');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Result: INSUFFICIENT BALANCE');
    END IF;
    DBMS_OUTPUT.PUT_LINE('');

    -- Test 3: Non-existent account (should be FALSE)
    v_result := HasSufficientBalance(999, 100);
    DBMS_OUTPUT.PUT_LINE('Test 3: Account 999 (non-existent), Amount $100');
    IF v_result THEN
        DBMS_OUTPUT.PUT_LINE('Result: SUFFICIENT BALANCE');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Result: INSUFFICIENT BALANCE');
    END IF;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================');
END;
/
