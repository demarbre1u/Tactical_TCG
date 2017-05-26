package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import main.Main;

public class Deck 
{
	private ArrayList<Card> deck;
	
	// Path sera le chemin du fichier ou se trouvera le contenu du deck plus tard 
	public Deck(String path)
	{
		loadDeck(path);
		
		shuffleCards();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) 
	{
		// On dessine une carte qui représente le deck
		g.setColor(Color.white);
		g.drawRect(Main.WIDTH - 200 + 24, Main.HEIGHT - 75, Card.WIDTH, Card.HEIGHT);
		
		// On dessine le nombre de cartes restantes dans le deck
		g.drawString("Remaining :", Main.WIDTH - 200 + 24 + 25, Main.HEIGHT - 65);
		g.drawString("" + deck.size(), Main.WIDTH - 200 + 24 + 65, Main.HEIGHT - 35);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) 
	{
		
	}
	
	// Une méthode qui permet de piocher la premiere carte du deck 
	public Card drawCardFromDeck()
	{
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
	public boolean isEmpty()
	{
		return deck.size() == 0;
	}
	
	// Une méthode qui mélangera les cartes du deck 
	private void shuffleCards() 
	{
		Collections.shuffle(deck);
	}

	private void loadDeck(String path)
	{
		deck = new ArrayList<Card>();
		
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			br.lines().forEachOrdered(card -> deck.add(new Card(Database.units.get(card))));
			
			br.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}
}
