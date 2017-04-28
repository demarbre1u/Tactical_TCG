package data;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import scene.SceneBattle;

public class Hand 
{
	public final static int MAX = 6;
	
	private final static float offsetX = 100, sepDist = 20, buttonSize = 48;
	private static float offsetButton;
	
	public Card clickedCard;
	
	public List<Card> cards;
	
	public Hand()
	{
		offsetButton = ( Card.WIDTH - (buttonSize*2 + sepDist) ) / 2f;
		
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
			if(SceneBattle.PHASE == SceneBattle.PLAY_OR_INFO)
			{
				// On choisit la couleur pour dessiner la carte en fonction de si c'est celle qu'on a cliqué ou pas 
				if(cards.get(i) == clickedCard)
				{
					// On dessine les boutons pour jouer ou regarder les infos de la carte
					drawPlayButton(gc, g, i);
					drawInfoButton(gc, g, i);
					
					g.setColor(Color.gray);
				}
				else
					g.setColor(Color.white);
			}
			else
			{
				if(isHoveringCard(gc, i))
					g.setColor(Color.gray);
				else
					g.setColor(Color.white);
				
			}
			
			drawCard(gc, g, i);
		}
	}
	

	private void drawInfoButton(GameContainer gc, Graphics g, int i) 
	{
		if(isHoveringInfo(gc, i))
			g.setColor(Color.gray);
		else
			g.setColor(Color.white);
		
		g.drawRect(offsetX + sepDist * i + Card.WIDTH * i + offsetButton + sepDist + buttonSize, 
				gc.getHeight() - 130, 
				buttonSize, 
				buttonSize);
		g.drawString("I",
				offsetX + sepDist * i + Card.WIDTH * i + offsetButton + sepDist + buttonSize, 
				gc.getHeight() - 130);
	}

	private boolean isHoveringInfo(GameContainer gc, int i) 
	{
		Input ipt = gc.getInput();
		
		float mX = ipt.getMouseX(), mY = ipt.getMouseY();
		
		if(mX > offsetX + sepDist*i + Card.WIDTH*i + offsetButton + sepDist + buttonSize
			&& mX < offsetX + sepDist*i + Card.WIDTH*i + offsetButton + sepDist + buttonSize*2
			&& mY > gc.getHeight() - 130
			&& mY < gc.getHeight() - 130 + buttonSize)
			return true;
		
		return false;
	}

	private void drawPlayButton(GameContainer gc, Graphics g, int i) 
	{
		if(isHoveringPlay(gc, i))
			g.setColor(Color.gray);
		else
			g.setColor(Color.white);
		
		g.drawRect(offsetX + sepDist * i + Card.WIDTH * i + offsetButton, 
				gc.getHeight() - 130, 
				buttonSize, 
				buttonSize);
		g.drawString("P", 
				offsetX + sepDist * i + Card.WIDTH * i + offsetButton, 
				gc.getHeight() - 130);
	}

	private boolean isHoveringPlay(GameContainer gc, int i) 
	{
		Input ipt = gc.getInput();
		
		float mX = ipt.getMouseX(), mY = ipt.getMouseY();
		
		if(mX > offsetX + sepDist*i + Card.WIDTH*i + offsetButton
			&& mX < offsetX + sepDist*i + Card.WIDTH*i + offsetButton + buttonSize
			&& mY > gc.getHeight() - 130
			&& mY < gc.getHeight() - 130 + buttonSize)
			return true;
		
		return false;
	}

	private void drawCard(GameContainer gc, Graphics g, int i) 
	{
		g.drawRect(offsetX + sepDist * i + Card.WIDTH * i, 
				gc.getHeight() - 75, 
				Card.WIDTH, 
				Card.HEIGHT);
	
		g.drawString(cards.get(i).getUnit().getName(), 
				offsetX + sepDist * i + Card.WIDTH * i + 10, 
				gc.getHeight() - 60);			
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		Input input = gc.getInput();
		
		switch(SceneBattle.PHASE)
		{
			case SceneBattle.STANDBY:
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{	
					for(int i = 0 ; i < cards.size() ; i++)
					{
						if(isHoveringCard(gc, i))
						{
							clickedCard = cards.get(i);
							SceneBattle.PHASE = SceneBattle.PLAY_OR_INFO;
							break;
						}
					}
				}
				break;
			case SceneBattle.PLAY_OR_INFO:
				if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					for(int i = 0 ; i < cards.size() ; i++)
					{
						if(isHoveringPlay(gc, i))
						{
							SceneBattle.PHASE = SceneBattle.SUMMONING;
							// Afficher les cases ou on peut poser l'unité
							SceneBattle.board.setSummonPossibilities();
							break;
						}
					}
				}
				
				if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
				{
					clickedCard = null;
					SceneBattle.PHASE = SceneBattle.STANDBY;
				}
				break;
		}
	} 
	
	public boolean isHoveringCard(GameContainer gc, int i)
	{
		Input input = gc.getInput();
		
		float mX = input.getMouseX();
		float mY = input.getMouseY();
		
		if(mX > offsetX + sepDist * i + Card.WIDTH * i && mX < offsetX + sepDist * i + Card.WIDTH * i + Card.WIDTH
				&& mY > gc.getHeight() - 75 && mY < gc.getHeight())
			return true;
		
		return false;
	}
	
	public void removeCard(Card c)
	{
		cards.remove(c);
	}
	
	public boolean isEmpty()
	{
		return cards.size() == 0 ? true : false;
	}
}
