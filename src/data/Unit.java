package data;

public class Unit 
{
	private int atk, def, mvt;
	
	private boolean isBuilding, isEnemy, isMoving;
	
	private String name;
	
	public Unit(int a, int d, int m, String n, boolean b)
	{
		atk = a;
		def = d;
		mvt = m;
		name = n;
		isBuilding = b;
	}
	
	public int getAtk() 
	{
		return atk;
	}

	public void setAtk(int atk) 
	{
		this.atk = atk;
	}

	public int getDef() 
	{
		return def;
	}

	public void setDef(int def) 
	{
		this.def = def;
	}

	public int getMvt() 
	{
		return mvt;
	}

	public void setMvt(int mvt) 
	{
		this.mvt = mvt;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isBuilding() 
	{
		return isBuilding;
	}

	public boolean isEnemy() 
	{
		return isEnemy;
	}

	public void setEnemy(boolean e)
	{
		isEnemy = e;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
}