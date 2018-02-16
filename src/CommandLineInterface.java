import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface extends EnigmaMachine {
	private boolean rotorsReady = false;
	private boolean reflectorReady = false;
	
	private Scanner sc = new Scanner(System.in);
	EnigmaFile files = new EnigmaFile();
	
	/**
	 * Instantiates the CommandLineInterface object and starts its menu
	 * @param args
	 */
	public static void main(String[] args) {
		CommandLineInterface cli = new CommandLineInterface();
		cli.menu();
	}
	
	/**
	 * Main menu of CLI
	 * Allows for configuration of all parts of EnigmaMachine before later running it.
	 */
	public void menu() {
		System.out.println("=== Options Menu ===");
		System.out.println("[1] Configure Plugs");
		System.out.println("[2] Configure Rotors");
		System.out.println("[3] Configure Reflector");
		System.out.println("[4] Run Enigma Machine");
		System.out.println("[5] Exit");
		
		switch (validateInt(sc.nextLine())) {
			case 1: 
				configPlugs();
				break;
			case 2:
				configRotors();
				break;
			case 3:
				configReflector();
				break;
			case 4:
				configRun();
				break;
			case 5:
				System.exit(0);
			default:
				System.err.println("Invalid Menu choice, try again");
				menu();
				break;
		}
	}

	/**
	 * Plug configuration menu of CLI
	 * Used to add, modify, clear and list plugs in the EnigmaMachine's Plugboard.
	 */
	public void configPlugs() {
		System.out.println("=== Plugs ===");
		System.out.println("[1] Add a Plug");
		System.out.println("[2] Modify Plug");
		System.out.println("[3] Clear Plugboard");
		System.out.println("[4] List all Plugs");
		System.out.println("[5] Menu");
		
		switch (validateInt(sc.nextLine())) {
			case 1:
				addPlug();
				break;
			case 2:
				modifyPlug();
				break;
			case 3:
				clearPlugboard();
				configPlugs();
				break;
			case 4:
				printPlugs();
				configPlugs();
				break;
			case 5:
				menu();
				break;
			default:
				System.err.println("Invalid Plug Menu choice, try again");
				configPlugs();
				break;
		}
	}
	
	/**
	 * Adds a plug to the plugboard using inputs entered by the user.
	 * This also checks for clashes when adding plugs.
	 */
	public void addPlug() {
		//Each end is read in as a String as Scanner does not supporot .nextChar() or similar
		System.out.println("What letter should the first end of the Plug be connected to?");
		String end1Str = sc.nextLine().toUpperCase();
		
		System.out.println("What letter should the second end of the Plug be connected to?");
		String end2Str = sc.nextLine().toUpperCase();
		
		//Then these strings are converted to chars.
		char end1 = end1Str.charAt(0);
		char end2 = end2Str.charAt(0);
		
		//If statement used as addPlug() returns true if successful, or false if not successful
		if (addPlug(end1, end2)) {
			System.out.println("Plug added successfully!");
		} else {
			System.err.println("Plug ends entered clash with an existing plug. Plug was not added.");
		}
		
		configPlugs();
	}
	
	/**
	 * Used to modify an end on a plug the user defined earlier.
	 */
	public void modifyPlug() {
		System.out.println("Enter the plug number to modify:");
		int plugNum = validateInt(sc.nextLine());
		
		try {
			System.out.println("That plug is: " + (char)this.getPlugEnd(plugNum, 1) + " - " + (char)this.getPlugEnd(plugNum, 2));
		} catch (Exception e) {
			System.err.println("Plug number not found. Plug was not modified.");
			configPlugs();
		}
		
		System.out.println("Which end would you like to modify:");
		System.out.println("[1] End 1 (Left side)");
		System.out.println("[2] End 2 (Right side)");
		//Contained in try-catch statement as if an invalid plug number or plug end is used the program would otherwise crash.
		try {
			switch (validateInt(sc.nextLine())) {
				case 1:
					setPlugEnd(plugNum, inputPlugLetter(), 1);
				case 2:
					setPlugEnd(plugNum, inputPlugLetter(), 2);
			}
		} catch (Exception e) {
			System.err.println("Invalid plug end entered. Plug was not modified.");
		}
		
		configPlugs();
	}
	
	/**
	 * Used to retrieve the user's choice for what letter they wish to change the plug's end to
	 * @return char containing the user's choice.
	 */
	public char inputPlugLetter() {
		System.out.println("What would you like to change it to?");
		String letterStr = sc.nextLine().toUpperCase();
		return letterStr.charAt(0);
	}
	
	/**
	 * Rotor configuration menu of CLI
	 * Used to add, change or list Rotors in the EnigmaMachine.
	 */
	public void configRotors() {
		System.out.println("=== Rotors ===");
		System.out.println("[1] Add all (3) Rotors");
		System.out.println("[2] Change Rotor Type");
		System.out.println("[3] Chage Rotor Position");
		System.out.println("[4] List all Rotors");
		System.out.println("[5] Menu");
		
		switch (validateInt(sc.nextLine())) {
			case 1:
				addRotors();
				break;
			case 2:
				changeRotorType();
				break;
			case 3:
				changeRotorPosition();
				break;
			case 4:
				printRotors();
				configRotors();
				break;
			case 5:
				menu();
				break;
			default:
				System.err.println("Invalid Rotor Menu choice, try again");
				configRotors();
				break;
		}
	}
	
	/**
	 * Adds all 3 Rotors to the EnigmaMachine. This allows the user to state whether each rotor is a BasicRotor or TurnoverRotor as well as their mapping type and starting position.
	 * Applicable TurnoverRotors have their nextRotor attribute set.
	 */
	public void addRotors() {
		ArrayList<BasicRotor> rotors = new ArrayList<BasicRotor>();
		
		int rotorType;
		String type = "";
		int position;
		
		//Adds user's selection of BasicRotor/TurnoverRotors to rotors
		for (int i=0; i<3; i++) {
			System.out.println("==Rotor " + i + "==");
			
			System.out.println("What type of Rotor should Rotor "+ i + " be?");
			System.out.println("[1] Basic Rotor");
			System.out.println("[2] Turnover Rotor");
			rotorType = validateInt(sc.nextLine());
			
			if (rotorType == 1 || rotorType == 2) {
				
				//Gets the user's selections for Mapping Type and Initial Position
				System.out.println("What mapping type should this Rotor use?");
				type = inputRotorType();
				position = inputRotorPosition();
				
				//Finally, instantiates and adds the relevant Rotor type.
				switch (rotorType) {
					case 1:
						rotors.add(new BasicRotor(type, position));
						break;
					case 2:
						rotors.add(new TurnoverRotor(type, position));
						break;
				}
			} else {
				System.err.println("Invalid Rotor Type entered, try again.");
				addRotors();
			}
		}
		
		//Checks if there are any TurnoverRotors that need to have nextRotor attribute set by going through the list backwards
		//Only checks for rotors to go in slot 0 and 1, as rotor to go in slot 2 *cannot* have a nextRotor.
		for (int i=1; i>=0; i--) {
			if (rotors.get(i) instanceof TurnoverRotor) {
				((TurnoverRotor) rotors.get(i)).setNextRotor(rotors.get(i+1)); 
			}
		}
		
		addRotor(rotors.get(0), 0);
		addRotor(rotors.get(1), 1);
		addRotor(rotors.get(2), 2);
		this.rotorsReady = true;
		
		configRotors();
	}
	
	/**
	 * Gets the user's choice of mapping type for the rotor and returns it, this choice is validated.
	 * @return String containing the mapping type
	 */
	public String inputRotorType() {
		System.out.println("[1] I");
		System.out.println("[2] II");
		System.out.println("[3] III");
		System.out.println("[4] IV");
		System.out.println("[5] V");
		
		switch (validateInt(sc.nextLine())) {
			case 1:
				return "I";
			case 2:
				return "II";
			case 3:
				return "III";
			case 4:
				return "IV";
			case 5:
				return "V";
			default:
				System.err.println("Invalid mapping type choice. Try again.");
				return inputRotorType();
		}
	}
	
	/**
	 * Gets the user's choice of position for the rotor and returns it, this choice is validated.
	 * @return integer position chosen by the user.
	 */
	public int inputRotorPosition() {
		System.out.println("What position should this Rotor have? (0-25)");
		int position = validateInt(sc.nextLine());
		
		if (position < 26 && position >= 0) {
			return position;
		} else {
			System.err.println("Invalid position choice. Try again.");
			return inputRotorPosition();
		}
	}
	
	/**
	 * Allows the user to change the type of a rotor in any slot (I/II/III/IV/V)
	 */
	public void changeRotorType() {
		System.out.println("What Rotor slot would you like to change?");
		System.out.println("[0] Slot 0");
		System.out.println("[1] Slot 1");
		System.out.println("[2] Slot 2");	
		int slot = validateInt(sc.nextLine());
		
		//Slots can only be between 0 and 2, this is validated
		if (slot >= 0 && slot < 3) {
			System.out.println("What Type would you like to change the Rotor to?");
			getRotor(slot).setMappingType(inputRotorType());
		} else {
			System.err.println("Invalid slot choice. Try again.");
			changeRotorType();
		}
		configRotors();
	}
		
	/**
	 * Allows the user to change the position of a rotor in any slot to a value between 0-25.
	 */
	public void changeRotorPosition() {
		System.out.println("What Rotor slot's position would you like to change?");
		System.out.println("[0] Slot 0");
		System.out.println("[1] Slot 1");
		System.out.println("[2] Slot 2");
		int rotorSelection = validateInt(sc.nextLine());
		
		//Validates the slot choice (0-2)
		if (rotorSelection < 3 && rotorSelection >= 0) {
			System.out.println("What position would you like to set?");
			int posSelection = validateInt(sc.nextLine());
			
			//Validates the position choice (0-25)
			if (posSelection <= 25 && posSelection >= 0) {
				try {
					getRotor(rotorSelection).setPosition(posSelection);
				} catch (Exception e) {
					System.err.println("Invalid Slot or Position, try again.");
					changeRotorPosition();
				}
			} else {
				System.err.println("Invalid Position, try again.");
				changeRotorPosition();
			}
			
		} else {
			System.err.println("Invalid Rotor Slot, try again.");
			changeRotorPosition();
		}
		//Returns to the Rotor configuration menu
		configRotors();
	}
	
	/**
	 * Reflector configuration menu of CLI
	 * Used to add, change and list reflectors in the EnigmaMachine.
	 */
	public void configReflector() {
		System.out.println("=== Reflector ===");
		System.out.println("[1] Add Reflector");
		System.out.println("[2] Change Reflector Type");
		System.out.println("[3] List Reflector Configuration");
		System.out.println("[4] Menu");
		
		switch (validateInt(sc.nextLine())) {
			case 1:
				addReflector();
				break;
			case 2:
				changeReflectorType();
				break;
			case 3:
				printReflector();
				configReflector();
				break;
			case 4:
				menu();
				break;
			default:
				System.err.println("Invalid Reflector Menu choice, try again");
				configReflector();
				break;
		}
		
	}
	
	/**
	 * Adds a reflector of the user's choice to the EnigmaMachine.
	 */
	void addReflector() {
		System.out.println("What type of reflector would you like to add?");
		System.out.println("[1] I");
		System.out.println("[2] II");
		switch (validateInt(sc.nextLine())) {
			case 1:
				addReflector(new Reflector("I"));
				break;
			case 2:
				addReflector(new Reflector("II"));
				break;
			default:
				System.err.println("Invalid Reflector type, try again.");
				addReflector();
				break;
		}
		this.reflectorReady = true;
		configReflector();
	}
	
	/**
	 * Method used to change the type of the reflector (between I or II)
	 */
	void changeReflectorType() {
		System.out.println("What type would you like to change the reflector to?");
		System.out.println("[1] I");
		System.out.println("[2] II");
		switch (validateInt(sc.nextLine())) {
			case 1:
				getReflector().setMappingType("I");
				break;
			case 2:
				getReflector().setMappingType("II");
				break;
			default:
				System.err.println("Invalid Reflector type, try again.");
				changeReflectorType();
				break;
		}
		configReflector();
	}
	
	/**
	 * Running configuration menu of CLI
	 * Used to determine if you want to read the input from a file, or if you want to type the input instead.
	 */
	public void configRun() {
		System.out.println("=== Run Menu ===");
		System.out.println("[1] Read from File to Encode");
		System.out.println("[2] Type Message to Encode");
		System.out.println("[3] Menu");
		
		switch (validateInt(sc.nextLine())) {
			case 1:
				if (isRunnable()) {
					fileEncode();
				} else {
					configRun();
				}
				break;
			case 2:
				if (isRunnable()) {
					encode();
				} else {
					configRun();
				}
				break;
			case 3:
				menu();
				break;
			default:
				System.err.println("Invalid Run Menu choice, try again");
				configRun();
				break;
		}
	}
	
	/**
	 * Method to determine if the enigma machine is runnable based on the configuration the user has provided.
	 * The minimum for this is that the Rotors and Reflector have been defined. (Input String is entered later on)
	 * @return True (if ready to run), False (if not ready to run)
	 */
	boolean isRunnable() {
		if (rotorsReady && reflectorReady) {
			return true;
		} else if (rotorsReady && !reflectorReady) {
			System.err.println("Machine is not ready to run, you must configure the Reflector");
			return false;
		} else if (!rotorsReady && reflectorReady) {
			System.err.println("Machine is not ready to run, you must configure the Rotors");
			return false;
		} else {
			System.err.println("Neither the Rotors or Reflector are ready, these must be configured before running the machine.");
			return false;
		}
	}
	
	/**
	 * Reads lines to encode from a Text file (the user enters the file path) and encodes the lines.
	 * The user is then given the option to write the lines to a file, or have them printed to the screen.
	 */
	public void fileEncode() {
		ArrayList<String> encoded = new ArrayList<String>();
		
		System.out.println("Enter the path to read the text from. Eg: C:\\\\input.txt");
		String path = sc.nextLine();
		files.setReadPath(path);
		List<String> lines = files.getLines();
		
		//Encodes each line from the input text file, and adds these to an ArrayList
		for (String encode : lines) {
			encoded.add(encodeString(encode));
		}
		
		System.out.println("Text encoded successfully, what would you like to do with it?");
		System.out.println("[1] Write to a file");
		System.out.println("[2] Print to screen");
		
		switch(validateInt(sc.nextLine())) {
			case 1:
				writeToFile(encoded);
				break;
			case 2:
				for (String line : encoded) {
					System.out.println(line);
				}
				break;
			default:
				System.err.println("Incorrect selection for handling encoded text. Try again.");
				fileEncode();
				break;
		}
		//Resets rotor positions incase the same EnigmaMachine is to be used again on another String
		resetRotorPositions();
		configRun();
	}
	
	/**
	 * Takes a String as input from the user and encodes it.
	 */
	public void encode() {
		System.out.println("Enter the string to encode");
		String lineToEncode = sc.nextLine().toUpperCase();
		String encodedMessage = encodeString(lineToEncode);
		
		System.out.println("Text encoded successfully, what would you like to do with it?");
		System.out.println("[1] Write to a file");
		System.out.println("[2] Print to screen");
		
		switch(validateInt(sc.nextLine())) {
			case 1:
				writeToFile(encodedMessage);
				break;
			case 2:
				System.out.println(encodedMessage);
				break;
			default:
				System.err.println("Incorrect selection for handling encoded text. Try again.");
				fileEncode();
				break;
		}
		//Resets rotor positions incase the same EnigmaMachine is to be used again on another String
		resetRotorPositions();
		configRun();
	}
	
	/**
	 * A method to reset the positions to their default positions, incase the same Enigma Machine configuration is to be used again on a different message
	 */
	public void resetRotorPositions() {
		getRotor(0).resetPosition();
		getRotor(1).resetPosition();
		getRotor(2).resetPosition();
	}
	
	/**
	 * Encodes a String using the configured EnigmaMachine and returns the encoded string
	 * @param line: String before encoding
	 * @return: String after encoding
	 */
	String encodeString(String line) {
		line.toUpperCase();
		String output = "";
		//Iterates through each character in the input string and encodes each.
		for (int i=0; i<line.length(); i++) {
			System.out.println("Processing letter: " + (char)(line.charAt(i)));
			output += (char)(encodeLetter(line.charAt(i)) + 'A');
		}
		return output;
	}
	
	/**
	 * Writes the encoded message to a text file specified by the user.
	 * The original line structure is maintained from the file read in fileEncode().
	 * @param lines: ArrayList containing each line of encoded message.
	 */
	void writeToFile(ArrayList<String> lines) {
		System.out.println("Enter the path to write the text to. Eg: C:\\\\Users\\----\\Desktop\\output.txt");
		String path = sc.nextLine();
		files.setWritePath(path);
		files.writeOutput(lines);
		
		System.out.println("Write completed successfully!");
	}
	
	/**
	 * Writes the encoded message to a text file specified by the user.
	 * The original line structure is maintained from the file read in fileEncode().
	 * @param lines: String containing the encoded message.
	 */
	void writeToFile(String line) {
		System.out.println("Enter the path to write the text to. Eg: C:\\\\Users\\----\\Desktop\\output.txt");
		String path = sc.nextLine();
		files.setWritePath(path);
		files.writeOutput(line);
		
		System.out.println("Write completed successfully!");
	}
	
	/**
	 * Takes in a user's input and attempts to parse it (and then return it) as an integer.
	 * If this fails, the user is asked to repeat their input until a parsable integer is entered.6
	 * @param number: String input to be parsed
	 * @return: parsed integer output
	 */
	int validateInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			System.err.println("Invalid choice. Try again");
			return validateInt(sc.nextLine());
		}
	}
	
}
