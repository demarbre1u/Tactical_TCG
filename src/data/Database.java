package data;

import java.util.HashMap;

public class Database 
{	
	public static HashMap<String, Unit> units;
	
	public Database()
	{
		units = new HashMap<String, Unit>();
		
		initUnits();
	}

	private void initUnits() 
	{
		// Unités mobiles
		units.put("soldier", new Unit(1, 1, 2, "Soldier", false, "melee"));
		units.put("knight", new Unit(2, 2, 3, "Knight", false, "spear"));
		units.put("king", new Unit(3, 3, 1, "King", false, "range"));
		units.put("mage", new Unit(2, 2, 2, "Mage", false, "range"));
		units.put("archer", new Unit(2, 2, 2, "Archer", false, "range"));
		
		// Les unités bâtiments		
		units.put("tower", new Unit(0, 10, 0, "Tower", true, "none"));
		units.put("house", new Unit(0, 5, 0, "House", true, "none"));
		units.put("castle", new Unit(0, 20, 0, "Shop", true, "none"));
	}
}
