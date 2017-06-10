package surveyTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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
	protected ArrayList<ArrayList<Response>> allUserResponses;
	protected String name;

	public Survey(AbstractSurveyIO io) {
		this.questions = new ArrayList<Question>();
		this.allUserResponses = new ArrayList<ArrayList<Response>>();
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
	}
	
	// Allows for modification of any question in survey
	public void modify(AbstractSurveyIO io) throws Exception {
		this.display(io); //display the survey itself
		
		String in = "";
		int qIndex = 0;
		do { // continuously ask for question to modify by specifying index or quitting by specifying 'n'
			io.display("Which question do you want to modify? (enter index or quit by entering 'n')");
			try {
				in = io.getUserInput();
				//skip if 'n' has been entered
				if (!in.equals("n")) {
					qIndex = Integer.parseInt(in);
					if (qIndex < 1 || qIndex > this.questions.size()) {
						io.display("Please enter an integer that is in the range from 1 to " + this.questions.size());
					}
					else {
						// modify the question
						this.questions.get(qIndex-1).modify(io);
						qIndex = 0;
					}
				}
			}
			catch (NumberFormatException e) {
				io.display("Invalid input, integer expected");
			}
		} while ((qIndex < 1 || qIndex > this.questions.size()) && !in.equals("n"));
	}
	
	// Allow for user to give their responses to questions
	public void take(AbstractSurveyIO io) throws Exception {
		ArrayList<Response> userResponses = new ArrayList<Response>();
		for (int i = 0; i < this.questions.size(); i++) {
			io.display("Question " + (i+1) + " of " + this.questions.size());
			Question q = this.questions.get(i);
			q.display(io);
			Response r = q.getResponse(io);
			userResponses.add(r);
		}
		
		// Add this user's responses to the list of all responses all other users have given
		this.allUserResponses.add(userResponses);
	}
	
	// Tabulate all given user responses to all questions and display them
	public void tabulate(AbstractSurveyIO io) {
		// go through each question
		for (int i = 0; i < this.questions.size(); i++) {
			io.display("Question " + (i+1) + ":");
			this.questions.get(i).display(io);
			
			io.display("Responses:");
			// for each user that took this, get their response to this question
			ArrayList<Response> questionResponses = new ArrayList<>();
			for (int j = 0; j < this.allUserResponses.size(); j++) {
				questionResponses.add(this.allUserResponses.get(j).get(i));
			}
			//display the response and its frequency of occurrence
			HashSet<Response> uniqueResponses = new HashSet<>(questionResponses);
			for (Response r : uniqueResponses) {
				io.display(r.toString() + ": " + Collections.frequency(questionResponses, r));
			}
		}
	}

}
