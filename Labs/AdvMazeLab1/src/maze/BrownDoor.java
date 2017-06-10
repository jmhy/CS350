package maze;

import java.awt.Color;

public class BrownDoor extends Door {

	public BrownDoor(Room r1, Room r2) {
		super(r1, r2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Color getColor()
	{
		//rgb for a shade of brown
		return new Color(165,42,42);
	}

}
