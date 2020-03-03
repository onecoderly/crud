package cn.blue.mapper;


import cn.blue.bean.Department;
import cn.blue.bean.Employee;
import cn.blue.dao.DepartmentMapper;
import cn.blue.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author Blue
 * Date: 2019/12/17
 * 推荐spring的项目，就可以使用spring的单元测试，可以自动注入我们需要的组件
 * 1.导入Spring-Test模块
 * 2.@ContextConfiguration指定的spring配置文件位置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    /**
     * 测试DepartmentMapper
     */
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCurd(){
        //1.创建SpringIoc容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.从容器中获取mapper
        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
        System.err.println("值："+departmentMapper);
        //1.插入部门
        departmentMapper.insertSelective(new Department(null, "开发部"));
        departmentMapper.insertSelective(new Department(null, "测试部"));
        //2.生成员工数据，测试员工插入
        employeeMapper.insertSelective(new Employee(null,"jerry",1,"Jerry@qq.com",1));
        //3.批量插入员工数据
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 1000; i++) {
            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null, uid, 1, uid + "@qq.com", 1));
        }
        System.out.println("批量完成");
    }
}

