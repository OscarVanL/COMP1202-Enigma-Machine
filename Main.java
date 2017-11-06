
public class Main {
	private static String encryptedPhrase = "";

	public static void main(String[] args) {
		start();
	}
	
	static void start() {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('b', 'c');
		enigma.addPlug('r', 'i');
		enigma.addPlug('s', 'm');
		enigma.addPlug('a', 'f');
		BasicRotor rotor1 = new BasicRotor("IV");
		rotor1.setPosition(23);
		BasicRotor rotor2 = new BasicRotor("V");
		rotor2.setPosition(4);
		BasicRotor rotor3 = new BasicRotor("II");
		rotor3.setPosition(9);
		enigma.addRotor(rotor1, 1);
		enigma.addRotor(rotor2, 2);
		enigma.addRotor(rotor3, 3);
		Reflector reflector = new Reflector();
		reflector.initialise("II");
		enigma.addReflector(reflector);
		
		String encodedMessage = "GACIG";
		char[] encodedMessageChars = encodedMessage.toLowerCase().toCharArray();
		
		for (int i=0; i<encodedMessage.length(); i++) {
			System.out.println("Encrypting letter: " + (char)(encodedMessageChars[i]));
			encryptedPhrase += (char)(enigma.encodeLetter((encodedMessageChars[i])) + 'a');
		}
		
		System.out.println("Full Encrypted message: " + encryptedPhrase);
	}

}
