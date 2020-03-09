package org.javaboy.vhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 当一个请求走完 FilterInvocationSecurityMetadataSource 中的 getAttributes 方法后，
 * 接下来就会来到  AccessDecisionManager 类中进行角色信息的比对
 *
 * @author ：HappyCheng
 * @date ：2019/10/22
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    /**
     * 判断当前用户是否具备当前请求URL所需的角色，如果不具备，则抛出异常，否则不做任何事情
     *
     * @param authentication 保存登录成功的用户信息
     * @param o              FilterInvocation 对象，可以获取当前请求的对象
     * @param collection     CustomerMatadataSource的 getAttributes方法的返回值 ，当前请求 URL所需要的角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            //当前请求所需要的权限
            String needRole = configAttribute.getAttribute();
            System.out.println("needRole = " + needRole);
            if ("ROLE_LOGIN".equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {  //匿名访问
                    throw new BadCredentialsException("未登录");
                } else {  //登录访问
                    return;
                }
            }

            //当前用户所具有的权限（此处保存的是hr所具有的角色信息）
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) { //如果当前用户具备访问请求所需要的权限，则啥也不做
                    return;
                }
            }
        }

        throw new AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
