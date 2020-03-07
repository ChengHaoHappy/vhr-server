package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.RespBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：HappyCheng
 * @date ：2019/10/4
 */
@RestController
public class LoginController {

    @RequestMapping("/login")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录！");
    }
}
