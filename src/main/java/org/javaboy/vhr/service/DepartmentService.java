package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.DepartmentMapper;
import org.javaboy.vhr.model.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：HappyCheng
 * @date ：2019/10/29
 */
@Service
public class DepartmentService {
    @Resource
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartmentByParentId(Integer parentId) {
        return departmentMapper.getAllDepartmentByParentId(parentId);
    }

    public void addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }

    public void delDepById(Department dep) {
        departmentMapper.delDepById(dep);
    }
}
