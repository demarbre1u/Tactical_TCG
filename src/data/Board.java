package data;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import main.Main;
import scene.SceneBattle;

public class Board 
{	
	private Cell[][] board;
	public static float offsetX, offsetY;
	public static int WIDTH, HEIGHT;
	
	public Cell currentCell;
	
	private Cursor cursor;

	public Board(int w, int h)
	{
		board = new Cell[w][h]; 
		WIDTH = w;
		HEIGHT = h;
		
		for(int i = 0 ; i < WIDTH ; i++)
		{
			for(int j = 0 ; j < HEIGHT ; j++)
			{
				board[i][j] = new Cell(i, j);
			}
		}
		
		offsetX = ( Main.WIDTH - (w * Cell.CELL_SIZE) ) / 2f;
		offsetY = ( Main.HEIGHT - (h * Cell.CELL_SIZE) ) / 2f;
		
		cursor = new Cursor();
		
		board[5][5].setUnit(new Unit(1, 1, 4, "DansLeCode"));
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		drawGrid(g);
		cursor.render(gc, sbg, g);
		drawCells(gc, sbg, g);
	}
	

	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		cursor.update(gc, sbg, delta);
		checkForInputs(gc);
	} 

	private void checkForInputs(GameContainer gc) 
	{
		Input i = gc.getInput();
		
		switch(SceneBattle.PHASE)
		{
			case SceneBattle.STANDBY:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && cursor.isOnBoard())
				{
					if(board[cursor.getCursorX()][cursor.getCursorY()].isOccupied())
					{
						currentCell = board[cursor.getCursorX()][cursor.getCursorY()];
						clearMovePossibilities();
						setMovePossibilities(0, currentCell.getXpos(), currentCell.getYpos());
						
						SceneBattle.PHASE = SceneBattle.MOVING;
					}
				}
				break;
			case SceneBattle.MOVING:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && cursor.isOnBoard())
				{
					if(board[cursor.getCursorX()][cursor.getCursorY()].canMove())
					{
						board[cursor.getCursorX()][cursor.getCursorY()].setUnit(currentCell.getUnit());
						currentCell.removeUnit();
						
						clearMovePossibilities();
						SceneBattle.PHASE = SceneBattle.STANDBY;
					}
				}
				
				if(i.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
				{
					clearMovePossibilities();
					currentCell = null;
					SceneBattle.PHASE = SceneBattle.STANDBY;
				}
				break;
		}
	}
	
	private void clearMovePossibilities() 
	{
		for(int i = 0 ; i < WIDTH ; i++)
		{
			for(int j = 0 ; j < HEIGHT ; j++)
			{
				board[i][j].setMovable(false);
			}
		}
	}

	private void setMovePossibilities(int dist, int x, int y)
	{
		if(dist == currentCell.getUnit().getMvt())
			return;
		else
		{
			if(x - 1 >= 0)
			{
				if(! board[x - 1][y].isOccupied())
				{
					board[x - 1][y].setMovable(true);
					setMovePossibilities(dist + 1, x - 1, y);
				}
			}
			
			if(x + 1 < WIDTH)
			{
				if(! board[x + 1][y].isOccupied())
				{
					board[x + 1][y].setMovable(true);
					setMovePossibilities(dist + 1, x + 1, y);
				}
			}
			
			if(y - 1 >= 0)
			{
				if(! board[x][y - 1].isOccupied())
				{
					board[x][y - 1].setMovable(true);
					setMovePossibilities(dist + 1, x, y - 1);
				}
			}
			
			if(y + 1 < HEIGHT)
			{
				if(! board[x][y + 1].isOccupied())
				{
					board[x][y + 1].setMovable(true);
					setMovePossibilities(dist + 1, x, y + 1);
				}
			}
		}
	}

	private void drawCells(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		for(int i = 0 ; i < board.length ; i++)
		{
			for(int j = 0 ; j < board[i].length ; j++)
			{
				board[i][j].render(gc, sbg, g);
			}
		}
	}
	
	public void drawGrid(Graphics g)
	{
		g.setColor(Color.white);
		for(int i = 0 ; i < board.length ; i++)
		{
			for(int j = 0 ; j < board[i].length ; j++)
			{
				g.drawRect(offsetX + i * Cell.CELL_SIZE, 
							offsetY + j * Cell.CELL_SIZE, 
							Cell.CELL_SIZE, 
							Cell.CELL_SIZE);
			}
		}
	}	
}
