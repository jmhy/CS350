package surveyTest;

//An abstract class to provide a framework for varying types of IO with the survey system
public abstract class AbstractSurveyIO {

	public AbstractSurveyIO() {
		// Auto-generated constructor stub
	}
	
	public abstract void display(String s);
	
	/*
	 * Allow for throwing exceptions in case of improper user input
	 * Methods or classes that call this will either have a throws Exception or attempt to catch it 
	 */
	public abstract String getUserInput() throws Exception;

}
