
public abstract class Rotor {
	static final int ROTORSIZE = 26;
	
	protected String name;
	private int position;
	protected int[] mapping;
	
	/**
	 * Constructor used in reflector. Takes type as parameter, not positions, as reflector cannot have a position.
	 * @param type: Type of reflector, (expected: I / II)
	 */
	Rotor(String type) {
		this.name = type;
		this.position = 0; //Reflector position is assigned to zero, as per answer in Coursework FAQ.
	}
	
	/**
	 * Constructor used in BasicRotor and TurnoverRotor. Takes type and position of rotors as parameters.
	 * @param type: Type of Rotor, (expected I/II/III/IV/V)
	 * @param position, Starting position of Rotor, (expected 0-25)
	 */
	Rotor(String type, int position) {
		this.name = type;
		this.position = position;
	}
	
	public abstract void initialise(String type);
	
	/**
	 * Abstract substitute method so that child classes (BasicRotor, TurnoverRotor & Reflector) must implement substitute
	 * @param letter: Letter to substitute as integer (0-25 expected)
	 * @return Letter after substitution (0-25)
	 */
	public abstract int substitute(int letter); 
	
	/**
	 * Setter method for Rotor's position attribute
	 * @param pos: Integer position to assign to Rotor's position attribute
	 * @throws Exception: Invalid position as argument, (0-25 expected)
	 */
	public void setPosition(int pos) throws Exception {
		if (pos < 26 && pos >= 0) {
			this.position = pos;
		} else {
			throw new Exception("Invalid position assigned to Rotor, expected value between 0 and 25" + pos);
		}
	}
	
	/**
	 * Getter method for Rotor's current position
	 * @return: Integer value of rotor's current position, (0-25 expected)
	 */
	public int getPosition() {
		return this.position;
	}
	
	/**
	 * Increases the position by one, be careful as this is NOT the same as rotate(), and using this instead of
	 * rotate() could cause issues with the position not 'wrapping around' when reaching ROTORSIZE (26).
	 */
	public void incrementPosition() {
		this.position++;
	}
	
	/**
	 * Getter method for Rotor's type 
	 * @return Rotor's 'name' attribute as a String, (I/II/III/IV/V expected)
	 */
	public String getType() {
		return name;
	}
}
