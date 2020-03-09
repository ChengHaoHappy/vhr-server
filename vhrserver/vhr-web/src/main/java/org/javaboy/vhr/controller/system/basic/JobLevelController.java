package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.model.JobLevel;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：HappyCheng
 * @date ：2019/10/25
 */
@RestController
@RequestMapping("/system/basic/jobLevel")
public class JobLevelController {

    @Autowired
    JobLevelService jobLevelService;

    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jObLevel){
        if(jobLevelService.addJobLevel(jObLevel)==1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel jObLevel){
        if (jobLevelService.updateJobLevel(jObLevel)==1) {
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable("id") Integer id){
        if (jobLevelService.deleteJobLevelById(id)==1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevels(Integer[] ids){
        if (jobLevelService.delJobLevels(ids)==ids.length) {
            return RespBean.ok("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }

    @PostMapping("/test")
    @Transactional
    public void insertTest(){
        jobLevelService.addTwo();
    }
}
