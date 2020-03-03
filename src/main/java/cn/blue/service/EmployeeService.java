package cn.blue.service;

import cn.blue.bean.Employee;

import java.util.List;

public interface EmployeeService {
    /**
     * 查询emp中所有信息
     * @return List<Employee>
     */
    List<Employee> getAll();

    /**
     * 员工信心保存
     * @param employee 员工
     * @return 是否
     */
    boolean saveEmp(Employee employee);

    /**
     * 检验员工用户名是否存在
     * @param empName 员工用户名
     * @return 真假
     */
    boolean checkName(String empName);

    /**
     * 根据员工id查找员工信息
     * @param empId 员工id
     * @return Employee
     */
    Employee getEmp(Integer empId);

    /**
     * 根据员工id修改员工信息
     * @param employee 员工id
     * @return Employee
     */
    boolean updateEmp(Employee employee);
}
