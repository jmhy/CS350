package surveyTest;

import java.util.ArrayList;

// A representation of a matching question
public class Matching extends Question {
	
	private static final long serialVersionUID = 5019155710123278882L;
	
	// An additional list of choices to be matched with the original choices
	// Must be equal to or greater in length than the original list
	ArrayList<String> choices2;
	
	public Matching(String prompt) {
		super(prompt);
		this.choices2 = new ArrayList<String>();
	}

	@Override
	public void setChoices(AbstractSurveyIO io) throws Exception {
		int numChoices = 0;
		
		// Prompt for number of choices for this question, must have at least 2 choices
		io.display("Please specify number of choices to be matched (at least 2):");
		do {
			try {
				numChoices = Integer.parseInt(io.getUserInput());
			} catch (NumberFormatException e) {
				io.display("Invalid input, integer expected");
			}
		} while (numChoices <= 1); //one and lower for number of choices not acceptable
		
		// Prompt for the text of each choice
		for (int i = 0; i < numChoices; i++) {
			io.display("Choice " + (i+1));
			String choiseStr = io.getUserInput();
			this.choices.add(choiseStr);
		}
		
		// Now prompt for matching options which may be equal or greater than in number than the choices
		io.display("Please specify number of matching options (same amount or more than choices to be matched):");
		do {
			try {
				numChoices = Integer.parseInt(io.getUserInput());
				if (numChoices < this.choices.size()) {
					io.display("Number of matching opts must be equal or greater than that of the choices");
				}
			} catch (NumberFormatException e) {
				io.display("Invalid input, integer expected");
			}
		} while (numChoices < this.choices.size()); //fewer matching opts not allowed
		
		// Prompt for the text of each matching option
		for (int i = 0; i < numChoices; i++) {
			io.display("Matching Option " + (i+1));
			String choiseStr = io.getUserInput();
			this.choices2.add(choiseStr);
		}

	}

	@Override
	protected void displayChoices(AbstractSurveyIO io) {
		// Display the choices requiring a match
		char choiceLetter = 'A';
		for (int i = 0; i < this.choices.size(); i++) {
			io.display((choiceLetter) + ") " + this.choices.get(i)); //print choice indexes as letters
			choiceLetter += 1;
		}
		
		// Display the matching options
		for (int i = 0; i < this.choices2.size(); i++) {
			io.display((i+1) + ") " + this.choices2.get(i)); //print index too, incremented by 1 since we start at 0
		}
	}

	@Override
	public Response getResponse(AbstractSurveyIO io) throws Exception {
		ArrayList<String> choiceIndexes = new ArrayList<String>();
		
		// For each choice, set its numeric ranking
		for (int i = 0; i < this.choices.size(); i++) {
			int choiceIndex = 0;
			char choiceLetter = (char)('A' + i);
			do {
				try {
					io.display("Please match choice " + choiceLetter + " with the number of one of the options");
					choiceIndex = Integer.parseInt(io.getUserInput());
					if (choiceIndex < 1 || choiceIndex > this.choices2.size()) {
						io.display("Please enter the integer of the matching option that is in the range from 1 to " + this.choices2.size());
					}
				}
				catch (NumberFormatException e) {
					io.display("Invalid input, integer expected");
				}
			} while (choiceIndex < 1 || choiceIndex > this.choices2.size());
			choiceIndexes.add(Integer.toString(choiceIndex));
		}
		
		// Combine matchings into a single Response string for easier comparison of answers
		String str = String.join(" ", choiceIndexes);
		Response r = new Response(str);
		
		return r;
	}

	@Override
	protected void modifyChoices(AbstractSurveyIO io) throws Exception {
		io.display("Do you wish to modify the choices to be matched to? (y/n)");
		
		//keep polling for response if not a y or n for yes or no
		String in;
		do {
			in = io.getUserInput();
		} while (!in.equals("y") && !in.equals("n"));
		
		if (in.equals("y")) {
			io.display("Which choice do you want to modify? (enter index)");
			
			int choiceIndex = 0;
			do {
				try {
					choiceIndex = Integer.parseInt(io.getUserInput());
					if (choiceIndex < 1 || choiceIndex > this.choices.size()) {
						io.display("Please enter an integer that is in the range from 1 to: " + this.choices.size());
					}
				}
				catch (NumberFormatException e) {
					io.display("Invalid input, integer expected");
				}
			} while (choiceIndex < 1 || choiceIndex > this.choices.size());
			
			// Replace the choice
			io.display("Enter the text of the choice:");
			this.choices.set(choiceIndex-1, io.getUserInput());
		}
		
		io.display("Do you wish to modify the matching options? (y/n)");
		//keep polling for response if not a y or n for yes or no
		do {
			in = io.getUserInput();
		} while (!in.equals("y") && !in.equals("n"));
		
		if (in.equals("y")) {
			io.display("Which matching option do you want to modify? (enter index)");
			
			int choiceIndex = 0;
			do {
				try {
					choiceIndex = Integer.parseInt(io.getUserInput());
					if (choiceIndex < 1 || choiceIndex > this.choices2.size()) {
						io.display("Please enter an integer that is in the range from 1 to: " + this.choices2.size());
					}
				}
				catch (NumberFormatException e) {
					io.display("Invalid input, integer expected");
				}
			} while (choiceIndex < 1 || choiceIndex > this.choices2.size());
			
			// Replace the choice
			io.display("Enter the text of the matching option:");
			this.choices2.set(choiceIndex-1, io.getUserInput());
		}
	}

}
