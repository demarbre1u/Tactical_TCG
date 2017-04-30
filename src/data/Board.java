package data;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import main.Main;
import pathfinding.AStar;
import scene.SceneBattle;

public class Board 
{	
	private Cell[][] board;
	public static float offsetX, offsetY;
	public static int WIDTH, HEIGHT;
	
	private float sepDist = 20, buttonSize = 32;
	
	private int targetX, targetY;
	
	public Cell currentCell;
	
	private Cursor cursor;
	
	public List<Cell> path;

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
		
		board[5][5].setUnit(new Unit(1, 1, 4, "DansLeCode", false));
		
		board[8][8].setUnit(new Unit(1, 1, 4, "DansLeCode", false));
		board[8][8].getUnit().setEnemy(true);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		drawGrid(g);
		drawCells(gc, sbg, g);
		cursor.render(gc, sbg, g);
		drawUnitInfo(gc, g);
		drawUnitChoice(gc, g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		cursor.update(gc, sbg, delta);
		checkForInputs(gc, sbg, delta);
		updateCells(gc, sbg, delta);
	}
	
	private void updateCells(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		for(int i = 0 ; i < WIDTH ; i++)
		{
			for(int j = 0; j < HEIGHT ; j++)
			{
				board[i][j].update(gc, sbg, delta);
			}
		}
	}

	private void drawUnitChoice(GameContainer gc, Graphics g) 
	{
		if(SceneBattle.PHASE == SceneBattle.UNIT_ACTION)
		{
			drawMoveButton(gc, g);
			drawAttackButton(gc, g);
			drawWaitButton(gc, g);
		}
	}

	private void drawWaitButton(GameContainer gc, Graphics g) 
	{
		// Bouton Wait
		float offX = (Cell.CELL_SIZE - buttonSize) / 2f;
		
		g.setColor(Color.black);
		g.fillRect(offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX + sepDist + buttonSize, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist, 
					buttonSize, 
					buttonSize);
		
		if(isHoveringWait(gc))
			g.setColor(Color.gray);
		else
			g.setColor(Color.white);
		
		g.drawRect(offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX + sepDist + buttonSize, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist, 
					buttonSize, 
					buttonSize);
		g.drawString("W", offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX + sepDist + buttonSize, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist); 
	}

	private boolean isHoveringWait(GameContainer gc) 
	{
		Input i = gc.getInput();
		float mX = i.getMouseX();
		float mY = i.getMouseY();
		float offX = (Cell.CELL_SIZE - buttonSize) / 2f;
		
		if(mX > offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX + sepDist + buttonSize
			&& mX < offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX + sepDist + buttonSize*2
			&& mY > offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist
			&& mY < offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist + buttonSize)
			return true;
		
		return false;
	}

	private void drawAttackButton(GameContainer gc, Graphics g) {
		// Bouton Attack
		float offX = (Cell.CELL_SIZE - buttonSize) / 2f;
		
		g.setColor(Color.black);
		g.fillRect(offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist, 
					buttonSize, 
					buttonSize);
		
		if(isHoveringAttack(gc))
			g.setColor(Color.gray);
		else
			g.setColor(Color.white);
		
		g.drawRect(offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist, 
					buttonSize, 
					buttonSize);
		g.drawString("A", offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist);
		
	}

	private boolean isHoveringAttack(GameContainer gc) 
	{
		Input i = gc.getInput();
		float mX = i.getMouseX();
		float mY = i.getMouseY();
		float offX = (Cell.CELL_SIZE - buttonSize) / 2f;
		
		if(mX > offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX
			&& mX < offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX + buttonSize
			&& mY > offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist
			&& mY < offsetY + Cell.CELL_SIZE*currentCell.getYpos() - sepDist)
			return true;
		
		return false;
	}

	private void drawMoveButton(GameContainer gc, Graphics g) 
	{
		// Bouton Move
		float offX = (Cell.CELL_SIZE - buttonSize) / 2f;
		
		g.setColor(Color.black);
		g.fillRect(offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX - sepDist - buttonSize, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist, 
					buttonSize, 
					buttonSize);
		
		if(isHoveringMove(gc))
			g.setColor(Color.gray);
		else
			g.setColor(Color.white);
		
		g.drawRect(offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX - sepDist - buttonSize, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist, 
					buttonSize, 
					buttonSize);
		g.drawString("M", offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX - sepDist - buttonSize, 
					offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist);
	}

	private boolean isHoveringMove(GameContainer gc) 
	{
		Input i = gc.getInput();
		float mX = i.getMouseX();
		float mY = i.getMouseY();
		float offX = (Cell.CELL_SIZE - buttonSize) / 2f;
		
		if(mX > offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX - sepDist - buttonSize
			&& mX < offsetX + Cell.CELL_SIZE*currentCell.getXpos() + offX - sepDist
			&& mY > offsetY + Cell.CELL_SIZE*currentCell.getYpos() - buttonSize - sepDist
			&& mY < offsetY + Cell.CELL_SIZE*currentCell.getYpos() - sepDist)
			return true;
		
		return false;
	}

