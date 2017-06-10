import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class SentimentComparerTest {

	@Test
	public void test1() throws Exception {
		String url1 = "https://en.wikipedia.org/wiki/World_War_II";
		String url2 = "https://en.wikipedia.org/wiki/Victory_in_Europe_Day";
		String url3 = "https://en.wikipedia.org/wiki/United_States";
		String url4 = "https://en.wikipedia.org/wiki/Soviet_Union";
		String url5 = "https://en.wikipedia.org/wiki/Nazi_Germany";
		
		ArrayList<String> urls = new ArrayList<>();
		urls.add(url1);
		urls.add(url2);
		urls.add(url3);
		urls.add(url4);
		urls.add(url5);
		
		assertEquals(5, urls.size());
		
		SentimentComparer sComparer = new SentimentComparer(urls);
		assertNotNull(sComparer.getAnalyzer());
		assertNotNull(sComparer.getDocs());
		assertEquals("World War II - Wikipedia", sComparer.getSiteName(sComparer.getDocs().get(0)));
		assertEquals("Victory in Europe Day - Wikipedia", sComparer.getSiteName(sComparer.getDocs().get(1)));
		assertEquals("United States - Wikipedia", sComparer.getSiteName(sComparer.getDocs().get(2)));
		assertEquals("Soviet Union - Wikipedia", sComparer.getSiteName(sComparer.getDocs().get(3)));
		assertEquals("Nazi Germany - Wikipedia", sComparer.getSiteName(sComparer.getDocs().get(4)));
		
		int s = sComparer.getSentimentValueOfDoc(sComparer.getDocs().get(0));
		int s2 = sComparer.getSentimentValueOfDoc(sComparer.getDocs().get(1));
		int s3 = sComparer.getSentimentValueOfDoc(sComparer.getDocs().get(2));
		int s4 = sComparer.getSentimentValueOfDoc(sComparer.getDocs().get(3));
		int s5 = sComparer.getSentimentValueOfDoc(sComparer.getDocs().get(4));
		
		assertEquals(-6, s);
		assertEquals(2, s2);
		assertEquals(-3, s3);
		assertEquals(-4, s4);
		assertEquals(-6, s5);
		
		String testStr1 = "Site Name: Victory in Europe Day - Wikipedia\n";
		String testStr2 = "Site Name: World War II - Wikipedia\n" +
				"Site Name: Nazi Germany - Wikipedia\n";
		
		assertEquals(testStr1, sComparer.getMostPositiveSiteNames());
		assertEquals(testStr2, sComparer.getMostNegativeSiteNames());
	}

}
