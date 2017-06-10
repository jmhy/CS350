package maze;

public class RedMazeGameCreator extends MazeGameCreator {
	
	@Override
	public Wall MakeWall() {
		return new RedWall();
	}
	
	@Override
	public Room MakeRoom(int id) {
		return new RedRoom(id);
	}

}
