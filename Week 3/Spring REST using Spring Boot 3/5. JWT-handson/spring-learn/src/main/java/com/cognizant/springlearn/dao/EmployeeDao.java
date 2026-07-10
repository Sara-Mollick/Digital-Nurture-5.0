package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.Employee;
import com.cognizant.springlearn.exception.EmployeeNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {
    private static ArrayList<Employee> EMPLOYEE_LIST;

    @SuppressWarnings("unchecked")
    public EmployeeDao() {
        if (EMPLOYEE_LIST == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
            EMPLOYEE_LIST = (ArrayList<Employee>) context.getBean("employeeList", ArrayList.class);
        }
    }

    public ArrayList<Employee> getAllEmployees() {
        return EMPLOYEE_LIST;
    }

    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        boolean found = false;
        for (int i = 0; i < EMPLOYEE_LIST.size(); i++) {
            Employee emp = EMPLOYEE_LIST.get(i);
            if (emp.getId().equals(employee.getId())) {
                EMPLOYEE_LIST.set(i, employee);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new EmployeeNotFoundException();
        }
    }

    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        Employee employeeToDelete = null;
        for (Employee emp : EMPLOYEE_LIST) {
            if (emp.getId() == id) {
                employeeToDelete = emp;
                break;
            }
        }
        if (employeeToDelete != null) {
            EMPLOYEE_LIST.remove(employeeToDelete);
        } else {
            throw new EmployeeNotFoundException();
        }
    }
}
