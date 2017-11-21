
public abstract class Rotor {
	static final int ROTORSIZE = 26;
	
	protected String name;
	private int position;
	protected int[] mapping;
	
	abstract void initialise(String type);
	
	abstract int substitute(int letter); 
	
	void setPosition(int pos) {
		this.position = pos;
	}
	
	int getPosition() {
		return this.position;
	}
	
	void incrementPosition() {
		this.position++;
	}
}
