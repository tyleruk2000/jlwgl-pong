package pong;

import entities.AbstractMoveableEntity;
import static org.lwjgl.opengl.GL11.*;


public class Bat extends AbstractMoveableEntity
{

	public Bat(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw()
	{
		glRectd(x, y, x+width, y+height);
	}

}
