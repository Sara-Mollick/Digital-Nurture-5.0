package com.cognizant.springlearn.service;

import com.cognizant.springlearn.Department;
import com.cognizant.springlearn.dao.DepartmentDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }
}
