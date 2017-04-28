package data;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Hand 
{
	public final static int MAX = 6;
	
	private final static float offsetX = 100, sepDist = 20;
	
	public List<Card> cards;
	
	public Hand()
	{
		cards = new ArrayList<Card>();
		
		cards.add(Database.cards.get("soldier"));
		cards.add(Database.cards.get("knight"));
		cards.add(Database.cards.get("king"));
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		g.setColor(Color.white);
		for(int i = 0 ; i < cards.size() ; i++)
		{
			g.drawRect(offsetX + sepDist * i + Card.WIDTH * i, 
						gc.getHeight() - 75, 
						Card.WIDTH, 
						Card.HEIGHT);
			
			g.drawString(cards.get(i).getUnit().getName(), 
						offsetX + sepDist * i + Card.WIDTH * i + 10, 
						gc.getHeight() - 60);			
		}
	}
	

	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{

	} 
	
	public boolean isEmpty()
	{
		return cards.size() == 0 ? true : false;
	}
}
