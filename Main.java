
public class Main {

	public static void main(String[] args) {
		start();
	}
	
	static void start() {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('a', 'm');
		enigma.addPlug('g', 'l');
		enigma.addPlug('e', 't');
		BasicRotor rotor1 = new BasicRotor("I");
		rotor1.setPosition(6);
		BasicRotor rotor2 = new BasicRotor("II");
		rotor2.setPosition(12);
		BasicRotor rotor3 = new BasicRotor("III");
		rotor3.setPosition(5);
		enigma.addRotor(rotor1, 1);
		enigma.addRotor(rotor2, 2);
		enigma.addRotor(rotor3, 3);
		Reflector reflector = new Reflector();
		reflector.initialise("I");
		enigma.addReflector(reflector);
		
		String encodedMessage = "GF";
		char[] encodedMessageChars = encodedMessage.toLowerCase().toCharArray();
		
		for (int i=0; i<encodedMessage.length(); i++) {
			System.out.println("Encrypting letter: " + (char)(encodedMessageChars[i]));
			enigma.encodeLetter((char)(encodedMessageChars[i]));
		}
	}

}
