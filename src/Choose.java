import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Klasse ist für die auswahl eines zufälligen Buildes zuständig.
 * 
 * @author Steffen Dworsky
 *
 */
public class Choose {

	/**
	 * @return Gibt einen zufällig ausgewählten Build auf der Konsole aus.
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void chooseBuild() throws IOException, URISyntaxException {
		Random rnd = new Random();
		int randomIndex = rnd.nextInt(PoeBuildChooser.globalBuildArray.anzahlBuilds());
		Build choosenBuild = PoeBuildChooser.globalBuildArray.buildArray.get(randomIndex);
		String forumlink = choosenBuild.getForumLink();
		System.out.println("Der Zufällig ausgewählte Build ist:" + " " + choosenBuild.getBuildName());

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI(forumlink));
		}
		System.out.println("Forums-Link: " + forumlink);
		System.out.println("PoB-Pastebin Link: " + choosenBuild.pobLink);
		System.out.println("");
	}
}
