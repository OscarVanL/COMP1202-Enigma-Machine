public class BasicRotor extends Rotor {
	protected final int[] I = {4, 10, 12, 5, 11, 6, 3, 16, 21, 25, 13, 19, 14, 22, 24, 7, 23, 20, 18, 15, 0, 8, 1, 17, 2, 9};
	protected final int[] II = {0, 9, 3, 10, 18, 8, 17, 20, 23, 1, 11, 7, 22, 19, 12, 2, 16, 6, 25, 13, 15, 24, 5, 21, 14, 4};
	protected final int[] III = {1, 3, 5, 7, 9, 11, 2, 15, 17, 19, 23, 21, 25, 13, 24, 4, 8, 22, 6, 0, 10, 12, 20, 18, 16, 14};
	protected final int[] IV = {4, 18, 14, 21, 15, 25, 9, 0, 24, 16, 20, 8, 17, 7, 23, 11, 13, 5, 19, 6, 10, 3, 2, 12, 22, 1};
	protected final int[] V = {21, 25, 1, 17, 6, 8, 19, 24, 20, 15, 18, 3, 13, 7, 11, 23, 0, 22, 12, 9, 16, 14, 5, 4, 2, 10};
	
	private int[] inverse = new int[26];
	
	 /**
	  * Constructor for BasicRotor objects.
	  * @param type: Type of rotor, I/II/III/IV/V
	  * @param position: Starting position of rotor, 0-25
	  */
	BasicRotor(String type, int position) {
		super(type, position);
		this.name = type;
		initialise(type);
		generateInverse();
	}
	
	/**
	 * Rotor substitution method for 'forward' stages of rotors.
	 * @param letterIn: An integer representing a letter (0-25) to go through one of the first rotors (1,2 or 3)
	 * @return Letter after passing through rotor, used in next rotor or reflector.
	 */
	@Override
	public int substitute(int letterIn) {
		//No rotor position shift, so mapping is simple.
		if (getPosition() == 0) {
			return mapping[letterIn];
		} else {
			int letter;
			//If there is a rotor shift, first this is subtracted from the input letter.
			//Then if this results in a negative letter (below 'A'), 26 is added so it 'loops' back to 'Z'
			letter = (letterIn - getPosition());
			if (letter < 0) {
				letter += 26;
			}
			//Then the mapping is used to find the encoded letter
			letter = mapping[letter];
			//Then the rotor shift is added back to this encoded letter
			letter += getPosition();
			//If this encoded letter is above 'Z', 26 is subtracted so it 'loops' back to 'A'
			if (letter > 25) {
				letter -= 26;
			}
			return letter;
		}
	}
	
	/**
	 * Rotor substitution method for 'backwards' stages of rotors.
	 * @param letterIn: An integer representing a letter (0-25) to go through a returning rotor (3, 2 or 1)
	 * @return Letter after passing through returning rotor, either used with next rotor or final letter.
	 */
	public int substituteBack(int letterIn) {
		//No rotor position shift, so inverse mapping is simple.
		if (getPosition() == 0) {
			return inverse[letterIn];
		} else {
			//If there is a rotor shift, first this is subtracted from the input letter.
			//Then if this results in a negative letter (below 'A'), 26 is added so it 'loops' back to 'Z'
			int letter;
			letter = (letterIn - getPosition());
			if (letter < 0) {
				letter += 26;
			}
			//Then the inverse mapping is used to find the encoded letter
			letter = inverse[letter];
			//Then the rotor shift is added back to this encoded letter
			letter += getPosition();
			//If this encoded letter is above 'Z', 25 is subtracted so it 'loops' back to 'A'
			if (letter > 25) {
				letter = (letter - 26);
			}
			return letter;
		}
	}
	
	/**
	 * Increments the rotor position, if it is at its maximum position then it is set to zero.
	 */
	public void rotate() {
		incrementPosition();
		if (getPosition() == ROTORSIZE) {
			try {
				setPosition(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Generates an inverse array from the rotor's mapping array.
	 */
	public void generateInverse() {
		for (int i : mapping) {
			inverse[mapping[i]] = i;
		}
	}

	/**
	 * Determines from the rotor type (String) what the mapping should be.
	 * @param type: Type of rotor, passed as a String.
	 */
	@Override
	public void initialise(String type) {
		switch(type) {
		case("I"):
			this.mapping = I;
			break;
		case("II"):
			this.mapping = II;
			break;
		case("III"):
			this.mapping = III;
			break;
		case("IV"):
			this.mapping = IV;
			break;
		case("V"):
			this.mapping = V;
			break;
		default:
			System.out.println("Invalid rotor type chosen in BasicRotor constructor. (This error has not been handled");
			// TODO add handling for invalid rotor choice? (maybe an extension)
	}
	}
}
