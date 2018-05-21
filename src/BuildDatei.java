import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

/**
 * 
 */

/**
 * @author Steffen Dworsky
 *
 */
public class BuildDatei {

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
		speichern(PoeBuildChooser.globalBuildArray.buildHashMap, new File("POEBuildChooserBuilds.csv"));
		System.out.println("Build Datei wurde erstellt und mit einigen Builds initialisiert."
				+ " Die Build Datei wurde als POEBuildChooserBuilds.csv gespeichert.");
	}

	public void dateiEinlesen() throws IOException {
		try {
			BufferedReader buildDatei = new BufferedReader(new FileReader("POEBuildChooserBuilds.csv"));
			String line = buildDatei.readLine();
			int buildAnzahl;
			if (line == null) {
				buildDatei.close();
				buildAnzahl = PoeBuildChooser.globalBuildArray.anzahlBuilds();
				System.out.println("Es wurden " + buildAnzahl + " Builds aus der Build Datei geladen");
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
				System.out.println("Es wurden " + buildAnzahl + " Builds aus der Build Datei geladen");
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

		PrintWriter printWriter = new PrintWriter(new FileWriter(datei));
		Tuple<String, String> forIteration = new Tuple<String, String>("", "");
		StringBuilder sb = new StringBuilder();
		Set<String> keys = buildHashMap.keySet();
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
		File f = new File("POEBuildChooserBuilds.csv");
		f.createNewFile();

	}

	/**
	 * 
	 */
	public Boolean checkForFileNotExists() {
		Path p = Paths.get("POEBuildChooserBuilds.csv");
		return Files.notExists(p);

	}
}
