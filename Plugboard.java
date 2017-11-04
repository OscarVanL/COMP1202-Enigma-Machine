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
	
	int getNumPlugs() {
		return plugList.size();
	}
	
	void clear() {
		plugList.clear();
	}
}
