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
	
	public Cell(int x, int y)
	{
		xpos = x;
		ypos = y;
		
		unit = null;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		if(isOccupied())
		{
			g.setColor(Color.blue);
			g.fillRect(Board.offsetX + xpos * Cell.CELL_SIZE +14, 
					Board.offsetY + ypos * Cell.CELL_SIZE +14, 
					20, 
					20);
		}
		else
		{
			g.setColor(Color.gray);
			g.drawRect(Board.offsetX + xpos * Cell.CELL_SIZE +14, 
					Board.offsetY + ypos * Cell.CELL_SIZE +14, 
					20, 
					20);
		}
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{

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
