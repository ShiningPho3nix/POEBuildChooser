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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 */

/**
 * @author Steffen Dworsky
 *
 */
public class BuildDatei {

	// TODO Grundtunktionalität ist wieder hergestellt, als nächstes Code aufräumen
	// und von HashMap<String, Tuple<String, String>> auf Build Objekte wechseln.
	// Danach für mehrere Dateien erweitern.

	static List<File> files = new ArrayList<File>();
	int anzahlFiles = 0;

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
			HashMap<String, Tuple<String, String>> buildHashMap = new HashMap<String, Tuple<String, String>>();
			Tuple<String, String> tuple1 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/1827487", "https://pastebin.com/QS1VB9Vp");
			buildHashMap.put("Golemancer", tuple1);
			Tuple<String, String> tuple2 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/2074126", "https://pastebin.com/Zm5JqGFs");
			buildHashMap.put("Kintetic Blast", tuple2);
			Tuple<String, String> tuple3 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/1971585", "https://pastebin.com/iV5wMmVV");
			buildHashMap.put("Spectres", tuple3);
			Tuple<String, String> tuple4 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/2093326",
					"Es wurde zu diesem Build kein POB-Pastebin Link angegeben.");
			buildHashMap.put("Aurabot", tuple4);
			Tuple<String, String> tuple5 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/2059332", "https://pastebin.com/DgA2MMsy");
			buildHashMap.put("Cursebot", tuple5);
			Tuple<String, String> tuple6 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/1893911", "https://pastebin.com/BT6G6KNE");
			buildHashMap.put("Ultrabot", tuple6);
			Tuple<String, String> tuple7 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/1730745", "https://pastebin.com/jm9NBFJ4");
			buildHashMap.put("Pizza-Sticks", tuple7);
			Tuple<String, String> tuple8 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/2119881", "https://pastebin.com/RXWzDTLF");
			buildHashMap.put("Reave Champion", tuple8);
			Tuple<String, String> tuple9 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/1846362", "https://pastebin.com/e5etK9i6");
			buildHashMap.put("Mjölner Discharge", tuple9);
			Tuple<String, String> tuple10 = new Tuple<String, String>(
					"https://www.pathofexile.com/forum/view-thread/2050819", "https://pastebin.com/MrC3xH2d");
			buildHashMap.put("The Poet's Pen Volatile Dead", tuple10);

			PoeBuildChooser.globalBuildArray.buildHashMap = buildHashMap;
			PoeBuildChooser.globalBuildArray.anzahlBuilds();
			deleteFile(PoeBuildChooser.buildDateiId);
			// TODO .csv Datei durch Datenbank ersetzen
			speichern(buildHashMap, new File(files.get(PoeBuildChooser.buildDateiId - 1).toString()));
			System.out.println(
					"Build Datei wurde erstellt und mit einigen Builds initialisiert." + " Die Build Datei wurde unter "
							+ files.get(PoeBuildChooser.buildDateiId - 1).toString() + " gespeichert.");
		} else {
			System.out.println("Vorgang abgebrochen");
		}
	}

	public void initializeBuildDatei() throws IOException {
		Tuple<String, String> tuple1 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/1827487", "https://pastebin.com/QS1VB9Vp");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Golemancer", tuple1);
		Tuple<String, String> tuple2 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/2074126", "https://pastebin.com/Zm5JqGFs");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Kintetic Blast", tuple2);
		Tuple<String, String> tuple3 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/1971585", "https://pastebin.com/iV5wMmVV");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Spectres", tuple3);
		Tuple<String, String> tuple4 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/2093326",
				"Es wurde zu diesem Build kein POB-Pastebin Link angegeben.");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Aurabot", tuple4);
		Tuple<String, String> tuple5 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/2059332", "https://pastebin.com/DgA2MMsy");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Cursebot", tuple5);
		Tuple<String, String> tuple6 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/1893911", "https://pastebin.com/BT6G6KNE");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Ultrabot", tuple6);
		Tuple<String, String> tuple7 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/1730745", "https://pastebin.com/jm9NBFJ4");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Pizza-Sticks", tuple7);
		Tuple<String, String> tuple8 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/2119881", "https://pastebin.com/RXWzDTLF");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Reave Champion", tuple8);
		Tuple<String, String> tuple9 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/1846362", "https://pastebin.com/e5etK9i6");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("Mjölner Discharge", tuple9);
		Tuple<String, String> tuple10 = new Tuple<String, String>(
				"https://www.pathofexile.com/forum/view-thread/2050819", "https://pastebin.com/MrC3xH2d");
		PoeBuildChooser.globalBuildArray.buildHashMap.put("The Poet's Pen Volatile Dead", tuple10);
		PoeBuildChooser.globalBuildArray.anzahlBuilds();
		speichern(PoeBuildChooser.globalBuildArray.buildHashMap, new File("New File - POEBuildChooserBuilds.csv"));
		System.out.println("Build Datei wurde erstellt und mit einigen Builds initialisiert. "
				+ "Die Build Datei wurde unter New File - POEBuildChooserBuilds.csv gespeichert.");
	}

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
				while (chooseFile) {
					boolean keineZahlEingegeben = false;
					System.out.println(
							"Nummer der zu ladenen Datei eingeben ('0' Eingeben um eine neue Datei zu erzeugen):");
					String input = in.readLine();
					char[] charArray = input.toCharArray();
					for (char c : charArray) {
						if (!Character.isDigit(c)) {
							System.out
									.println("Bitte nur Zahlen eingeben, keine Buchstaben, Leer- oder Sonderzeichen.");
							keineZahlEingegeben = true;
							break;
						} else {
							continue;
						}
					}
					if (keineZahlEingegeben) {
						continue;
					} else if (antwort == 0) {
						System.out.println("Bitte einen Namen für die Datei eingeben:");
						String buildName = in.readLine();
						HashMap<String, Tuple<String, String>> buildHashMap = new HashMap<String, Tuple<String, String>>();
						PoeBuildChooser.globalBuildArray.buildHashMap = buildHashMap;
						PoeBuildChooser.globalBuildArray.anzahlBuilds();
						speichern(PoeBuildChooser.globalBuildArray.buildHashMap,
								new File(buildName + " - POEBuildChooserBuilds.csv"));
						System.out.println(
								"Build Datei wurde erstellt und mit einigen Builds initialisiert. Die Build Datei wurde als "
										+ buildName + " - POEBuildChooserBuilds.csv gespeichert.");

						return;
					} else {
						antwort = Integer.parseInt(input);
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

	public void speichern(HashMap<String, Tuple<String, String>> buildHashMap, File datei) throws IOException {
		TreeMap<String, Tuple<String, String>> sortedBuildHashMap = (TreeMap<String, Tuple<String, String>>) PoeBuildChooser.globalBuildArray
				.sortMapByKey(buildHashMap);
		PrintWriter printWriter = new PrintWriter(new FileWriter(datei));
		Tuple<String, String> forIteration = new Tuple<String, String>("", "");
		StringBuilder sb = new StringBuilder();
		Set<String> keys = sortedBuildHashMap.keySet();
		String saveBuildName;
		String saveForumURL;
		String savePOBURL;

		for (String key : keys) {
			saveBuildName = key;
			forIteration = buildHashMap.get(key);
			saveForumURL = forIteration.getX();
			savePOBURL = forIteration.getY();
			sb.append(saveBuildName);
			// System.out.println(sb);
			sb.append(',');
			sb.append(saveForumURL);
			// System.out.println(sb);
			sb.append(',');
			sb.append(savePOBURL);
			// System.out.println(sb);
			sb.append('\n');
			// System.out.println(sb);
			printWriter.write(sb.toString());
			sb.delete(0, sb.length());
		}

		printWriter.close();

	}

	/**
	 * @throws IOException
	 * 
	 */
	public void createFile() throws IOException {
		File f = new File("Default - POEBuildChooserBuilds.csv");
		f.createNewFile();
	}

	/**
	 * 
	 */
	public boolean checkForFileNotExists() {
		File dir = new File(currentDirectory());
		return accept(dir);
	}

	public boolean accept(File file) {
		String zuTesten = file.getName();
		Boolean contains = zuTesten.endsWith(" - POEBuildChooserBuilds.csv");
		return contains;
	}

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
				Tuple<String, String> tupleread = new Tuple<String, String>(forum, pob);
				PoeBuildChooser.globalBuildArray.buildHashMap.put(build, tupleread);
				line = buildDatei.readLine();
			}
			buildDatei.close();
			buildAnzahl = PoeBuildChooser.globalBuildArray.anzahlBuilds();
			System.out.println("");
			System.out.println(
					"Es wurden " + buildAnzahl + " Builds aus der Build Datei: " + gewaehlteDatei + " geladen");
		}
		PoeBuildChooser.buildDateiId = fileNumber;
	}

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

	public String currentDirectory() {
		Path currentRelativePath = Paths.get("");
		return currentRelativePath.toAbsolutePath().toString();
	}
}
