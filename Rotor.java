
public abstract class Rotor {
	static final int ROTORSIZE = 26;
	
	protected String name;
	private int position;
	protected int[] mapping;
	
	Rotor() {
		this("I", 1);
	}
	
	//This will be used in Reflector (which has no position), hence only type as parameter.
	Rotor(String type) {
		this.name = type;
	}
	
	Rotor(String type, int position) {
		this.name = type;
		this.position = position;
	}
	
	public abstract void initialise(String type);
	
	public abstract int substitute(int letter); 
	
	public void setPosition(int pos) {
		this.position = pos;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public void incrementPosition() {
		this.position++;
	}
	
	public String getType() {
		return name;
	}
}
