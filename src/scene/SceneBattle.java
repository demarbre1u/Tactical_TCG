package scene;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import data.Board;
import data.Hand;

public class SceneBattle extends SceneBase
{
	private Board board;
	private Hand hand;
	
	public static final int STANDBY = 0;
	public static final int MOVING = 1;
	
	public static int PHASE = STANDBY;
	
	public SceneBattle()
	{
		init();
	}
	
	@Override
	public void init() 
	{
		board = new Board(17, 9);
		hand = new Hand();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		board.render(gc, sbg, g);
		hand.render(gc, sbg, g);
		
		g.drawString("Phase : " + PHASE, 500, 10);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		board.update(gc, sbg, delta);
		hand.update(gc, sbg, delta);
	}
}
