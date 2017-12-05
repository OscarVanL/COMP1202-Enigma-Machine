import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws Exception {
		//fileUsageExample();
		//test1();
		//test2();
		//test3();
	}
	
	/**
	 * Completes Test 1 from Part 5 of specification.
	 * This is a simple test for functionality after first working EnigmaMachine is created
	 * Outputs BADGER
	 */
	static void test1() {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('A', 'M');
		enigma.addPlug('G', 'L');
		enigma.addPlug('E', 'T');
		
		enigma.addRotor(new BasicRotor("I", 6), 0);
		enigma.addRotor(new BasicRotor("II", 12), 1);
		enigma.addRotor(new BasicRotor("III", 5), 2);
		enigma.addReflector(new Reflector("I"));
		//enigma.toggleDebugMode();
		
		String output = "";
		String encodedMessage = "GFWIQH";

		for (int i=0; i<encodedMessage.length(); i++) {
			System.out.println("Processing letter: " + (char)(encodedMessage.charAt(i)));
			output += (char)(enigma.encodeLetter(encodedMessage.charAt(i)) + 'A');
		}
		
		System.out.println(output);
	}
	
	/**
	 * Completes Test 2 from Part 5 of specification
	 * This is a simple test for functionality after the first working EnigmaMachine is created
	 * Outputs SNAKE
	 */
	static void test2() {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('B', 'C');
		enigma.addPlug('R', 'I');
		enigma.addPlug('S', 'M');
		enigma.addPlug('A', 'F');

		enigma.addRotor(new BasicRotor("IV", 23), 0);
		enigma.addRotor(new BasicRotor("V", 4), 1);
		enigma.addRotor(new BasicRotor("II", 9), 2);
		enigma.addReflector(new Reflector("II"));
		//enigma.toggleDebugMode();
		
		String output = "";
		String encodedMessage = "GACIG";
		
		for (int i=0; i<encodedMessage.length(); i++) {
			System.out.println("Processing letter: " + (char)(encodedMessage.charAt(i)));
			output += (char)(enigma.encodeLetter(encodedMessage.charAt(i)) + 'A');
		}
		
		System.out.println(output);
	}
	
	/**
	 * Completes Test 3 from Part 7 of specification
	 * This is a simple test for functionality after the TurnoverRotors were created
	 * Outputs THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOG
	 */
	static void test3() {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('Q', 'F');
		
		TurnoverRotor rotor2 = new TurnoverRotor("III", 7);
		TurnoverRotor rotor1 = new TurnoverRotor("II", 11, rotor2);
		TurnoverRotor rotor0 = new TurnoverRotor("I", 23, rotor1);
		
		enigma.addRotor(rotor0, 0);
		enigma.addRotor(rotor1, 1);
		enigma.addRotor(rotor2, 2);
		enigma.addReflector(new Reflector("I"));
		
		String output = "";
		String encodedMessage = "OJVAYFGUOFIVOTAYRNIWJYQWMXUEJGXYGIFT";

		for (int i=0; i<encodedMessage.length(); i++) {
			System.out.println("Processing letter: " + (char)(encodedMessage.charAt(i)));
			output += (char)(enigma.encodeLetter(encodedMessage.charAt(i)) + 'A');
		}
		
		System.out.println(output);
	}
	
	/**
	 * Example implementation of Part 6's Reading & Writing of files on Test 3's configuration
	 * @throws Exception: If a Rotor is put in the incorrect Slot
	 */
	static void fileUsageExample() throws Exception {
		EnigmaFile enigmaFile = new EnigmaFile();
		
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('Q', 'F');
		TurnoverRotor rotor2 = new TurnoverRotor("III", 7);
		TurnoverRotor rotor1 = new TurnoverRotor("II", 11, rotor2);
		TurnoverRotor rotor0 = new TurnoverRotor("I", 23, rotor1);
		
		enigma.addRotor(rotor0, 0);
		enigma.addRotor(rotor1, 1);
		enigma.addRotor(rotor2, 2);
		enigma.addReflector(new Reflector("I"));
		
		enigmaFile.setReadPath("partSixInput.txt");
		enigmaFile.setWritePath("partSixOutput.txt");
		
		List<String> encodedLines = enigmaFile.getLines();
		ArrayList<String> decodedLines = new ArrayList<String>();
		String output = "";
		
		for (String line : encodedLines) {
			for (int i=0; i<line.length(); i++) {
				System.out.println("Processing letter: " + (char)(line.charAt(i)));
				output += (char)(enigma.encodeLetter(line.charAt(i)) + 'A');
			}
			decodedLines.add(output);
		}
		
		System.out.println(decodedLines.toString());
		enigmaFile.writeOutput(decodedLines);
	}

}
