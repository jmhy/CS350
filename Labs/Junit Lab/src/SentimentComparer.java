import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SentimentComparer {
	private Analyzer analyzer = null;
	private ArrayList<Document> docs = null;
	private final String elementsQuery = "h1, h2, h3, h4"; 
	
	public SentimentComparer(ArrayList<String> urls) throws IOException {
		this.analyzer = new Analyzer();
		this.docs = new ArrayList<>();
		
		for (String url : urls) {
			Document document = JSoupScraper.getWebsiteContent(url);
			this.docs.add(document);
		}
	}
	
	/*
	 * Begin getters
	 */
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public ArrayList<Document> getDocs() {
		return docs;
	}
	
	/*
	 * End getters
	 */

	public String getSiteName(Document document) {
		Elements elements = JSoupScraper.selectElementsFromDocument(document, "title");
		Element element = elements.get(0);
		return element.text();
	}
	
	public int getSentimentValueOfDoc(Document document) {
		int sentimentValue = 0;
		
		Elements elements = JSoupScraper.selectElementsFromDocument(document, this.elementsQuery);
		for (Element element : elements) {
			sentimentValue += this.analyzer.analyzeString(element.text());
		}
		
		return sentimentValue;
	}
	
	public ArrayList<Integer> indexOfAll(Object obj, ArrayList<Integer> list){
	    ArrayList<Integer> indexList = new ArrayList<>();
	    for (int i = 0; i < list.size(); i++)
	        if(obj.equals(list.get(i)))
	            indexList.add(i);
	    return indexList;
	}
	
	public String getMostPositiveSiteNames() {
		ArrayList<Integer> sentimentValues = new ArrayList<>();
		
		for (Document document : this.docs) {
			sentimentValues.add(getSentimentValueOfDoc(document));
		}
		
		int mostPositiveVal = Collections.max(sentimentValues);
		
		ArrayList<Integer> mostPositiveIndices = indexOfAll(mostPositiveVal, sentimentValues);
		
		String mostPositiveSiteNames = "";
		for (int index : mostPositiveIndices) {
			Document document = this.docs.get(index);
			mostPositiveSiteNames += "Site Name: " + getSiteName(document) + "\n";
		}
		
		return mostPositiveSiteNames;
	}
	
	public String getMostNegativeSiteNames() {
		ArrayList<Integer> sentimentValues = new ArrayList<>();
		
		for (Document document : this.docs) {
			sentimentValues.add(getSentimentValueOfDoc(document));
		}
		
		int mostNegativeVal = Collections.min(sentimentValues);
		
		ArrayList<Integer> mostNegativeIndices = indexOfAll(mostNegativeVal, sentimentValues);
		
		String mostNegativeSiteNames = "";
		for (int index : mostNegativeIndices) {
			Document document = this.docs.get(index);
			mostNegativeSiteNames += "Site Name: " + getSiteName(document) + "\n";
		}
		
		return mostNegativeSiteNames;
	}

}
