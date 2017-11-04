
public class Rotor {
	static int ROTORSIZE = 26;
	
	protected String name;
	protected int position;
	protected int[] mapping;
	
	void initialise(String str) {
		// TODO add logic for initialise and rename argument (note: is overridden in subclasses)
	}
	
	int substitute(int integer) {
		return integer;
		// TODO add logic for substitute and rename argument (note: is overridden in subclasses) 
	}
	
	void setPosition(int pos) {
		this.position = pos;
	}
	
	int getPosition() {
		return this.position;
	}
}
