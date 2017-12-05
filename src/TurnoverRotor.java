
public class TurnoverRotor extends BasicRotor {
	
	private int turnoverPos;
	private BasicRotor nextRotor;
	
	/**
	 * Constructor for TurnoverRotor that takes in both type and the next rotor
	 * @param type: Type of Rotor, used in super constructor to assign mapping. Used in setTurnoverPos set the turnover position.
	 * @param nextRotor: Object reference to next rotor in Enigma machine, later used for advancing position when at turnover position
	 */
	TurnoverRotor(String type, int position, BasicRotor nextRotor) {
		super(type, position); //Initialises the Rotor's type and position.
		this.nextRotor = nextRotor;
		setTurnoverPos(type);
	}
	
	/**
	 * Constructor for TurnoverRotor taking just one argument (type)
	 * @param type: Type of rotor, used in super constructor to assign mapping. Used in setTurnerPos to set the turnover position
	 */
	TurnoverRotor(String type, int position) {
		super(type, position); //Initialises the Rotor's type and position.
		this.nextRotor = null; //No nextRotor, likely because this Rotor is in slot 2, or that a nextRotor will be set later.
		setTurnoverPos(type);
	}
	
	/**
	 * Determines from the Rotor type what the turnover position should be
	 * @param type: Type of rotor, passed in as a string
	 */
	public void setTurnoverPos(String type) {
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
				System.err.println("Invalid rotor type chosen in BasicRotor constructor. (This error has not been handled");
				// TODO add handling for invalid turnover rotor choice? (maybe an extension)
		}
	}
	
	/**
	 * Sets nextRotor attribute
	 * @param nextRotor: The next rotor (eg: If this TurnoverRotor is in slot 1, this is the rotor in slot 2)
	 */
	public void setNextRotor(BasicRotor nextRotor) {
		this.nextRotor = nextRotor;
	}
	
	/**
	 * Rotates the TurnoverRotor, if the TurnoverRotor is at its turnover position and there is a relevant nextRotor then advance that nextRotor too.
	 * If the TurnoverRotor reaches the maximum ROTORSIZE, set this to zero. (This scenario for the nextRotor is handled by the recursive .rotate() method)
	 */
	@Override
	public void rotate() {
		incrementPosition();
		//If the rotor is at the turnover position, and there is an applicable next rotor, rotate the next rotor too.
		if ((getPosition() == turnoverPos) && (nextRotor != null)) {
			nextRotor.rotate();
		}
		
		//Then set the rotor position to zero.
		if (getPosition() == ROTORSIZE) {
			try {
				setPosition(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
