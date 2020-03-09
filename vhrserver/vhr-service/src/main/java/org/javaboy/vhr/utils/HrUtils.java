package org.javaboy.vhr.utils;

import org.javaboy.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created By ChengHao On 2020/2/18
 */
public class HrUtils {
    /**
     * 获取当前登录的对象
     * @return
     */
    public static Hr getCurrentHr() {
        return (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
