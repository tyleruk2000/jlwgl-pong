package pong;


import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.*;


import static org.lwjgl.opengl.GL11.*;

public class pongGame
{
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 480;
	
	private boolean isRunning = true;
	
	private Ball ball;
	private Bat playerBat;
	private Bat aiBat;
	
	int playerScore = 0;
	int aiScore = 0;
	
	Random rnd = new Random();
	
	
	public pongGame()
	{
		setupDisplay();
		setupOpenGL();
		setupEntities();
		setupTimer();
		
		//MAIN LOOP
		while (isRunning)
		{
			render();
			logic(getDelta());
			input();
			Display.update();
			Display.sync(60);
			if (Display.isCloseRequested())
				isRunning = false;
		}
		System.out.println("PLAYER: " + playerScore);
		System.out.println("AI: " + aiScore);
		Display.destroy();
	}
	

	private void input()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			playerBat.setDY(-0.2);
		else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			playerBat.setDY(0.2);
		else
			playerBat.setDY(0);
	}

	private long lastFrame;
	private long getTime()
	{
		return (Sys.getTime() * 1000)/Sys.getTimerResolution();
	}
	
	private int getDelta()
	{
		long cunrrentTime = getTime();
		int delta  = (int)(cunrrentTime-lastFrame);
		lastFrame = getTime();
		return delta;
	}
	private void logic(int delta)
	{
		ball.update(delta);
		playerBat.update(delta);
		aiBat.update(delta);
		
		if (ball.intersects(playerBat))
		{
			ball.setDX(0.1);
			//ball.setDY(0.1);
		}
		if (ball.intersects(aiBat))
		{
			ball.setDX(-0.1);
			//ball.setDY(-0.1);
		}
		
		if (ball.getX() < 0)
		{
			setupEntities();
			aiScore++;
		}
		if ( ball.getX() > SCREEN_WIDTH )
		{
			setupEntities();
			playerScore++;
		}
		
		if (ball.getY() < 0)
			ball.setDY(0.1);
		if (ball.getY() > SCREEN_HEIGHT)
			ball.setDY(-0.1);
		
		//AI Code
		if (ball.getY() < (aiBat.getHeight()-20)/2+aiBat.getY() && ball.getDX() > 0)
			aiBat.setDY(-0.2);
		else if (ball.getY() > aiBat.getHeight()/2+aiBat.getY() && ball.getDX() > 0)
			aiBat.setDY(0.2);
		else
			aiBat.setDY(0);
	}

	private void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		ball.draw();
		playerBat.draw();
		aiBat.draw();
	}

	private void setupTimer()
	{
		lastFrame = getTime();
	}

	private void setupEntities()
	{
		playerBat = new Bat(10, (SCREEN_HEIGHT/2 - 80 /2 ), 10, 80);
		aiBat = new Bat(SCREEN_WIDTH-20, (SCREEN_HEIGHT/2 - 80 /2 ), 10, 80);
		ball = new Ball((SCREEN_WIDTH/2-10/2), (SCREEN_HEIGHT/2-10/2),10,10);
		ball.setDX(-0.1);
		ball.setDY(rnd.nextFloat()/10);
	}

	private void setupOpenGL()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 1,-1);
		glMatrixMode(GL_MODELVIEW);	
	}

	private void setupDisplay()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
			Display.setTitle("PONG");
			Display.create();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new pongGame();
	}

}
