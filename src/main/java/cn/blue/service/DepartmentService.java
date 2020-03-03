package cn.blue.service;

import cn.blue.bean.Department;

import java.util.List;

public interface DepartmentService {
    /**
     * @return 查询所有部门信息
     */
    List<Department> getDepts();
}
