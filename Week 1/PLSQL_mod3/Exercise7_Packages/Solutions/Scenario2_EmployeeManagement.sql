-- ============================================================
-- Exercise 7: Packages
-- Scenario 2: Employee Management Package
-- ============================================================
-- Question: Write a package EmployeeManagement with procedures to
-- hire new employees, update employee details, and a function to
-- calculate annual salary.
-- ============================================================

SET SERVEROUTPUT ON;

-- ============================================================
-- Package Specification
-- ============================================================
CREATE OR REPLACE PACKAGE EmployeeManagement
AS
    -- Procedure to hire a new employee
    PROCEDURE HireEmployee (
        p_id         IN NUMBER,
        p_name       IN VARCHAR2,
        p_position   IN VARCHAR2,
        p_salary     IN NUMBER,
        p_department IN VARCHAR2
    );

    -- Procedure to update employee details
    PROCEDURE UpdateEmployeeDetails (
        p_id         IN NUMBER,
        p_name       IN VARCHAR2,
        p_position   IN VARCHAR2,
        p_department IN VARCHAR2
    );

    -- Function to calculate annual salary
    FUNCTION CalculateAnnualSalary (
        p_id IN NUMBER
    ) RETURN NUMBER;

END EmployeeManagement;
/

-- ============================================================
-- Package Body
-- ============================================================
CREATE OR REPLACE PACKAGE BODY EmployeeManagement
AS

    -- --------------------------------------------------------
    -- Procedure: HireEmployee
    -- --------------------------------------------------------
    PROCEDURE HireEmployee (
        p_id         IN NUMBER,
        p_name       IN VARCHAR2,
        p_position   IN VARCHAR2,
        p_salary     IN NUMBER,
        p_department IN VARCHAR2
    )
    AS
    BEGIN
        INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (p_id, p_name, p_position, p_salary, p_department, SYSDATE);

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Employee hired successfully.');
        DBMS_OUTPUT.PUT_LINE('  ID        : ' || p_id);
        DBMS_OUTPUT.PUT_LINE('  Name      : ' || p_name);
        DBMS_OUTPUT.PUT_LINE('  Position  : ' || p_position);
        DBMS_OUTPUT.PUT_LINE('  Salary    : $' || TO_CHAR(p_salary, '99,999.00'));
        DBMS_OUTPUT.PUT_LINE('  Department: ' || p_department);
        DBMS_OUTPUT.PUT_LINE('  Hire Date : ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY'));

    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Employee with ID ' || p_id || ' already exists.');

        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
    END HireEmployee;

    -- --------------------------------------------------------
    -- Procedure: UpdateEmployeeDetails
    -- --------------------------------------------------------
    PROCEDURE UpdateEmployeeDetails (
        p_id         IN NUMBER,
        p_name       IN VARCHAR2,
        p_position   IN VARCHAR2,
        p_department IN VARCHAR2
    )
    AS
    BEGIN
        UPDATE Employees
        SET Name = p_name,
            Position = p_position,
            Department = p_department
        WHERE EmployeeID = p_id;

        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Employee with ID ' || p_id || ' not found.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Employee details updated successfully.');
            DBMS_OUTPUT.PUT_LINE('  ID        : ' || p_id);
            DBMS_OUTPUT.PUT_LINE('  Name      : ' || p_name);
            DBMS_OUTPUT.PUT_LINE('  Position  : ' || p_position);
            DBMS_OUTPUT.PUT_LINE('  Department: ' || p_department);
        END IF;

    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
    END UpdateEmployeeDetails;

    -- --------------------------------------------------------
    -- Function: CalculateAnnualSalary
    -- --------------------------------------------------------
    FUNCTION CalculateAnnualSalary (
        p_id IN NUMBER
    ) RETURN NUMBER
    AS
        v_monthly_salary NUMBER;
        v_annual_salary  NUMBER;
    BEGIN
        SELECT Salary INTO v_monthly_salary
        FROM Employees
        WHERE EmployeeID = p_id;

        -- Annual salary = monthly salary * 12
        v_annual_salary := v_monthly_salary * 12;

        RETURN v_annual_salary;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Employee with ID ' || p_id || ' not found.');
            RETURN -1;

        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
            RETURN -1;
    END CalculateAnnualSalary;

END EmployeeManagement;
/

-- ============================================================
-- Test Cases
-- ============================================================

-- Test 1: Hire a new employee
BEGIN
    EmployeeManagement.HireEmployee(3, 'Carol White', 'Analyst', 55000, 'Finance');
END;
/

-- Test 2: Update employee details
BEGIN
    EmployeeManagement.UpdateEmployeeDetails(3, 'Carol W. White', 'Senior Analyst', 'Finance');
END;
/

-- Test 3: Calculate annual salary
DECLARE
    v_annual NUMBER;
BEGIN
    v_annual := EmployeeManagement.CalculateAnnualSalary(1);
    DBMS_OUTPUT.PUT_LINE('Employee 1 Annual Salary: $' || TO_CHAR(v_annual, '999,999.00'));

    v_annual := EmployeeManagement.CalculateAnnualSalary(2);
    DBMS_OUTPUT.PUT_LINE('Employee 2 Annual Salary: $' || TO_CHAR(v_annual, '999,999.00'));

    v_annual := EmployeeManagement.CalculateAnnualSalary(999);
    DBMS_OUTPUT.PUT_LINE('Employee 999 Annual Salary: $' || v_annual);
END;
/

-- Test 4: Duplicate employee
BEGIN
    EmployeeManagement.HireEmployee(1, 'Duplicate', 'Test', 10000, 'Test');
END;
/

-- Verify
SELECT * FROM Employees;
