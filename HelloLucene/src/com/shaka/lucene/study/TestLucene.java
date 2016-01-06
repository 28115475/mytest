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
		//����һ���ʷ�������
		Analyzer analyzer = new StandardAnalyzer();
		//ȷ�������ļ��洢��λ�ã�Lucene�ṩ���������ַ�ʽ��
		Directory directory = null;
		try {
			directory = FSDirectory.open(Paths.get("E:/lucene_test/indexfile"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//����IndexWriter�����������ļ���д�롣
		/*IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = null;
		try {
			iwriter = new IndexWriter(directory, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//������ȡ�����������Ĵ洢��
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
        	//�򿪴洢λ��
			ireader = DirectoryReader.open(directory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //����������
        IndexSearcher isearcher = new IndexSearcher(ireader);
        
        //����SQL�����йؼ��ֲ�ѯ
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
