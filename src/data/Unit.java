package data;

public class Unit 
{
	private int atk, def, mvt, damageTaken;
	
	private boolean isBuilding, isEnemy, isMoving;
	
	public boolean hasAlreadyMoved = false, hasAlreadyAttacked = false;
	
	private String name, attackType;
	
	public Unit(int a, int d, int m, String n, boolean b, String type)
	{
		atk = a;
		def = d;
		mvt = m;
		name = n;
		attackType = type;
		isBuilding = b;
		
		damageTaken = 0;
	}
	
	public void inflictDamage(int dmg)
	{
		damageTaken += dmg;
		
		if(damageTaken < 0)
			damageTaken = 0;
		
		if(damageTaken > def)
			damageTaken = def;
	}
	
	public int getDamageTaken()
	{
		return damageTaken;
	}
	
	public boolean isDead()
	{
		return def == damageTaken;
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

	public boolean isMoving() 
	{
		return isMoving;
	}

	public void setMoving(boolean isMoving) 
	{
		this.isMoving = isMoving;
	}

	public String getAttackType()
	{
		return attackType;
	}
}