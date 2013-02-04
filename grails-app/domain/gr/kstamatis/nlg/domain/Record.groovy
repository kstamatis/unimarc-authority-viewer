package gr.kstamatis.nlg.domain

import com.sun.istack.internal.Nullable;

class Record {

	String recordId;
	
	String displayValue;
	
	String isoValue;
	
	int tag;
	
    static constraints = {
		recordId(nullable:true)
		displayValue(nullable:true, maxSize: 1024)
		isoValue(nullable:true, maxSize: 2048)
    }
}
