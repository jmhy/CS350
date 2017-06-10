package surveyTest;

import java.io.Serializable;
import java.util.ArrayList;

//A class for containing survey questions and responses and methods for accessing such data
public class Survey implements Serializable {
	
	private static final long serialVersionUID = -6209827558735788353L;
	/*
	 * Survey will hold its name, a list of questions to be displayed and responded to,
	 * a list of a list of responses, as the survey will likely be taken multiple times,
	 * and use a concrete implementation of AbstractSurveyIO for various IO scenarios for displaying
	 * i.e. ConsoleIO, GUI, audio, etc.
	 */
	protected ArrayList<Question> questions;
	protected ArrayList<ArrayList<Response>> userResponses;
	protected String name;

	public Survey(AbstractSurveyIO io) {
		this.questions = new ArrayList<Question>();
		this.userResponses = new ArrayList<ArrayList<Response>>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addQuestion(Question q) {
		this.questions.add(q);
	}
	
	public void removeQuestion(int index) {
		this.questions.remove(index);
	}
	
	// displays the survey and all questions, based on specified IO method
	public void display(AbstractSurveyIO io) {
		io.display("Survey name: " + name);
		io.display("Number of questions: " + this.questions.size());
		
		// display the individual questions
		for (int i = 0; i < this.questions.size(); i++) {
			io.display("Question " + (i+1) + ": ");
			this.questions.get(i).display(io);
		}
		
		// TODO display user responses, which first requires a way to take the survey
	}

}
