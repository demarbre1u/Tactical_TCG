package game;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends BasicGameState
{
	
	public Game(int state) { }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{	 

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.drawString("TEST !", 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		
	}

	@Override
	public int getID() 
	{
		return 0;
	}

}
