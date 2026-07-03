-- ============================================================
-- Exercise 5: Triggers
-- Scenario 1: Auto-Update LastModified on Customer Update
-- ============================================================
-- Question: Write a trigger UpdateCustomerLastModified that updates
-- the LastModified column of the Customers table to the current date
-- whenever a customer's record is updated.
-- ============================================================

SET SERVEROUTPUT ON;

CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON Customers
FOR EACH ROW
BEGIN
    :NEW.LastModified := SYSDATE;

    DBMS_OUTPUT.PUT_LINE('Trigger fired: LastModified updated for Customer ID ' ||
                         :NEW.CustomerID || ' (' || :NEW.Name || ')');
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Show current data before update
SELECT CustomerID, Name, Balance, LastModified FROM Customers;

-- Test 1: Update a customer's balance
UPDATE Customers
SET Balance = 2000
WHERE CustomerID = 1;
COMMIT;

-- Verify LastModified was automatically updated
SELECT CustomerID, Name, Balance, LastModified FROM Customers WHERE CustomerID = 1;

-- Test 2: Update customer name
UPDATE Customers
SET Name = 'John D. Doe'
WHERE CustomerID = 1;
COMMIT;

-- Verify again
SELECT CustomerID, Name, Balance, LastModified FROM Customers WHERE CustomerID = 1;

-- Reset name back
UPDATE Customers SET Name = 'John Doe' WHERE CustomerID = 1;
COMMIT;
