-- ============================================================
-- Exercise 2: Error Handling
-- Scenario 2: Update Employee Salary with Error Management
-- ============================================================
-- Question: Write a stored procedure UpdateSalary that increases
-- the salary of an employee by a given percentage. If the employee
-- ID does not exist, handle the exception and log an error message.
-- ============================================================

SET SERVEROUTPUT ON;

-- Ensure ErrorLog table exists (created in Scenario 1)
BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE ErrorLog (
        ErrorID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        ProcedureName VARCHAR2(100),
        ErrorMessage VARCHAR2(500),
        ErrorDate DATE DEFAULT SYSDATE
    )';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN NULL; -- Table already exists
        ELSE RAISE;
        END IF;
END;
/

-- Create the UpdateSalary procedure
CREATE OR REPLACE PROCEDURE UpdateSalary (
    p_employee_id IN NUMBER,
    p_percentage  IN NUMBER
)
AS
    v_current_salary NUMBER;
    v_new_salary     NUMBER;
    v_emp_name       VARCHAR2(100);
    e_invalid_percentage EXCEPTION;
BEGIN
    -- Validate percentage
    IF p_percentage <= 0 THEN
        RAISE e_invalid_percentage;
    END IF;

    -- Attempt to fetch current salary (raises NO_DATA_FOUND if not found)
    SELECT Salary, Name
    INTO v_current_salary, v_emp_name
    FROM Employees
    WHERE EmployeeID = p_employee_id;

    -- Calculate new salary
    v_new_salary := v_current_salary + (v_current_salary * p_percentage / 100);

    -- Update the salary
    UPDATE Employees
    SET Salary = v_new_salary
    WHERE EmployeeID = p_employee_id;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Salary updated successfully!');
    DBMS_OUTPUT.PUT_LINE('Employee     : ' || v_emp_name || ' (ID: ' || p_employee_id || ')');
    DBMS_OUTPUT.PUT_LINE('Old Salary   : $' || TO_CHAR(v_current_salary, '99,999.00'));
    DBMS_OUTPUT.PUT_LINE('Increase     : ' || p_percentage || '%');
    DBMS_OUTPUT.PUT_LINE('New Salary   : $' || TO_CHAR(v_new_salary, '99,999.00'));

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('UpdateSalary',
                'Employee ID ' || p_employee_id || ' does not exist.');
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Employee with ID ' || p_employee_id || ' not found.');

    WHEN e_invalid_percentage THEN
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('UpdateSalary',
                'Invalid percentage: ' || p_percentage || '%. Must be positive.');
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Percentage must be a positive number.');

    WHEN OTHERS THEN
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('UpdateSalary', 'Unexpected error: ' || SQLERRM);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Unexpected error - ' || SQLERRM);
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Valid salary update (10% increase for Employee 1)
EXEC UpdateSalary(1, 10);

-- Test 2: Non-existent employee
EXEC UpdateSalary(999, 10);

-- Test 3: Invalid percentage
EXEC UpdateSalary(1, -5);

-- Check error log
SELECT * FROM ErrorLog WHERE ProcedureName = 'UpdateSalary';
