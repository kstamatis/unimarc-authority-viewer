import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.lucene.util.RecordUtils;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;
//import org.marc4j.marc.Record;

import gr.kstamatis.nlg.domain.Record;

class BootStrap {

    def init = { servletContext ->
		
		/*if (Record.count()==0){
			String path = servletContext.getRealPath("/resources/records.xml")
			InputStream input = new FileInputStream(path)
			MarcReader reader = new MarcXmlReader(input)
			
			int i = 1;
			while ( reader.hasNext() ) {
				org.marc4j.marc.Record record = reader.next()
				Record recordToSave = new Record()
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				RecordUtils.toXML(record, out)
				//byte[] bytes = RecordUtils.marshal(record, "UTF8")
				recordToSave.isoValue = out.toString() ;//new String(bytes, "UTF-8")
				
				recordToSave.recordId = getControlField(record, "001")
				
				if (!recordToSave.recordId) continue;
				
				String displayValue = getDataField(record, "200")
				recordToSave.tag = 200
				if (!displayValue){
					displayValue = getDataField(record, "250")
					recordToSave.tag = 250
				}
				if (!displayValue){
					displayValue = getDataField(record, "215")
					recordToSave.tag = 215
				}
				if (!displayValue){
					displayValue = getDataField(record, "210")
					recordToSave.tag = 210
				}
				if (!displayValue){
					displayValue = getDataField(record, "220")
					recordToSave.tag = 220
				}
				if (!displayValue){
					displayValue = getDataField(record, "230")
					recordToSave.tag = 230
				}
				if (!displayValue){
					displayValue = getDataField(record, "225")
					recordToSave.tag = 225
				}
				if (!displayValue){
					displayValue = getDataField(record, "240")
					recordToSave.tag = 240
				}
				if (!displayValue){
					displayValue = getDataField(record, "235")
					recordToSave.tag = 235
				}
				
				if (!displayValue){
					continue;
				}
				
				//System.out.println(displayValue);
				recordToSave.displayValue = displayValue
				
				if (recordToSave.validate())
					recordToSave.save()
				else
					println "no verify"
					
					
				i++;
				
				if (i%1000==0)
					println i
					
				//if (i==20)
				//	break;
			}
		}	*/
		
    }
	
    def destroy = {
    }

	private String getControlField(org.marc4j.marc.Record record, String tag) {

		List varFields = record.getVariableFields(tag)
		if (varFields.size() > 0) {
			Iterator it = varFields.iterator()
			while (it.hasNext()) {
				String result = ""
				ControlField cf = (ControlField) it.next()

				return cf.getData()
			}
		}
		return null;
	}

	private String getDataField(org.marc4j.marc.Record record, String tag) {

		List varFields = record.getVariableFields(tag)
		if (varFields.size() > 0) {
			Iterator it = varFields.iterator()
			while (it.hasNext()) {
				String result = ""
				DataField df = (DataField) it.next()
				List subfields = df.getSubfields()
				Iterator si = subfields.iterator()
				while (si.hasNext()) {
					Subfield subfield = (Subfield) si.next()
					String data = getDataElement(subfield.getData())
					result = result + "<span class=\"code\">\$"+subfield.getCode()+"</span>"+data+" "
				}
					
				return result.trim()
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
}
