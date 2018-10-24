import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author Steffen Dworsky
 * 
 *         Enthält alle Funktionen zum Abspeichern und bearbeiten der
 *         Build-Dateien
 *
 */
public class BuildDatei {

	// TODO Code aufräumen, von HashMap<String, Tuple<String, String>> auf Build
	// Objekte wechseln.
	// TODO Danach für mehrere Dateien erweitern.

	static List<File> files = new ArrayList<File>();
	int anzahlFiles = 0;

	/**
	 * Setzt die Aktuell gewählte Build Datei entweder auf die standard
	 * Build-Auswahl zurück oder leert die Datei komplett.
	 * 
	 * @throws IOException
	 */
	public void resetBuildDatei() throws IOException {
		System.out.println(
				"Die Aktuelle Build-Datei wird dabei gelöscht und durch eine neue Leere oder Standard inizialisierte Datei ersetzt. Bitte bestätigen (Y/N)!");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean noAnswere = true;
		boolean delete = false;

		while (noAnswere) {

			String another = in.readLine();
			another = another.toUpperCase();

			switch (another) {
			case "Y":
				noAnswere = false;
				delete = true;
				break;
			case "N":
				noAnswere = false;
				delete = false;
				break;
			case "CANCEL":
				return;
			default:
				System.out.println("Bitte 'Y' oder 'N' eingeben!");
				break;
			}
		}

		if (delete) {
			PoeBuildChooser.globalBuildArray.buildArray = new ArrayList<Build>();
			makeStandardArray();
			PoeBuildChooser.globalBuildArray.buildAnzahl = PoeBuildChooser.globalBuildArray.anzahlBuilds();
			deleteFile(PoeBuildChooser.buildDateiId);
			// TODO .csv Datei durch Datenbank ersetzen
			speichern(PoeBuildChooser.globalBuildArray.buildArray,
					new File(files.get(PoeBuildChooser.buildDateiId - 1).toString()));
			System.out.println(
					"Build Datei wurde erstellt und mit einigen Builds initialisiert." + " Die Build Datei wurde unter "
							+ files.get(PoeBuildChooser.buildDateiId - 1).toString() + " gespeichert.");
		} else {
			System.out.println("Vorgang abgebrochen");
		}
	}

	/**
	 * Inizialisiert eine Standard Datei, falls ervorderlich (z.B. wenn noch keine
	 * Build Datei existiert.
	 * 
	 * @throws IOException
	 */
	public void initializeBuildDatei() throws IOException {
		// TODO soll später, durch eine extra Funktion übernommen werden, die hier dann
		// einfach aufgerufen wird. Ebenso bei resetBuildDatei (Codeminimierung)
		makeStandardArray();
		PoeBuildChooser.globalBuildArray.anzahlBuilds();
		speichern(PoeBuildChooser.globalBuildArray.buildArray, new File("Default - POEBuildChooserBuilds.csv"));
		System.out.println("Build Datei wurde erstellt und mit einigen Builds initialisiert. "
				+ "Die Build Datei wurde unter New File - POEBuildChooserBuilds.csv gespeichert.");
	}

