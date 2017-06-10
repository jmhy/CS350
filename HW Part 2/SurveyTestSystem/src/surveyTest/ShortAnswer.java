package surveyTest;

public class ShortAnswer extends Question {

	private static final long serialVersionUID = 1185338224276155076L;

	public ShortAnswer(String prompt) {
		super(prompt);
	}

	@Override
	public void setChoices(AbstractSurveyIO io) throws Exception {
		// Short answer doesn't have choices, do nothing
	}

	@Override
	protected void displayChoices(AbstractSurveyIO io) {
		// Short answer doesn't have choices, do nothing
	}

	// Expect user to enter a short response
	@Override
	public Response getResponse(AbstractSurveyIO io) throws Exception {
		String inStr;
		do {
			inStr = io.getUserInput();
			if (inStr.length() <= 0 || inStr.length() > 100) {
				io.display("A short answer cannot be empty or have more than 100 characters");
			}
		} while (inStr.length() <= 0 || inStr.length() > 100);
		
		Response r = new Response(inStr);
		
		return r;
	}

}
