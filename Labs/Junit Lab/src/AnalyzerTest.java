import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by orion on 5/21/17.
 */
public class AnalyzerTest {
    @Test
    public void exampleTest() throws Exception{
        Analyzer analyzer = new Analyzer();
        int val = analyzer.analyzeString("wise men bleed after a crime");
        int val2 = analyzer.analyzeString("Abolish crime so men do not bleed, bleed, bleed");
        int val3 = analyzer.analyzeString("Wise people rule fairly and are supreme leaders. " +
                "Hopefully wise people will rule");

        assertEquals(-1, val);
        assertEquals(-5, val2);
        assertEquals(4, val3);

        Document doc = JSoupScraper.getWebsiteContent("http://www.greattreks.com/");
        Elements elements = JSoupScraper.selectElementsFromDocument(doc, "h1, h2, strong");
        int strong_sentiment = 0;
        for(Element e : elements)
            strong_sentiment += analyzer.analyzeString(e.text());
        assertEquals(3, strong_sentiment);
        assertEquals("Neutral", analyzer.getSentiment(strong_sentiment));
        assertNotEquals("Positive", analyzer.getSentiment(strong_sentiment));
        assertNotEquals("Negative", analyzer.getSentiment(strong_sentiment));
    }
    
    @Test
    public void myTest() throws Exception{
    	Analyzer analyzer = new Analyzer();
    	int val = analyzer.analyzeString("Here be dragons");
    	int val2 = analyzer.analyzeString("The universe is a cold, meaningless place, indifferent to suffering and failure");
    	int val3 = analyzer.analyzeString("the greatest joy in life is to love and be loved");
    	
    	assertEquals(0, val);
    	assertEquals(-5, val2);
    	assertEquals(4, val3);
    	
    	assertEquals("Neutral", analyzer.getSentiment(val));
    	assertEquals("Negative", analyzer.getSentiment(val2));
    	assertEquals("Positive", analyzer.getSentiment(val3));
    	
    	org.jsoup.Connection con = JSoupScraper.connect("https://en.wikipedia.org/wiki/World_War_II");
    	assertNotNull(con);
    	assertTrue(con instanceof org.jsoup.Connection);
    	Document testDoc = JSoupScraper.getDocumentFromConnection(con);
    	assertNotNull(testDoc);
    	assertTrue(testDoc instanceof Document);
    	
    	Document doc = JSoupScraper.getWebsiteContent("https://en.wikipedia.org/wiki/World_War_II");
        Elements elements = JSoupScraper.selectElementsFromDocument(doc, "h1, h2, p");
        int sentiment = 0;
        for(Element e : elements)
            sentiment += analyzer.analyzeString(e.text());
        assertEquals(-90, sentiment);
        assertEquals("Negative", analyzer.getSentiment(sentiment));
        assertNotEquals("Neutral", analyzer.getSentiment(sentiment));
        assertNotEquals("Positive", analyzer.getSentiment(sentiment));
    }
}