package maze;

public class MazeFactory {
	
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
