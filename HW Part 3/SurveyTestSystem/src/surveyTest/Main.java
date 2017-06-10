package surveyTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Main {
	
	public static Survey sessionSurvey;
	public static ConsoleSurveyIO io;
	public static int typeIndex = 0;

	public static void main(String[] args) {
		// initialize IO type
		io = new ConsoleSurveyIO();
		
		io.display("Please select a Question type by entering its corresponding number below:");
		io.display("1: Survey");
		io.display("2: Test");
		
		// determine the whether survey or test was chosen
		
		do {
			// parseInt will throw an exception if it cannot parse the input
			try {
				String inStr = io.getUserInput();
				typeIndex = Integer.parseInt(inStr);
				if (typeIndex < 1 || typeIndex > 2) {
					io.display("Input out of range, options are 1 through 2");
				}
			}
			catch (NumberFormatException e) {
				io.display("Invalid input, integer expected");
			}
			catch (IOException e) {				
				io.display("Failure choosing between survey/test: ");
				// this will get the stack trace and convert it to a string so the IO type can display it
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				io.display(sw.toString());
			}
		} while (typeIndex < 1 || typeIndex > 2);
		
		io.display("Selected type: " + typeIndex);
		
		// determine what to do with survey/test
		
		switch(typeIndex) {
		case 1: //survey
			runSurveyMenu();
			break;
		case 2: //test
			runTestMenu();
			break;
		}
		System.exit(0);
	}
	
	public static void runSurveyMenu() {
		int selectionIndex = 0;
		do {
			io.display("Choose option:");
			io.display("1: Create a new survey");
			io.display("2: Display a survey");
			io.display("3: Load survey from file");
			io.display("4: Save survey to file");
			io.display("5: Modify an existing survey");
			io.display("6: Take a survey");
			io.display("7: Tabulate a survey");
			io.display("8: Quit");
			do {
				// parseInt will throw an exception if it cannot parse the input
				try {
					String inStr = io.getUserInput();
					selectionIndex = Integer.parseInt(inStr);
					if (selectionIndex < 1 || selectionIndex > 8) {
						io.display("Input out of range, options are 1 through 8");
					}
				}
				catch (NumberFormatException e) {
					io.display("Invalid input, integer expected");
				}
				catch (IOException e) {				
					io.display("Failure choosing option: ");
					// this will get the stack trace and convert it to a string so the IO type can display it
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					io.display(sw.toString());
				}
			} while (selectionIndex < 1 || selectionIndex > 8);
			
			switch (selectionIndex) {
			case 1: // create
				if (sessionSurvey != null) {
					io.display("Survey or test already created! Save to file first or quit.");
				}
				else {
					SurveyMaker sm = new SurveyMaker(io);
					
					try {
						sessionSurvey = sm.MakeSurvey();
						sessionSurvey.display(io);
					} catch (Exception e) {
						io.display("Failure to make survey, error given: ");
						// this will get the stack trace and convert it to a string so the IO type can display it
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						io.display(sw.toString());
					}
				}
				break;
			case 2: //display
				if (sessionSurvey == null) {
					io.display("No survey created or loaded into memory! Create or load one first");
				}
				else {
					sessionSurvey.display(io);
				}
				break;
			case 3: //load and deserialize
				load();
				break;
			case 4: //serialize and save
				save();
				break;
			case 5: //modify
				load();
				try {
					sessionSurvey.modify(io);
				} catch (Exception e) {
					io.display("Failure modifying survey, error given: ");
					// this will get the stack trace and convert it to a string so the IO type can display it
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					io.display(sw.toString());
				}
				break;
			case 6: //take
				load();
				io.display("Beginning survey...");
				try {
					sessionSurvey.take(io);
				} catch (Exception e) {
					io.display("Failure taking survey, error given: ");
					// this will get the stack trace and convert it to a string so the IO type can display it
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					io.display(sw.toString());
				}
				save();
				break;
			case 7: //tabulate
				if (sessionSurvey == null) {
					io.display("No survey created or loaded into memory! Create or load one first");
				}
				else {
					io.display("Tabulating survey...");
					sessionSurvey.tabulate(io);
				}
				break;
			case 8: //quit
				io.display("Quitting...");
				break;
			}
			
		} while (selectionIndex != 8);
	}
	
	public static void runTestMenu() {
		int selectionIndex = 0;
		do {
			io.display("Choose option:");
			io.display("1: Create a new test");
			io.display("2: Display a test");
			io.display("3: Load test from file");
			io.display("4: Save test to file");
			io.display("5: Modify an existing test");
			io.display("6: Take a test");
			io.display("7: Tabulate a test");
			io.display("8: Grade");
			io.display("9: Quit");
			do {
				// parseInt will throw an exception if it cannot parse the input
				try {
					String inStr = io.getUserInput();
					selectionIndex = Integer.parseInt(inStr);
					if (selectionIndex < 1 || selectionIndex > 9) {
						io.display("Input out of range, options are 1 through 9");
					}
				}
				catch (NumberFormatException e) {
					io.display("Invalid input, integer expected");
				}
				catch (IOException e) {				
					io.display("Failure choosing option: ");
					// this will get the stack trace and convert it to a string so the IO type can display it
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					io.display(sw.toString());
				}
			} while (selectionIndex < 1 || selectionIndex > 9);
			
			switch (selectionIndex) {
			case 1: // create
				if (sessionSurvey != null) {
					io.display("Test already created! Save to file first or quit.");
				}
				else {
					TestMaker tm = new TestMaker(io);
					
					try {
						sessionSurvey = tm.MakeTest();
						sessionSurvey.display(io);
					} catch (Exception e) {
						io.display("Failure to make test, error given: ");
						// this will get the stack trace and convert it to a string so the IO type can display it
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						io.display(sw.toString());
					}
				}
				break;
			case 2: //display
				if (sessionSurvey == null) {
					io.display("No test created or loaded into memory! Create or load one first");
				}
				else {
					sessionSurvey.display(io);
				}
				break;
			case 3: //load and deserialize
				load();
				break;
			case 4: //serialize and save
				save();
				break;
			case 5: //modify
				load();
				try {
					sessionSurvey.modify(io);
				} catch (Exception e) {
					io.display("Failure modifying test, error given: ");
					// this will get the stack trace and convert it to a string so the IO type can display it
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					io.display(sw.toString());
				}
				save();
				break;
			case 6: //take
				load();
				io.display("Beginning test...");
				try {
					sessionSurvey.take(io);
				} catch (Exception e) {
					io.display("Failure taking survey, error given: ");
					// this will get the stack trace and convert it to a string so the IO type can display it
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					io.display(sw.toString());
				}
				save();
				break;
			case 7: //tabulate
				if (sessionSurvey == null) {
					io.display("No test created or loaded into memory! Create or load one first");
				}
				else {
					io.display("Tabulating test...");
					sessionSurvey.tabulate(io);
				}
				break;
			case 8: //grade test
				if (sessionSurvey == null) {
					io.display("No test created or loaded into memory! Create or load one first");
				}
				else {
					Test t = (Test)sessionSurvey;
					t.grade(io);
				}
				break;
			case 9: //quit
				io.display("Quitting...");
				break;
			}
			
		} while (selectionIndex != 9);
	}
	
	// Handles the selection of survey/test files and loading them into memory
	public static void load() {
		// Determine which folder to start looking in
		String path = "";
		switch (typeIndex) {
		case 1:
			path = "surveys/";
			break;
		case 2:
			path = "tests/";
			break;
		}
		
		// Get all files in folder
		File folder = new File(path);
		File[] files = folder.listFiles();
		
		if (files.length == 0) {
			io.display("No files found");
			// will go back to menu
		} else {
			// Prompt user to select file and attempt to load and deserialize it
			int selectedFileIdx = 0;
			do {
				io.display("Choose a file to load by specifying the number next to it:");
				for (int i = 0; i < files.length; i++) {
					io.display((i+1) + ") " + files[i].getName());
				}
				try {
					selectedFileIdx = Integer.parseInt(io.getUserInput());
					if (selectedFileIdx < 1 || selectedFileIdx > files.length) {
						io.display("Input out of range, options are 1 through " + files.length);
					}
				} catch (NumberFormatException e) {
					io.display("Invalid input, integer expected");
				} catch (IOException e) {
					io.display("Failure to load file, error given: ");
					// this will get the stack trace and convert it to a string so the IO type can display it
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					io.display(sw.toString());
				}
			} while (selectedFileIdx < 1 || selectedFileIdx > files.length);
			
			// Create path to selected files
			path = files[selectedFileIdx-1].getPath();
			
			try {
				FileInputStream fileIn = new FileInputStream(path);
				ObjectInputStream ois = new ObjectInputStream(fileIn);
				
				switch(typeIndex) {
				case 1:
					sessionSurvey = (Survey) ois.readObject();
					break;
				case 2:
					sessionSurvey = (Test) ois.readObject();
					break;
				}
				ois.close();
				fileIn.close();
				io.display("File successfully loaded");
			} catch (Exception e) {
				io.display("Failure to load file, error given: ");
				// this will get the stack trace and convert it to a string so the IO type can display it
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				io.display(sw.toString());
			}
		}
	}
	
	// Handles the saving of the current survey/test to memory
	public static void save() {
		if (sessionSurvey == null) {
			io.display("Nothing in memory to save! Create something first.");
		}
		else {
			try {
				// Ensure that the save folders are created if they do not exist
				File saveDir = new File("surveys/");
				saveDir.mkdirs();
				saveDir = new File("tests/");
				saveDir.mkdirs();
				
				String path = "";
				switch (typeIndex) {
				case 1:
					path = "surveys/" + sessionSurvey.getName() + ".ser";
					break;
				case 2:
					path = "tests/" + sessionSurvey.getName() + ".ser";
					break;
				}
				FileOutputStream fileOut = new FileOutputStream(path);
				ObjectOutputStream oos = new ObjectOutputStream(fileOut);
				oos.writeObject(sessionSurvey);
				oos.close();
				fileOut.close();
				io.display("Successfully saved to: " + path);
				sessionSurvey = null;
			} catch (IOException e) {
				io.display("Failure to save to file, error given: ");
				// this will get the stack trace and convert it to a string so the IO type can display it
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				io.display(sw.toString());
			}	
		}
	}
}
