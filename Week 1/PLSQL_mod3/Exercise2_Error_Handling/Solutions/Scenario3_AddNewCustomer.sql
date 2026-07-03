-- ============================================================
-- Exercise 2: Error Handling
-- Scenario 3: Add New Customer with Data Integrity
-- ============================================================
-- Question: Write a stored procedure AddNewCustomer that inserts
-- a new customer into the Customers table. If a customer with the
-- same ID already exists, handle the exception by logging an error
-- and preventing the insertion.
-- ============================================================

SET SERVEROUTPUT ON;

-- Ensure ErrorLog table exists
BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE ErrorLog (
        ErrorID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        ProcedureName VARCHAR2(100),
        ErrorMessage VARCHAR2(500),
        ErrorDate DATE DEFAULT SYSDATE
    )';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN NULL;
        ELSE RAISE;
        END IF;
END;
/

-- Create the AddNewCustomer procedure
CREATE OR REPLACE PROCEDURE AddNewCustomer (
    p_customer_id IN NUMBER,
    p_name        IN VARCHAR2,
    p_dob         IN DATE,
    p_balance     IN NUMBER
)
AS
    e_invalid_balance EXCEPTION;
BEGIN
    -- Validate balance
    IF p_balance < 0 THEN
        RAISE e_invalid_balance;
    END IF;

    -- Attempt to insert new customer
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_balance, SYSDATE);

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Customer added successfully!');
    DBMS_OUTPUT.PUT_LINE('Customer ID : ' || p_customer_id);
    DBMS_OUTPUT.PUT_LINE('Name        : ' || p_name);
    DBMS_OUTPUT.PUT_LINE('DOB         : ' || TO_CHAR(p_dob, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Balance     : $' || TO_CHAR(p_balance, '99,999.00'));

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('AddNewCustomer',
                'Customer with ID ' || p_customer_id ||
                ' already exists. Insertion prevented.');
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Customer with ID ' || p_customer_id || ' already exists.');
        DBMS_OUTPUT.PUT_LINE('Insertion has been prevented to maintain data integrity.');

    WHEN e_invalid_balance THEN
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('AddNewCustomer',
                'Invalid balance: $' || p_balance || ' for customer ' || p_name);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Balance cannot be negative.');

    WHEN OTHERS THEN
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('AddNewCustomer', 'Unexpected error: ' || SQLERRM);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Unexpected error - ' || SQLERRM);
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Add a new customer (should succeed)
EXEC AddNewCustomer(3, 'Michael Johnson', TO_DATE('1982-11-30', 'YYYY-MM-DD'), 5000);

-- Test 2: Duplicate customer ID (should fail gracefully)
EXEC AddNewCustomer(1, 'Duplicate John', TO_DATE('1995-01-01', 'YYYY-MM-DD'), 2000);

-- Test 3: Negative balance (should fail gracefully)
EXEC AddNewCustomer(4, 'Bad Balance', TO_DATE('2000-01-01', 'YYYY-MM-DD'), -500);

-- Check error log
SELECT * FROM ErrorLog WHERE ProcedureName = 'AddNewCustomer';
