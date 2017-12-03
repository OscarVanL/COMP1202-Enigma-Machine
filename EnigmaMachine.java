import java.util.ArrayList;
import java.util.List;

public class EnigmaMachine {
	
	private List<String> outputPhrase;
	private Plugboard plugboard;
	private BasicRotor[] rotor = new BasicRotor[3];
	private Reflector reflector;
	private boolean debugMode = false;
	
	EnigmaMachine() {
		plugboard = new Plugboard();
		outputPhrase = new ArrayList<String>();
	}
	
	List<String> start(List<String> message) throws Exception {
		addPlug('b', 'c');
		addPlug('r', 'i');
		addPlug('s', 'm');
		addPlug('a', 'f');
		addRotor(new BasicRotor("IV", 23), 0);
		addRotor(new BasicRotor("V", 4), 1);
		addRotor(new BasicRotor("II", 9), 2);
		addReflector(new Reflector("II"));
		
		for (String line : message) {
			String out = "";
			String encodedMessage = line;
			char[] encodedMessageChars = encodedMessage.toLowerCase().toCharArray();
			
			for (int i=0; i<encodedMessage.length(); i++) {
				if (debugMode) {
					System.out.println("Processing letter: " + (char)(encodedMessageChars[i]));
				}
				out += (char)(encodeLetter((encodedMessageChars[i])) + 'a');
			}
			outputPhrase.add(out);
		}
		
		System.out.println("Full message: " + outputPhrase);
		return outputPhrase;
	}
	
	void addPlug (char char1, char char2) {
		plugboard.addPlug(char1,  char2);
	}
	
	void clearPlugboard() {
		plugboard.clear();
	}
	
	int getPlugEnd(int plugNumber, int end) throws Exception {
		return plugboard.getPlugEnd(plugNumber, end);
	}
	
	void setPlugEnd(int plugNumber, char character, int end) throws Exception {
		plugboard.setPlug(plugNumber, character, end);
	}
	
	void addRotor(BasicRotor rotor, int slot) throws Exception {
		switch(slot) {
			case(0):
				this.rotor[0] = rotor;
				break;
			case(1):
				this.rotor[1] = rotor;
				break;
			case(2):
				this.rotor[2] = rotor;
				break;
			default:
				throw new Exception("Invalid rotor slot used in method addRotor(), Expected: 0/1/2, Actual: " + slot);
		}
	}
	
	BasicRotor getRotor(int slot) throws Exception {
		switch(slot) {
			case(0):
				return rotor[0];
			case(1):
				return rotor[1];
			case(2):
				return rotor[2];
			default:
				throw new Exception("Invalid rotor slot used in method getRotor(), Expected: 0/1/2, Actual: " + slot);
		}
	}
	
	void addReflector(Reflector reflector) {
		this.reflector = reflector;
	}
	
	Reflector getReflector() {
		return this.reflector;
	}
	
	void setPosition(int slot, int position) throws Exception {
		switch(slot) {
			case(0):
				rotor[0].setPosition(position);
				break;
			case(1):
				rotor[1].setPosition(position);
				break;
			case(2):
				rotor[2].setPosition(position);
				break;
			default:
				throw new Exception("Invalid rotor slot used in method getPosition(), Expected: 0/1/2, Actual: " + slot);
		}
	}
	
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
			
			letter = letter - 'a'; //Converts from ASCII char to a 0-25 integer representation
			
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
				if ((plug.getEnd1()-'a') == letter) {
					letter = plug.getEnd2() - 'a';
				} else if ((plug.getEnd2()-'a') == letter) {
					letter = plug.getEnd1() - 'a';
				}
			}
			
			rotor[0].rotate();
			return letter;
		}
	}
	
	int encodeLetterDebug(char input) {
		int letter = input;
		
		//If there exists a plug in plugboard for that letter, switch it
		for (Plug plug : plugboard.plugList) {
			if (plug.getEnd1() == input) {
				letter = plug.getEnd2();
				System.out.println("Plug exists, letter changed to: " + (char)(letter) + "(" + (letter-'a') + ")" );
			} else if (plug.getEnd2() == input) {
				letter = plug.getEnd1();
				System.out.println("Plug exists, letter changed to: " + (char)(letter) + "(" + (letter-'a') + ")");
			}
		}
		
		letter = letter - 'a'; //Converts from ASCII char to a 0-25 integer representation
		
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
			if ((plug.getEnd1()-'a') == letter) {
				letter = plug.getEnd2() - 'a';
				System.out.println("Plug exists, letter changed to: " + (char)(letter) + "(" + letter + ")" );
			} else if ((plug.getEnd2()-'a') == letter) {
				letter = plug.getEnd1() - 'a';
				System.out.println("Plug exists, letter changed to: " + (char)(letter) + "(" + letter + ")");
				
			}
		}
		
		System.out.println("===Encoded letter: " + (char)(letter + 'a') + "===");
		rotor[0].rotate();
		return letter;
	}
	
	void toggleDebugMode() {
		this.debugMode = true;
	}
	
	boolean isDebugging() {
		return this.debugMode;
	}
	
	/**
	 * Used to determine whether a possible (complete) rotor configuration is valid
	 * Example of possible: I, II, III
	 * Example of impossible:, I, I, V (I repeated twice, but types must be unique according to specification).
	 * @return true: Rotor configuration is valid. false: Rotor configuration is invalid
	 */
	static boolean isRotorConfigValid(BasicRotor slot0, BasicRotor slot1, BasicRotor slot2) {
		if (slot0.getType().equals(slot1.getType()) || slot0.getType().equals(slot2.getType()) || slot1.getType().equals(slot2.getType())) {
			return false;
		} else {
			return true;
		}
	}
}
