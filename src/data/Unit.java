package data;

public class Unit 
{
	private int atk, def, mvt;
	private String name;
	
	public Unit(int a, int d, int m, String n)
	{
		atk = a;
		def = d;
		mvt = m;
		
		name = n;
	}
	
	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getMvt() {
		return mvt;
	}

	public void setMvt(int mvt) {
		this.mvt = mvt;
	}
	
	public String getName()
	{
		return name;
	}
}
