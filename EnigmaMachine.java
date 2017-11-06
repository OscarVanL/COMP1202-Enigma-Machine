import java.util.ArrayList;
import java.util.List;

public class EnigmaMachine {
	
	private List<String> outputPhrase;
	private Plugboard plugboard;
	private BasicRotor rotor1;
	private BasicRotor rotor2;
	private BasicRotor rotor3;
	private Reflector reflector;
	
	EnigmaMachine() {
		plugboard = new Plugboard();
		outputPhrase = new ArrayList<String>();
	}
	
	List<String> start(List<String> message) {
		addPlug('b', 'c');
		addPlug('r', 'i');
		addPlug('s', 'm');
		addPlug('a', 'f');
		BasicRotor rotor1 = new BasicRotor("IV");
		rotor1.setPosition(23);
		BasicRotor rotor2 = new BasicRotor("V");
		rotor2.setPosition(4);
		BasicRotor rotor3 = new BasicRotor("II");
		rotor3.setPosition(9);
		addRotor(rotor1, 1);
		addRotor(rotor2, 2);
		addRotor(rotor3, 3);
		Reflector reflector = new Reflector();
		reflector.initialise("II");
		addReflector(reflector);
		
		for (String line : message) {
			String out = "";
			String encodedMessage = line;
			char[] encodedMessageChars = encodedMessage.toLowerCase().toCharArray();
			
			for (int i=0; i<encodedMessage.length(); i++) {
				System.out.println("Processing letter: " + (char)(encodedMessageChars[i]));
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
	
	void addRotor(BasicRotor rotor, int slot) {
		switch(slot) {
			case(1):
				this.rotor1 = rotor;
				break;
			case(2):
				this.rotor2 = rotor;
				break;
			case(3):
				this.rotor3 = rotor;
				break;
			default:
				System.out.println("Invalid rotor slot used in method addRotor() (Error is not handled.)");
		}
	}
	
	BasicRotor getRotor(int slot) {
		switch(slot) {
			case(1):
				return rotor1;
			case(2):
				return rotor2;
			case(3):
				return rotor3;
			default:
				System.out.println("Invalid rotor slot choice in method getRotor() (Error is not handled.)");
				return rotor1;
		}
	}
	
	void addReflector(Reflector reflector) {
		this.reflector = reflector;
	}
	
	Reflector getReflector() {
		return this.reflector;
	}
	
	void setPosition(int slot, int position) {
		switch(slot) {
			case(1):
				rotor1.setPosition(position);
				break;
			case(2):
				rotor2.setPosition(position);
				break;
			case(3):
				rotor3.setPosition(position);
				break;
			default:
				System.out.println("Invalid slot choice in method setPosition() (Error is not handled.)");
		}
	}
	
	int encodeLetter(char input) {
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
		System.out.println("	==Rotor 1==");
		letter = rotor1.substitute(letter);
		System.out.println("	After rotor 1: " + letter);
		System.out.println("	==Rotor 2==");
		letter = rotor2.substitute(letter);
		System.out.println("	After rotor 2: " + letter);
		System.out.println("	==Rotor 3==");
		letter = rotor3.substitute(letter);
		System.out.println("	After rotor 3: " + letter);
		//Applies the reflector substitution
		System.out.println("	==Reflector==");
		letter = reflector.substitute(letter);
		System.out.println("	After reflector: " + letter);
		//Applies the rotor inverse substitutions for rotor 3, 2, 1
		System.out.println("	==Rotor 3 Substitute==");
		letter = rotor3.substituteBack(letter);
		System.out.println("	After rotor 3 substitute back: " + letter);
		System.out.println("	==Rotor 2 Substitute==");
		letter = rotor2.substituteBack(letter);
		System.out.println("	After rotor 2 substitute back: " + letter);
		System.out.println("	==Rotor 1 Substitute==");
		letter = rotor1.substituteBack(letter);
		System.out.println("	After rotor 1 substitute back: " + letter);
		
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
		rotor1.rotate();
		return letter;
	}
}
