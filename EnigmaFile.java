import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EnigmaFile {
	private List<String> phrase;
	private List<String> output;
	private String inputPath;
	private String outputPath;
	private EnigmaMachine enigma;
	
	public EnigmaFile() {
		this.enigma = new EnigmaMachine();
	}

	void start(String filepath) throws Exception {
		this.inputPath = filepath;
		try {
			this.phrase = Files.readAllLines(Paths.get(inputPath));
			this.output = enigma.start(phrase);
		} catch (IOException e) {
			System.out.println("Failed to read text from file specified in EnigmaFile Constructor (Error not handled)");
			e.printStackTrace();
		}
	}
	
	void writeOutput(String filepath) {
		this.outputPath = filepath;
		try {
			Files.write(Paths.get(outputPath), output, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Failed to write to file specified in writeOutput(), (Error not handled)");
			e.printStackTrace();
		}
		
	}
	

}
