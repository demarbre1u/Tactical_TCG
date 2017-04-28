package data;

import java.util.HashMap;

public class Database 
{	
	public static HashMap<String, Card> cards;
	
	public Database()
	{
		cards = new HashMap<String, Card>();
		
		initCards();
	}

	private void initCards() 
	{
		cards.put("soldier", new Card(new Unit(1, 1, 2, "Soldier")));
		cards.put("knight", new Card(new Unit(2, 2, 3, "Knight")));
		cards.put("king", new Card(new Unit(3, 3, 1, "King")));
	}
}
