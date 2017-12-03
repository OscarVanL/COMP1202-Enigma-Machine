import java.util.ArrayList;

public class Plugboard {
	
	ArrayList<Plug> plugList = new ArrayList<Plug>();
	
	boolean addPlug(char plug1, char plug2) {
		//Has a boolean variable to record if there are any clashes in the later for-loop check.
		Boolean clash = false;
		//Creates a intermediate plug with the arguments, which may or may-not be added to the plugList.
		Plug potentialPlug = new Plug(plug1, plug2);
		//Iterates through the ArrayList and checks every plug to ensure there are no clashes.
		for(Plug plug : plugList) {
			if (plug.clashesWith(potentialPlug)) {
				//A plug has been found that clashes with our potential plug, so the boolean is set to true.
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
	
	char substitute(char letter) {
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
	
	int getPlugEnd(int plugNumber, int end) throws Exception {
		switch(end) {
			case(1):
				return plugList.get(plugNumber-1).getEnd1();
			case(2):
				return plugList.get(plugNumber-1).getEnd2();
			default:
				throw new Exception("Error: Invalid plug end given in getPlugEnd. Expected values: 1/2");
		}
	}
	
	void setPlug(int plugNumber, char letter, int end) throws Exception {
		switch(end) {
			case(1):
				plugList.get(plugNumber-1).setEnd1(letter);
				break;
			case(2):
				plugList.get(plugNumber-1).setEnd2(letter);
				break;
			default:
				throw new Exception("Error: Invalid plug end given in setPlug. Expected values: 1/2, Actual value: " + end);
		}
	}
	
	int getNumPlugs() {
		return plugList.size();
	}
	
	void clear() {
		plugList.clear();
	}
}
