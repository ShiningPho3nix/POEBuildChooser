import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 
 * @author Steffen Dworsky
 *
 *         BuildArray verwaltet die Sammlung an Builds, Array im Namen ist noch
 *         von einer �lteren Version. In Zukunft wird vermutlich wieder ein
 *         Array verwendet.
 */
public class BuildArray {

	int buildAnzahl;
	private boolean initialize;
	private BufferedReader in;
	private String initializeAnswere;
	public ArrayList<Build> buildArray;

	/**
	 * Inizialisiert einige Werte.
	 */
	public BuildArray() {
		buildAnzahl = 0;
		initialize = true;
		initializeAnswere = null;
		buildArray = new ArrayList<Build>();
	}

	/**
	 * 
	 * @throws IOException Initialisiert die Map mit den Builds, welche aus einer
	 *                     Datei eingelesen werden. Existiert bisher keine Build
	 *                     Datei, so wird eine neue erzeugt. Auf Wunsch des Users
	 *                     entweder mit einigen Builds oder komplett leer.
	 */
	public void initializeBuildArray() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		if (PoeBuildChooser.globalBuildDatei.checkForFileNotExists()) {
			System.out.println("Build Datei existiert nicht, es wird eine neue Build Datei erzeugt.");
			System.out.println("Soll die neue Build Datei mit einigen Standardbuilds initialisiert werden (Y/N)?");
			while (initialize) {
				initializeAnswere = in.readLine().toUpperCase();
				switch (initializeAnswere) {
				case "Y":
					PoeBuildChooser.globalBuildDatei.initializeBuildDatei();
					initialize = false;
					break;
				case "N":
					PoeBuildChooser.globalBuildDatei.createFile();
					System.out.println("Build Datei wurde leer erstellt."
							+ " Die Build Datei wurde als 'Default - POEBuildChooserBuilds.csv' gespeichert.");
					initialize = false;
					break;
				default:
					System.out.println(
							"Bitte 'Y' eingeben, wenn die Build Datei erstellt und mit einigen Builds initialisiert werden soll.");
					System.out.println(
							"Oder 'N' eingeben, wenn die Build Datei erstellt, aber nicht mit einigen Builds initialisiert werden soll.");
				}
			}
		} else {
			System.out.println("Build Datei gefunden, Builds werden geladen.");
			PoeBuildChooser.globalBuildDatei.dateiEinlesen();
		}
	}

	/**
	 * Erm�glicht das hinzuf�gen von einem oder mehreren Builds zu der aktuellen
	 * Map. Die Map wird anschlie�end in der Aktuellen Datei gespeichert.
	 * 
	 * @throws IOException
	 */
	public void addBuild() throws IOException {
		String inputBuildName = "";
		String inputForumURL = "";
		String inputPOBURL = "";
		Boolean tryAgain = true;
		Boolean anotherOne = true;
		Boolean exists = false;
		Boolean continueAnotherOne = false;
		ArrayList<Build> localBuildArray = new ArrayList<Build>();

		while (anotherOne) {
			localBuildArray = PoeBuildChooser.globalBuildArray.buildArray;
			in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Build Namen eingeben:");
			inputBuildName = in.readLine();

			if (inputBuildName != null && !inputBuildName.contains(",") && !inputBuildName.equals("")) {

				for (Build build : localBuildArray) {
					String string = build.getBuildName();

					if (string.equals(inputBuildName)) {
						exists = true;
						break;
					}
				}

				if (exists) {
					System.out.println("Es existiert bereits ein Build mit diesem Namen.");
					System.out.println(
							"M�chtest Du den Namen �ndern (Y/N)? Andernfalls wird der Buildname um eine fortlaufende Nummer erweitert.");
					Boolean noAnswere = true;

					while (noAnswere) {
						String another = in.readLine();
						another = another.toUpperCase();

						switch (another) {
						case "Y":
							noAnswere = false;
							continueAnotherOne = true;
							exists = false;
							continue;
						case "N":
							noAnswere = false;
							inputBuildName = createContinuousName(inputBuildName);
							exists = false;
							break;
						case "CANCEL":
							return;
						default:
							System.out.println("Bitte 'Y' oder 'N' eingeben!");
							break;
						}
					}
				}

				if (continueAnotherOne) {
					continue;
				}
				System.out.println("Optional: Forums Link zum Build angeben (�berspringen mit Enter):");
				tryAgain = true;
				while (tryAgain) {
					inputForumURL = in.readLine();

					if (inputForumURL.equals("")) {
						tryAgain = false;
						inputForumURL = "Es wurde zu diesem Build kein Forums Link angegeben.";
						continue;

					} else if (inputForumURL.contains("pathofexile.com/forum")) {
						tryAgain = false;
						System.out.println(inputForumURL + " wird als Forums Link dem Build zugeordnet.");

					} else if (in.toString().toUpperCase().equals("CANCEL")) {
						return;

					} else {
						System.out.println("Eingabe nicht als g�ltigen Forums Link erkannt.");
						continue;
					}
				}

				tryAgain = true;
				System.out.println("Optional: POB-Pastebin Link zum Build angeben (�berspringen mit Enter):");

				while (tryAgain) {
					inputPOBURL = in.readLine();

					if (inputPOBURL.equals("")) {
						tryAgain = false;
						inputPOBURL = "Es wurde zu diesem Build kein POB-Pastebin Link angegeben.";
						continue;

					} else if (inputPOBURL.contains("pastebin.com")) {
						tryAgain = false;
						System.out.println(inputPOBURL + " wird als POB-Pastebin Link dem Build zugeordnet.");

					} else if (in.toString().toUpperCase().equals("CANCEL")) {
						return;

					} else {
						System.out.println("Eingabe nicht als g�ltigen POB-Pastebin Link erkannt.");
						continue;
					}
				}
				localBuildArray.add(new Build(inputBuildName, inputForumURL, inputPOBURL));
				PoeBuildChooser.globalBuildArray.buildAnzahl = anzahlBuilds();
				PoeBuildChooser.globalBuildDatei.speichern(buildArray,
						new File(BuildDatei.files.get(PoeBuildChooser.buildDateiId - 1).toString()));
			} else if (in.toString().toUpperCase().equals("CANCEL")) {
				return;
			} else {
				System.out.println("Build Name darf nicht leer sein und darf kein ',' enthalten.");
			}
			System.out.println("Einen weitern Build hinzuf�gen (Y/N)?");
			Boolean noAnswere = true;
			while (noAnswere) {
				String another = in.readLine();
				another = another.toUpperCase();
				switch (another) {
				case "Y":
					noAnswere = false;
					break;
				case "N":
					noAnswere = false;
					anotherOne = false;
					break;
				case "CANCEL":
					return;
				default:
					System.out.println("Bitte 'Y' oder 'N' eingeben!");
					break;

				}
			}

		}
	}

	/**
	 * Erm�glicht das L�schen eines Buildes �ber den Namen.
	 * 
	 * @throws IOException
	 */
	public void deleteBuild() throws IOException {
		Boolean unclear = true;
		Boolean noAnswere = true;

		while (unclear) {
			System.out.println("Zu l�schenden Buildnamen eingeben:");
			String buildNameToTest = in.readLine();
			Boolean buildExists = false;
			Build buildToDelete = new Build("", "", "");

			if (buildNameToTest.equals("") || buildNameToTest.equals(null)) {
				System.out.println("Eingabe darf nicht leer sein");
				continue;
			}

			for (Build build : PoeBuildChooser.globalBuildArray.buildArray) {
				String buildNameFromArray = build.getBuildName();
				if (buildNameFromArray.equals(buildNameToTest)) {
					buildExists = true;
					buildToDelete = build;
					break;
				}

			}

			if (buildExists) {
				PoeBuildChooser.globalBuildArray.buildArray.remove(buildToDelete);
				PoeBuildChooser.globalBuildDatei.speichern(buildArray,
						new File(BuildDatei.files.get(PoeBuildChooser.buildDateiId - 1).toString()));
				System.out.println("L�schen des Builds " + buildNameToTest + " war erfolgreich");
				unclear = false;
				break;
			} else {
				System.out.println(
						"Diesen Buildnamen scheint es nicht zu geben. " + "Bitte den exakten Buildnamen eingeben. "
								+ "Gegebenenfalls mit 'list' eine Liste aller Builds ausgeben lassen.");
			}
		}

		System.out.println("Soll ein weiterer Build gel�scht werden (Y/N)?");
		noAnswere = true;
		while (noAnswere) {
			String another = in.readLine();
			another = another.toUpperCase();

			switch (another) {
			case "Y":
				noAnswere = false;
				unclear = true;
				break;
			case "N":
				noAnswere = false;
				unclear = false;
				break;
			case "CANCEL":
				return;
			default:
				System.out.println("Bitte 'Y' oder 'N' eingeben!");
				break;

			}
		}
	}

	/**
	 * F�r den Fall das es einen Namen schon gibt, so wird dieser mit einer
	 * fortlaufenden Zahl erweitert. Verwendbar f�r Build-Namen, als auch
	 * Build-Dateien.
	 */
	private String createContinuousName(String inputBuildName) {
		String buildName = inputBuildName;
		String originalBuildName = inputBuildName;
		int i = 1;

		for (Build build : PoeBuildChooser.globalBuildArray.buildArray) {
			String string = build.getBuildName();

			if (string.equals(buildName)) {
				buildName = originalBuildName + i;
				i++;
				continue;
			}
		}
		return buildName;
	}

	/**
	 * Gibt eine Liste mit allen Build-Namen in der aktuellen Build-Datei auf der
	 * Konsole aus.
	 */
	public void list() {
		System.out.println("Die derzeitigen Builds sind:");
		for (Build build : PoeBuildChooser.globalBuildArray.buildArray) {
			String string = build.getBuildName();
			System.out.println(string);
		}
		System.out.println("");
	}

	/**
	 * Funktion um die Anzahl an Builds in der aktuellen Datei zu z�hlen.
	 * 
	 * @return Gibt die Anzahl an Builds zur�ck
	 */
	public int anzahlBuilds() {
		buildAnzahl = buildArray.size();
		return buildAnzahl;
	}

}
