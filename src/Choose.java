import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Choose {

	public String build;
	private int localbuildAnzahl;

	/**
	 * @return gibt einen zufällig ausgewählten Build asl String zurück.
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void chooseBuild() throws IOException, URISyntaxException {
		Collection<String> buildCollection = PoeBuildChooser.globalBuildArray.buildHashMap.keySet();
		List<String> buildList = new ArrayList<>(buildCollection);
		localbuildAnzahl = PoeBuildChooser.globalBuildArray.anzahlBuilds();
		Random rnd = new Random();
		int randomIndex = rnd.nextInt(localbuildAnzahl);
		String choosenBuild = buildList.get(randomIndex);
		String forumlink = PoeBuildChooser.globalBuildArray.getForumsLink(choosenBuild);
		System.out.println("Der Zufällig ausgewählte Build ist:" + " " + choosenBuild);

		if (Desktop.isDesktopSupported()) {
			if (forumlink.contains("pathofexile.com/forum")) {
				Desktop.getDesktop().browse(new URI(forumlink));
				System.out.println("Forums-Link: " + forumlink);
				if (PoeBuildChooser.globalBuildArray.getPOBLink(choosenBuild).contains("pastebin.com")) {
					System.out.println("POB-Link: " + PoeBuildChooser.globalBuildArray.getPOBLink(choosenBuild));
				} else {
					System.out.println(PoeBuildChooser.globalBuildArray.getPOBLink(choosenBuild));
				}
			} else {
				System.out.println(forumlink);
				return;
			}

		} else {
			if (forumlink.contains("pathofexile.com/forum")) {
				System.out.println("Forums-Link: " + forumlink);
				if (PoeBuildChooser.globalBuildArray.getPOBLink(choosenBuild).contains("pastebin.com")) {
					System.out.println("POB-Link: " + PoeBuildChooser.globalBuildArray.getPOBLink(choosenBuild));
				} else {
					System.out.println(PoeBuildChooser.globalBuildArray.getPOBLink(choosenBuild));
				}
			} else {
				System.out.println(forumlink);
				return;
			}
		}
	}

}
