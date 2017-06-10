package maze;

public class RedMazeFactory extends MazeFactory {
	
	@Override
	public Wall MakeWall() {
		return new RedWall();
	}
	
	@Override
	public Room MakeRoom(int id) {
		return new RedRoom(id);
	}

}
