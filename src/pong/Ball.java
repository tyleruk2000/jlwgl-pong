package pong;

import static org.lwjgl.opengl.GL11.glRectd;
import entities.AbstractMoveableEntity;

public class Ball extends AbstractMoveableEntity
{

	public Ball(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw()
	{
		glRectd(x, y, x+width, y+width);
	}

}
