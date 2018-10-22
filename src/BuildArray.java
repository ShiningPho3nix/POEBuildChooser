import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Steffen Dworsky
 *
 *         BuildArray verwaltet die Sammlung an Builds, Array im Namen ist noch
 *         von einer älteren Version. In Zukunft wird vermutlich wieder ein
 *         Array verwendet.
 */
public class BuildArray {

	public int buildAnzahl;
	public boolean initialize;
	BufferedReader in;
	String initializeAnswere;
	public HashMap<String, Tuple<String, String>> buildHashMap;

	/**
	 * Inizialisiert einige Werte.
	 */
	public BuildArray() {
		buildAnzahl = 0;
		initialize = true;
		initializeAnswere = null;
		buildHashMap = new HashMap<String, Tuple<String, String>>();
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
	 * Ermöglicht das hinzufügen von einem oder mehreren Builds zu der aktuellen
	 * Map. Die Map wird anschließend in der Aktuellen Datei gespeichert.
	 * 
	 * @throws IOException
	 */
	public void addBuild() throws IOException {
		String inputBuildName;
		String inputBuildURL;
		String inputPOBURL;
		Boolean tryAgain = true;
		Boolean anotherOne = true;
		Boolean exists = false;
		Boolean continueAnotherOne = false;

		while (anotherOne) {
			in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Build Namen eingeben:");
			inputBuildName = in.readLine();

			if (inputBuildName != null && !inputBuildName.contains(",") && !inputBuildName.equals("")) {
				Collection<String> buildCollection = PoeBuildChooser.globalBuildArray.buildHashMap.keySet();
				List<String> buildList = new ArrayList<>(buildCollection);

				for (Iterator<String> iterator = buildList.iterator(); iterator.hasNext();) {
					String string = iterator.next();

					if (string.equals(inputBuildName)) {
						exists = true;
						break;
					}
				}

				if (exists) {
					System.out.println("Es existiert bereits ein Build mit diesem Namen.");
					System.out.println(
							"Möchtest Du den Namen ändern (Y/N)? Andernfalls wird der Buildname um eine fortlaufende Nummer erweitert.");
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
				Tuple<String, String> tuple = new Tuple<String, String>(null, null);
				System.out.println("Optional: Forums Link zum Build angeben (überspringen mit Enter):");
				tryAgain = true;
				while (tryAgain) {
					inputBuildURL = in.readLine();

					if (inputBuildURL.equals("")) {
						tryAgain = false;
						tuple.setX("Es wurde zu diesem Build kein Forums Link angegeben.");
						continue;

					} else if (inputBuildURL.contains("pathofexile.com/forum")) {
						tryAgain = false;
						System.out.println(inputBuildURL + " wird als Forums Link dem Build zugeordnet.");
						tuple.setX(inputBuildURL);

					} else if (in.toString().toUpperCase().equals("CANCEL")) {
						return;

					} else {
						System.out.println("Eingabe nicht als gültigen Forums Link erkannt.");
						continue;
					}
				}

				tryAgain = true;
				System.out.println("Optional: POB-Pastebin Link zum Build angeben (überspringen mit Enter):");

				while (tryAgain) {
					inputPOBURL = in.readLine();

					if (inputPOBURL.equals("")) {
						tryAgain = false;
						tuple.setY("Es wurde zu diesem Build kein POB-Pastebin Link angegeben.");
						continue;

					} else if (inputPOBURL.contains("pastebin.com")) {
						tryAgain = false;
						System.out.println(inputPOBURL + " wird als POB-Pastebin Link dem Build zugeordnet.");
						tuple.setY(inputPOBURL);

					} else if (in.toString().toUpperCase().equals("CANCEL")) {
						return;

					} else {
						System.out.println("Eingabe nicht als gültigen POB-Pastebin Link erkannt.");
						continue;
					}
				}
				buildHashMap.put(inputBuildName, tuple);
				buildAnzahl = anzahlBuilds();
				PoeBuildChooser.globalBuildDatei.speichern(buildHashMap,
						new File(BuildDatei.files.get(PoeBuildChooser.buildDateiId - 1).toString()));
			} else if (in.toString().toUpperCase().equals("CANCEL")) {
				return;
			} else {
				System.out.println("Build Name darf nicht leer sein und darf kein ',' enthalten.");
			}
			System.out.println("Einen weitern Build hinzufügen (Y/N)?");
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
	 * Ermöglicht das Löschen eines Buildes über den Namen.
	 * 
	 * @throws IOException
	 */
	public void deleteBuild() throws IOException {
		Boolean unclear = true;
		Boolean noAnswere = true;

		while (unclear) {
			System.out.println("Zu löschenden Buildnamen eingeben:");
			String buildName = in.readLine();
			Boolean buildExists = false;

			if (buildName.equals("") || buildName.equals(null)) {
				System.out.println("Eingabe darf nicht leer sein");
				continue;
			}

			buildExists = PoeBuildChooser.globalBuildArray.buildHashMap.containsKey(buildName);
			if (buildExists) {
				PoeBuildChooser.globalBuildArray.buildHashMap.remove(buildName);
				PoeBuildChooser.globalBuildDatei.speichern(buildHashMap,
						new File(BuildDatei.files.get(PoeBuildChooser.buildDateiId - 1).toString()));
				System.out.println("Löschen des Builds " + buildName + " war erfolgreich");
				unclear = false;
				break;
			} else {
				System.out.println(
						"Diesen Buildnamen scheint es nicht zu geben. " + "Bitte den exakten Buildnamen eingeben. "
								+ "Gegebenenfalls mit 'list' eine Liste aller Builds ausgeben lassen.");
			}
		}

		System.out.println("Soll ein weiterer Build gelöscht werden (Y/N)?");
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
	 * Für den Fall das es einen Namen schon gibt, so wird dieser mit einer
	 * fortlaufenden Zahl erweitert. Verwendbar für Build-Namen, als auch
	 * Build-Dateien.
	 */
	private String createContinuousName(String inputBuildName) {

		Collection<String> buildCollection = PoeBuildChooser.globalBuildArray.buildHashMap.keySet();
		List<String> buildList = new ArrayList<>(buildCollection);
		String buildName = inputBuildName;
		String originalBuildName = inputBuildName;
		int i = 1;

		for (Iterator<String> iterator = buildList.iterator(); iterator.hasNext();) {
			String string = iterator.next();

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
		TreeMap<String, Tuple<String, String>> sortedBuildHashMap = (TreeMap<String, Tuple<String, String>>) PoeBuildChooser.globalBuildArray
				.sortMapByKey(buildHashMap);
		Collection<String> buildCollection = sortedBuildHashMap.keySet();
		List<String> buildList = new ArrayList<>(buildCollection);
		System.out.println("Die derzeitigen Builds sind:");
		for (Iterator<String> iterator = buildList.iterator(); iterator.hasNext();) {
			String string = iterator.next();
			System.out.println(string);
		}
		System.out.println("");
	}

	/**
	 * Sortiert die Builds alphabetisch.
	 * 
	 * @param aItems
	 * @return Gibt eine alphabetisch sortierte Map zurück.
	 */
	public Map<String, Tuple<String, String>> sortMapByKey(Map<String, Tuple<String, String>> aItems) {
		TreeMap<String, Tuple<String, String>> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		result.putAll(aItems);
		return result;
	}

	/**
	 * Funktion um die Anzahl an Builds in der aktuellen Datei zu zählen.
	 * 
	 * @return Gibt die Anzahl an Builds zurück
	 */
	public int anzahlBuilds() {
		buildAnzahl = buildHashMap.size();
		return buildAnzahl;
	}

	/**
	 * @param buildname
	 * @return Gibt den ersten Wert im Tupel zurück, was immer der Forums Link sein
	 *         solte.
	 */
	public String getForumsLink(String buildname) {
		return buildHashMap.get(buildname).getX();
	}

	/**
	 * @param buildname
	 * @return Gibt den zweiten Wert im Tupel zurück, was immer der POB Link sein
	 *         sollte.
	 */
	public String getPOBLink(String buildname) {
		return buildHashMap.get(buildname).getY();
	}

	/**
	 * Funktion fügt der HashMap einen Build mitsamt Forums-Link und PoB-Link hinzu.
	 * 
	 * @param buildname
	 * @param forum
	 * @param pob
	 */
	public void putBuild(String buildname, String forum, String pob) {
		Tuple<String, String> tuple = new Tuple<String, String>(forum, pob);
		buildHashMap.put(buildname, tuple);
	}

}
