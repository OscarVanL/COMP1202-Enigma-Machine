
public class Plug {
	
	private char plug1;
	private char plug2;
	
	/**
	 * Constructor for Plugs, takes two char arguments
	 * @param plug1: Char at plug's End 1, assigned to Plug attribute plug1
	 * @param plug2: Char at plug's End 2, assigned to Plug attribute plug2
	 */
	Plug(char plug1, char plug2) {
		this.plug1 = plug1;
		this.plug2 = plug2;
	}
	
	/**
	 * Used for swapping input letters according to plugs
	 * @param letterIn: Letter to check against the plug's ends
	 * @return: If the letter is on a plug's end, return the other end's letter.
	 */
	char encode(char letterIn) {
		//If the letterIn is on either end of the plug, return the letter on the other end of the plug
		if (plug1 == letterIn) {
			return plug2;
		} else if (plug2 == letterIn) {
			return plug1;
		} else {
			//If the letterIn is not connected to the plug, return the unchanged letter.
			return letterIn;
		}
	}
	
	/**
	 * Used to ensure that a given plug (plugin) does not share any plugs with this instance of Plug.
	 * Usage: boolean doesItClash = plug1.clashesWith(plug2)
	 * @param plugin: Plug object as input to check with
	 * @return: True (if there is a clash), False (if there is no clash)
	 */
	boolean clashesWith(Plug plugin) {
		//If either ends of the plug are the same, they have a clash, so true is returned. 
		if (plugin.getEnd1() == this.plug1 || plugin.getEnd1() == this.plug2 || plugin.getEnd2() == this.plug1 || plugin.getEnd2() == this.plug2) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Getter method for End 1 of the plug object
	 * @return: char at End 1 of plug
	 */
	char getEnd1() {
		return plug1;
	}
	
	/**
	 * Getter method for End 2 of the plug object
	 * @return: char at End 2 of the plug
	 */
	char getEnd2() {
		return plug2;
	}
	
	/**
	 * Setter method for End 1 of the plug object
	 * @param plug: Char to be set to
	 */
	void setEnd1(char plug) {
		this.plug1 = plug;
	}
	
	/**
	 * Setter method for End 2 of the plug object
	 * @param plug: Char to be set to
	 */
	void setEnd2(char plug) {
		this.plug2 = plug;
	}
}
