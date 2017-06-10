import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {

	public static void main(String[] args) {
		//run unit tests for the analyzer
		Result res = JUnitCore.runClasses(AnalyzerTest.class);
		
		for (Failure f : res.getFailures()) {
			System.out.println(f.toString());
		}
		
		//run unit tests for the sentiment comparer
		res = JUnitCore.runClasses(SentimentComparerTest.class);
		
		for (Failure f : res.getFailures()) {
			System.out.println(f.toString());
		}
		
		System.out.println("All tests were successful: " + res.wasSuccessful());
	}

}
