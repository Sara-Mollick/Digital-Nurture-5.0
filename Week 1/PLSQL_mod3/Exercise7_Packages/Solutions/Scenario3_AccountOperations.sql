-- ============================================================
-- Exercise 7: Packages
-- Scenario 3: Account Operations Package
-- ============================================================
-- Question: Create a package AccountOperations with procedures for
-- opening a new account, closing an account, and a function to get
-- the total balance of a customer across all accounts.
-- ============================================================

SET SERVEROUTPUT ON;

-- ============================================================
-- Package Specification
-- ============================================================
CREATE OR REPLACE PACKAGE AccountOperations
AS
    -- Procedure to open a new account
    PROCEDURE OpenAccount (
        p_account_id      IN NUMBER,
        p_customer_id     IN NUMBER,
        p_account_type    IN VARCHAR2,
        p_initial_balance IN NUMBER
    );

    -- Procedure to close an account
    PROCEDURE CloseAccount (
        p_account_id IN NUMBER
    );

    -- Function to get total balance across all accounts for a customer
    FUNCTION GetTotalBalance (
        p_customer_id IN NUMBER
    ) RETURN NUMBER;

END AccountOperations;
/

-- ============================================================
-- Package Body
-- ============================================================
CREATE OR REPLACE PACKAGE BODY AccountOperations
AS

    -- --------------------------------------------------------
    -- Procedure: OpenAccount
    -- --------------------------------------------------------
    PROCEDURE OpenAccount (
        p_account_id      IN NUMBER,
        p_customer_id     IN NUMBER,
        p_account_type    IN VARCHAR2,
        p_initial_balance IN NUMBER
    )
    AS
        v_customer_exists NUMBER;
    BEGIN
        -- Verify the customer exists
        SELECT COUNT(*) INTO v_customer_exists
        FROM Customers
        WHERE CustomerID = p_customer_id;

        IF v_customer_exists = 0 THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Customer ID ' || p_customer_id || ' does not exist.');
            DBMS_OUTPUT.PUT_LINE('Cannot open account for non-existent customer.');
            RETURN;
        END IF;

        -- Validate initial balance
        IF p_initial_balance < 0 THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Initial balance cannot be negative.');
            RETURN;
        END IF;

        -- Insert the new account
        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (p_account_id, p_customer_id, p_account_type, p_initial_balance, SYSDATE);

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Account opened successfully.');
        DBMS_OUTPUT.PUT_LINE('  Account ID     : ' || p_account_id);
        DBMS_OUTPUT.PUT_LINE('  Customer ID    : ' || p_customer_id);
        DBMS_OUTPUT.PUT_LINE('  Account Type   : ' || p_account_type);
        DBMS_OUTPUT.PUT_LINE('  Initial Balance: $' || TO_CHAR(p_initial_balance, '99,999.00'));

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Account ID ' || p_account_id || ' already exists.');

        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
            ROLLBACK;
    END OpenAccount;

    -- --------------------------------------------------------
    -- Procedure: CloseAccount
    -- --------------------------------------------------------
    PROCEDURE CloseAccount (
        p_account_id IN NUMBER
    )
    AS
        v_balance       NUMBER;
        v_txn_count     NUMBER;
        v_customer_name VARCHAR2(100);
    BEGIN
        -- Fetch account details
        SELECT a.Balance, c.Name
        INTO v_balance, v_customer_name
        FROM Accounts a
        JOIN Customers c ON a.CustomerID = c.CustomerID
        WHERE a.AccountID = p_account_id;

        -- Check for pending transactions
        SELECT COUNT(*) INTO v_txn_count
        FROM Transactions
        WHERE AccountID = p_account_id;

        -- Remove associated transactions first (referential integrity)
        IF v_txn_count > 0 THEN
            DELETE FROM Transactions
            WHERE AccountID = p_account_id;

            DBMS_OUTPUT.PUT_LINE('Removed ' || v_txn_count || ' associated transaction(s).');
        END IF;

        -- Delete the account
        DELETE FROM Accounts
        WHERE AccountID = p_account_id;

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Account closed successfully.');
        DBMS_OUTPUT.PUT_LINE('  Account ID  : ' || p_account_id);
        DBMS_OUTPUT.PUT_LINE('  Customer    : ' || v_customer_name);
        DBMS_OUTPUT.PUT_LINE('  Final Balance: $' || TO_CHAR(v_balance, '99,999.00'));

        IF v_balance > 0 THEN
            DBMS_OUTPUT.PUT_LINE('  Note: Remaining balance of $' ||
                                 TO_CHAR(v_balance, '99,999.00') ||
                                 ' should be refunded to customer.');
        END IF;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Account ID ' || p_account_id || ' not found.');

        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
            ROLLBACK;
    END CloseAccount;

    -- --------------------------------------------------------
    -- Function: GetTotalBalance
    -- --------------------------------------------------------
    FUNCTION GetTotalBalance (
        p_customer_id IN NUMBER
    ) RETURN NUMBER
    AS
        v_total_balance NUMBER;
    BEGIN
        SELECT NVL(SUM(Balance), 0)
        INTO v_total_balance
        FROM Accounts
        WHERE CustomerID = p_customer_id;

        RETURN v_total_balance;

    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
            RETURN -1;
    END GetTotalBalance;

END AccountOperations;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Open a new account
BEGIN
    AccountOperations.OpenAccount(3, 1, 'Fixed Deposit', 5000);
END;
/

-- Test 2: Get total balance for customer 1 (should include all accounts)
DECLARE
    v_total NUMBER;
BEGIN
    v_total := AccountOperations.GetTotalBalance(1);
    DBMS_OUTPUT.PUT_LINE('Customer 1 Total Balance: $' || TO_CHAR(v_total, '99,999.00'));

    v_total := AccountOperations.GetTotalBalance(2);
    DBMS_OUTPUT.PUT_LINE('Customer 2 Total Balance: $' || TO_CHAR(v_total, '99,999.00'));

    v_total := AccountOperations.GetTotalBalance(999);
    DBMS_OUTPUT.PUT_LINE('Customer 999 Total Balance: $' || TO_CHAR(v_total, '99,999.00'));
END;
/

-- Test 3: Close the newly created account
BEGIN
    AccountOperations.CloseAccount(3);
END;
/

-- Test 4: Try to close non-existent account
BEGIN
    AccountOperations.CloseAccount(999);
END;
/

-- Test 5: Try to open account with non-existent customer
BEGIN
    AccountOperations.OpenAccount(99, 999, 'Savings', 100);
END;
/

-- Verify
SELECT * FROM Accounts;
