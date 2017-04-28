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
		// Unités mobiles
		cards.put("soldier", new Card(new Unit(1, 1, 2, "Soldier", false)));
		cards.put("knight", new Card(new Unit(2, 2, 3, "Knight", false)));
		cards.put("king", new Card(new Unit(3, 3, 1, "King", false)));
	
		// Les unités bâtiments
		cards.put("tower", new Card(new Unit(0, 10, 0, "Tower", true)));
		cards.put("house", new Card(new Unit(0, 5, 0, "House", true)));
		cards.put("castle", new Card(new Unit(0, 20, 0, "Castle", true)));
	}
}
