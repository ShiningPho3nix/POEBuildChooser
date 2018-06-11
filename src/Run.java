import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

/**
 * 
 */

/**
 * @author Steffen Dworsky
 *
 */
public class Run {

	public void runProgram() throws IOException, URISyntaxException {
		PoeBuildChooser.globalBuildArray.initializeBuildArray();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean runAndRepeat = true;

		while (runAndRepeat) {
			System.out.println(
					"Bitte einen Befehl eingeben ('h' oder 'help' eingeben um eine Liste mit Befehlen angezeigt zu bekommen):");
			String input = in.readLine();
			input = input.toLowerCase();

			switch (input) {
			case "addbuild":
				PoeBuildChooser.globalBuildArray.addBuild();
				break;
			case "delete":
				PoeBuildChooser.globalBuildArray.deleteBuild();
				break;
			case "choose":
				PoeBuildChooser.globalChooser.chooseBuild();
				break;
			case "initialize":
				PoeBuildChooser.globalBuildDatei.resetBuildDatei();
				break;
			case "list":
				PoeBuildChooser.globalBuildArray.list();
				break;
			case "":
				break;
			case "exit":
				runAndRepeat = false;
				break;
			case "h":
				System.out.println("'h' oder 'help': Gibt diese Liste mit Befehlen aus.");
				System.out.println("'initialize': Löscht die Build-Datei und erstellt eine neue mit 10 Standardbuilds");
				System.out.println("'addbuild': Ermöglicht das hinzufügen und abspeichern eines neuen Builds.");
				System.out.println("'delete': Ermöglicht das Löschen eines Buildes.");
				System.out.println("'list': Gibt eine Liste mit allen Builds aus.");
				System.out.println("'choose': Gibt einen zufälligen Build aus der Sammlung an Builds aus.");
				System.out.println("'cancel': ermöglicht das Abbrechen der meisten Funktionen.");
				System.out.println("'exit': Beendet das Programm.");
				break;
			case "help":
				System.out.println("'h' oder 'help': Gibt diese Liste mit Befehlen aus.");
				System.out.println("'initialize': Löscht die Build-Datei und erstellt eine neue mit 10 Standardbuilds");
				System.out.println("'addbuild': Ermöglicht das hinzufügen und abspeichern eines neuen Builds.");
				System.out.println("'delete': Ermöglicht das Löschen eines Buildes.");
				System.out.println("'list': Gibt eine Liste mit allen Builds aus.");
				System.out.println("'choose': Gibt einen zufälligen Build aus der Sammlung an Builds aus.");
				System.out.println("'cancel': ermöglicht das Abbrechen der meisten Funktionen.");
				System.out.println("'exit': Beendet das Programm.");
				break;
			default:
				System.out.println("unbekannter Befehl");
				break;
			}

		}
		in.close();
	}
}
