package scene;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SceneManager 
{
	private HashMap<String, SceneBase> scenes;
	private SceneBase currentScene;

	public SceneManager() 
	{
		// On crée toutes les Scenes du jeu
		scenes = new HashMap<String, SceneBase>();
		scenes.put("battle", new SceneBattle());
		
		currentScene = scenes.get("battle");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		currentScene.render(gc, sbg, g);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		currentScene.update(gc, sbg, delta);
	}
}
