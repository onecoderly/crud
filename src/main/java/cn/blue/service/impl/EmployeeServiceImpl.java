package cn.blue.service.impl;

import cn.blue.bean.Employee;
import cn.blue.bean.EmployeeExample;
import cn.blue.dao.EmployeeMapper;
import cn.blue.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Blue
 * Date: 2019/12/17
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    EmployeeMapper employeeMapper;

    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    @Override
    public boolean saveEmp(Employee employee) {
        return employeeMapper.insertSelective(employee) > 0;
    }

    @Override
    public boolean checkName(String empName) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        //查看根据员工用户名查找，是否有返回记录数
        return count == 0;
    }

    @Override
    public Employee getEmp(Integer empId) {
        return employeeMapper.selectByPrimaryKey(empId);
    }

    @Override
    public boolean updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKey(employee) > 0;
    }
}
