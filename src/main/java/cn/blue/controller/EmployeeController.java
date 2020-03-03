package cn.blue.controller;

import java.util.HashMap;
import java.util.Map;

import cn.blue.bean.Employee;
import cn.blue.service.EmployeeService;
import cn.blue.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author Blue
 * Date: 2019/12/17
 */
@Controller
public class EmployeeController {
    @Resource
    EmployeeService employeeService;

    /**
     * 跳转到emps页面
     * @return
     */
    @RequestMapping("/list")
    public String home() {
        return "emps";
    }

    /**
     * 获取所有员工信息
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Result getEmpsWithJson(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        //在查询之前只需调用，传入页码，每页数据量
        PageHelper.startPage(page, 5);
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装后的结果，只需要将pageInfo传给页面就好了
        //封装了详细的分页信息，包括我们查询出来的数据，传入连续显示的页数
        PageInfo pageInfo = new PageInfo(emps, 5);
        return Result.success().add("pageInfo", pageInfo).setMsg("查询成功");
    }

    /**
     * 根据员工id获取员工信息
     * @param empId 员工id
     * @return Emp员工信息
     */
    @ResponseBody
    @GetMapping("/emp/{empId}")
    public Result getEmp(@PathVariable("empId") Integer empId){
        try {
            Employee emp = employeeService.getEmp(empId);
            return Result.success().setMsg("查询成功").add("emp",emp);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500).setMsg("查询失败").add("error",e.getMessage());
        }
    }

    /**
     * 员工信息修改
     *
     * @param employee 员工信息
     */
    @PutMapping("/update")
    @ResponseBody
    public Result updateEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors
            ) {
                //错误信息，错误字段
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Result.error(500).setMsg("保存失败").add("error", map);
        } else {
            try {
                employeeService.updateEmp(employee);
                return Result.success().setMsg("保存成功");
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error(500).setMsg("保存失败").add("error", e.getMessage());
            }
        }
    }

    @RequestMapping("/find")
    public String getEmps(@RequestParam(value = "page", defaultValue = "1") Integer page, ModelMap map) {
        //在查询之前只需调用，传入页码，每页数据量
        PageHelper.startPage(page, 5);
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装后的结果，只需要将pageInfo传给页面就好了
        //封装了详细的分页信息，包括我们查询出来的数据，传入连续显示的页数
        PageInfo pageInfo = new PageInfo(emps, 5);
        map.put("pageInfo", pageInfo);
        return "list";
    }

    /**
     * 员工信息保存
     *
     * @param employee 员工信息
     */
    @PostMapping("/save")
    @ResponseBody
    public Result saveEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors
            ) {
                //错误信息，错误字段
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Result.error(500).setMsg("保存失败").add("error", map);
        } else {
            employeeService.saveEmp(employee);
            return Result.success().setMsg("保存成功");
        }
    }

    //检验员工用户名是否存在
    @PostMapping("/checkName")
    @ResponseBody
    public Result checkName(@RequestParam("empName") String empName) {
        String regx = "(^[a-z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})$";
        if(!empName.matches(regx)){
            return Result.error(500).setMsg("需要输入6-16位字母和数字或者2-5位中文的组合");
        }
        boolean b = employeeService.checkName(empName);
        if (b) {
            return Result.success().setMsg("此用户名可用");
        }
        return Result.error(500).setMsg("此用户名重复");
    }
}
