package data;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import main.Main;

public class Board 
{	
	private Cell[][] board;
	public static float offsetX, offsetY;
	public static int WIDTH, HEIGHT;
	
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
		
		board[2][2].setUnit(new Unit());
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
