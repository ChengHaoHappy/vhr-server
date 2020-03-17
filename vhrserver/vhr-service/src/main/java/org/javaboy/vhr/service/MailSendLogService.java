package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.MailSendLogMapper;
import org.javaboy.vhr.model.MailSendLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created By ChengHao On 2020/3/17
 */
@Service
public class MailSendLogService {
    @Autowired
    MailSendLogMapper mailSendLogMapper;

    /**
     * 更新消息状态
     * @param msgId
     * @param status
     * @return
     */
    public Integer updateMailSendLogStatus(String msgId, Integer status) {
        return mailSendLogMapper.updateMailSendLogStatus(msgId, status);
    }

    /**
     * 添加消息日志
     * @param mailSendLog
     * @return
     */
    public Integer insert(MailSendLog mailSendLog) {
        return mailSendLogMapper.insert(mailSendLog);
    }

    /**
     * 获取未投递成功的消息
     * @return
     */
    public List<MailSendLog> getMailSendLogsByStatus() {
        return mailSendLogMapper.getMailSendLogsByStatus();
    }

    /**
     * 更新重试次数
     * @param msgId
     * @param date
     * @return
     */
    public Integer updateCount(String msgId, Date date) {
        return mailSendLogMapper.updateCount(msgId,date);
    }
}
