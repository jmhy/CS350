package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import maze.ui.MazeViewer;

public class MazeGame {

	public static Maze createMaze(MazeFactory inFactory)
	{
		Maze maze = inFactory.MakeMaze();
		
		Room r0 = inFactory.MakeRoom(0);
		Room r1 = inFactory.MakeRoom(1);
		Room r2 = inFactory.MakeRoom(2);
		Door d1 = inFactory.MakeDoor(r0, r1);
		maze.addRoom(r0);
		maze.addRoom(r1);
		maze.addRoom(r2);
		r0.setSide(Direction.North, inFactory.MakeWall());
		r0.setSide(Direction.South, inFactory.MakeWall());
		r0.setSide(Direction.East, d1);
		r0.setSide(Direction.West, inFactory.MakeWall());
		r1.setSide(Direction.North, inFactory.MakeWall());
		r1.setSide(Direction.South, r2);
		r1.setSide(Direction.East, inFactory.MakeWall());
		r1.setSide(Direction.West, d1);
		r2.setSide(Direction.North, r1);
		r2.setSide(Direction.South, inFactory.MakeWall());
		r2.setSide(Direction.East, inFactory.MakeWall());
		r2.setSide(Direction.West, inFactory.MakeWall());
		
		maze.setCurrentRoom(0);
		
		//System.out.println("The maze does not have any rooms yet!");
		
		return maze;
	}

	public static Maze loadMaze(MazeFactory inFactory, final String path)
	{
		Maze maze = inFactory.MakeMaze();
		
		ArrayList<String> lines = new ArrayList<String>();
		HashMap<String, Room> rooms = new HashMap<>();
		HashMap<String, Door> doors = new HashMap<>();
		
	    //read file once and store maze details in memory
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	lines.add(line);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//first loop through maze details to construct rooms (walls not specified yet) and doors
		for (String line:lines) {
			String[] lineWords = line.split("\\s+");
	    	if (lineWords[0].equals("room")) {
	    		Room r = inFactory.MakeRoom(Integer.parseInt(lineWords[1]));
	    		rooms.put(lineWords[1], r);
	    	}
	    	else if (lineWords[0].equals("door")) {
	    		Room r1 = rooms.get(lineWords[2]);
	    		Room r2 = rooms.get(lineWords[3]);
	    		Door d = inFactory.MakeDoor(r1, r2);
	    		doors.put(lineWords[1], d);
	    	}
		}
		
		//second loop through details to build sides of rooms
		for (String line:lines) {
			String[] lineWords = line.split("\\s+");
	    	if (lineWords[0].equals("room")) {
	    		Room r = rooms.get(lineWords[1]);
	    		
	    		for (int i = 2; i <= 5; i++) {
	    			Direction dir;
	    			
	    			switch (i) {
	    			case 2:
	    				dir = Direction.North;
	    				break;
	    			case 3:
	    				dir = Direction.South;
	    				break;
	    			case 4:
	    				dir = Direction.East;
	    				break;
	    			case 5:
	    				dir = Direction.West;
	    				break;
    				default:
    					//otherwise compiler will complain of no value assigned
    					dir = Direction.North;
	    			}
	    			
	    			if (lineWords[i].equals("wall")) {
		    			r.setSide(dir, inFactory.MakeWall());
		    		}
		    		else if (rooms.containsKey(lineWords[i])) {
		    			r.setSide(dir, rooms.get(lineWords[i]));
		    		}
		    		else if (doors.containsKey(lineWords[i])) {
		    			r.setSide(dir, doors.get(lineWords[i]));
		    		}
	    		}
	    	}
		}
		
		for (Room room:rooms.values()) {
			maze.addRoom(room);
		}
		
		maze.setCurrentRoom(0);
		
		//System.out.println("Please load a maze from the file!");
		
		return maze;
	}
	
	public static void main(String[] args)
	{
		if (args.length == 0) { //create a normal, predefined maze
			MazeFactory mf = new MazeFactory();
			MazeViewer viewer = new MazeViewer(createMaze(mf));
			viewer.run();
		}
		else if (args.length == 1) { //create colored maze specified by args
			MazeFactory mf;
			MazeViewer viewer;
			
			switch (args[0]) {
			case "basic":
				mf = new MazeFactory();
				break;
			case "red":
				mf = new RedMazeFactory();
				break;
			case "blue":
				mf = new BlueMazeFactory();
				break;
			default:
				//if not one of the predefined above, create basic maze
				mf = new MazeFactory();
				System.out.println("Maze type not recognized, basic maze created by default");
				break;
			}
			
			viewer = new MazeViewer(createMaze(mf));
			viewer.run();
		}
		else if (args.length == 2) { //load maze and color it, first arg is color, second is file path
			MazeFactory mf;
			MazeViewer viewer;
			
			switch (args[0]) {
			case "basic":
				mf = new MazeFactory();
				break;
			case "red":
				mf = new RedMazeFactory();
				break;
			case "blue":
				mf = new BlueMazeFactory();
				break;
			default:
				//if not one of the predefined above, create basic maze
				mf = new MazeFactory();
				System.out.println("Maze type not recognized, basic maze created by default");
				break;
			}
			
			viewer = new MazeViewer(loadMaze(mf, args[1]));
			viewer.run();
		}
		else {
			System.out.println("Expected no more than 2 args. Actual number of args: " + args.length);
		}
	}

}
