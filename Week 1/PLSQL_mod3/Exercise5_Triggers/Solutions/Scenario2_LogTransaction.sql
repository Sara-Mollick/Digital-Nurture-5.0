-- ============================================================
-- Exercise 5: Triggers
-- Scenario 2: Audit Log for Transactions
-- ============================================================
-- Question: Write a trigger LogTransaction that inserts a record
-- into an AuditLog table whenever a transaction is inserted into
-- the Transactions table.
-- ============================================================

SET SERVEROUTPUT ON;

-- Step 1: Create the AuditLog table
BEGIN
    EXECUTE IMMEDIATE '
        CREATE TABLE AuditLog (
            AuditID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            TransactionID NUMBER,
            AccountID NUMBER,
            Amount NUMBER,
            TransactionType VARCHAR2(10),
            TransactionDate DATE,
            LogTimestamp DATE DEFAULT SYSDATE
        )';
    DBMS_OUTPUT.PUT_LINE('AuditLog table created successfully.');
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            DBMS_OUTPUT.PUT_LINE('AuditLog table already exists.');
        ELSE
            RAISE;
        END IF;
END;
/

-- Step 2: Create the trigger
CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (TransactionID, AccountID, Amount, TransactionType, TransactionDate)
    VALUES (:NEW.TransactionID, :NEW.AccountID, :NEW.Amount,
            :NEW.TransactionType, :NEW.TransactionDate);

    DBMS_OUTPUT.PUT_LINE('Audit Log: Transaction ' || :NEW.TransactionID ||
                         ' logged. Type: ' || :NEW.TransactionType ||
                         ', Amount: $' || :NEW.Amount);
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Insert a new transaction (trigger should fire)
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (3, 1, SYSDATE, 500, 'Deposit');
COMMIT;

-- Test 2: Insert another transaction
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (4, 2, SYSDATE, 150, 'Withdrawal');
COMMIT;

-- Verify audit log entries
SELECT * FROM AuditLog ORDER BY AuditID;

-- Verify transactions
SELECT * FROM Transactions ORDER BY TransactionID;
