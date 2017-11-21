
public class TurnoverRotor extends BasicRotor {
	
	private int turnoverPos;
	private BasicRotor nextRotor;
	

	TurnoverRotor(String type, BasicRotor nextRotor) {
		super(type);
		this.nextRotor = nextRotor;
		setTurnoverPos(type);
	}
	
	TurnoverRotor(String type) {
		super(type);
		this.nextRotor = null;
		setTurnoverPos(type);
	}
	
	void setTurnoverPos(String type) {
		switch(type) {
			case("I"):
				this.turnoverPos = 24;
				break;
			case("II"):
				this.turnoverPos = 12;
				break;
			case("III"):
				this.turnoverPos = 3;
				break;
			case("IV"):
				this.turnoverPos = 17;
				break;
			case("V"):
				this.turnoverPos = 7;
				break;
			default:
				this.turnoverPos = 0;
				System.out.println("Invalid rotor type chosen in BasicRotor constructor. (This error has not been handled");
				// TODO add handling for invalid turnover rotor choice? (maybe an extension)
		}
	}
	
	@Override
	void rotate() {
		System.out.println("TurnoverRotor's rotate method");
		incrementPosition();
		System.out.println("TurnoverRotor incremented to " + getPosition());
		if ((getPosition() == turnoverPos) && (nextRotor != null)) {
			nextRotor.rotate();
		}
		
		if (getPosition() == ROTORSIZE) {
			System.out.println("Turnover rotor at ROTORSIZE maximum, setting to 0");
			setPosition(0);
		}
	}

	void incrementNextRotor() {
		nextRotor.incrementPosition();
	}
}
