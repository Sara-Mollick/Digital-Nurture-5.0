-- ============================================================
-- Exercise 1: Control Structures
-- Scenario 2: VIP Status Based on Balance
-- ============================================================
-- Question: Write a PL/SQL block that iterates through all
-- customers and sets a flag IsVIP to TRUE for those with a
-- balance over $10,000.
-- ============================================================

SET SERVEROUTPUT ON;

-- Step 1: Add IsVIP column if it doesn't exist
DECLARE
    v_col_exists NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO v_col_exists
    FROM USER_TAB_COLUMNS
    WHERE TABLE_NAME = 'CUSTOMERS'
      AND COLUMN_NAME = 'ISVIP';

    IF v_col_exists = 0 THEN
        EXECUTE IMMEDIATE 'ALTER TABLE Customers ADD IsVIP VARCHAR2(5) DEFAULT ''FALSE''';
        DBMS_OUTPUT.PUT_LINE('Column IsVIP added to Customers table.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Column IsVIP already exists.');
    END IF;
END;
/

-- Step 2: Iterate through customers and set VIP status
DECLARE
    CURSOR c_customers IS
        SELECT CustomerID, Name, Balance
        FROM Customers;

    v_vip_count NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('VIP Status Update Report');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('');

    FOR rec IN c_customers LOOP
        IF rec.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE CustomerID = rec.CustomerID;

            v_vip_count := v_vip_count + 1;

            DBMS_OUTPUT.PUT_LINE('Customer: ' || rec.Name ||
                                 ' | Balance: $' || TO_CHAR(rec.Balance, '99,999.00') ||
                                 ' | Status: VIP');
        ELSE
            UPDATE Customers
            SET IsVIP = 'FALSE'
            WHERE CustomerID = rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE('Customer: ' || rec.Name ||
                                 ' | Balance: $' || TO_CHAR(rec.Balance, '99,999.00') ||
                                 ' | Status: Regular');
        END IF;
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Total VIP customers: ' || v_vip_count);
    DBMS_OUTPUT.PUT_LINE('============================================');

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
        ROLLBACK;
END;
/
