package com.leyou.service;

import com.leyou.domain.JobInfo;
import com.leyou.mapper.JobInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Service
public class JobInfoServiceImpl {
    @Autowired
    private JobInfoMapper jobInfoMapper;
    public List<JobInfo> findAll(){
        List<JobInfo> jobInfos = jobInfoMapper.selectAll();
        return jobInfos;
    }
}
