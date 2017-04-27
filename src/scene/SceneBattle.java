package scene;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import data.Board;

public class SceneBattle extends SceneBase
{
	private Board board;
	
	public SceneBattle()
	{
		init();
	}
	
	@Override
	public void init() 
	{
		board = new Board(11, 9);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		board.render(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		board.update(gc, sbg, delta);
	}
}
