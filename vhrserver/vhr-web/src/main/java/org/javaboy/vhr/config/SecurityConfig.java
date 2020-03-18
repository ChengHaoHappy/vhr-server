package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Spring Security 配置
 * @author ：HappyCheng
 * @date ：2019/10/4
 * @Description 首先在usernamePasswordAuthenticationFilter中来拦截登录请求，并调用AuthenticationManager
 * AuthenticationManager调用Provider，provider调用userDetaisService来根据username获取真实的数据库信息
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;

    @Autowired
    CustomerMatadataSource customerMatadataSource;

    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    @Autowired
    VerificationCodeFilter verificationCodeFilter;
    
    @Bean
    PasswordEncoder passwordEncoder(){  //密码加密
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);  //验证
    }

    @Override
    public void configure(WebSecurity web) throws Exception {  //访问/login 不经过spring security 拦截
        web.ignoring().antMatchers("/login","/css/**","/js/**","/index.html","/img/**","/fonts/**","/favicon.ico","/verifyCode");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
//                .anyRequest().authenticated() //所有的请求都需要认证
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(customerMatadataSource);
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")//配置的登录页面
                .loginProcessingUrl("/doLogin")//登录接口
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override  //Authentication 保存登录成功的用户信息
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        Hr hr =(Hr) authentication.getPrincipal(); //获取登录成功的Hr对象
                        hr.setPassword(null); //不返回 Password
                        RespBean ok =RespBean.ok("登录成功",hr); //
                        String s = new ObjectMapper().writeValueAsString(ok);  //把对象转变成字符串
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        //使客户端浏览器，区分不同种类的数据，并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        RespBean respBean = RespBean.error("error");
                        if (e instanceof LockedException) {
                            respBean.setMsg("账户被锁定，请联系系统管理员");
                        }else if( e instanceof DisabledException){
                            respBean.setMsg("账户被禁用，请联系管理员");
                        }else if(e instanceof CredentialsExpiredException){
                            respBean.setMsg("密码过期，请联系管理员");
                        }else if(e instanceof AccountExpiredException){
                            respBean.setMsg("账号过期，请联系管理员");
                        }else if(e instanceof BadCredentialsException){
                            respBean.setMsg("用户名或密码输入错误，请重新输入！");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()//表示和登录相关的接口都不需要认证即可访问
                .and()
                .logout()//注销默认接口（/logout),可以通过 logoutUrl 自定义
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling()
                //没有认证，即直接访问内部地址，在这里处理结果，不重定向
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
                    throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.setStatus(401);  //尚未登录，请登录
                PrintWriter out = httpServletResponse.getWriter();
                RespBean respBean = RespBean.error("访问失败");
                if (e instanceof InsufficientAuthenticationException) {
                    respBean.setMsg("请求失败，请联系管理员");
                }
                out.write(new ObjectMapper().writeValueAsString(respBean));
                out.flush();
                out.close();
            }
        });
    }
}
