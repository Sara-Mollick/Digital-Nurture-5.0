-- ============================================================
-- Exercise 7: Packages
-- Scenario 1: Customer Management Package
-- ============================================================
-- Question: Create a package CustomerManagement with procedures
-- for adding a new customer, updating customer details, and a
-- function to get customer balance.
-- ============================================================

SET SERVEROUTPUT ON;

-- ============================================================
-- Package Specification
-- ============================================================
CREATE OR REPLACE PACKAGE CustomerManagement
AS
    -- Procedure to add a new customer
    PROCEDURE AddCustomer (
        p_id      IN NUMBER,
        p_name    IN VARCHAR2,
        p_dob     IN DATE,
        p_balance IN NUMBER
    );

    -- Procedure to update customer details
    PROCEDURE UpdateCustomerDetails (
        p_id   IN NUMBER,
        p_name IN VARCHAR2,
        p_dob  IN DATE
    );

    -- Function to get customer balance
    FUNCTION GetCustomerBalance (
        p_id IN NUMBER
    ) RETURN NUMBER;

END CustomerManagement;
/

-- ============================================================
-- Package Body
-- ============================================================
CREATE OR REPLACE PACKAGE BODY CustomerManagement
AS

    -- --------------------------------------------------------
    -- Procedure: AddCustomer
    -- --------------------------------------------------------
    PROCEDURE AddCustomer (
        p_id      IN NUMBER,
        p_name    IN VARCHAR2,
        p_dob     IN DATE,
        p_balance IN NUMBER
    )
    AS
    BEGIN
        INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
        VALUES (p_id, p_name, p_dob, p_balance, SYSDATE);

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Customer added successfully.');
        DBMS_OUTPUT.PUT_LINE('  ID     : ' || p_id);
        DBMS_OUTPUT.PUT_LINE('  Name   : ' || p_name);
        DBMS_OUTPUT.PUT_LINE('  DOB    : ' || TO_CHAR(p_dob, 'DD-MON-YYYY'));
        DBMS_OUTPUT.PUT_LINE('  Balance: $' || TO_CHAR(p_balance, '99,999.00'));

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Customer with ID ' || p_id || ' already exists.');

        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
    END AddCustomer;

    -- --------------------------------------------------------
    -- Procedure: UpdateCustomerDetails
    -- --------------------------------------------------------
    PROCEDURE UpdateCustomerDetails (
        p_id   IN NUMBER,
        p_name IN VARCHAR2,
        p_dob  IN DATE
    )
    AS
    BEGIN
        UPDATE Customers
        SET Name = p_name,
            DOB = p_dob,
            LastModified = SYSDATE
        WHERE CustomerID = p_id;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Customer with ID ' || p_id || ' not found.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Customer updated successfully.');
            DBMS_OUTPUT.PUT_LINE('  ID      : ' || p_id);
            DBMS_OUTPUT.PUT_LINE('  New Name: ' || p_name);
            DBMS_OUTPUT.PUT_LINE('  New DOB : ' || TO_CHAR(p_dob, 'DD-MON-YYYY'));
        END IF;

    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
    END UpdateCustomerDetails;

    -- --------------------------------------------------------
    -- Function: GetCustomerBalance
    -- --------------------------------------------------------
    FUNCTION GetCustomerBalance (
        p_id IN NUMBER
    ) RETURN NUMBER
    AS
        v_balance NUMBER;
    BEGIN
        SELECT Balance INTO v_balance
        FROM Customers
        WHERE CustomerID = p_id;

        RETURN v_balance;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Customer with ID ' || p_id || ' not found.');
            RETURN -1;

        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
            RETURN -1;
    END GetCustomerBalance;

END CustomerManagement;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Add a new customer
BEGIN
    CustomerManagement.AddCustomer(10, 'Test Customer', TO_DATE('1995-03-15', 'YYYY-MM-DD'), 3000);
END;
/

-- Test 2: Update customer details
BEGIN
    CustomerManagement.UpdateCustomerDetails(10, 'Updated Customer', TO_DATE('1995-04-20', 'YYYY-MM-DD'));
END;
/

-- Test 3: Get customer balance
DECLARE
    v_balance NUMBER;
BEGIN
    v_balance := CustomerManagement.GetCustomerBalance(1);
    DBMS_OUTPUT.PUT_LINE('Customer 1 Balance: $' || TO_CHAR(v_balance, '99,999.00'));

    v_balance := CustomerManagement.GetCustomerBalance(999);
    DBMS_OUTPUT.PUT_LINE('Customer 999 Balance: $' || v_balance);
END;
/

-- Test 4: Duplicate customer
BEGIN
    CustomerManagement.AddCustomer(1, 'Duplicate', SYSDATE, 0);
END;
/

-- Verify
SELECT * FROM Customers;
