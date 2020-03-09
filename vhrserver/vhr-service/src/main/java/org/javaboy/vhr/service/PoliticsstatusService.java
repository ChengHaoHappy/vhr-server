package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.PoliticsstatusMapper;
import org.javaboy.vhr.model.Politicsstatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created By ChengHao On 2020/3/7
 */
@Service
public class PoliticsstatusService {
    @Resource
    PoliticsstatusMapper politicsstatusMapper;

    /**
     * 获取政治面貌
     * @return
     */
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
