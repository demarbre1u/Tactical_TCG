package game;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import scene.SceneManager;

public class Game extends BasicGameState
{
	
	private SceneManager sceneManager;
	
	public Game(int state) { }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{	 
		sceneManager = new SceneManager();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		sceneManager.render(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		sceneManager.update(gc, sbg, delta);
	}

	@Override
	public int getID() 
	{
		return 0;
	}

}
