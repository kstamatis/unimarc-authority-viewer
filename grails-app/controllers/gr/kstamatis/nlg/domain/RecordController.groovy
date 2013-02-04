package gr.kstamatis.nlg.domain

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.marc4j.MarcException;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.MarcXmlWriter;
import org.marc4j.lucene.util.RecordUtils;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataIntegrityViolationException
import org.w3c.dom.Document;

class RecordController implements ResourceLoaderAware{

	ResourceLoader resourceLoader

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list() {
		params.max = 100;//Math.min(params.max ? params.int('max') : 10, 100)

		if (params.tag){
			def c = Record.createCriteria()
			def recordList = c.list(
					max:params.max,
					offset: params.offset,
					sort: params.sort,
					order: params.order){
						eq('tag', params.int('tag'))
					}
			def totalRecords = recordList.totalCount
			[recordInstanceList: recordList, recordInstanceTotal: totalRecords, tag: params.tag]
		}
		else {
			[recordInstanceList: Record.list(params), recordInstanceTotal: Record.count()]
		}
	}

	/*def create() {
	 [recordInstance: new Record(params)]
	 }
	 def save() {
	 def recordInstance = new Record(params)
	 if (!recordInstance.save(flush: true)) {
	 render(view: "create", model: [recordInstance: recordInstance])
	 return
	 }
	 flash.message = message(code: 'default.created.message', args: [message(code: 'record.label', default: 'Record'), recordInstance.id])
	 redirect(action: "show", id: recordInstance.id)
	 }*/

	def show() {
		def recordInstance = Record.get(params.id)
		if (!recordInstance) {
			flash.message = "Η εγγραφή που ζητήσατε δεν υπάρχει"
			redirect(action: "list")
			return
		}

		String xmlRecord = recordInstance.isoValue
		InputStream is = new ByteArrayInputStream(xmlRecord.bytes)
		Source source = new StreamSource(is)
		//String stylesheetUrl = "http://kstamatis.gr:8080/marcss.xsl"
		File stylesheetUrl = resourceLoader?.getResource("resources/marcss.xsl")?.getFile()
		Source stylesheet = new StreamSource(stylesheetUrl)
		ByteArrayOutputStream bout = new ByteArrayOutputStream()
		Result result = new StreamResult(bout)

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance()
		DocumentBuilder dbuilder = dbFactory.newDocumentBuilder()
		Document doc = dbuilder.parse(is)

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(stylesheet);
		transformer.transform(new DOMSource(doc), result);

		[recordInstance: recordInstance, marc: bout.toString()]
	}

	def showrecid() {
		def recordInstance = Record.findByRecordId(params.recid)
		if (!recordInstance) {
			flash.message = "Η εγγραφή που ζητήσατε δεν υπάρχει"
			redirect(action: "list")
			return
		}

		String xmlRecord = recordInstance.isoValue
		InputStream is = new ByteArrayInputStream(xmlRecord.bytes)
		Source source = new StreamSource(is)
		//String stylesheetUrl = "http://kstamatis.gr:8080/marcss.xsl"
		File stylesheetUrl = resourceLoader?.getResource("resources/marcss.xsl")?.getFile()
		Source stylesheet = new StreamSource(stylesheetUrl)
		ByteArrayOutputStream bout = new ByteArrayOutputStream()
		Result result = new StreamResult(bout)

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance()
		DocumentBuilder dbuilder = dbFactory.newDocumentBuilder()
		Document doc = dbuilder.parse(is)

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(stylesheet);
		transformer.transform(new DOMSource(doc), result);

		render (view: 'show', model: [recordInstance: recordInstance, marc: bout.toString()])

	}

	/*def edit() {
	 def recordInstance = Record.get(params.id)
	 if (!recordInstance) {
	 flash.message = message(code: 'default.not.found.message', args: [message(code: 'record.label', default: 'Record'), params.id])
	 redirect(action: "list")
	 return
	 }
	 [recordInstance: recordInstance]
	 }
	 def update() {
	 def recordInstance = Record.get(params.id)
	 if (!recordInstance) {
	 flash.message = message(code: 'default.not.found.message', args: [message(code: 'record.label', default: 'Record'), params.id])
	 redirect(action: "list")
	 return
	 }
	 if (params.version) {
	 def version = params.version.toLong()
	 if (recordInstance.version > version) {
	 recordInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
	 [message(code: 'record.label', default: 'Record')] as Object[],
	 "Another user has updated this Record while you were editing")
	 render(view: "edit", model: [recordInstance: recordInstance])
	 return
	 }
	 }
	 recordInstance.properties = params
	 if (!recordInstance.save(flush: true)) {
	 render(view: "edit", model: [recordInstance: recordInstance])
	 return
	 }
	 flash.message = message(code: 'default.updated.message', args: [message(code: 'record.label', default: 'Record'), recordInstance.id])
	 redirect(action: "show", id: recordInstance.id)
	 }
	 def delete() {
	 def recordInstance = Record.get(params.id)
	 if (!recordInstance) {
	 flash.message = message(code: 'default.not.found.message', args: [message(code: 'record.label', default: 'Record'), params.id])
	 redirect(action: "list")
	 return
	 }
	 try {
	 recordInstance.delete(flush: true)
	 flash.message = message(code: 'default.deleted.message', args: [message(code: 'record.label', default: 'Record'), params.id])
	 redirect(action: "list")
	 }
	 catch (DataIntegrityViolationException e) {
	 flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'record.label', default: 'Record'), params.id])
	 redirect(action: "show", id: params.id)
	 }
	 }*/

	/*def upload() {
		File path = resourceLoader?.getResource("resources/records.xml")?.getFile()
		//String path = servletContext.getRealPath("/resources/records.xml")
		InputStream input = new FileInputStream(path)
		MarcReader reader = new MarcXmlReader(input)

		int offset = params.int('offset')

		int i = 0

		while ( reader.hasNext() ) {

			if (i<offset)  {
				reader.next()
				i++
				continue
			}

			org.marc4j.marc.Record record = reader.next()

			Record recordToSave = new Record()

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			RecordUtils.toXML(record, out)
			//byte[] bytes = RecordUtils.marshal(record, "UTF8")
			recordToSave.isoValue = out.toString() ;//new String(bytes, "UTF-8")

			recordToSave.recordId = getControlField(record, "001")

			if (!recordToSave.recordId) {
				i++;
				continue;
			}
			
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
				i++;
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

			if (i-offset==10000)
				break;
		}
		println "finished"
		redirect(action: "list")
	}*/

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
