package scene;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class SceneBase 
{
	public abstract void init();
	
	public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics g);

	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta);
	
}
