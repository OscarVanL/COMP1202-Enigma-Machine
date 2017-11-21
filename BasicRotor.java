public class BasicRotor extends Rotor {
	protected final int[] I = {4, 10, 12, 5, 11, 6, 3, 16, 21, 25, 13, 19, 14, 22, 24, 7, 23, 20, 18, 15, 0, 8, 1, 17, 2, 9};
	protected final int[] II = {0, 9, 3, 10, 18, 8, 17, 20, 23, 1, 11, 7, 22, 19, 12, 2, 16, 6, 25, 13, 15, 24, 5, 21, 14, 4};
	protected final int[] III = {1, 3, 5, 7, 9, 11, 2, 15, 17, 19, 23, 21, 25, 13, 24, 4, 8, 22, 6, 0, 10, 12, 20, 18, 16, 14};
	protected final int[] IV = {4, 18, 14, 21, 15, 25, 9, 0, 24, 16, 20, 8, 17, 7, 23, 11, 13, 5, 19, 6, 10, 3, 2, 12, 22, 1};
	protected final int[] V = {21, 25, 1, 17, 6, 8, 19, 24, 20, 15, 18, 3, 13, 7, 11, 23, 0, 22, 12, 9, 16, 14, 5, 4, 2, 10};
	
	//Inverse I  [20, 22, 24, 6, 0, 3, 5, 15, 21, 25, 1, 4, 2, 10, 12, 19, 7, 23, 18, 11, 17, 8, 13, 16, 14, 9]
	//Inverse II [0, 9, 15, 2, 25, 22, 17, 11, 5, 1, 3, 10, 14, 19, 24, 20, 16, 6, 4, 13, 7, 23, 12, 8, 21, 18]
	//Inverse III[19, 0, 6, 1, 15, 2, 18, 3, 16, 4, 20, 5, 21, 13, 25, 7, 24, 8, 23, 9, 22, 11, 17, 10, 14, 12]
	//Inverse IV [7, 25, 22, 21, 0, 17, 19, 13, 11, 6, 20, 15, 23, 16, 2, 4, 9, 12, 1, 18, 10, 3, 24, 14, 8, 5]
	//Inverse V  [16, 2, 24, 11, 23, 22, 4, 13, 5, 19, 25, 14, 18, 12, 21, 9, 20, 3, 10, 6, 8, 0, 17, 15, 7, 1]
	
	private int[] inverse = new int[26];
	
	BasicRotor(String type) {
		this.name = type; //Sets name of superclass Rotor.
		initialise(type);
		generateInverse();
	}
	
	/**
	 * Rotor substitution method for 'forward' stages of rotors.
	 * @param An integer representing a letter (0-25) to go through one of the first rotors (1,2 or 3)
	 * @return Letter after passing through rotor, used in next rotor or reflector.
	 */
	@Override
	int substitute(int letterIn) {
		//No rotor position shift, so mapping is simple.
		if (getPosition() == 0) {
			return mapping[letterIn];
		} else {
			int letter;
			//If there is a rotor shift, first this is subtracted from the input letter.
			//Then if this results in a negative letter (below 'a'), 26 is added so it 'loops' back to 'z'
			letter = (letterIn - getPosition());
			System.out.println("		position substitution: " + letter);
			if (letter < 0) {
				letter = (letter + 26);
			}
			//Then the mapping is used to find the encoded letter
			letter = mapping[letter];
			System.out.println("		after mapping: " + letter);
			//Then the rotor shift is added back to this encoded letter
			letter += getPosition();
			System.out.println("		position addition: " + letter);
			//If this encoded letter is above 'z', 25 is subtracted so it 'loops' back to 'a'
			if (letter > 25) {
				letter = (letter - 26);
			}
			return letter;
		}
	}
	
	/**
	 * Rotor substitution method for 'backwards' stages of rotors.
	 * @param An integer representing a letter (0-25) to go through a returning rotor (3, 2 or 1)
	 * @return Letter after passing through returning rotor, either used with next rotor or final letter.
	 */
	int substituteBack(int letterIn) {
		//No rotor position shift, so inverse mapping is simple.
		if (getPosition() == 0) {
			return inverse[letterIn];
		} else {
			//If there is a rotor shift, first this is subtracted from the input letter.
			//Then if this results in a negative letter (below 'a'), 26 is added so it 'loops' back to 'z'
			int letter;
			letter = (letterIn - getPosition());
			System.out.println("		position substitution: " + letter);
			if (letter < 0) {
				letter = (letter + 26);
			}
			//Then the inverse mapping is used to find the encoded letter
			letter = inverse[letter];
			System.out.println("		after mapping inverse: " + letter);
			//Then the rotor shift is added back to this encoded letter
			letter += getPosition();
			System.out.println("		position addition: " + letter);
			//If this encoded letter is above 'z', 25 is subtracted so it 'loops' back to 'a'
			if (letter > 25) {
				letter = (letter - 26);
			}
			return letter;
		}
	}
	
	/**
	 * Increments the rotor position, if it is at its maximum position then it is set to zero.
	 */
	void rotate() {
		System.out.println("BasicRotor Basic bitch rotate method");
		incrementPosition();
		if (getPosition() == ROTORSIZE) {
			setPosition(0);
		}
		System.out.println("Rotor advanced, new position: " + getPosition());
	}
	
	/**
	 * Generates an inverse array from the mapping array.
	 */
	void generateInverse() {
		for (int i : mapping) {
			inverse[mapping[i]] = i;
		}
	}

	@Override
	void initialise(String type) {
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
