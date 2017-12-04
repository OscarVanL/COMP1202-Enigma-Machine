
public class EnigmaMachine {

	private Plugboard plugboard;
	private BasicRotor[] rotor = new BasicRotor[3];
	private Reflector reflector;
	private boolean debugMode = false;
	
	/**
	 * EnigmaMachine constructor. Instantiates a new Plugboard object. 
	 */
	EnigmaMachine() {
		plugboard = new Plugboard();
	}
	
	/**
	 * Adds a plug to the plugboard from two characters to be at either end of a plug
	 * @param char1: Character to be at end 1
	 * @param char2: Character to be at end 2
	 */
	void addPlug (char char1, char char2) {
		plugboard.addPlug(char1,  char2);
	}
	
	/**
	 * Removes all plugs in plugboard
	 */
	void clearPlugboard() {
		plugboard.clear();
	}
	
	/**
	 * Gets the letter at the end of a plug in the plugboard
	 * @param plugNumber: Number plug to get from (Numbered from 1 in order of creation)
	 * @param end: End of plug to check (1/2 expected)
	 * @return The letter at the plug's end as an int (although is in ASCII numbering, not 0-25)
	 * @throws Exception: Invalid plug end entered (not 1/2)
	 */
	int getPlugEnd(int plugNumber, int end) throws Exception {
		return plugboard.getPlugEnd(plugNumber, end);
	}
	
	/**
	 * Sets one plug end in a defined plug pair to a character
	 * @param plugNumber: Number plug to assign (Numbered from 1 in order of creation)
	 * @param character: Letter to change plug end to (char)
	 * @param end: Which side of the plug to assign (1 or 2 expected)
	 * @throws Exception
	 */
	void setPlugEnd(int plugNumber, char character, int end) throws Exception {
		plugboard.setPlug(plugNumber, end, character);
	}
	
	/**
	 * Implements getNumPlugs method in EnigmaMachine too
	 * @return integer number of plugs in EnigmaMachine's plugboard.
	 */
	int getNumPlugs() {
		return plugboard.getNumPlugs();
	}
	
	/**
	 * Setter method for Rotor in EnigmaMachine
	 * @param rotor: Rotor object (BasicRotor or TurnoverRotor)
	 * @param slot: Slot to add Rotor object to in EnigmaMachine
	 */
	void addRotor(BasicRotor rotor, int slot) {
		this.rotor[slot] = rotor;
	}
	
	/**
	 * Getter method for a Rotor in a given slot in the EnigmaMachine
	 * @param slot: Slot to retrieve Rotor from
	 * @return Rotor in that slot
	 */
	BasicRotor getRotor(int slot) {
		return rotor[slot];
	}
	
	/**
	 * Returns whether the rotor is instanceof BasicRotor or is instanceof TurnoverRotor
	 * @param slot: Slot the rotor is occupying
	 * @return "BasicRotor" if it is instanceof BasicRotor, or "TurnoverRotor" if it is an instanceof TurnoverRotor
	 * @throws Exception: If the Rotor wasn't an instance of either, then it throws an exception.
	 */
	String getRotorInstanceType(int slot) throws Exception {
		if (this.rotor[slot] instanceof BasicRotor) {
			return "BasicRotor";
		} else if (this.rotor[slot] instanceof TurnoverRotor) {
			return "TurnoverRotor";
		} else {
			throw new Exception("Rotor Type was determined to not be instanceof BasicRotor OR TurnoverRotor, something is wrong!");
		}
	}
	
	/**
	 * Setter method for EnigmaMachine's reflector
	 * @param reflector: Reflector to use in EnignaMachine
	 */
	void addReflector(Reflector reflector) {
		this.reflector = reflector;
	}
	
	/**
	 * Getter method for the EnigmaMachine's reflector
	 * @return Reflector object in EnigmaMachine
	 */
	Reflector getReflector() {
		return this.reflector;
	}
	
