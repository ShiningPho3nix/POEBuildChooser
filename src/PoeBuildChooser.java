import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Iterator;

public class PoeBuildChooser {

	static Choose globalChooser;
	static BuildArray globalBuildArray;
	static BuildDatei globalBuildDatei;

	public static void main(String[] args) throws IOException, URISyntaxException {
		globalChooser = new Choose();
		globalBuildArray = new BuildArray();
		globalBuildDatei = new BuildDatei();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean runAndRepeat = true;
		globalBuildArray.initializeBuildArray();
		// System.out.println("Bitte einen Befehl eingeben ('h' oder 'help'
		// eingeben um eine Liste mit Befehlen angezeigt zu bekommen):");
		// System.out.println("");

		while (runAndRepeat) {
			System.out.println(
					"Bitte einen Befehl eingeben ('h' oder 'help' eingeben um eine Liste mit Befehlen angezeigt zu bekommen):");
			String input = in.readLine();
			input = input.toLowerCase();
			// TODO Funktion list hinzufügen (gibt alle derzeitig in der Datei
			// befindlichen Builds aus)
			
			//TODO Funktion delete hinzufügen
			switch (input) {
			case "addbuild":
				globalBuildArray.addBuild();
				break;
			case "choose":
				globalChooser.chooseBuild();
				break;
			case "initialize":
				// TODO so umändern, das die Datei mit default builds neu
				// erstellt wird? Eventuell eine nneue funktion default
				// einführen, mit y/n sicherheitsabfrage
				globalBuildDatei.initializeBuildDatei();
				break;
			case "list":
				System.out.println("Die derzeitig abgespeicherten Builds sind:");
				for (Iterator<String> iterator = globalBuildArray.buildHashMap.keySet().iterator(); iterator
						.hasNext();) {
					String build = (String) iterator.next();
					System.out.println(build);
				}
				break;
			case "":
				break;
			case "exit":
				runAndRepeat = false;
				break;
			case "h":
				System.out.println("'h' oder 'help': Gibt diese Liste mit Befehlen aus.");
				System.out.println("'initialize': Füllt die Build Datei mit einigen Builds.");
				System.out.println("'addbuild': Ermöglicht das hinzufügen und abspeichern eines neuen Builds.");
				System.out.println("'choose': Gibt einen zufälligen Build aus der Sammlung an Builds aus.");
				System.out.println("'exit': Beendet das Programm.");
				break;
			case "help":
				System.out.println("'h' oder 'help': Gibt diese Liste mit Befehlen aus.");
				System.out.println("'initialize': Füllt die Build Datei mit einigen Builds.");
				System.out.println("'addbuild': Ermöglicht das hinzufügen und abspeichern eines neuen Builds.");
				System.out.println("'exit': Beendet das Programm.");
				System.out.println("'choose': Gibt einen zufälligen Build aus der Sammlung an Builds aus.");
				break;
			default:
				System.out.println("unbekannter Befehl");
				break;
			}

		}
		in.close();
	}
}
