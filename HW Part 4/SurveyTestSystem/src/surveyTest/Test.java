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
		}
	}
	
	// Allows for modification of any question in test, overridden to allow for answer modification
	@Override
	public void modify(AbstractSurveyIO io) throws Exception {
		this.display(io); //display the test itself
		
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
						//modify the question
						this.questions.get(qIndex-1).modify(io);
						
						// modify the question's answer(s)
						String ansIn = "";
						io.display("Do you wish to modify Question #" + qIndex + "'s answer(s)? (y/n)");
						do {
							ansIn = io.getUserInput();
						} while (!ansIn.equals("y") && !ansIn.equals("n"));
						
						if (ansIn.equals("y")) {
							Question q = this.questions.get(qIndex-1);
							
							// Make the set of answers for this question, minimum size being 1
							int numAnswers = 0;
							io.display("Please specify the number of correct responses for this question, which must be greater than 0:");
							do {
								try {
									numAnswers = Integer.parseInt(io.getUserInput());
								}
								catch (NumberFormatException e) {
									io.display("Invalid input, integer expected");
								}
							} while (numAnswers < 1);
							
							ArrayList<Response> thisQuestionAns = new ArrayList<Response>();
							for (int i = 0; i < numAnswers; i++) {
								io.display("Setting correct response number " + (i+1) + " of " + numAnswers );
								thisQuestionAns.add(q.getResponse(io));
							}
							
							// add the set of answers for this question to the test's list of answers
							this.correctResponses.set(qIndex-1, thisQuestionAns);
						}
						
						qIndex = 0;
					}
				}
			}
			catch (NumberFormatException e) {
				io.display("Invalid input, integer expected");
			}
		} while ((qIndex < 1 || qIndex > this.questions.size()) && !in.equals("n"));	
	}
	
	// Grade all users who have taken the test
	public void grade (AbstractSurveyIO io) {
		// first count number of essay questions to later deduct from total number of q's since they are not graded
		int numEssays = 0;
		for (Question q : this.questions) {
			if (q instanceof Essay) {
				numEssays++;
			}
		}
		// start grading each user
		for (int i = 0; i < this.allUserResponses.size(); i++) {
			int numCorrect = 0;
			
			io.display("User #" + (i+1) + "'s grade:");
			
			// the set of correct responses to each question
			for (int j = 0; j < this.correctResponses.size(); j++) {
				Response userR = this.allUserResponses.get(i).get(j);
				
				// compare to at least one of the correct responses
				for (int k = 0; k < this.correctResponses.get(j).size(); k++) {
					Response correctR = this.correctResponses.get(j).get(k);
					if (userR.equals(correctR)) {
						numCorrect++;
						break; // stop if equivalent to just one possible correct answer to not overcount
					}
					else if (!(this.questions.get(j) instanceof Essay)) {
						io.display("Question #" + (j+1) + " was incorrect");
					}
				}
			}
			
			double grade = ((double)numCorrect / (this.questions.size() - numEssays)) * 100D;
			io.display(numCorrect + " out of " + (this.questions.size() - numEssays) + " questions correct.");
			io.display("Overall Grade: " + grade + "%");
			io.display("Number of ungraded essay questions: " + numEssays);
		}
	}
	
}
