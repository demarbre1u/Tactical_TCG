package main;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import game.Game;

public class Main extends StateBasedGame
{
	public static final String gamename = "TCG - Tactical !"; 
	public static final int WIDTH = 1280, HEIGHT = 720;
	
	public static final int BATTLE = 0;
	
	public Main(String name) 
	{
		super(name);
		addState(new Game(BATTLE));
	}

	public static void main(String[] args) 
	{
		AppGameContainer appgc;
		try
		{
			appgc = new AppGameContainer(new Main(gamename));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.setVSync(true);
			appgc.setMouseGrabbed(false);
			appgc.start();
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException 
	{
		getState(BATTLE).init(gc, this);
		enterState(BATTLE);
	}

}
