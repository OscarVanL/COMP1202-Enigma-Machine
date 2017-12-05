import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class EnigmaFile {
	private List<String> phrase;
	private Path readPath;
	private Path writePath;
	
	/**
	 * Sets the EnigmaFile's read path, must be used before getLines()
	 * @param path: Path as a String, for example: C:\\input.txt
	 */
	public void setReadPath(String path) {
		this.readPath = Paths.get(path);
	}
	
	/**
	 * Sets the EnigmaFile's read path, must be used before getLines()
	 * @param path: Path as a Path object
	 */
	public void setReadPath(Path path) {
		this.readPath = path;
	}
	
	/**
	 * Getter for EnigmaFile's read Path
	 * @return: Path object for reading path
	 */
	public Path getReadPath() {
		return writePath;
	}
	
	/**
	 * Sets the EnigmaFile's write path, must be used before writeOutput()
	 * @param path: Path as a String, for example C:\\Users\----\Desktop\output.txt
	 */
	public void setWritePath(String path) {
		this.writePath = Paths.get(path);
	}
	
	/**
	 * Sets the EnigmaFile's write path, must be used before writeOutput()
	 * @param path: Path as a Path object
	 */
	public void setWritePath(Path path) {
		this.writePath = path;
	}
	
	/**
	 * Getter for EnigmaFile's write Path
	 * @return: Path object for writing path
	 */
	public Path getWritePath() {
		return writePath;
	}
	
	/**
	 * Reads all lines contained in the readPath and creates a List, where each element is a subsequent line
	 * @return: List<String> where each element is a subsequent line of the file
	 */
	List<String> getLines() {
		try {
			this.phrase = Files.readAllLines(readPath);
		} catch (IOException e) {
			System.err.println("Failed to read from file specified (Error not handled): " + readPath.toString());
			System.err.println("Ensure that setReadPath has been used before this method with a valid Path");
			e.printStackTrace();
		}
		validateInput();
		return this.phrase;
	}
	
	/**
	 * Implements the Input Conversion Extension task.
	 */
	void validateInput() {
		int i=0;
		String filteredString = "";
		
		for (String line : this.phrase) {
			filteredString = line.replaceAll("\\s", ""); //Removes all spaces (\\s) by replacing them with empty string
			filteredString = line.replaceAll("[^a-zA-Z]", ""); //Replaces all non a-z or A-Z values wiht empty string
			filteredString.toUpperCase();
			this.phrase.set(i, filteredString);
			i++;
		}
	}
	
	/**
	 * Writes a List of multiple lines to the file
	 * @param output: A List<String>, where each element is written to a separate line.
	 */
	void writeOutput(List<String> output) {
		try {
			Files.write(writePath, output, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Failed to write to file specified in writeOutput(), (Error not handled)");
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a single line (single string) to the file
	 * @param output: A String that will be writte to a single line in the file.
	 */
	void writeOutput(String output) {
		//Single output String is made into an Array as the output must be of type 'iterable' for Files.write(), and Strings are not.
		List<String> outputList = Arrays.asList(output);
		try {
			Files.write(writePath, outputList, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Failed to write to file specified in writeOutput(), (Error not handled)");
			e.printStackTrace();
		}
	}
	

}
