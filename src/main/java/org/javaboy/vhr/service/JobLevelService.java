package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.JobLevelMapper;
import org.javaboy.vhr.model.JobLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.EOFException;
import java.util.Date;
import java.util.List;

/**
 * @author ：HappyCheng
 * @date ：2019/10/25
 */
@Service
public class JobLevelService {

    @Resource
    JobLevelMapper jObLevelMapper;

    public List<JobLevel> getAllJobLevels() {
        return jObLevelMapper.selectAllJobLevels();
    }

    public Integer addJobLevel(JobLevel jObLevel) {
        jObLevel.setEnabled(true);
        jObLevel.setCreateDate(new Date());
        return jObLevelMapper.insertSelective(jObLevel);
    }

    public Integer updateJobLevel(JobLevel jObLevel) {
        return jObLevelMapper.updateByPrimaryKeySelective(jObLevel);
    }

    public Integer deleteJobLevelById(Integer id) {
        return jObLevelMapper.deleteByPrimaryKey(id);
    }


    public Integer delJobLevels(Integer[] ids) {
        return jObLevelMapper.delJobLevels(ids);
    }




    public void addTwo()  {
        JobLevel jobLevelOne = new JobLevel();
        jobLevelOne.setName("java工程师");
        jObLevelMapper.insertSelective(jobLevelOne);

        try {
            throw new EOFException();
        } catch (EOFException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }

    }
}
