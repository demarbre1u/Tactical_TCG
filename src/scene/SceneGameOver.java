package scene;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import main.Main;

public class SceneGameOver extends SceneBase 
{

	@Override
	public void init() {}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		g.setColor(Color.white);
		
		Font font = gc.getDefaultFont();
		
		String gameOver = "Defeat";
		float posX = Main.WIDTH / 2f - font.getWidth(gameOver) / 2f;
		float posY = Main.HEIGHT / 2;	
		
		g.drawString(gameOver, posX, posY);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		
	}

}
