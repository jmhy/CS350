/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class MazeGameCreator
{
	/**
	 * Creates a small maze.
	 */
	public Maze createMaze()
	{
		Maze maze = MakeMaze();
		
		Room r0 = MakeRoom(0);
		Room r1 = MakeRoom(1);
		Room r2 = MakeRoom(2);
		Door d1 = MakeDoor(r0, r1);
		maze.addRoom(r0);
		maze.addRoom(r1);
		maze.addRoom(r2);
		r0.setSide(Direction.North, MakeWall());
		r0.setSide(Direction.South, MakeWall());
		r0.setSide(Direction.East, d1);
		r0.setSide(Direction.West, MakeWall());
		r1.setSide(Direction.North, MakeWall());
		r1.setSide(Direction.South, r2);
		r1.setSide(Direction.East, MakeWall());
		r1.setSide(Direction.West, d1);
		r2.setSide(Direction.North, r1);
		r2.setSide(Direction.South, MakeWall());
		r2.setSide(Direction.East, MakeWall());
		r2.setSide(Direction.West, MakeWall());
		
		maze.setCurrentRoom(0);
		
		//System.out.println("The maze does not have any rooms yet!");
		
		return maze;
	}

	public Maze loadMaze(final String path)
	{
		Maze maze = MakeMaze();
		
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
	    		Room r = MakeRoom(Integer.parseInt(lineWords[1]));
	    		rooms.put(lineWords[1], r);
	    	}
	    	else if (lineWords[0].equals("door")) {
	    		Room r1 = rooms.get(lineWords[2]);
	    		Room r2 = rooms.get(lineWords[3]);
	    		Door d = MakeDoor(r1, r2);
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
		    			r.setSide(dir, MakeWall());
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
	
	public Maze MakeMaze() {
		return new Maze();
	}
	
	public Wall MakeWall() {
		return new Wall();
	}
	
	public Door MakeDoor(Room r1, Room r2) {
		return new Door(r1, r2);
	}
	
	public Room MakeRoom(int id) {
		return new Room(id);
	}
}
