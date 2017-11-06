import java.util.Scanner;

public class Main {

	private static String inPath;
	private static String outPath;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		EnigmaFile enigmaFile = new EnigmaFile();
		
		System.out.println("Type the input file name and path, eg: C:\\\\input.txt");
		inPath = sc.nextLine();
		enigmaFile.start(inPath);
		
		System.out.println("Type the output file name and path, eg: C:\\\\Users\\XXX\\Desktop\\output.txt");
		outPath = sc.nextLine();
		enigmaFile.writeOutput(outPath);
		
		sc.close();
	}


}
