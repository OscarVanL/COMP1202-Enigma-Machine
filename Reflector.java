
public class Reflector extends Rotor {

	private final int[] ReflectorI = {24, 17, 20, 7, 16, 18, 11, 3, 15, 23, 13, 6, 14, 10, 12, 8, 4, 1, 5, 25, 2, 22, 21, 9, 0, 19};
	private final int[] ReflectorII = {5, 21, 15, 9, 8, 0, 14, 24, 4, 3, 17, 25, 23, 22, 6, 2, 19, 10, 20, 16, 18, 1, 13, 12, 7, 11};
		
	@Override
	void initialise(String type) {
		this.name = type;
		if (type.equals("ReflectorI") || type.equals("I")) {
			this.mapping = ReflectorI;
		} else if (type.equals("ReflectorII") || type.equals("II")) {
			this.mapping = ReflectorII;
		} else {
			this.mapping = null;
			System.out.println("Invalid rotor type chosen. (This error has not been handled)");
			// TODO add handling for invalid rotor choice? (maybe an extension)
		}
	}
	
	@Override
	int substitute(int letterIn) {
		return mapping[letterIn];
	}
}
