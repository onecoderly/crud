package cn.blue.controller;

import cn.blue.bean.Department;
import cn.blue.service.DepartmentService;
import cn.blue.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Blue
 * Date: 2019/12/19
 * 处理和部门有关的请求
 */
@Controller
@RequestMapping("/depts")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 查出部门所有信息
     */
    @GetMapping
    @ResponseBody
    public Result getDepts(){
        List<Department> list = departmentService.getDepts();
        return Result.success().add("depts",list).setMsg("查询成功");
    }

}
