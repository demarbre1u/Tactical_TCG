package game;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import data.Database;
import main.Main;
import scene.SceneManager;

public class Game extends BasicGameState
{
	
	private SceneManager sceneManager;
	private Database database;
	
	public Game(int state) { }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{	 
		database = new Database();
		sceneManager = new SceneManager();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		sceneManager.render(gc, sbg, g);
		
		g.drawString("FPS : " + gc.getFPS(), 10, 10);
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
