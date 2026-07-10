package com.cognizant.springlearn.service;

import com.cognizant.springlearn.Employee;
import com.cognizant.springlearn.dao.EmployeeDao;
import com.cognizant.springlearn.exception.EmployeeNotFoundException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Transactional(readOnly = true)
    public ArrayList<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Transactional
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        employeeDao.updateEmployee(employee);
    }

    @Transactional
    public void deleteEmployee(int id) throws EmployeeNotFoundException {
        employeeDao.deleteEmployee(id);
    }
}
