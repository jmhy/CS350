package surveyTest;

public class Essay extends Question {

	private static final long serialVersionUID = -7077515037086496096L;

	public Essay(String prompt) {
		super(prompt);
	}

	@Override
	public void setChoices(AbstractSurveyIO io) throws Exception {
		// Essay doesn't have choices, do nothing
	}

	@Override
	protected void displayChoices(AbstractSurveyIO io) {
		// Essay doesn't have choices, do nothing
	}

	// Expect user to enter a long response
	@Override
	public Response getResponse(AbstractSurveyIO io) throws Exception {
		String inStr;
		do {
			inStr = io.getUserInput();
			if (inStr.length() <= 0 || inStr.length() > 10000) {
				io.display("An essay cannot be empty or have more than 10,000 characters");
			}
		} while (inStr.length() <= 0 || inStr.length() > 10000);
		
		Response r = new Response(inStr);
		
		return r;
	}

}
