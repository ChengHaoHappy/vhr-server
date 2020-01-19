package org.javaboy.vhr.config;

import org.javaboy.vhr.model.Menu;
import org.javaboy.vhr.model.Role;
import org.javaboy.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 这个类的作用，主要是根据用户传来的地址，分析用户请求需要哪些角色
 * @author ：HappyCheng
 * @date ：2019/10/22
 */
@Component
public class CustomerMatadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * @param o 是一个FilterInvocation，开发者可以从 FilterInvocation 中提取出当前请求的 URL
     * @return 当前请求 URL所需要的角色
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println("requestUrl = " + requestUrl);
        List<Menu> allMenus = menuService.getAllMenusWithRole();
        for (Menu menu : allMenus) {
            if(antPathMatcher.match(menu.getUrl(),requestUrl)&& menu.getRoles().size()>0){  //用户请求 URL 和菜单 URL 匹配
                List<Role> roles = menu.getRoles();  //可以访问该菜单的所有角色
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getName();  //存储访问该菜单的所有角色名
                }
                return SecurityConfig.createList(values);
            }
        }
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
