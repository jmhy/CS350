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

	public static void main(String[] args) {
		// initialize IO type
		io = new ConsoleSurveyIO();
		
		io.display("Please select a Question type by entering its corresponding number below:");
		io.display("1: Survey");
		io.display("2: Test");
		
		// determine the whether survey or test was chosen
		
		int typeIndex = 0;
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
		
		int selectionIndex = 0;
		do {
			io.display("Choose option:");
			io.display("1: Create");
			io.display("2: Display");
			io.display("3: Load from file");
			io.display("4: Save to file");
			io.display("5: Quit");
			do {
				// parseInt will throw an exception if it cannot parse the input
				try {
					String inStr = io.getUserInput();
					selectionIndex = Integer.parseInt(inStr);
					if (selectionIndex < 1 || selectionIndex > 5) {
						io.display("Input out of range, options are 1 through 5");
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
			} while (selectionIndex < 1 || selectionIndex > 5);
			
			switch (selectionIndex) {
			case 1: // create
				if (sessionSurvey != null) {
					io.display("Survey or test already created! Save to file first or quit.");
				}
				else {
					switch (typeIndex) {
					case 1:
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
						break;
					case 2:
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
						break;
					}
				}
				break;
			case 2: //display
				if (sessionSurvey == null) {
					io.display("No survey/test created or loaded into memory! Create or load one first");
				}
				else {
					sessionSurvey.display(io);
				}
				break;
			case 3: //load and deserialize
				if (sessionSurvey != null) {
					io.display("Cannot load from file when current survey/test has not been saved to file yet!");
				}
				else {
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
				break;
			case 4: //serialize and save
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
				break;
			case 5: //quit
				io.display("Quitting...");
				break;
			}
			
		} while (selectionIndex != 5);	
	}
}
