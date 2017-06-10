package surveyTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

//A concrete console IO implementation for interaction with the survey system via standard input
public class ConsoleSurveyIO extends AbstractSurveyIO {
	BufferedReader br;

	public ConsoleSurveyIO() {
		this.br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	@Override
	public void display(String s) {
		System.out.println(s);	
	}
	
	//This must be able to throw an IOException in order to use BufferedReader
	@Override
	public String getUserInput() throws IOException {
		String in = this.br.readLine();
		return in;
	}

}
