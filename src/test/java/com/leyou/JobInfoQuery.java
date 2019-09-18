package com.leyou;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;


import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.util.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JobInfoQuery {
    @Test
    public void query() throws Exception {
        //1.指定索引文件存储的位置
        FSDirectory open = FSDirectory.open(new File("D:\\index"));
        //2.创建一个用来读取索引的对象
        DirectoryReader indexReader = DirectoryReader.open(open);
        //3.创建一个用来查询索引的对象 IndexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //使用term查询：指定查询的域名和关键字
      // Query query = new TermQuery(new Term("companyName", "凌宇"));//term查询
        //通配符查询
    //    WildcardQuery query = new WildcardQuery(new Term("companyName", "*宇*"));
//        模糊查询(容错查询)
//        FuzzyQuery query = new FuzzyQuery(new Term("companyName", "零宇"));
      //区间查询
       // Query query = NumericRangeQuery.newIntRange("salary",10000,20000,true,true);
       // Query  query = NumericRangeQuery.newIntRange("salary", 10000, 20000, true, true);
        //组合查询
       // BooleanQuery query = new BooleanQuery();
        //查询名称中带宇的，并且工资范围是10000到20000之间
//        WildcardQuery query1 = new WildcardQuery(new Term("companyName", "*宇*"));
//        NumericRangeQuery<Integer> query2 = NumericRangeQuery.newIntRange("salary", 100000, 200000, true, true);
//        query.add(query1,BooleanClause.Occur.MUST);
//        query.add(query2,BooleanClause.Occur.MUST);
        //分词查询
        QueryParser queryParser=new QueryParser("companyName",new IKAnalyzer());
        Query query = queryParser.parse("北京东进航空科技股份有限公司");
       //指定多个域名分词查询
        /*QueryParser parser=new MultiFieldQueryParser(new String[]{"companyName","companyAddr"},new IKAnalyzer());
        Query query = parser.parse("航空科技");*/

        TopDocs topDocs = indexSearcher.search(query, 100);//第二个参数：最多显示多少个


        int totalHits = topDocs.totalHits;//查询总数量
        System.out.println("符合条件的总数"+totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;//获取命中的文档 存储的是文档的id
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            //根据id查询文档
            Document document = indexSearcher.doc(doc);
            System.out.println("id:"+document.get("id"));
            System.out.println("companyName:"+document.get("companyName"));
            System.out.println("companyAddr:"+document.get("companyAddr"));
            System.out.println("salary:"+document.get("salary"));
            System.out.println("url:"+document.get("url"));
            System.out.println("********************************************************");
        }
    }
}
