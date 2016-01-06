package com.shaka.lucene.study;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class TestLucene {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createIndexFile();
	}

	private static void createIndexFile() {
		//定义一个词法分析器
		Analyzer analyzer = new StandardAnalyzer();
		//确定索引文件存储的位置，Lucene提供给我们两种方式：
		Directory directory = null;
		try {
			directory = FSDirectory.open(Paths.get("E:/lucene_test/indexfile"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//创建IndexWriter，进行索引文件的写入。
		/*IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = null;
		try {
			iwriter = new IndexWriter(directory, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//内容提取，进行索引的存储。
		Document document = null;  
        File f = new File("E:/lucene_test/data");
		
        
        try {
        	for (File file : f.listFiles()) {  
                System.out.println("filename:" + file.getName());
                document = new Document();
                document.add(new LongField("modified", f.lastModified(), Field.Store.NO));
                document.add(new TextField("contents", new FileReader(file)));
                document.add(new StringField("path", file.toString(), Field.Store.YES));
                iwriter.addDocument(document);

            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iwriter != null) {
				try {
					iwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
        
        
        System.out.println("=======================================");
        ///////////////////////////
        DirectoryReader ireader = null;
        try {
        	//打开存储位置
			ireader = DirectoryReader.open(directory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //创建搜索器
        IndexSearcher isearcher = new IndexSearcher(ireader);
        
        //类似SQL，进行关键字查询
        QueryParser parser = new QueryParser("contents", analyzer);
        try {
        	  Query query = parser.parse("hello");
              ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
              for (int i = 0; i < hits.length; i++) {
                  Document hitDoc = isearcher.doc(hits[i].doc);
                  System.out.println("The Filename is:" + hitDoc.get("path"));
              }
              ireader.close();
              directory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
      
        
        
	}

	
}
