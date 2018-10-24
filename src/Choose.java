import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Klasse ist f�r die auswahl eines zuf�lligen Buildes zust�ndig.
 * 
 * @author Steffen Dworsky
 *
 */
public class Choose {

	/**
	 * @return Gibt einen zuf�llig ausgew�hlten Build auf der Konsole aus.
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void chooseBuild() throws IOException, URISyntaxException {
		Random rnd = new Random();
		int randomIndex = rnd.nextInt(PoeBuildChooser.globalBuildArray.anzahlBuilds());
		Build choosenBuild = PoeBuildChooser.globalBuildArray.buildArray.get(randomIndex);
		String forumlink = choosenBuild.getForumLink();
		System.out.println("Der Zuf�llig ausgew�hlte Build ist:" + " " + choosenBuild.getBuildName());

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI(forumlink));
		}
		System.out.println("Forums-Link: " + forumlink);
		System.out.println("PoB-Pastebin Link: " + choosenBuild.pobLink);
		System.out.println("");
	}
}
