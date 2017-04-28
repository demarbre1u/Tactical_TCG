package data;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class Cursor 
{

	private int cursorX, cursorY;
	
	public Cursor() 
	{
		cursorX = -1;
		cursorY = -1;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		drawCursor(g);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		updateCursorPosition(gc);
	} 
	
	private void drawCursor(Graphics g) 
	{
		g.drawString(cursorX+"", 10, 50);
		g.drawString(cursorY+"", 10, 75);
		
		g.drawString("Cursor : " + isOnBoard(), 10, 100);
		
		if(cursorX < 0 || cursorX >= Board.WIDTH || cursorY < 0 || cursorY >= Board.HEIGHT)
			return;
		
		g.setColor(Color.yellow);
		g.drawOval(Board.offsetX + cursorX * Cell.CELL_SIZE, 
					Board.offsetY + cursorY * Cell.CELL_SIZE, 
					Cell.CELL_SIZE, 
					Cell.CELL_SIZE);	
	}
	
	private void updateCursorPosition(GameContainer gc) {
		Input input = gc.getInput();
		
		float xpos = ((input.getMouseX() - Board.offsetX) / Cell.CELL_SIZE);
		float ypos = ((input.getMouseY() - Board.offsetY) / Cell.CELL_SIZE);
		
		// On fait le test ou on vérifie si x/yPos est négatif afin d'éviter les problèmes d'arrondis lors des calculs de coord.
		cursorX = xpos < 0 ? -1 : (int) xpos;
		cursorY = ypos < 0 ? -1 : (int) ypos;
	}
	
	public boolean isOnBoard()
	{
		if(cursorX >= 0 && cursorX < Board.WIDTH && cursorY >= 0 && cursorY < Board.HEIGHT)
			return true;
	
		return false;
	}

	public int getCursorX() 
	{
		return cursorX;
	}

	public int getCursorY() 
	{
		return cursorY;
	}
}
