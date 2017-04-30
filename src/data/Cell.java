package data;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import scene.SceneBattle;

public class Cell 
{	
	// Pour le A* Pathfinding
	private int f = 0, g = 0, h = 0;
	private Cell previous = null;

	// Autre
	public final static int CELL_SIZE = 48;
	public final static int SPEED = 6;


	private int xpos, ypos;
	private float relativeX, relativeY;

	private Unit unit;
	private boolean movable, summon, attackable;

	public Cell(int x, int y)
	{
		xpos = x;
		ypos = y;

		relativeX = 0;
		relativeY = 0;

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

		if(canSummon())
		{
			g.setColor(Color.orange);
			g.fillRect(Board.offsetX + xpos * Cell.CELL_SIZE +2, 
					Board.offsetY + ypos * Cell.CELL_SIZE +2, 
					42, 
					42);
		}

		if(isAttackable())
		{
			g.setColor(Color.red);
			g.fillRect(Board.offsetX + xpos * Cell.CELL_SIZE +2, 
					Board.offsetY + ypos * Cell.CELL_SIZE +2, 
					42, 
					42);
		}
		
		// Si il y a une unité 
		if(isOccupied())
		{
			if(! unit.isEnemy())
				g.setColor(Color.blue);
			else
				g.setColor(Color.gray);

			if(unit.isBuilding())
				g.fillRect(Board.offsetX + xpos * Cell.CELL_SIZE +14, 
						Board.offsetY + ypos * Cell.CELL_SIZE +14, 
						20, 
						20);
			else
				g.fillOval(Board.offsetX + xpos * Cell.CELL_SIZE +14 + relativeX,  // PAS SUR
						Board.offsetY + ypos * Cell.CELL_SIZE +14 + relativeY, // PAS SUR  
						20, 
						20);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		if(unit == null) 
			return;

		if(unit.isMoving())
		{
			Board b = SceneBattle.board;

			if(! b.path.isEmpty())
			{
				int targetX = b.path.get( b.path.size() - 1 ).getXpos();
				int targetY = b.path.get( b.path.size() - 1 ).getYpos();

				int xDiff = xpos - targetX;
				int yDiff = ypos - targetY;

				if(xDiff == 0)
				{
					if(yDiff > 0)
					{
						relativeY -= SPEED;
						if(relativeY <= -Cell.CELL_SIZE)
						{
							relativeY = -Cell.CELL_SIZE;
							b.getCell(targetX, targetY).setUnit( unit );
							unit = null;
							relativeY = 0;
							b.path.remove( b.path.size() - 1 );
						}
					}
					else
					{
						relativeY += SPEED;
						if(relativeY >= Cell.CELL_SIZE)
						{
							relativeY = Cell.CELL_SIZE;
							b.getCell(targetX, targetY).setUnit( unit );
							unit = null;
							relativeY = 0;
							b.path.remove( b.path.size() - 1 );
						}
					}
				}
				else
				{
					if(xDiff > 0)
					{
						relativeX -= SPEED;
						if(relativeX <= -Cell.CELL_SIZE)
						{
							relativeX = -Cell.CELL_SIZE;
							b.getCell(targetX, targetY).setUnit( unit );
							unit = null;
							relativeX = 0;
							b.path.remove( b.path.size() - 1 );
						}
					}
					else
					{
						relativeX += SPEED;
						if(relativeX >= Cell.CELL_SIZE)
						{
							relativeX = Cell.CELL_SIZE;
							b.getCell(targetX, targetY).setUnit( unit );
							unit = null;
							relativeX = 0;
							b.path.remove( b.path.size() - 1 );
						}
					}
				}
			}
			else
			{
				b.path = null;
				b.currentCell = null;
				b.resetBoardForPathFinding();
				unit.setMoving(false);
				SceneBattle.PHASE = SceneBattle.STANDBY;
			}
		}
	}

	public void initCellForPathFinding()
	{
		f = 0;
		g = 0;
		h = 0;

		previous = null;
	}

	public boolean canMove()
	{
		return movable;
	}

	public boolean canSummon()
	{
		return summon;
	}

	public int getXpos() 
	{
		return xpos;
	}

	public int getYpos() 
	{
		return ypos;
	}

	public void setSummon(boolean s)
	{
		summon = s;  
	}

	public void setMovable(boolean movable) 
	{
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

	public int getF() {
		return f;
	}

	public void setF(int f) 
	{
		this.f = f;
	}

	public int getG() 
	{
		return g;
	}

	public void setG(int g) 
	{
		this.g = g;
	}

	public int getH() 
	{
		return h;
	}

	public void setH(int h) 
	{
		this.h = h;
	}

	public Cell getPrevious() 
	{
		return previous;
	}

	public void setPrevious(Cell previous) 
	{
		this.previous = previous;
	}

	public boolean isAttackable() 
	{
		return attackable;
	}

	public void setAttackable(boolean attackable) 
	{
		this.attackable = attackable;
	}
}
