package gr.kstamatis.nlg

import org.apache.lucene.document.Document;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

class SearchController implements ResourceLoaderAware{

	ResourceLoader resourceLoader
	
    def index() { 
		redirect(action: 'search')	
	}
	
	def search() {
		params.max = 100;
		int pagenumber = 1
		if (params.int('offset')>0)
			pagenumber = params.int('offset')/100+1
			
		if (!params.query){
			render(view: "list", model: [resultList: null, resultListTotal:0, query: "", searchtype:"-1"],)
		}
		else {
			//do search and return results
			File indexFile = resourceLoader?.getResource("resources/index")?.getFile()

			def myquery = getForIndex(params.query)
			
			def luceneIndex = "v"+params.searchtype
			if (params.int('searchtype')==-1){
				luceneIndex = "vall"
			}
			Query query = new Query()
			Map<String, Object> resultsMap = query.search(myquery, luceneIndex, pagenumber, indexFile)
			
			render(view: "list", model: [resultList: resultsMap.get("results"), resultListTotal:resultsMap.get("totalCount"), 
				query: myquery, searchtype:params.searchtype])
			
		}
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
