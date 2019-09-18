package com.leyou.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "job_info")//指定数据库名
public class JobInfo {
    @Id //指定主键
    private Long id;
    private String  companyName;
    private String  companyAddr;
    private String  jobName;
    private String  jobAddr;
    @Column(name = "salary_min")//指定行
    private Integer  salary;
    private String  url;

}
