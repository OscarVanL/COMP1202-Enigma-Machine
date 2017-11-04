
public class Plug {
	
	private char plug1;
	private char plug2;
	
	Plug(char plug1, char plug2) {
		this.plug1 = plug1;
		this.plug2 = plug2;
	}
	
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
	
	boolean clashesWith(Plug plugin) {
		//If either ends of the plug are the same, they have a clash, so true is returned. 
		if (plugin.getEnd1() == this.plug1 || plugin.getEnd1() == this.plug2 || plugin.getEnd2() == this.plug1 || plugin.getEnd2() == this.plug2) {
			return true;
		} else {
			return false;
		}
	}
	
	char getEnd1() {
		return plug1;
	}
	
	char getEnd2() {
		return plug2;
	}
	
	void setEnd1(char plug) {
		this.plug1 = plug;
	}
	
	void setEnd2(char plug) {
		this.plug2 = plug;
	}
}
