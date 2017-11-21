import java.util.Scanner;

public class Main {

	private static String inPath;
	private static String outPath;
	
	public static void main(String[] args) {
		//messageFromFile();
		test1();
	}
	
	static void messageFromFile() {
		Scanner sc = new Scanner(System.in);
		EnigmaFile enigmaFile = new EnigmaFile();
		
		System.out.println("Type the input file name and path, eg: C:\\\\input.txt");
		inPath = sc.nextLine();
		enigmaFile.start(inPath);
		
		System.out.println("Type the output file name and path, eg: C:\\\\Users\\----\\Desktop\\output.txt");
		outPath = sc.nextLine();
		enigmaFile.writeOutput(outPath);
		
		sc.close();
	}
	
	static void test1() {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('q', 'f');
		TurnoverRotor rotor3 = new TurnoverRotor("III");
		rotor3.setPosition(7);
		TurnoverRotor rotor2 = new TurnoverRotor("II", rotor3);
		rotor2.setPosition(11);
		TurnoverRotor rotor1 = new TurnoverRotor("I", rotor2);
		rotor1.setPosition(23);
		
		
		enigma.addRotor(rotor1, 1);
		enigma.addRotor(rotor2, 2);
		enigma.addRotor(rotor3, 3);
		Reflector reflector = new Reflector();
		reflector.initialise("I");
		enigma.addReflector(reflector);
		
		String output = "";
		String encodedMessage = "OJVAYFGUOFIVOTAYRNIWJYQWMXUEJGXYGIFT";
		encodedMessage = encodedMessage.toLowerCase();

		for (int i=0; i<encodedMessage.length(); i++) {
			System.out.println("Processing letter: " + (char)(encodedMessage.charAt(i)));
			output += (char)(enigma.encodeLetter(encodedMessage.charAt(i)) + 'a');
		}
		
		System.out.println(output);
	}


}
