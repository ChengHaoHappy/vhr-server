package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.Role;

import java.util.List;

@Mapper
public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    //插入所有的信息，如果传入的对象某一属性为空，则插入空，如果数据库中设置了默认值，默认值就失效了。
    int insert(Hr record);

    //插入含有数据的属性（不为空的属性），对于为空的属性，不予以处理，这样的话如果数据库中设置有默认值，就不会被空值覆盖了。
    int insertSelective(Hr record);


    Hr selectByPrimaryKey(Integer id);

    //通过ID更新所有设置了值的列（不为空的列）
    int updateByPrimaryKeySelective(Hr record);

    //通过ID更新除了text类型(数据库)的所有列
    int updateByPrimaryKey(Hr record);

    Hr loadUserByUsername(String username);

    List<Role> getHrRolesById(Integer id);
}