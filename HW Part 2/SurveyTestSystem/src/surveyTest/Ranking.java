package surveyTest;

import java.util.ArrayList;

// A representation of a ranking question
public class Ranking extends Question {
	
	private static final long serialVersionUID = 2808353272687955802L;

	public Ranking(String prompt) {
		super(prompt);
	}

	@Override
	public void setChoices(AbstractSurveyIO io) throws Exception {
		int numChoices = 0;
		
		// Prompt for number of choices for this question, must have at least 2 choices
		io.display("Please specify number of choices (at least 2):");
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

	}

	@Override
	protected void displayChoices(AbstractSurveyIO io) {
		for (int i = 0; i < this.choices.size(); i++) {
			io.display((i+1) + ") " + this.choices.get(i)); //print index too, incremented by 1 since we start at 0
		}
	}

	@Override
	public Response getResponse(AbstractSurveyIO io) throws Exception {
		ArrayList<String> choiceIndexes = new ArrayList<String>();
		
		// For each choice, set its numeric ranking
		for (int i = 0; i < this.choices.size(); i++) {
			int choiceIndex = 0;
			do {
				try {
					io.display("Please rank choice " + (i+1));
					choiceIndex = Integer.parseInt(io.getUserInput());
					if (choiceIndex < 1 || choiceIndex > this.choices.size()) {
						io.display("Please enter an integer rank that is in the range from 1 to: " + this.choices.size());
					}
				}
				catch (NumberFormatException e) {
					io.display("Invalid input, integer expected");
				}
			} while (choiceIndex < 1 || choiceIndex > this.choices.size());
			choiceIndexes.add(Integer.toString(choiceIndex));
		}
		
		// Combine rankings into a single Response string for easier comparison of answers
		String str = String.join(" ", choiceIndexes);
		Response r = new Response(str);
		
		return r;
	}

}
