package org.javaboy.vhr.model;


import lombok.Data;

import java.util.List;

/**
 * 分页对象
 * Created By ChengHao On 2020/3/7
 */
@Data
public class RespPageBean {
    private Long total;
    private List<?> data;
}
