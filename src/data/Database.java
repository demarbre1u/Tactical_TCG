package data;

import java.util.HashMap;

public class Database 
{	
	public static HashMap<String, Unit> units;
	
	public Database()
	{
		units = new HashMap<String, Unit>();
		
		initCards();
	}

	private void initCards() 
	{
		// Unités mobiles
		units.put("soldier", new Unit(1, 1, 2, "Soldier", false));
		units.put("knight", new Unit(2, 2, 3, "Knight", false));
		units.put("king", new Unit(3, 3, 1, "King", false));
	
		// Les unités bâtiments
		units.put("tower", new Unit(0, 10, 0, "Tower", true));
		units.put("house", new Unit(0, 5, 0, "House", true));
		units.put("castle", new Unit(0, 20, 0, "Castle", true));
	}
}
