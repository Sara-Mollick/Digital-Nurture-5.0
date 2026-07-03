-- ============================================================
-- Exercise 1: Control Structures
-- Scenario 1: Loan Interest Rate Discount for Senior Customers
-- ============================================================
-- Question: Write a PL/SQL block that loops through all customers,
-- checks their age, and if they are above 60, apply a 1% discount
-- to their current loan interest rates.
-- ============================================================

SET SERVEROUTPUT ON;

DECLARE
    v_age NUMBER;
    v_updated_count NUMBER := 0;

    -- Cursor to loop through all customers who have loans
    CURSOR c_customers IS
        SELECT c.CustomerID, c.Name, c.DOB
        FROM Customers c
        WHERE EXISTS (
            SELECT 1 FROM Loans l WHERE l.CustomerID = c.CustomerID
        );

BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Loan Interest Rate Discount for Senior Customers');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('');

    FOR rec IN c_customers LOOP
        -- Calculate the age of the customer
        v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, rec.DOB) / 12);

        DBMS_OUTPUT.PUT_LINE('Customer: ' || rec.Name || ' | Age: ' || v_age);

        IF v_age > 60 THEN
            -- Apply 1% discount to all loans of this customer
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = rec.CustomerID
              AND InterestRate > 1;  -- Ensure rate doesn't go below 1%

            v_updated_count := v_updated_count + SQL%ROWCOUNT;

            DBMS_OUTPUT.PUT_LINE('   --> Discount Applied! Interest rate reduced by 1%.');
        ELSE
            DBMS_OUTPUT.PUT_LINE('   --> No discount. Customer is ' || v_age || ' years old (must be > 60).');
        END IF;
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Total loans updated: ' || v_updated_count);
    DBMS_OUTPUT.PUT_LINE('============================================');

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
        ROLLBACK;
END;
/
