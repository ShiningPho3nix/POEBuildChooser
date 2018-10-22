import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Klasse welche die main Methode enthält.
 * 
 * @author Steffen Dworsky
 *
 */
public class PoeBuildChooser {

	static Choose globalChooser;
	static BuildArray globalBuildArray;
	static BuildDatei globalBuildDatei;
	static Run globalRunProgram;
	static int buildDateiId;

	/**
	 * Erzeugt einige Objekte und führt dann runProgram() aus.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		globalRunProgram = new Run();
		globalBuildArray = new BuildArray();
		globalChooser = new Choose();
		globalBuildDatei = new BuildDatei();
		buildDateiId = 0;

		globalRunProgram.runProgram();

		// TODO hinzufügen einer copy funktion für die datei, wenn man etwas
		// testen will etc.

	}
}
