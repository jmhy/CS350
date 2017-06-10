package maze;

import java.awt.Color;

public class RedRoom extends Room {

	public RedRoom(int num) {
		super(num);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Color getColor()
	{
		//light red
		return new Color(255, 130, 130);
	}

}
