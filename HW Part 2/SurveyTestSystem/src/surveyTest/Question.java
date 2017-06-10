package surveyTest;

import java.io.Serializable;
import java.util.ArrayList;

//A class for representing the basics of a question and answering it properly
public abstract class Question implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7972399753353821144L;
	//Representation of the question itself, or what is being asked
	protected String prompt;
	protected ArrayList<String> choices;

	public Question(String prompt) {
		this.prompt = prompt;
		this.choices = new ArrayList<String>();
	}
	
	public String getPrompt() {
		return this.prompt;
	}

	public void setPrompt(AbstractSurveyIO io) {
		try {
			this.prompt = io.getUserInput();
		} catch (Exception e) {
			io.display("Error setting the prompt:");
			e.printStackTrace();
			io.display(e.getMessage());
		}
	}
	
	public ArrayList<String> getChoices() {
		return choices;
	}

	//Concrete implementations to get user input based on the IO type for the survey
	public abstract void setChoices(AbstractSurveyIO io) throws Exception;
	
	//Displays the question using given IO type, all questions have prompts, but some may not have options, i.e. essays
	public void display(AbstractSurveyIO io) {
		io.display(this.prompt);
		displayChoices(io);
	}
	
	//Displays the potential choices, if a question should have any
	protected abstract void displayChoices(AbstractSurveyIO io);
	
	/*
	 * Concrete questions will get input based on specified IO type
	 * and implement their own way of checking for sane input.
	 * Different IO types may generate different exceptions, to be handled by caller
	 */
	public abstract Response getResponse(AbstractSurveyIO io) throws Exception;

}
