package pathfinding;

import java.util.ArrayList;
import java.util.List;

import data.Board;
import data.Cell;
import scene.SceneBattle;

public class AStar 
{
	private List<Cell> openSet = new ArrayList<Cell>(), 
						closeSet = new ArrayList<Cell>(), 
						path = new ArrayList<Cell>();
	
	private Cell start, end;
	private Board grid;
	
	public AStar() {}
	
	public List<Cell> findPath(Cell s, Cell e)
	{	
		Cell start = s;
		Cell end = e;
		
		grid = SceneBattle.board;
		
		openSet.add(start);
		
		while(! openSet.isEmpty())
		{
			int winner = 0;
			for(int i = 0 ; i < openSet.size() ; i++)
			{
				if(openSet.get(i).getF() < openSet.get(winner).getF())
					winner = i;
			}
			
			Cell current = openSet.get(winner);
			
			if(current == end)
			{
				// Fin du l'algo !
				// Il faut construire le path qu'on va renvoyer maintenant
				path.add(end);
				
				Cell temp = current;
				
				while(temp.getPrevious() != null)
				{
					path.add(temp.getPrevious());
					temp = temp.getPrevious();
				}
				
				// On retire ma case de départ dont on a pas besoin
				path.remove( path.size() - 1 );
				
				return path;
			}
			
			openSet.remove(current);
			closeSet.add(current);
			
			ArrayList<Cell> neighbors = getNeighbors(current);
			
			for(Cell neighbor : neighbors)
			{
				if(! closeSet.contains(neighbor) && !neighbor.isOccupied())
				{
					int tempG = current.getG() + 1;
					
					if(openSet.contains(neighbor))
					{
						if(tempG < neighbor.getG())
							neighbor.setG(tempG);
						
					}
					else
					{
						neighbor.setG(tempG);
						openSet.add(neighbor);
					}
					
					neighbor.setH( heuristic(neighbor, end) );
					neighbor.setF( neighbor.getG() + neighbor.getH() );
					neighbor.setPrevious(current);
				}
			}
		}
		
		// Pas de solution si on arrive ici :/
		// Donc path est vide normalement 
		System.out.println("Path : " + path.size());
		
		return path;
	}
	
	public void dumpPath()
	{
		System.out.println("----------");
		for(int i = 0 ; i < path.size() ; i++)
		{
			String s = "[" + path.get(i).getXpos() + ", " + path.get(i).getYpos() + "]";
			System.out.println(s);
		}
		System.out.println("----------");
	}
	
	private int heuristic(Cell n, Cell e) 
	{
		return Math.abs( n.getXpos() - e.getXpos() ) + Math.abs( n.getYpos() - e.getYpos() );
	}

	private ArrayList<Cell> getNeighbors(Cell c)
	{
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		
		if(c.getXpos() - 1 >= 0)
			neighbors.add(grid.getCell(c.getXpos() - 1, c.getYpos()));
		
		if(c.getXpos() + 1 < Board.WIDTH)
			neighbors.add(grid.getCell(c.getXpos() + 1, c.getYpos()));
		
		if(c.getYpos() - 1 >= 0)
			neighbors.add(grid.getCell(c.getXpos(), c.getYpos() - 1));
		
		if(c.getYpos() + 1 < Board.HEIGHT)
			neighbors.add(grid.getCell(c.getXpos(), c.getYpos() + 1));
		
		return neighbors;
	}
}
