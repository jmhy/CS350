package surveyTest;

import java.util.ArrayList;

public class Test extends Survey {
	
	private static final long serialVersionUID = 8285733750504088374L;
	
	// Test stores a list of a list of correct responses to allow for multiple correct responses per question
	protected ArrayList<ArrayList<Response>> correctResponses;
	
	public Test(AbstractSurveyIO io) {
		super(io);
		this.correctResponses = new ArrayList<ArrayList<Response>>();
	}
	
	//Should be called each and every time a new question is made
	public void addAnswer(ArrayList<Response> ans) {
		this.correctResponses.add(ans);
	}
	
	//Should be called each and every time a question is removed
	public void removeAnswer(int index) {
		this.correctResponses.remove(index);
	}
	
	// displays the test, all questions, responses, and answers, based on specified IO method
	@Override
	public void display(AbstractSurveyIO io) {
		io.display("Survey name: " + name);
		io.display("Number of questions: " + this.questions.size());
		
		// display the individual questions and all possible responses for each question
		for (int i = 0; i < this.questions.size(); i++) {
			io.display("Question " + (i+1) + ": ");
			this.questions.get(i).display(io);
			
			// display all answers for each
			ArrayList<Response> questionAnswers = this.correctResponses.get(i);
			for (int j = 0; j < questionAnswers.size(); j++) {
				io.display("Correct answer " + (j+1) + ": ");
				questionAnswers.get(j).display(io);
			}
			
			// TODO display user responses, which first requires a way to take the test
		}
	}

}
