package org.javaboy.vhr.mapper;

import org.javaboy.vhr.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepartmentByParentId(Integer parentId);

    void addDep(Department dep);

    void delDepById(Department dep);

    List<Department> getAllDepartmentsByParentId(int i);
}