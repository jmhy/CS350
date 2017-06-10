package maze;

public class BlueMazeFactory extends MazeFactory {
	
	@Override
	public Wall MakeWall() {
		return new BlueWall();
	}
	
	@Override
	public Door MakeDoor(Room r1, Room r2) {
		return new BrownDoor(r1, r2);
	}
	
	@Override
	public Room MakeRoom(int id) {
		return new GreenRoom(id);
	}

}