	/**
	 * Sets the position for a rotor in a given slot
	 * @param slot: Slot occupying rotor to change position of (0-2 expected)
	 * @param position: Position to be changed to (0-25 expected)
	 * @throws Exception: Invalid slot or position used
	 */
	void setPosition(int slot, int position) throws Exception {
		rotor[slot].setPosition(position);
	}
	
	/**
	 * Encodes a letter and returns the encoded letter. Used for encrypting/decrypting.
	 * @param input: Character to be encoded as a char (NOT as a 0-25 integer, this conversion occurs in this function)
	 * @return Encoded letter as a 0-25 integer representation
	 */
	int encodeLetter(char input) {
		if (debugMode) {
			return encodeLetterDebug(input);
		} else {
			int letter = input;
			
			//If there exists a plug in plugboard for that letter, switch it
			for (Plug plug : plugboard.plugList) {
				if (plug.getEnd1() == input) {
					letter = plug.getEnd2();
				} else if (plug.getEnd2() == input) {
					letter = plug.getEnd1();
				}
			}
			
			letter = letter - 'A'; //Converts from ASCII char to a 0-25 integer representation
			
			//Applies rotor substitutions for rotor 0, 1, then 2
			letter = rotor[0].substitute(letter);
			letter = rotor[1].substitute(letter);
			letter = rotor[2].substitute(letter);
			//Applies the reflector substitution
			letter = reflector.substitute(letter);
			//Applies the rotor inverse substitutions for rotor 2, 1, 0
			letter = rotor[2].substituteBack(letter);
			letter = rotor[1].substituteBack(letter);
			letter = rotor[0].substituteBack(letter);
			
			//Runs plugs on output from rotors if applicable.
			for (Plug plug : plugboard.plugList) {
				if ((plug.getEnd1()-'A') == letter) {
					letter = plug.getEnd2() - 'A';
				} else if ((plug.getEnd2()-'A') == letter) {
					letter = plug.getEnd1() - 'A';
				}
			}
			
			rotor[0].rotate();
			return letter;
		}
	}
	
	/**
	 * Encodes a letter and returns the encoded letter. Used for encrypting/decrypting.
	 * Debug version of this function prints the state of the encoded letter throughout the function.
	 * @param input: Character to be encoded as a char (NOT as a 0-25 integer, this conversion occurs in this function)
	 * @return Encoded letter as a 0-25 integer representation
	 */
	int encodeLetterDebug(char input) {
		int letter = input;
		
		//If there exists a plug in plugboard for that letter, switch it
		for (Plug plug : plugboard.plugList) {
			if (plug.getEnd1() == input) {
				letter = plug.getEnd2();
				System.out.println("Plug exists, letter changed to: " + (char)(letter) + "(" + (letter-'A') + ")" );
			} else if (plug.getEnd2() == input) {
				letter = plug.getEnd1();
				System.out.println("Plug exists, letter changed to: " + (char)(letter) + "(" + (letter-'A') + ")");
			}
		}
		
		letter = letter - 'A'; //Converts from ASCII char to a 0-25 integer representation
		
		//Applies rotor substitutions for rotor 1, 2, then 3
		System.out.println("	==Rotor 0==");
		letter = rotor[0].substitute(letter);
		System.out.println("	After rotor 0: " + letter);
		System.out.println("	==Rotor 1==");
		letter = rotor[1].substitute(letter);
		System.out.println("	After rotor 1: " + letter);
		System.out.println("	==Rotor 2==");
		letter = rotor[2].substitute(letter);
		System.out.println("	After rotor 2: " + letter);
		//Applies the reflector substitution
		System.out.println("	==Reflector==");
		letter = reflector.substitute(letter);
		System.out.println("	After reflector: " + letter);
		//Applies the rotor inverse substitutions for rotor 3, 2, 1
		System.out.println("	==Rotor 2 Substitute==");
		letter = rotor[2].substituteBack(letter);
		System.out.println("	After rotor 2 substitute back: " + letter);
		System.out.println("	==Rotor 1 Substitute==");
		letter = rotor[1].substituteBack(letter);
		System.out.println("	After rotor 1 substitute back: " + letter);
		System.out.println("	==Rotor 0 Substitute==");
		letter = rotor[0].substituteBack(letter);
		System.out.println("	After rotor 0 substitute back: " + letter);
		
		//Runs plugs on output from rotors if applicable.
		for (Plug plug : plugboard.plugList) {
			if ((plug.getEnd1()-'A') == letter) {
				letter = plug.getEnd2() - 'A';
				System.out.println("Plug exists, letter changed to: " + (char)(letter) + "(" + letter + ")" );
			} else if ((plug.getEnd2()-'A') == letter) {
				letter = plug.getEnd1() - 'A';
				System.out.println("Plug exists, letter changed to: " + (char)(letter) + "(" + letter + ")");
				
			}
		}
		
		System.out.println("===Encoded letter: " + (char)(letter + 'A') + "===");
		//Rotates rotor after letter encoding/decryption complete
		rotor[0].rotate();
		return letter;
	}
	
