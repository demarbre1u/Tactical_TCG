package scene;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import data.Card;
import data.Unit;
import game.Game;
import main.Main;

public class SceneCardInfo extends SceneBase
{	
	private Card card;
	
	private float cardWidth;
	private float cardHeight;
	
	public SceneCardInfo(Card c)
	{
		card = c;
		
		cardWidth = 225;
		cardHeight = 300;
	}
	
	public void init() {}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		// On met un fond sympa semi-opaque
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		// On dessine la carte
		float offsetX = 400;
		float offsetY = Main.HEIGHT /2f - cardHeight / 2f;
		Unit unit = card.getUnit();
		
		g.setColor(Color.white);
		g.drawRect(offsetX, offsetY, cardWidth, cardHeight);
		
		g.drawRect(offsetX + 10, offsetY + 10, cardWidth - 20, 35);
		g.drawString(unit.getName(), offsetX + 15, offsetY + 17);
		
		g.drawRect(offsetX + 10, offsetY + 20 + 35, cardWidth - 20, cardHeight - 30 - 35);
		
		// On dessine le panneau d'infos à droite
		offsetX += cardWidth + 20;
		offsetY += 10;
		
		g.drawString("Name : " + unit.getName(), offsetX, offsetY);
		
		if(unit.isBuilding())
		{
			g.drawString("Type : Building", offsetX, offsetY + 30);
			
			g.drawString("HP : " + unit.getDef(), offsetX, offsetY + 90);
			
			g.drawString("Effect : None", offsetX, offsetY + 150);
		}
		else
		{
			g.drawString("Type : Unit", offsetX, offsetY + 30);
			g.drawString("Weapon Type : " + unit.getAttackType(), offsetX, offsetY + 60);
			
			g.drawString("Attack : " + unit.getAtk(), offsetX, offsetY + 120);
			g.drawString("Defense : " + unit.getDef(), offsetX, offsetY + 150);
			g.drawString("Energy : " + unit.getMvt(), offsetX, offsetY + 180);
			
			g.drawString("Effect : None", offsetX, offsetY + 240);
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
		{
			Game.sceneManager.resumeBackgroundScene();
		}
	}
}
