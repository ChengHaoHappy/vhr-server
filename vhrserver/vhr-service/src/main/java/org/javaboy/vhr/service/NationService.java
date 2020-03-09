package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.NationMapper;
import org.javaboy.vhr.model.Nation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created By ChengHao On 2020/3/7
 */
@Service
public class NationService {
    @Resource
    NationMapper nationMapper;

    /**
     * 获取所有的名族
     *
     * @return
     */
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
