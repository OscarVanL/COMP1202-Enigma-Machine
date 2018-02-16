import java.util.ArrayList;

public class Plugboard {
	
	private ArrayList<Plug> plugList = new ArrayList<Plug>();
	
	/**
	 * Takes two characters and creates a plug and adds it to the plugList ArrayList, IF the plugs don't clash with any existing plugs.
	 * @param plug1: Letter of plug1 (char)
	 * @param plug2: Letter of plug2 (char)
	 * @return: true (if the plug was added successfully), false (if there was a clash, so the plug wasn't added)
	 */
	public boolean addPlug(char plug1, char plug2) {
		//Boolean to store if there are any clashes in for-loop
		Boolean clash = false;
		//Intermediate plug with the arguments, may or may-not be added to the plugList.
		Plug potentialPlug = new Plug(plug1, plug2);
		//Iterates through plugList and checks every plug to ensure there are no clashes.
		for(Plug plug : plugList) {
			if (plug.clashesWith(potentialPlug)) {
				//Plug found that clashes with our intermediate plug, boolean is set to true.
				clash = true;
			}
		}
		
		if (clash) {
			return false;
		} else {
			plugList.add(potentialPlug);
			return true;
		}
	}
	
	/**
	 * Used on letters being decoded to make any necessary swaps. Iterates through all ends of plugs to make any potential swaps
	 * @param letter: Letter being decoded
	 * @return Letter afterwards (as char), unchanged if there were no swaps, or changed if there were
	 */
	public char substitute(char letter) {
		//Iterates through the ArrayList and checks every plug to see if the letter is present on any plug
		for(Plug plug : plugList) {
			//Returns letter on the opposite end of the plug if the letter is present on one side.
			if (plug.getEnd1() == letter) {
				return plug.getEnd2();
			} else if (plug.getEnd2() == letter) {
				return plug.getEnd1();
			}
		}
		return letter;
	}
	
	/**
	 * Gets the character at a given end of a given plug
	 * @param plugNumber: Number plug to retrieve from, in order of being added (0,1,2,... expected)
	 * @param end: End of plug to retrieve from (1/2 expected)
	 * @return: char at the end of the plug
	 * @throws Exception: If an invalid end value is used (must be 1 or 2)
	 */
	public char getPlugEnd(int plugNumber, int end) throws Exception {
		switch(end) {
			case(1):
				return plugList.get(plugNumber).getEnd1();
			case(2):
				return plugList.get(plugNumber).getEnd2();
			default:
				throw new Exception("Error: Invalid plug end given in getPlugEnd. Expected values: 1 or 2");
		}
	}
	
	/**
	 * Sets the character at a given end of a given plug
	 * @param plugNumber: Number plug to set, in order of being added (0,1,2,... expected)
	 * @param end: End of plug to set (1/2 expected)
	 * @param letter: char to set the plug's end to
	 * @throws Exception: If an invalid end value is used (must be 1 or 2)
	 */
	public void setPlug(int plugNumber, int end, char letter) throws Exception {
		switch(end) {
			case(1):
				plugList.get(plugNumber).setEnd1(letter);
				break;
			case(2):
				plugList.get(plugNumber).setEnd2(letter);
				break;
			default:
				throw new Exception("Error: Invalid plug end given in setPlug. Expected values: 1/2, Actual value: " + end);
		}
	}
	
	/**
	 * Gets the number of Plugs in the Plugboard
	 * @return: integer number of plugs in plugboard
	 */
	public int getNumPlugs() {
		return plugList.size();
	}
	
	public ArrayList<Plug> getPlugList() {
		return plugList;
	}
	
	/**
	 * Removes all plugs from the plugboard
	 */
	public void clear() {
		plugList.clear();
	}
}
