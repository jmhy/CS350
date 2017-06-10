import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orion on 5/18/17.
 */
public class Analyzer {
    private List<String> positive = null;
    private List<String> negative = null;

    public Analyzer(){
        positive = new ArrayList<>();
        negative = new ArrayList<>();
        loadWordLists();
    }

    public int analyzeString(String the_string){
        int sentiment = 0;
        the_string = the_string.toLowerCase();
        String[] words = the_string.split(" ");
        for(String word : words){
            if(word.charAt(word.length() - 1) == ',')
                word = word.substring(0, word.length() - 1);
            if(positive.contains(word)) {
                ++sentiment;
            }
            if(negative.contains(word)) {
                --sentiment;
            }
        }

        return sentiment;
    }

    public String getSentiment(int sentiment_value){
        if(sentiment_value >= -3 && sentiment_value <=3 )
            return "Neutral";
        else if(sentiment_value > 3)
            return "Positive";
        else
            return "Negative";
    }


    // ----- Private methods -----
    private void loadWordLists(){
        String pos_path = "input/Positive.txt";
        String neg_path = "input/Negative.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(pos_path))){
            String line;
            while((line = br.readLine()) != null)
                positive.add(line);
        }
        catch(IOException e){
            System.err.println("Error while reading " + pos_path);
        }

        try(BufferedReader br = new BufferedReader(new FileReader(neg_path))){
            String line;
            while((line = br.readLine()) != null)
                negative.add(line);
        }
        catch(IOException e){
            System.err.println("Error while reading " + neg_path);
        }
    }

}
