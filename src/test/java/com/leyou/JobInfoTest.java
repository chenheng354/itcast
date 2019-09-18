package com.leyou;

import com.leyou.domain.JobInfo;
import com.leyou.service.JobInfoServiceImpl;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobInfoTest {
    @Autowired
    private JobInfoServiceImpl jobInfoService;
    @Test
    public void test() throws Exception {
        //1.指定索引文件存储的位置
       Directory directory = FSDirectory.open(new File("D:\\index"));
        //2.配置版本和分词器
       // StandardAnalyzer analyzer = new StandardAnalyzer();
        IKAnalyzer analyzer = new IKAnalyzer();//IK分词器
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        //3.创建一个用来创建索引的对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.deleteAll();//先删除索引
        //4.获取原数据
        List<JobInfo> all = jobInfoService.findAll();
        //有多少数据就应该构建多少lucene文档对象documents
        for (JobInfo jobInfo : all) {
            Document document = new Document();
            //域名 值 源数据是否存储
            document.add(new LongField("id",jobInfo.getId(), Field.Store.YES));
            //TextField是需要分词时使用，StringField是分词时不使用
            document.add(new TextField("companyName",jobInfo.getCompanyName(), Field.Store.YES));
            document.add(new TextField("companyAddr",jobInfo.getCompanyAddr(), Field.Store.YES));
            document.add(new TextField("jobName",jobInfo.getJobName(), Field.Store.YES));
            document.add(new TextField("jobAddr",jobInfo.getJobAddr(), Field.Store.YES));
            document.add(new IntField("salary",jobInfo.getSalary(), Field.Store.YES));
            document.add(new StringField("url",jobInfo.getUrl(), Field.Store.YES));
            indexWriter.addDocument(document);
        }
        //关闭资源
        indexWriter.close();
    } @Test
    public void test1() throws Exception {
        //1.指定索引文件存储的位置
       Directory directory = FSDirectory.open(new File("D:\\index"));
        //2.配置版本和分词器
       // StandardAnalyzer analyzer = new StandardAnalyzer();
        IKAnalyzer analyzer = new IKAnalyzer();//IK分词器
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        //3.创建一个用来创建索引的对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            Document document = new Document();
            //域名 值 源数据是否存储
            document.add(new LongField("id",123456, Field.Store.YES));
            //TextField是需要分词时使用，StringField是分词时不使用
        TextField textField = new TextField("companyName", "qqqqq北京东进航空科技股份有限公司", Field.Store.YES);
        textField.setBoost(100);
        document.add(textField);
            indexWriter.addDocument(document);
        //关闭资源
        indexWriter.close();
    }
}
