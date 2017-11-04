
public class EnigmaMachine {
	
	private Plugboard plugboard;
	private BasicRotor rotor1;
	private BasicRotor rotor2;
	private BasicRotor rotor3;
	private Reflector reflector;
	
	EnigmaMachine() {
		plugboard = new Plugboard();
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
	
	void encodeLetter(char input) {
		int letter = -1;
		
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
		 if (letter == -1) {
			 letter = input - 'a';
		 } else {
			 letter = letter - 'a';
		 }
		
		//Applies rotor substitutions for rotor 1, 2, then 3
		letter = rotor1.substitute(letter);
		System.out.println("	After rotor 1: " + letter);
		letter = rotor2.substitute(letter);
		System.out.println("	After rotor 2: " + letter);
		letter = rotor3.substitute(letter);
		System.out.println("	After rotor 3: " + letter);
		//Applies the reflector substitution
		letter = reflector.substitute(letter);
		System.out.println("	After reflector: " + letter);
		//Applies the rotor inverse substitutions for rotor 3, 2, 1
		letter = rotor3.substituteBack(letter);
		System.out.println("	After rotor 3 substitute back: " + letter);
		letter = rotor2.substituteBack(letter);
		System.out.println("	After rotor 2 substitute back: " + letter);
		letter = rotor1.substituteBack(letter);
		System.out.println("	After rotor 1 substitute back: " + letter);
		
		for (Plug plug : plugboard.plugList) {
			if (plug.getEnd1() == letter) {
				letter = plug.getEnd2();
			} else if (plug.getEnd2() == letter) {
				letter = plug.getEnd1();
			}
		}
		
		System.out.println("===Encoded letter: " + (char)(letter + 'a' - 1) + "===");
		
		rotor1.rotate();		
	}
}
