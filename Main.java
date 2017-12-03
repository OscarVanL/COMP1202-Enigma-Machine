import java.util.Scanner;

public class Main {

	private static String inPath;
	private static String outPath;
	
	public static void main(String[] args) throws Exception {
		//messageFromFile();
		//test1();
		test2();
		//test3();
	}
	
	static void messageFromFile() throws Exception {
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
	
	static void test1() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('a', 'm');
		enigma.addPlug('g', 'l');
		enigma.addPlug('e', 't');
		
		enigma.addRotor(new BasicRotor("I", 6), 0);
		enigma.addRotor(new BasicRotor("II", 12), 1);
		enigma.addRotor(new BasicRotor("III", 5), 2);
		enigma.addReflector(new Reflector("I"));
		enigma.toggleDebugMode();
		
		String output = "";
		String encodedMessage = "GFWIQH";
		encodedMessage = encodedMessage.toLowerCase();

		for (int i=0; i<encodedMessage.length(); i++) {
			System.out.println("Processing letter: " + (char)(encodedMessage.charAt(i)));
			output += (char)(enigma.encodeLetter(encodedMessage.charAt(i)) + 'a');
		}
		
		System.out.println(output);
	}
	
	static void test2() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('b', 'c');
		enigma.addPlug('r', 'i');
		enigma.addPlug('s', 'm');
		enigma.addPlug('a', 'f');

		enigma.addRotor(new BasicRotor("IV", 23), 0);
		enigma.addRotor(new BasicRotor("V", 4), 1);
		enigma.addRotor(new BasicRotor("II", 9), 2);
		enigma.addReflector(new Reflector("II"));
		
		enigma.toggleDebugMode();
		
		String output = "";
		String encodedMessage = "GACIG";
		encodedMessage = encodedMessage.toLowerCase();

		for (int i=0; i<encodedMessage.length(); i++) {
			System.out.println("Processing letter: " + (char)(encodedMessage.charAt(i)));
			output += (char)(enigma.encodeLetter(encodedMessage.charAt(i)) + 'a');
		}
		
		System.out.println(output);
	}
	
	static void test3() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		enigma.addPlug('q', 'f');
		
		TurnoverRotor rotor2 = new TurnoverRotor("III", 7);
		TurnoverRotor rotor1 = new TurnoverRotor("II", 11, rotor2);
		TurnoverRotor rotor0 = new TurnoverRotor("I", 23, rotor1);
		
		enigma.addRotor(rotor0, 0);
		enigma.addRotor(rotor1, 1);
		enigma.addRotor(rotor2, 2);
		enigma.addReflector(new Reflector("I"));
		
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
