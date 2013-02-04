package gr.kstamatis.nlg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class Query {

	public Map<String, Object> search(String query, String indexName, int pageNumber, File index){
		HashMap<String, Object> results = null;
		try {
			results = new HashMap<String, Object>();

			Directory indexDir = new SimpleFSDirectory(index);

			org.apache.lucene.search.Query q = new QueryParser (Version.LUCENE_41, indexName, new StandardAnalyzer(Version.LUCENE_41)).parse(query);

			int hitsPerPage = 10;
			IndexReader reader = IndexReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);
			//TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
			TopDocs hits2 = searcher.search(q, 100000/*, new Sort(new SortField("displayvalue", Type.STRING))*/);
			ScoreDoc[] hits = hits2.scoreDocs;// collector.topDocs().scoreDocs;

			ArrayList<Document> tmp = new ArrayList<Document>();

			if (hits.length>0){
				Paginator paginator = new Paginator();
				ArrayLocation arrayLocation = paginator.calculateArrayLocation(hits.length, pageNumber, 100);

				for (int i = arrayLocation.getStart() - 1; i < arrayLocation.getEnd(); i++) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					//System.out.println(d.get("record"));
					//System.out.println(d.get("v200"));

					//System.out.println(d.get("displayvalue"));
					//System.out.println(d.get("c001"));
					//System.out.println(d.get("tag"));

					tmp.add(d);
				}
			}

			results.put("results", tmp);
			results.put("totalCount", hits.length);
			//System.out.println(hits.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}

	public static void main( String[] args ) throws Exception {

		Directory indexDir = new SimpleFSDirectory(new File("index"));

		String querystr = "περιουσ*";
		org.apache.lucene.search.Query q = new QueryParser (Version.LUCENE_41, "vall", new StandardAnalyzer(Version.LUCENE_41)).parse(querystr);


		int hitsPerPage = 10;
		IndexReader reader = IndexReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);
		//TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		TopDocs hits2 = searcher.search(q, 100000);
		ScoreDoc[] hits = hits2.scoreDocs;// collector.topDocs().scoreDocs;

		Paginator paginator = new Paginator();
		ArrayLocation arrayLocation = paginator.calculateArrayLocation(hits.length, 1, 100);
		for (int i = arrayLocation.getStart() - 1; i < arrayLocation.getEnd(); i++) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			//System.out.println(d.get("record"));
			//System.out.println(d.get("v200"));

			//System.out.println(d.get("displayvalue"));
			System.out.println(d.get("c001"));
			//System.out.println(d.get("tag"));
		}

		System.out.println(hits.length);
	}
}
