package surveyTest;

import java.io.Serializable;
import java.util.ArrayList;

//A class for representing the basics of a question and answering it properly
public abstract class Question implements Serializable {
	
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
			String pr;
			do {
				pr = io.getUserInput();
				if (pr.length() == 0) {
					io.display("The prompt cannot be empty, try again");
				}
			} while (pr.length() == 0);
			
			this.prompt = pr;
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
	
	//To modify the details of the question, mainly the prompt and choices (if applicable)
	public void modify(AbstractSurveyIO io) throws Exception {
		this.display(io); //display the question to be modified first
		io.display("Do you wish to modify the prompt? (y/n)");
		
		//keep polling for response if not a y or n for yes or no
		String in;
		do {
			in = io.getUserInput();
		} while (!in.equals("y") && !in.equals("n"));
		
		if (in.equals("y")) {
			io.display("Old Prompt: " + this.prompt);
			io.display("Enter a new prompt:");
			
			this.setPrompt(io);
		}
		
		//modify choices (if any) after modifying the prompt
		modifyChoices(io);
	}
	
	//Each question will have to implement its own means of modifying the choices if applicable, or do nothing
	protected abstract void modifyChoices(AbstractSurveyIO io) throws Exception;

}
