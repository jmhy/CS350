package maze;

import maze.ui.MazeViewer;

public class MazeGame {

	public MazeGame() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)
	{
		if (args.length == 0) { //create a normal, predefined maze
			MazeGameCreator mgc = new MazeGameCreator();
			MazeViewer viewer = new MazeViewer(mgc.createMaze());
			viewer.run();
		}
		else if (args.length == 1) { //create colored maze specified by args
			MazeGameCreator mgc;
			MazeViewer viewer;
			
			switch (args[0]) {
			case "basic":
				mgc = new MazeGameCreator();
				break;
			case "red":
				mgc = new RedMazeGameCreator();
				break;
			case "blue":
				mgc = new BlueMazeGameCreator();
				break;
			default:
				//if not one of the predefined above, create basic maze
				mgc = new MazeGameCreator();
				System.out.println("Maze type not recognized, basic maze created by default");
				break;
			}
			
			viewer = new MazeViewer(mgc.createMaze());
			viewer.run();
		}
		else if (args.length == 2) { //load maze and color it, first arg is color, second is file path
			MazeGameCreator mgc;
			MazeViewer viewer;
			
			switch (args[0]) {
			case "basic":
				mgc = new MazeGameCreator();
				break;
			case "red":
				mgc = new RedMazeGameCreator();
				break;
			case "blue":
				mgc = new BlueMazeGameCreator();
				break;
			default:
				mgc = new MazeGameCreator();
				System.out.println("Maze type not recognized, basic maze created by default");
				break;
			}
			
			viewer = new MazeViewer(mgc.loadMaze(args[1]));
			viewer.run();
		}
		else {
			System.out.println("Expected no more than 2 args. Actual number of args: " + args.length);
		}
	}

}
