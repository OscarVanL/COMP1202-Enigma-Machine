
public class Reflector extends Rotor {

	private final int[] ReflectorI = {24, 17, 20, 7, 16, 18, 11, 3, 15, 23, 13, 6, 14, 10, 12, 8, 4, 1, 5, 25, 2, 22, 21, 9, 0, 19};
	private final int[] ReflectorII = {5, 21, 15, 9, 8, 0, 14, 24, 4, 3, 17, 25, 23, 22, 6, 2, 19, 10, 20, 16, 18, 1, 13, 12, 7, 11};
		
	/**
	 * Constructor for Reflector.
	 * @param type: Type of reflector, either I or II
	 */
	Reflector(String type) {
		super(type);
		initialise(type);
	}
	
	/**
	 * Initialises the reflector from its type represented as a String 
	 * @param type: Used to determine which mapping to use
	 */
	@Override
	public void initialise(String type) {
		this.name = type;
		if (type.equals("ReflectorI") || type.equals("I")) {
			this.mapping = ReflectorI;
		} else if (type.equals("ReflectorII") || type.equals("II")) {
			this.mapping = ReflectorII;
		} else {
			this.mapping = null;
			System.err.println("Invalid rotor type chosen. (This error has not been handled)");
		}
	}
	
	/**
	 * Takes output from Rotors and maps with reflector's substitutions.
	 * @param letterIn: Letter (represented as 0-25 integer) to map with Reflector's substitution
	 * @return Return the mapped letter (as 0-25 integer)
	 */
	@Override
	public int substitute(int letterIn) {
		return mapping[letterIn];
	}

	/**
	 * Method for changing the Reflector's mapping type (Do not use during execution)
	 * @param type: Type of Reflector mapping to use (I/II)
	 */
	@Override
	public void setMappingType(String type) {
		initialise(type);
		this.name = type;
	}
}
