-- ============================================================
-- Exercise 3: Stored Procedures
-- Scenario 2: Employee Bonus Scheme by Department
-- ============================================================
-- Question: Write a stored procedure UpdateEmployeeBonus that
-- updates the salary of employees in a given department by adding
-- a bonus percentage passed as a parameter.
-- ============================================================

SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department       IN VARCHAR2,
    p_bonus_percentage IN NUMBER
)
AS
    CURSOR c_employees IS
        SELECT EmployeeID, Name, Salary
        FROM Employees
        WHERE Department = p_department;

    v_new_salary NUMBER;
    v_bonus      NUMBER;
    v_count      NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('Employee Bonus Update Report');
    DBMS_OUTPUT.PUT_LINE('Department: ' || p_department);
    DBMS_OUTPUT.PUT_LINE('Bonus Percentage: ' || p_bonus_percentage || '%');
    DBMS_OUTPUT.PUT_LINE('============================================');
    DBMS_OUTPUT.PUT_LINE('');

    FOR rec IN c_employees LOOP
        -- Calculate bonus and new salary
        v_bonus := rec.Salary * p_bonus_percentage / 100;
        v_new_salary := rec.Salary + v_bonus;

        -- Update the salary
        UPDATE Employees
        SET Salary = v_new_salary
        WHERE EmployeeID = rec.EmployeeID;

        v_count := v_count + 1;

        DBMS_OUTPUT.PUT_LINE('Employee    : ' || rec.Name || ' (ID: ' || rec.EmployeeID || ')');
        DBMS_OUTPUT.PUT_LINE('Old Salary  : $' || TO_CHAR(rec.Salary, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('Bonus Amount: $' || TO_CHAR(v_bonus, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('New Salary  : $' || TO_CHAR(v_new_salary, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('--------------------------------------------');
    END LOOP;

    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No employees found in department: ' || p_department);
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Total employees updated: ' || v_count);
    END IF;

    DBMS_OUTPUT.PUT_LINE('============================================');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
        ROLLBACK;
END;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Apply 10% bonus to IT department
EXEC UpdateEmployeeBonus('IT', 10);

-- Test 2: Apply 15% bonus to HR department
EXEC UpdateEmployeeBonus('HR', 15);

-- Test 3: Non-existent department
EXEC UpdateEmployeeBonus('Finance', 5);

-- Verify updated salaries
SELECT EmployeeID, Name, Position, Salary, Department FROM Employees;
