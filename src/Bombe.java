
public class Bombe {
	
	/**
	 * Bombe's main method. Particular test challenges can be uncommented to test bombe functionality.
	 * @param args: Execution arguments, not implemented.
	 * @throws Exception: Throws exception if 
	 */
	public static void main(String[] args) throws Exception {
		//challenge1();
		//challenge2();
		//challenge3();
	}
	
	/**
	 * Test Harness / Bombe for finding unknown plug ends from a known word in the decrypted message
	 * @throws Exception: If invalid plug selections are made, or rotors are added to the wrong slots
	 * SOLUTION: Detailed in readme
	 */
	static void challenge1() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		
		enigma.addPlug('D', 'A');
		enigma.addPlug('S', 'B');
		enigma.addRotor(new BasicRotor("IV", 8), 0);
		enigma.addRotor(new BasicRotor("III", 4), 1);
		enigma.addRotor(new BasicRotor("II", 21), 2);
		enigma.addReflector(new Reflector("I")); 
		
		String encodedMessage = "JBZAQVEBRPEVPUOBXFLCPJQSYFJI";
		String knownWord = "ANSWER";

		int attemptNumber = 0;
		String result;

		//Iterates through all possibilities for plugs.
		for (int i=0; i<26; i++) {
			//Change Plug 1's second end
			enigma.setPlugEnd(0, (char)(i+'A'), 2);
			
			for (int j=0; j<26; j++) {
				//Change Plug 2's second end
				enigma.setPlugEnd(1, (char)(j+'A'), 2);
				//Resets plugs to original values before each attempt
				enigma.setPosition(0, 8);
				enigma.setPosition(1, 4);
				enigma.setPosition(2, 21);
				
				attemptNumber++;
				result = trySolution(enigma, encodedMessage);
				
				if (result.contains(knownWord)) {
					System.out.println("Attempt: " + attemptNumber);
					System.out.println("	Output: " + result);
					//Resets Rotors to original values before printing the Enigma Machine's state
					enigma.setPosition(0, 8);
					enigma.setPosition(1, 4);
					enigma.setPosition(2, 21);
					enigma.printState();			
				}
				
			} 
		}		
	}
	
	/**
	 * Test Harness / Bombe for finding 3 unknown rotor positions from a known word in the decrypted message
	 * @throws Exception: If invalid plug selections are made, or rotors are added to the wrong slots
	 * SOLUTION: Detailed in readme
	 */
	static void challenge2() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		
		enigma.addPlug('H', 'L');
		enigma.addPlug('G', 'P');
		enigma.addRotor(new BasicRotor("V", 0), 0);
		enigma.addRotor(new BasicRotor("III", 0), 1);
		enigma.addRotor(new BasicRotor("II", 0), 2);
		enigma.addReflector(new Reflector("I"));
		
		String encodedMessage = "AVPBLOGHFRLTFELQEZQINUAXHTJMXDWERTTCHLZTGBFUPORNHZSLGZMJNEINTBSTBPPQFPMLSVKPETWFD";
		String knownWord = "ELECTRIC";
		
		
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
						//Sets Rotors to working positions before printing the Enigma Machine's state
						enigma.setPosition(0, i);
						enigma.setPosition(1, j);
						enigma.setPosition(2, k);
						enigma.printState();
					}
				}
			}
		}
		
	}
	
	/**
	 * Test Harness / Bombe for finding 3 unknown rotor types from a known word in the decrypted message
	 * @throws Exception: If invalid plug selections are made, or rotors are added to the wrong slots
	 * SOLUTION: Detailed in readme
	 */
	static void challenge3() throws Exception {
		EnigmaMachine enigma = new EnigmaMachine();
		
		enigma.addPlug('M', 'F');
		enigma.addPlug('O', 'I');
		BasicRotor slot0;
		BasicRotor slot1;
		BasicRotor slot2;
		String[] type = {"I", "II", "III", "IV", "V"};
		enigma.addReflector(new Reflector("I"));
		
		String encodedMessage= "WMTIOMNXDKUCQCGLNOIBUYLHSFQSVIWYQCLRAAKZNJBOYWW";
		String knownWord = "JAVA";
		
		int attemptNumber = 0;
		String result = "";
		
		//Iterates through all possibilities of mapping type for each rotor
		for (int i=0; i<5; i++) {
			//Change rotor 0's type
			slot0 = new BasicRotor(type[i], 22);
			
			for (int j=0; j<5; j++) {
				//Change rotor 1's type
				slot1 = new BasicRotor(type[j], 24);				
				
				for (int k=0; k<5; k++) {
					//Change rotor 2's type
					slot2 = new BasicRotor(type[k], 23);
					
					//Checks if the rotors are in a valid configuration before continuing (no two or more rotors have the same type)
					if (EnigmaMachine.isRotorConfigValid(slot0, slot1, slot2)) {
						enigma.addRotor(slot0, 0);
						enigma.addRotor(slot1, 1);
						enigma.addRotor(slot2, 2);
						enigma.setPosition(0, 22);
						enigma.setPosition(1, 24);
						enigma.setPosition(2, 23);

						attemptNumber++;
						result = trySolution(enigma, encodedMessage);
						
						//If the result contains the known word then our solution has been found
						if (result.contains(knownWord)) {
							System.out.println("Attempt: " + attemptNumber);
							System.out.println("	Output: " + result);
							//Resets Rotors position to original values before printing Enigma Machine's state
							enigma.setPosition(0, 22);
							enigma.setPosition(1, 24);
							enigma.setPosition(2, 23);
							enigma.printState();
						}
					}
					
				}
			}
		}
	}	
	
	/**
	 * Runs an enigma machine instance on a given encoded message. Returns the output after completion
	 * @param enigma: Complete enigma machine with all plugs, rotors and reflectors.
	 * @param encodedMessage: String containing the encoded message (to be decrypted)
	 * @return: Message after encoded message passes through Enigma machine.s
	 */
	static String trySolution(EnigmaMachine enigma, String encodedMessage) {
		String output = "";
		
		for (int i=0; i<encodedMessage.length(); i++) {
			output += (char)(enigma.encodeLetter(encodedMessage.charAt(i)) + 'A');
		}
		
		return output;
		
	}

}
