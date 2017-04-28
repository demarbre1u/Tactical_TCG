package data;

public class Card 
{
	public final static float WIDTH = 150, HEIGHT = 250;
	
	private Unit unit;
	
	public Card(Unit u)
	{
		unit = u;
	}
	
	public Unit getUnit()
	{
		return unit;
	}
}
