import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by orion on 5/21/17.
 */
public class JSoupScraper {

    /*
        connect and get split into separate methods to increase surface area of junit testing
        in practice this would be done in a single step (as seen in getWebsiteContent())
     */

    public static Connection connect(String website){
        return Jsoup.connect(website);
    }

    public static Document getDocumentFromConnection(Connection conn) throws IOException{
        return conn.get();
    }

    /*
        Less granularity for testing but does the same as the above in a single step
     */
    public static Document getWebsiteContent(String website) throws IOException{
        return Jsoup.connect(website).get();
    }

    /*
        Select elements from a document, see the junit test for an example
     */
    public static Elements selectElementsFromDocument(Document doc, String elements){
        return doc.select(elements);
    }
}
