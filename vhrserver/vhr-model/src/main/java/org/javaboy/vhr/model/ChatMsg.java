package org.javaboy.vhr.model;

import lombok.Data;

import java.util.Date;

/**
 * Created By ChengHao On 2020/3/11
 */
@Data
public class ChatMsg {
    private String from;
    private String to;
    private String content;
    private Date date;
    private String fromNickname;
}
