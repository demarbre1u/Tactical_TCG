package data;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Cell 
{	
	public static int CELL_SIZE = 48;
	private int xpos, ypos;
	
	private Unit unit;
	private boolean movable, summon;
	
	public Cell(int x, int y)
	{
		xpos = x;
		ypos = y;
		
		unit = null;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		if(canMove())
		{
			g.setColor(Color.green);
			g.fillRect(Board.offsetX + xpos * Cell.CELL_SIZE +2, 
						Board.offsetY + ypos * Cell.CELL_SIZE +2, 
						42, 
						42);
		}
		
		if(isOccupied())
		{
			g.setColor(Color.blue);
			g.fillRect(Board.offsetX + xpos * Cell.CELL_SIZE +14, 
					Board.offsetY + ypos * Cell.CELL_SIZE +14, 
					20, 
					20);
		}
		
		if(canSummon())
		{
			g.setColor(Color.orange);
			g.fillRect(Board.offsetX + xpos * Cell.CELL_SIZE +2, 
					Board.offsetY + ypos * Cell.CELL_SIZE +2, 
					42, 
					42);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{

	} 
	
	public boolean canMove()
	{
		return movable;
	}
	
	public boolean canSummon()
	{
		return summon;
	}
	
	public int getXpos() {
		return xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setSummon(boolean s)
	{
		summon = s;  
	}
	
	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public boolean isOccupied()
	{
		return unit == null ? false : true; 
	}
	
	public void setUnit(Unit u)
	{
		unit = u;
	}
	
	public void removeUnit()
	{
		unit = null;
	}
	
	public Unit getUnit()
	{
		return unit;
	}
}
