package data;

public class Unit 
{
	private int atk, def, mvt;

	public Unit()
	{
		atk = 1;
		def = 1;
		mvt = 4;
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
}
