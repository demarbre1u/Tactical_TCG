package data;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import game.Game;
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
	
	public static boolean isPlayerTurn = true;

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
		
		board[5][5].setUnit(new Unit(1, 1, 4, "DansLeCode", false, "melee"));
		
		board[8][8].setUnit(new Unit(2, 2, 4, "DansLeCode", false, "melee"));
		board[7][8].setUnit(new Unit(2, 2, 4, "DansLeCode", false, "range"));
		board[9][8].setUnit(new Unit(2, 2, 4, "DansLeCode", false, "spear"));
		board[6][8].setUnit(new Unit(2, 2, 4, "DansLeCode", false, "melee"));
		
		board[6][8].getUnit().setEnemy(true);
		board[7][8].getUnit().setEnemy(true);
		board[8][8].getUnit().setEnemy(true);
		board[9][8].getUnit().setEnemy(true);
		
		// On place les bases de chaque camps
		board[0][HEIGHT / 2].setUnit(new Unit(0, 30, 0, "Base", true, "none"));
		board[WIDTH - 1][HEIGHT / 2].setUnit(new Unit(0, 30, 0, "Base", true, "none"));
		board[WIDTH - 1][HEIGHT / 2].getUnit().setEnemy(true);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		drawGrid(g);
		drawCells(gc, sbg, g);
		
		if(isPlayerTurn)
		{
			cursor.render(gc, sbg, g);
			drawUnitInfo(gc, g);
			drawUnitChoice(gc, g);
			drawEndTurnButton(gc, g);
		}

		g.setColor(Color.white);
		g.drawString("Player Turn : " + isPlayerTurn, 600, 75);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	{
		checkWinOrLose();
		checkIfEnemyTurnEnded();
		
		if(isPlayerTurn)
		{
			cursor.update(gc, sbg, delta);
			checkForInputs(gc, sbg, delta);
		}
		else
		{
			isPlayerTurn = true;
			readyAllyUnit();
			
			if(!SceneBattle.hand.deck.isEmpty() && SceneBattle.hand.cards.size() < Hand.MAX)
			{
				System.out.println("on rentre bien dans la condition");
				
				SceneBattle.hand.cards.add(SceneBattle.hand.deck.drawCardFromDeck());
			}
		}
		
		updateCells(gc, sbg, delta);
	}
	
	private void checkIfEnemyTurnEnded() 
	{
		if(!isPlayerTurn)
		{
			// On regarde si l'ennemi peut encore jouer 
			boolean found = false;
			
			for(int i = 0 ; i < WIDTH ; i++)
			{
				for(int j = 0 ; j < HEIGHT ; j++)
				{
					// Si il y a une unité, qu'elle est ennemie et qu'elle n'a pas encore attaqué ou bougé, et qu'elle n'est pas un batiment
					if(board[i][j].getUnit() != null && board[i][j].getUnit().isEnemy() && (!board[i][j].getUnit().hasAlreadyAttacked || !board[i][j].getUnit().hasAlreadyMoved) && !board[i][j].getUnit().isBuilding())
					{
						found = true;
						break;
					}
				}
				
				if(found)
					break;
			}
			
			if(!found)
			{
				isPlayerTurn = true;
				readyAllyUnit();
			}
		}
	}

	private void readyAllyUnit() 
	{
		for(int i = 0 ; i < WIDTH ; i++)
		{
			for(int j = 0 ; j < HEIGHT ; j++)
			{
				if(board[i][j].getUnit() != null && !board[i][j].getUnit().isEnemy() && !board[i][j].getUnit().isBuilding())
				{
					board[i][j].getUnit().hasAlreadyAttacked = false;
					board[i][j].getUnit().hasAlreadyMoved = false;
				}
			}
		}
	}
	
	private void readyEnemyUnit() 
	{
		for(int i = 0 ; i < WIDTH ; i++)
		{
			for(int j = 0 ; j < HEIGHT ; j++)
			{
				if(board[i][j].getUnit() != null && board[i][j].getUnit().isEnemy() && !board[i][j].getUnit().isBuilding())
				{
					board[i][j].getUnit().hasAlreadyAttacked = false;
					board[i][j].getUnit().hasAlreadyMoved = false;
				}
			}
		}
	}

	private void checkWinOrLose() 
	{
		if(board[0][HEIGHT / 2].getUnit() == null)
		{
			// Perdu
			Game.sceneManager.pushToTop("gameOver");
		}
		
		if(board[WIDTH - 1][HEIGHT / 2].getUnit() == null)
		{
			// Gagné
			Game.sceneManager.pushToTop("victory");
		}
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
			g.setColor(Color.blue);
		
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
		
		if(currentCell.getUnit().hasAlreadyAttacked)
			g.setColor(new Color(0, 0, 100));
		else if(isHoveringAttack(gc))
			g.setColor(Color.gray);
		else
			g.setColor(Color.blue);
		
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
		
		if(currentCell.getUnit().hasAlreadyMoved)
			g.setColor(new Color(0, 0, 100));
		else if(isHoveringMove(gc))
			g.setColor(Color.gray);
		else
			g.setColor(Color.blue);
		
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
					g.drawString("HP : " + cell.getUnit().getDef() + " - " + cell.getUnit().getDamageTaken(), 10 + offset, 325);
				else
				{
					g.drawString("Atk : " + cell.getUnit().getAtk(), 10 + offset, 325);
					g.drawString("HP : " + cell.getUnit().getDef() + " - " + cell.getUnit().getDamageTaken(), 10 + offset, 350);
					g.drawString("Mvt : " + cell.getUnit().getMvt(), 10 + offset, 375);
					g.drawString("Weapon type : " + cell.getUnit().getAttackType(), 10 + offset, 400);
				}
			}
		}
	}
	
	private void drawEndTurnButton(GameContainer gc, Graphics g)
	{
		if(isHoveringEndTurn(gc))
			g.setColor(Color.gray);
		else
			g.setColor(Color.white);
		
		float posX = Main.WIDTH - 175;
		float posY = Main.HEIGHT - 150;
		
		g.drawRect(posX, posY, 150, 50);
		g.drawString("End Turn", posX + 37, posY + 15);
	}
	
	private boolean isHoveringEndTurn(GameContainer gc)
	{
		Input input = gc.getInput();
		float mX = input.getMouseX();
		float mY = input.getMouseY();
		float posX = Main.WIDTH - 175;
		float posY = Main.HEIGHT - 150;
		
		if(mX >= posX && 
			mX <= posX + 150 &&
			mY >= posY &&
			mY <= posY + 50)
			return true;
		else
			return false;
	}
	
	private void checkForInputs(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		Input i = gc.getInput();
		
		switch(SceneBattle.PHASE)
		{
			case SceneBattle.STANDBY:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				{
					// Si on clique sur le plateau
					if(cursor.isOnBoard())
					{
						Cell cell = board[cursor.getCursorX()][cursor.getCursorY()];
						if(cell.isOccupied() && !cell.getUnit().isEnemy() && !cell.getUnit().isBuilding() && (!cell.getUnit().hasAlreadyAttacked || !cell.getUnit().hasAlreadyMoved))
						{
							currentCell = board[cursor.getCursorX()][cursor.getCursorY()];
							SceneBattle.PHASE = SceneBattle.UNIT_ACTION;
						}
					}
					
					// Si on clique sur le bouton de fin de tour
					if(isHoveringEndTurn(gc))
					{
						readyEnemyUnit();
						isPlayerTurn = false;
					}
				}
				break;
			case SceneBattle.MOVING:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && cursor.isOnBoard())
				{
					if(board[cursor.getCursorX()][cursor.getCursorY()].canMove())
					{
						// Déplacement améliorés
						targetX = cursor.getCursorX();
						targetY = cursor.getCursorY();
						
						AStar astar = new AStar();
						path = astar.findPath(currentCell, board[targetX][targetY]);
						clearMovePossibilities();
						currentCell.getUnit().setMoving(true);
						currentCell.getUnit().hasAlreadyMoved = true;
						
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
					if(isHoveringMove(gc) && !currentCell.getUnit().hasAlreadyMoved)
					{
						clearMovePossibilities();
						setMovePossibilities(0, currentCell.getXpos(), currentCell.getYpos());
						
						SceneBattle.PHASE = SceneBattle.MOVING;
					}
					
					if(isHoveringAttack(gc) && !currentCell.getUnit().hasAlreadyAttacked)
					{
						setAttackPossibilities();
						SceneBattle.PHASE = SceneBattle.ATTACKING;
					}
					
					if(isHoveringWait(gc))
					{
						currentCell.getUnit().hasAlreadyAttacked = true;
						currentCell.getUnit().hasAlreadyMoved = true;
						
						SceneBattle.PHASE = SceneBattle.STANDBY;
					}
				}
				
				if(i.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
				{
					SceneBattle.PHASE = SceneBattle.STANDBY;
				}
				break;
			case SceneBattle.ATTACKING:
				if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				{
					if(! cursor.isOnBoard())
						return;
					
					Cell target = board[cursor.getCursorX()][cursor.getCursorY()];
					
					if(target.isAttackable() && target.getUnit() != null && target.getUnit().isEnemy())
					{
						currentCell.getUnit().hasAlreadyAttacked = true;
						doAttack(currentCell, target);
						clearAttackPossibilities();
						
						SceneBattle.PHASE = SceneBattle.STANDBY;
					}
				}
				
				if(i.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
				{
					clearAttackPossibilities();
					SceneBattle.PHASE = SceneBattle.STANDBY;
				}
				break;
		}
	}

	public void doAttack(Cell attacker, Cell target) 
	{
		target.getUnit().inflictDamage( attacker.getUnit().getAtk() );
		
		if(target.getUnit().isDead())
		{
			target.removeUnit();
			return;
		}

		if(canCounterAttack(attacker, target))
			attacker.getUnit().inflictDamage( target.getUnit().getAtk() );

		if(attacker.getUnit().isDead())
			attacker.removeUnit();
	}
	
	public boolean canCounterAttack(Cell attacker, Cell target)
	{
		boolean canCounter = false;
		switch(attacker.getUnit().getAttackType())
		{
			case "melee":
				if(target.getUnit().getAttackType().equals("spear") || target.getUnit().getAttackType().equals("melee"))
					canCounter = true;
				break;
			case "spear":
				if(target.getUnit().getAttackType().equals("spear"))
					canCounter = true;
				
				int dist = Math.abs( attacker.getXpos() - target.getXpos() ) + Math.abs( attacker.getYpos() - target.getYpos() );
				if(target.getUnit().getAttackType().equals("melee") && dist == 1)
					canCounter = true;
				
				break;
			case "range":
				if(target.getUnit().getAttackType().equals("range"))
					canCounter = true;
				
				int diffX = Math.abs( target.getXpos() - attacker.getXpos() );
				int diffY = Math.abs( target.getYpos() - attacker.getYpos() );
				if(target.getUnit().getAttackType().equals("spear") && diffX == 1 && diffY == 1)
					canCounter = true;
				
				break;
		}
		
		return canCounter;
	}

	private void clearAttackPossibilities()
	{
		for(int i = 0 ; i < WIDTH ; i++)
		{
			for(int j = 00 ; j < HEIGHT ; j++)
			{
				board[i][j].setAttackable(false);
			}
		}
	}
	
	private void setAttackPossibilities() 
	{
		switch(currentCell.getUnit().getAttackType())
		{
			case "spear":
				setAttackPossibilitiesForSpear();
				setAttackPossibilitiesForMelee();
				break;
			case "melee":
				setAttackPossibilitiesForMelee();
				break;
			case "range":
				setAttackPossibilitiesForSpear();
				setAttackPossibilitiesForRange();
				break;
		}
	}
	
	private void setAttackPossibilitiesForSpear()
	{
		int baseX = currentCell.getXpos();
		int baseY = currentCell.getYpos();
		
		if(baseX + 1 < WIDTH && baseY + 1 < HEIGHT)
			board[baseX + 1][baseY + 1].setAttackable(true);
		
		if(baseX - 1 >= 0 && baseY + 1 < HEIGHT)
			board[baseX - 1][baseY + 1].setAttackable(true);
		
		if(baseX - 1 >= 0 && baseY - 1 >= 0)
			board[baseX - 1][baseY - 1].setAttackable(true);
		
		if(baseX + 1 < WIDTH && baseY - 1 >= 0)
			board[baseX + 1][baseY - 1].setAttackable(true);
	}
	
	private void setAttackPossibilitiesForMelee()
	{
		int baseX = currentCell.getXpos();
		int baseY = currentCell.getYpos();
		
		if(baseX + 1 < WIDTH)
			board[baseX + 1][baseY].setAttackable(true);
		
		if(baseX - 1 >= 0)
			board[baseX - 1][baseY].setAttackable(true);
		
		if(baseY + 1 < HEIGHT)
			board[baseX][baseY + 1].setAttackable(true);
		
		if(baseY - 1 >= 0)
			board[baseX][baseY - 1].setAttackable(true);
	}
	
	private void setAttackPossibilitiesForRange()
	{
		int baseX = currentCell.getXpos();
		int baseY = currentCell.getYpos();
		
		if(baseX + 2 < WIDTH)
			board[baseX + 2][baseY].setAttackable(true);
		
		if(baseX - 2 >= 0)
			board[baseX - 2][baseY].setAttackable(true);
		
		if(baseY + 2 < HEIGHT)
			board[baseX][baseY + 2].setAttackable(true);
		
		if(baseY - 2 >= 0)
			board[baseX][baseY - 2].setAttackable(true);
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