	private void drawUnitInfo(GameContainer gc, Graphics g) 
	{	
		if(cursor.isOnBoard() && SceneBattle.PHASE != SceneBattle.UNIT_ACTION)
		{
			Cell cell = board[cursor.getCursorX()][cursor.getCursorY()];
			if(cell.isOccupied())
			{
				float offset = 0;
				if(cell.getUnit().isEnemy())
				{
					g.setColor(Color.red);
					offset = Main.WIDTH - offsetX;;
				}
				else
				{
					g.setColor(Color.blue);
				}
				
				g.drawString(cell.getUnit().getName(), 10 + offset, 300);
				if(cell.getUnit().isBuilding())
					g.drawString("HP : " + cell.getUnit().getDef(), 10 + offset, 325);
				else
				{
					g.drawString("Atk : " + cell.getUnit().getAtk(), 10 + offset, 325);
					g.drawString("Def : " + cell.getUnit().getDef(), 10 + offset, 350);
					g.drawString("Mvt : " + cell.getUnit().getMvt(), 10 + offset, 375);
				}
			}
		}
	}
	
	private void checkForInputs(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		Input i = gc.getInput();
		
		switch(SceneBattle.PHASE)
		{
			case SceneBattle.STANDBY:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && cursor.isOnBoard())
				{
					Cell cell = board[cursor.getCursorX()][cursor.getCursorY()];
					if(cell.isOccupied() && !cell.getUnit().isEnemy() && !cell.getUnit().isBuilding())
					{
						currentCell = board[cursor.getCursorX()][cursor.getCursorY()];
						SceneBattle.PHASE = SceneBattle.UNIT_ACTION;
					}
				}
				break;
			case SceneBattle.MOVING:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && cursor.isOnBoard())
				{
					if(board[cursor.getCursorX()][cursor.getCursorY()].canMove())
					{
						// D�placement am�lior�s
						targetX = cursor.getCursorX();
						targetY = cursor.getCursorY();
						
						AStar astar = new AStar();
						path = astar.findPath(currentCell, board[targetX][targetY]);
						// astar.dumpPath();
						clearMovePossibilities();
						currentCell.getUnit().setMoving(true);
						//board[targetX][targetY].setUnit(currentCell.getUnit());
						//currentCell.removeUnit();
						
						/*
						currentCell = null;
						resetBoardForPathFinding();
						SceneBattle.PHASE = SceneBattle.STANDBY;
						*/
						SceneBattle.PHASE = SceneBattle.ANIMATING;
					}
				}
				
				if(i.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
				{
					clearMovePossibilities();
					currentCell = null;
					SceneBattle.PHASE = SceneBattle.STANDBY;
				}
				break;
			case SceneBattle.SUMMONING:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				{
					if(cursor.isOnBoard())
					{
						if(board[cursor.getCursorX()][cursor.getCursorY()].canSummon())
						{
							board[cursor.getCursorX()][cursor.getCursorY()].setUnit(SceneBattle.hand.clickedCard.getUnit());
							
							clearSummonPossibilities();
							SceneBattle.hand.removeCard(SceneBattle.hand.clickedCard);
							SceneBattle.PHASE = SceneBattle.STANDBY;
						}
					}
				}
				
				if(i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
				{
					SceneBattle.PHASE = SceneBattle.STANDBY;
					clearSummonPossibilities();
				}
				break;
			case SceneBattle.UNIT_ACTION:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				{
					if(isHoveringMove(gc))
					{
						clearMovePossibilities();
						setMovePossibilities(0, currentCell.getXpos(), currentCell.getYpos());
						
						SceneBattle.PHASE = SceneBattle.MOVING;
					}
					
					if(isHoveringAttack(gc))
					{
						// Pas encore de phase d'attaque
						SceneBattle.PHASE = SceneBattle.STANDBY;
					}
					
					if(isHoveringWait(gc))
					{
						// Pas encore de syst�me de Wait
						SceneBattle.PHASE = SceneBattle.STANDBY;
					}
				}
				
				if(i.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
				{
					SceneBattle.PHASE = SceneBattle.STANDBY;
				}
				break;
		}
	}

	public void resetBoardForPathFinding() 
	{
		for(int i = 0 ; i < board.length ; i++)
		{
			for(int j = 0 ; j < board[i].length ; j++)
			{
				board[i][j].initCellForPathFinding();
			}
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

	public void setSummonPossibilities()
	{
		if(SceneBattle.hand.clickedCard.getUnit().isBuilding())
		{
			for(int i = 0 ; i < board[0].length ; i++)
			{
				if(! board[0][i].isOccupied())
					board[0][i].setSummon(true);
			}
		}	
		else
		{
			for(int i = 0 ; i < board[0].length ; i++)
			{
				if(board[0][i].getUnit() == null)
					continue;
				
				if(board[0][i].getUnit().isBuilding())
				{
					if(i + 1 < HEIGHT)
						if(! board[0][i + 1].isOccupied())
							board[0][i + 1].setSummon(true);
					
					if(i - 1 >= 0)
						if(! board[0][i - 1].isOccupied())
							board[0][i - 1].setSummon(true);
					
					if(! board[1][i].isOccupied())
						board[1][i].setSummon(true);
				}
			}
		}	
	}
	
	public void clearSummonPossibilities()
	{
		for(int i = 0 ; i < board.length ; i++)
		{
			for(int j = 0 ; j < board[0].length ; j++)
			{
				board[i][j].setSummon(false);
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

	public Cell getCell(int x, int y)
	{
		return board[x][y];
	}
	
	public Cursor getCursor()
	{
		return cursor;
	}
}
