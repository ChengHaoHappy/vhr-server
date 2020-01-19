package org.javaboy.vhr.Controller.system.basic;

import org.javaboy.vhr.model.Department;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：HappyCheng
 * @date ：2019/10/29
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartmentByParentId(-1);
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep){
        departmentService.addDep(dep);
        if (dep.getResult()==1) {
            return RespBean.ok("添加成功",dep);
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBean delDep(@PathVariable Integer id){
        Department dep =new Department();
        dep.setId(id);
        departmentService.delDepById(dep);
        if (dep.getResult()==-2) {
            return RespBean.error("该部分下有子部门");
        }else if(dep.getResult()==-1){
            return RespBean.error("该部门下有员工");
        }else if(dep.getResult()==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