	/**
	 * Toggles the state of Debug mode. Is false at creation of EnigmaMachine.
	 * When enabled, this results in encodeLetterDebug() being used instead of encodeLetter(), this prints the value of the encoded letter at many stages which can help with debugging and tracing.
	 */
	void toggleDebugMode() {
		this.debugMode = !this.debugMode;
	}
	
	/**
	 * Checks the status of whether the EnigmaMachine is in debug mode or not.
	 * Debug mode prints the progress of the encoding of letters at many stages to help spot where any faults occur.
	 * @return: true (Program is in debug mode), false (Program is not in debug mode)
	 */
	boolean isDebugging() {
		return this.debugMode;
	}
	
	/**
	 * Used to determine whether a possible (complete) rotor configuration is valid
	 * Example of possible: I, II, III
	 * Example of impossible:, I, I, V (I repeated twice, but types must be unique according to specification).
	 * @return true: Rotor configuration is valid. false: Rotor configuration is invalid
	 */
	public static boolean isRotorConfigValid(BasicRotor slot0, BasicRotor slot1, BasicRotor slot2) {
		if (slot0.getType().equals(slot1.getType()) || slot0.getType().equals(slot2.getType()) || slot1.getType().equals(slot2.getType())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Prints the configuration of an enigma machine. Used after a working configuration is found by a bombe test.
	 * @param enigma: An enigma machine to print the configuration of.
	 * @throws Exception: If invalid Enigma Machine components are accessed (eg: A plug end that is not 1 or 2)
	 */
	public void printState() throws Exception {
		System.out.println("	=== Enigma Configuration ===");
		
		System.out.println("	Plugs: ");
		for (int i=0; i<this.getNumPlugs(); i++) {
			System.out.println("		" + (char)this.getPlugEnd(i, 1) + " - " + (char)this.getPlugEnd(i, 2));
		}
		
		System.out.println("	Rotor Configurations: ");
		System.out.println("		Slot 0:");
		System.out.println("			Instance Type: " + this.getRotorInstanceType(0) + " Type: " + this.getRotor(0).getType() + " Position: " + this.getRotor(0).getPosition());
		System.out.println("		Slot 1:");
		System.out.println("			Instance Type: " + this.getRotorInstanceType(1) + " Type: " + this.getRotor(1).getType() + " Position: " + this.getRotor(1).getPosition());
		System.out.println("		Slot 2:");
		System.out.println("			Instance Type: " + this.getRotorInstanceType(2) + " Type: " + this.getRotor(2).getType() + " Position: " + this.getRotor(2).getPosition());
		
		System.out.println("	Reflector Configuration: ");
		System.out.println("		Reflector Type: " + this.getReflector().getType());
		System.out.println("	=== \n");
	}
}
