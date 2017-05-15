package scene;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import main.Main;

public class SceneManager 
{
	private HashMap<String, SceneBase> scenes;
	private SceneBase currentScene;
	private SceneBase backgroundScene;

	public SceneManager() 
	{
		// On crée toutes les Scenes du jeu
		scenes = new HashMap<String, SceneBase>();
		scenes.put("battle", new SceneBattle());
		scenes.put("gameOver", new SceneGameOver());
		scenes.put("victory", new SceneVictory());
		
		currentScene = scenes.get("battle");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		if(backgroundScene != null)
		{
			backgroundScene.render(gc, sbg, g);
		}
		
		currentScene.render(gc, sbg, g);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		currentScene.update(gc, sbg, delta);
	}
	
	public void pushToTop(SceneBase scene)
	{
		backgroundScene = currentScene;
		currentScene = scene;
	}
	
	public void pushToTop(String scene)
	{
		backgroundScene = currentScene;
		currentScene = scenes.get(scene);
	}
	
	public void resumeBackgroundScene()
	{
		if(backgroundScene == null)
			return;
		
		currentScene = backgroundScene;
	}
}