	/**
	 * Liest alle Build Dateien (Dateien mit dem Suffix '-
	 * POEBuildChooserBuilds.csv') ein und bereitet daraus die Map mit den Builds
	 * vor.
	 * 
	 * @throws IOException
	 */
	public void dateiEinlesen() throws IOException {
		int antwort = 0;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			File dir = new File(currentDirectory());
			File[] allFiles = dir.listFiles();

			for (File file : allFiles) {
				if (accept(file)) {
					files.add(file);
				}
			}

			anzahlFiles = files.size();
			if (anzahlFiles > 1) {
				System.out.println("Es wurden mehrere Build Dateien gefunden:");
				for (int i = 0; i < anzahlFiles; i++) {
					int counter = i + 1;
					System.out.println(String.valueOf(counter) + ": " + files.get(i));
				}
				boolean chooseFile = true;
				mainWhileLoop: while (chooseFile) {
					System.out.println(
							"Nummer der zu ladenen Datei eingeben ('0' Eingeben um eine neue Datei zu erzeugen):");
					String input = in.readLine();
					char[] charArray = input.toCharArray();
					for (char c : charArray) {
						if (!Character.isDigit(c)) {
							System.out
									.println("Bitte nur Zahlen eingeben, keine Buchstaben, Leer- oder Sonderzeichen.");
							continue mainWhileLoop;
						} else {
							continue;
						}
					}
					antwort = Integer.parseInt(input);

					if (antwort == 0) {
						// TODO als extra Funktion die erstellung einer Build-Datei anbieten, nicht
						// unter dieser Funktion
						System.out.println("Bitte einen Namen für die Datei eingeben:");
						String fileName = in.readLine();
						PoeBuildChooser.globalBuildArray.buildAnzahl = PoeBuildChooser.globalBuildArray.anzahlBuilds();
						speichern(PoeBuildChooser.globalBuildArray.buildArray,
								new File(fileName + " - POEBuildChooserBuilds.csv"));
						System.out.println(
								"Build Datei wurde erstellt und mit einigen Builds initialisiert. Die Build Datei wurde als "
										+ fileName + " - POEBuildChooserBuilds.csv gespeichert.");
						return;
					} else {
						readFiles(antwort);
					}

					chooseFile = false;
					if (antwort > anzahlFiles) {
						System.out.println("Unzugeordnete Zahl eingegeben");
						chooseFile = true;
						continue;
					}
				}
			} else if (anzahlFiles == 0) {
				System.out.println(
						"Es wurden keine Gültigen Build Dateien gefunden. Es wird eine neue Build Datei erzeugt und geladen.");
				initializeBuildDatei();
			} else {
				readFiles(1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println(ex.toString());
		}
	}

	/**
	 * Speichert die aktuelle Map in der Datei ab. Später, wenn korrekt mit mehreren
	 * Dateien umgegangen werden kann, wird die Map in der aktuell gewählten Datei
	 * gespeichert.
	 * 
	 * @param buildHashMap
	 * @param datei
	 * @throws IOException
	 */
	public void speichern(ArrayList<Build> buildArray, File datei) throws IOException {
		// TODO Für den Umgang mit mehreren Dateien sollte hier noch hinzugefügt werden
		// das der Nutzer eine Datei wählen kann in der er speichern möchte.
		PrintWriter printWriter = new PrintWriter(new FileWriter(datei));
		StringBuilder sb = new StringBuilder();
		String saveBuildName;
		String saveForumURL;
		String savePOBURL;

		for (Build build : buildArray) {
			saveBuildName = build.getBuildName();
			saveForumURL = build.getForumLink();
			savePOBURL = build.getPobLink();
			sb.append(saveBuildName);
			sb.append(',');
			sb.append(saveForumURL);
			sb.append(',');
			sb.append(savePOBURL);
			sb.append('\n');
			printWriter.write(sb.toString());
			sb.delete(0, sb.length());
		}

		printWriter.close();

	}

	/**
	 * Erstellt eine neue leere Datei mit dem Namen 'Default -
	 * POEBuildChooserBuilds.csv'.
	 * 
	 * @throws IOException
	 */
	public void createFile() throws IOException {
		File f = new File("Default - POEBuildChooserBuilds.csv");
		f.createNewFile();
	}

	/**
	 * Checkt ob es Dateien gibt, bzw. eher ob es keine gibt.
	 */
	public boolean checkForFileNotExists() {
		File dir = new File(currentDirectory());
		return accept(dir);
	}

	/**
	 * Prüft ob die übergebene Datei den Suffix für Build Dateien hat (' -
	 * POEBuildChooserBuilds.csv').
	 * 
	 * @param file
	 * @return true, wenn die Datei den gewünschten Suffix hat.
	 */
	public boolean accept(File file) {
		String zuTesten = file.getName();
		Boolean contains = zuTesten.endsWith(" - POEBuildChooserBuilds.csv");
		return contains;
	}

	/**
	 * Liest die Dateien ein, teilt die Einträge auf und füllt damit die Tupel und
	 * die Map.
	 * 
	 * @param fileNumber
	 * @throws IOException
	 */
	public void readFiles(int fileNumber) throws IOException {
		int buildAnzahl = 0;
		String gewaehlteDatei = files.get(fileNumber - 1).toString();
		BufferedReader buildDatei = new BufferedReader(new FileReader(gewaehlteDatei));
		String line = buildDatei.readLine();

		if (line == null) {
			buildDatei.close();
			buildAnzahl = PoeBuildChooser.globalBuildArray.anzahlBuilds();
			System.out.println(
					"Es wurden " + buildAnzahl + " Builds aus der Build Datei: " + gewaehlteDatei + " geladen");
		} else {
			String build;
			String forum;
			String pob;
			String[] parts;

			while (line != null) {
				parts = line.split(",");
				build = parts[0];
				forum = parts[1];
				pob = parts[2];
				PoeBuildChooser.globalBuildArray.buildArray.add(new Build(build, forum, pob));
				line = buildDatei.readLine();
			}
			buildDatei.close();
			PoeBuildChooser.globalBuildArray.buildAnzahl = PoeBuildChooser.globalBuildArray.anzahlBuilds();
			System.out.println("");
			System.out.println("Es wurden " + PoeBuildChooser.globalBuildArray.buildAnzahl
					+ " Builds aus der Build Datei: " + gewaehlteDatei + " geladen");
		}
		PoeBuildChooser.buildDateiId = fileNumber;
	}

	/**
	 * Ermöglicht es eine gewählte Datei zu löschen.
	 * 
	 * @param fileId
	 */
	private void deleteFile(int fileId) {
		Path p = Paths.get(files.get(fileId - 1).toString());
		try {
			Files.deleteIfExists(p);
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", "POEBuildChooserBuilds.csv");
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", "POEBuildChooserBuilds.csv");
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	/**
	 * @return Gibt das aktuelle Verzeichniss zurück.
	 */
	public String currentDirectory() {
		Path currentRelativePath = Paths.get("");
		return currentRelativePath.toAbsolutePath().toString();
	}

	/**
	 * Funktion um das Array mit einigen Standard Build Objekten zu füllen, für
	 * verschiedene Zwecke. Die Build Objekte werden zu dem Array hinzugefügt.
	 */
	public void makeStandardArray() {
		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("Golemancer",
				"https://www.pathofexile.com/forum/view-thread/1827487", "https://pastebin.com/QS1VB9Vp"));

		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("Kintetic Blast",
				"https://www.pathofexile.com/forum/view-thread/2074126", "https://pastebin.com/Zm5JqGFs"));

		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("Spectres",
				"https://www.pathofexile.com/forum/view-thread/1971585", "https://pastebin.com/iV5wMmVV"));

		PoeBuildChooser.globalBuildArray.buildArray
				.add(new Build("Aurabot", "https://www.pathofexile.com/forum/view-thread/2093326",
						"Es wurde zu diesem Build kein POB-Pastebin Link angegeben."));

		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("Cursebot",
				"https://www.pathofexile.com/forum/view-thread/2059332", "https://pastebin.com/DgA2MMsy"));

		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("Ultrabot",
				"https://www.pathofexile.com/forum/view-thread/1893911", "https://pastebin.com/BT6G6KNE"));

		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("Pizza-Sticks",
				"https://www.pathofexile.com/forum/view-thread/1730745", "https://pastebin.com/jm9NBFJ4"));

		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("Reave Champion",
				"https://www.pathofexile.com/forum/view-thread/2119881", "https://pastebin.com/RXWzDTLF"));

		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("Mjölner Discharge",
				"https://www.pathofexile.com/forum/view-thread/1846362", "https://pastebin.com/e5etK9i6"));

		PoeBuildChooser.globalBuildArray.buildArray.add(new Build("The Poet's Pen Volatile Dead",
				"https://www.pathofexile.com/forum/view-thread/2050819", "https://pastebin.com/MrC3xH2d"));
	}
}
