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
	public void chooseBuild() throws IOException, URISyntaxException{
		Collection<String> buildCollection = PoeBuildChooser.globalBuildArray.buildHashMap.keySet();
		List<String> buildList = new ArrayList<>(buildCollection);
		localbuildAnzahl = PoeBuildChooser.globalBuildArray.anzahlBuilds();
		Random rnd = new Random();
		int randomIndex = rnd.nextInt(localbuildAnzahl);
		String choosenBuild = buildList.get(randomIndex);
		String forumlink = PoeBuildChooser.globalBuildArray.getForumsLink(choosenBuild);
		System.out.println("Der Zufällig ausgewählte Build ist:" + " " + choosenBuild);
		System.out.println("Forums-Link: " + forumlink);
		System.out.println("POB-Link: " + PoeBuildChooser.globalBuildArray.getPOBLink(choosenBuild));
		if(Desktop.isDesktopSupported()){
			Desktop.getDesktop().browse(new URI(forumlink));
		}
	}

}
