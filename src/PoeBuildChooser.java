import java.io.IOException;
import java.net.URISyntaxException;

public class PoeBuildChooser {

	static Choose globalChooser;
	static BuildArray globalBuildArray;
	static BuildDatei globalBuildDatei;
	static Run globalRunProgram;

	public static void main(String[] args) throws IOException, URISyntaxException {
		globalRunProgram = new Run();
		globalBuildArray = new BuildArray();
		globalChooser = new Choose();
		globalBuildDatei = new BuildDatei();
		globalRunProgram.runProgram();

		// TODO hinzufügen einer copy funktion für die datei, wenn man etwas
		// testen will etc.
		// eventuell dann auch gleich eine Funktion hinzufügen, die es
		// ermöglicht mehrere Build-Dateien zu haben und davon eine zu laden.

	}
}
