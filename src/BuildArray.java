import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BuildArray {

	public int buildAnzahl;
	public boolean initialize;
	BufferedReader in;
	String initializeAnswere;
	public HashMap<String, Tuple<String, String>> buildHashMap;
	public BuildDatei buildDatei;

	/**
	 * 
	 */
	public BuildArray() {
		buildAnzahl = 0;
		initialize = true;
		initializeAnswere = null;
		buildHashMap = new HashMap<String, Tuple<String, String>>();
		buildDatei = new BuildDatei();
	}

	/**
	 * 
	 * @throws IOException
	 *             Initialisiert das Array mit den Builds. Existiert bisher
	 *             keine Build Datei, so wird eine neue erzeugt. Auf Wunsch des
	 *             Users entweder mit einigen Builds oder komplett leer.
	 *             Existiert eine Datei werden diese in das Array geladen.
	 */
	public void initializeBuildArray() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		if (buildDatei.checkForFileNotExists()) {
			System.out.println("Build Datei existiert nicht, es wird eine neue Build Datei erzeugt.");
			System.out.println("Soll die neue Build Datei mit einigen Standardbuilds initialisiert werden (Y/N)?");
			while (initialize) {
				initializeAnswere = in.readLine().toUpperCase();
				switch (initializeAnswere) {
				case "Y":
					buildDatei.initializeBuildDatei();
					initialize = false;
					break;
				case "N":
					buildDatei.createFile();
					System.out.println("Build Datei wurde leer erstellt."
							+ " Die Build Datei wurde als POEBuildChooserBuilds.csv gespeichert.");
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
			buildDatei.dateiEinlesen();
		}
	}

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

					} else {
						System.out.println("Eingabe nicht als gültigen POB-Pastebin Link erkannt.");
						continue;
					}
				}
				buildHashMap.put(inputBuildName, tuple);
				buildAnzahl = anzahlBuilds();
				buildDatei.speichern(buildHashMap, new File("POEBuildChooserBuilds.csv"));
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
				default:
					System.out.println("Bitte 'Y' oder 'N' eingeben!");
					break;

				}
			}

		}
	}

	/**
	 * 
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

	public int anzahlBuilds() {
		buildAnzahl = buildHashMap.size();
		return buildAnzahl;
	}

	public String getForumsLink(String buildname) {
		return buildHashMap.get(buildname).getX();
	}

	public String getPOBLink(String buildname) {
		return buildHashMap.get(buildname).getY();
	}

	public void putBuild(String buildname, String forum, String pob) {
		Tuple<String, String> tuple = new Tuple<String, String>(forum, pob);
		buildHashMap.put(buildname, tuple);
	}
}
