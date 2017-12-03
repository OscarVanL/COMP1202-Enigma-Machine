
public class Bombe {
	
	/**
	 * Bombe's main method. Particular test challenges can be uncommented to test bombe functionality.
	 * @param args: Execution arguments, not implemented.
	 * @throws Exception: Throws exception if 
	 */
	public static void main(String[] args) throws Exception {
		//challenge1();
		//challenge2();
		challenge3();
	}
	
	/**
	 * Test Harness / Bombe for finding unknown plug ends from a known word in the decrypted message
	 * @throws Exception: If invalid plug selections are made, or rotors are added to the wrong slots
	 * SOLUTION: Detailed in readme
	 */
	static void challenge1() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		
		enigma.addPlug('d', 'a');
		enigma.addPlug('s', 'b');
		enigma.addRotor(new BasicRotor("IV", 8), 0);
		enigma.addRotor(new BasicRotor("III", 4), 1);
		enigma.addRotor(new BasicRotor("II", 21), 2);
		enigma.addReflector(new Reflector("I")); 
		
		String encodedMessage = "JBZAQVEBRPEVPUOBXFLCPJQSYFJI".toLowerCase();
		String knownWord = "ANSWER".toLowerCase();

		int attemptNumber = 0;
		String result;

		//Iterates through all possibilities for plugs.
		for (int i=0; i<26; i++) {
			//Change Plug 1's second end
			enigma.setPlugEnd(1, (char)(i+'a'), 2);
			
			for (int j=0; j<26; j++) {
				//Change Plug 2's second end
				enigma.setPlugEnd(2, (char)(j+'a'), 2);
				//Resets plugs to original values before each attempt
				enigma.setPosition(0, 8);
				enigma.setPosition(1, 4);
				enigma.setPosition(2, 21);
				
				attemptNumber++;
				result = trySolution(enigma, encodedMessage);
				
				if (result.contains(knownWord)) {
					System.out.println("Attempt: " + attemptNumber);
					System.out.println("	Output: " + result);
					System.out.println("	Successful Plugs: ");
					System.out.println("		" + (char)enigma.getPlugEnd(1, 1) + " - " + (char)enigma.getPlugEnd(1, 2));
					System.out.println("		" + (char)enigma.getPlugEnd(2, 1) + " - " + (char)enigma.getPlugEnd(2, 2));				
				}
				
			} 
		}		
	}
	
	/**
	 * Test Harness / Bombe for finding 3 unknown rotor positions from a known word in the decrypted message
	 * @throws Exception : If invalid plug selections are made, or rotors are added to the wrong slots
	 * SOLUTION: Detailed in readme
	 */
	static void challenge2() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		
		enigma.addPlug('h', 'l');
		enigma.addPlug('g', 'p');
		enigma.addRotor(new BasicRotor("V", 0), 0);
		enigma.addRotor(new BasicRotor("III", 0), 1);
		enigma.addRotor(new BasicRotor("II", 0), 2);
		enigma.addReflector(new Reflector("I"));
		
		String encodedMessage = "AVPBLOGHFRLTFELQEZQINUAXHTJMXDWERTTCHLZTGBFUPORNHZSLGZMJNEINTBSTBPPQFPMLSVKPETWFD".toLowerCase();
		String knownWord = "ELECTRIC".toLowerCase();
		
		
		int attemptNumber = 0;
		String result;
		
		//Iterates through all possibilities for Rotor positions, for each rotor.
		for (int i=0; i<26; i++) {
			//Change Rotor 0's position
			enigma.setPosition(0, i);
			
			for (int j=0; j<26; j++) {
				//Change Rotor 1's position
				enigma.setPosition(1, j);
				
				for (int k=0; k<26; k++) {
					//Change Rotor 2's position
					enigma.setPosition(2,  k);
					
					attemptNumber++;
					result = trySolution(enigma, encodedMessage);
					
					if (result.contains(knownWord)) {
						System.out.println("Attempt: " + attemptNumber);
						System.out.println("	Output: " + result);
						System.out.println("	Successful Rotor Positions: ");
						System.out.println("		Rotor 0: " + enigma.getRotor(0).getPosition());
						System.out.println("		Rotor 1: " + enigma.getRotor(1).getPosition());
						System.out.println("		Rotor 2: " + enigma.getRotor(2).getPosition());
					}
				}
			}
		}
		
	}
	
	static void challenge3() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		
		enigma.addPlug('m', 'f');
		enigma.addPlug('o', 'i');
		BasicRotor slot0;
		BasicRotor slot1;
		BasicRotor slot2;
		String[] type = {"I", "II", "III", "IV", "V"};
		enigma.addReflector(new Reflector("I"));
		
		String encodedMessage= "WMTIOMNXDKUCQCGLNOIBUYLHSFQSVIWYQCLRAAKZNJBOYWW".toLowerCase();
		String knownWord = "JAVA".toLowerCase();
		
		int attemptNumber = 0;
		String result = "";
		
		for (int i=0; i<5; i++) {
			slot0 = new BasicRotor(type[i], 22);
			
			for (int j=0; j<5; j++) {
				slot1 = new BasicRotor(type[j], 24);				
				
				for (int k=0; k<5; k++) {
					slot2 = new BasicRotor(type[k], 23);
					
					if (EnigmaMachine.isRotorConfigValid(slot0, slot1, slot2)) {
						enigma.addRotor(slot0, 0);
						enigma.addRotor(slot1, 1);
						enigma.addRotor(slot2, 2);
						enigma.setPosition(0, 22);
						enigma.setPosition(1, 24);
						enigma.setPosition(2, 23);

						attemptNumber++;
						result = trySolution(enigma, encodedMessage);
					}
					
					if (result.contains(knownWord)) {
						System.out.println("Attempt: " + attemptNumber);
						System.out.println("	Output: " + result);
						System.out.println("	Successful Rotor Slots: ");
						System.out.println("		Rotor 0: " + enigma.getRotor(0).getType());
						System.out.println("		Rotor 1: " + enigma.getRotor(1).getType());
						System.out.println("		Rotor 2: " + enigma.getRotor(2).getType());
					}
				}
			}
		}
	}	
	
	static String trySolution(EnigmaMachine enigma, String encodedMessage) {
		String output = "";
		
		for (int i=0; i<encodedMessage.length(); i++) {
			output += (char)(enigma.encodeLetter(encodedMessage.charAt(i)) + 'a');
		}
		
		return output;
		
	}
}
