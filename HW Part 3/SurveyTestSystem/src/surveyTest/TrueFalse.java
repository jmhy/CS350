package surveyTest;

// A representation of a True/False question
public class TrueFalse extends Question {

	private static final long serialVersionUID = -7669026017455914263L;

	public TrueFalse(String prompt) {
		super(prompt);
		this.choices.add("True");
		this.choices.add("False");
	}
	
	@Override
	public void setChoices(AbstractSurveyIO io) {
		// TrueFalse only has true and false choices, don't do anything
	}

	@Override
	public void displayChoices(AbstractSurveyIO io) {
		// Will display choices true and false
		for (String choice : this.choices) {
			io.display(choice);
		}
	}
	
	@Override
	public Response getResponse(AbstractSurveyIO io) throws Exception {
		String in;
		// input must be a 't' or 'f', keep prompting until correct input
		do {
			in = io.getUserInput();
			if (!in.equals("t") && !in.equals("f")) {
				io.display("Please enter t or f for true and false, respectively");
			}
		} while (!in.equals("t") && !in.equals("f"));
		
		Response r = new Response(in);
		return r;
	}

	@Override
	protected void modifyChoices(AbstractSurveyIO io) throws Exception {
		// Do nothing, as the only choices of "true" and "false" should not be modified
	}

}
