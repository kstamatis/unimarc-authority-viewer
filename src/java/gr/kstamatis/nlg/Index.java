package gr.kstamatis.nlg;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.marc4j.*;
import org.marc4j.converter.CharConverter;
import org.marc4j.converter.impl.AnselToUnicode;
import org.marc4j.lucene.MarcIndexWriter;
import org.marc4j.lucene.util.RecordUtils;
import org.marc4j.marc.*;

public class Index {

	static final File INDEX_DIR = new File( "index" );

	public static void main( String[] args ) throws Exception {

		(new Index()).doIndex();

	}

	private void doIndex() throws FileNotFoundException, IOException{
		InputStream in     = new FileInputStream( "input/records.xml" );
		MarcReader reader  = new MarcXmlReader( in );

		Directory indexDir = new SimpleFSDirectory(INDEX_DIR);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, new StandardAnalyzer(Version.LUCENE_41));
		IndexWriter indexWriter = new IndexWriter(indexDir, config);
		//MarcIndexWriter marcWriter = new MarcIndexWriter(indexWriter);

		int i = 1;
		while ( reader.hasNext() ) {

			// initialize
			Record record     = reader.next();
			
			Document doc = new Document();
			Field.Store storeYes = Store.YES;
	        Field.Store storeNo = Store.NO;
			
	        //add id
	        String c001 = getControlField(record, "001");
	        if (c001 == null) continue;
	        doc.add(new TextField("c001", c001, storeYes));
	        
			//add record
			//byte[] bytes = RecordUtils.marshal(record, null, "UTF8");
	        //doc.add(new TextField("record", new String(bytes), storeYes));
	        
			//add fatafields
	        addDataField(doc, record, "200", storeYes);
	        addDataField(doc, record, "210", storeYes);
	        addDataField(doc, record, "215", storeYes);
	        addDataField(doc, record, "220", storeYes);
	        addDataField(doc, record, "250", storeYes);
	        addDataField(doc, record, "230", storeYes);
	        addDataField(doc, record, "235", storeYes);
	        addDataField(doc, record, "240", storeYes);
	        addDataField(doc, record, "225", storeYes);
			
			indexWriter.addDocument(doc);
			
			i++; 
			
			if (i%1000==0)
				System.out.println(i);
		}

		indexWriter.close();
	}
	
	private void addDataField(Document doc, Record record, String tag, Store store) throws IOException {
       
		List varFields = record.getVariableFields(tag);
        if (varFields.size() > 0) {
            Iterator it = varFields.iterator();
            while (it.hasNext()) {
                DataField df = (DataField) it.next();
                String result = "";
                //char[] codes = code.toCharArray();
                //for (int i = 0; i < codes.length; i++) {
                    List subfields = df.getSubfields(/*codes[i]*/);
                    Iterator si = subfields.iterator();
                    while (si.hasNext()) {
                        Subfield subfield = (Subfield) si.next();
                        String data = getDataElement(subfield.getData());
                        
                        result = result + "<span class=\"code\">$"+subfield.getCode()+"</span>"+data+" ";
                        
                        doc.add(new TextField("v"+tag, getForIndex(data), store));
                        doc.add(new TextField("vall", getForIndex(data), store));
                    }
                //}
                doc.add(new TextField("displayvalue", result, store));
                doc.add(new TextField("tag", tag, store));
            }
        }
    }
	
	private String getControlField(org.marc4j.marc.Record record, String tag) {

		List varFields = record.getVariableFields(tag);
		if (varFields.size() > 0) {
			Iterator it = varFields.iterator();
			while (it.hasNext()) {
				String result = "";
				ControlField cf = (ControlField) it.next();

				return cf.getData();
			}
		}
		return null;
	}
	private String getDataElement(String data) {
		String dataElement = null;
		try {
			byte[] bytes = data.getBytes("UTF-8");
			dataElement = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataElement;
	}
	
	private String getForIndex(String input){
		String result = input.toLowerCase();
		result = result.replace("ά", "α");
		result = result.replace("έ", "ε");
		result = result.replace("ό", "ο");
		result = result.replace("ί", "ι");
		result = result.replace("ύ", "υ");
		result = result.replace("ή", "η");
		result = result.replace("ώ", "ω");
		result = result.replace("ϊ", "ι");
		result = result.replace("ϋ", "υ");
		result = result.replace("́", "");
		//result = result.replace("ι", "");
		
		//System.out.println(result);
		
		return result;
		
	}
	
}
