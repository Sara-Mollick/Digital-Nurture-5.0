package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.Department;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDao {
    private static ArrayList<Department> DEPARTMENT_LIST;

    @SuppressWarnings("unchecked")
    public DepartmentDao() {
        if (DEPARTMENT_LIST == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
            DEPARTMENT_LIST = (ArrayList<Department>) context.getBean("departmentList", ArrayList.class);
        }
    }

    public List<Department> getAllDepartments() {
        return DEPARTMENT_LIST;
    }
}
