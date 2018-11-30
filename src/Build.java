/**
 * Neue Klasse Build, welche die HashMap und Tupel ersetzen wird.
 * 
 * @author Steffen Dworsky
 *
 */
public class Build {

	public String buildName;
	public String forumLink;
	public String pobLink;

	/**
	 * Construktor erzeugt ein neues Build Objekt. Kann einen Buildnamen, einen
	 * Forumslink und einen PoB-Link haben.
	 * 
	 * @param name
	 * @param forum
	 * @param pob
	 */
	public Build(String name, String forum, String pob) {
		this.buildName = name;
		this.forumLink = forum;
		this.pobLink = pob;
	}

	/**
	 * Funktion um den Buildnamen zu erhalten.
	 * 
	 * @returnGibt den Buildnamen zur�ck.
	 */
	public String getBuildName() {
		return buildName;
	}

	/**
	 * Erm�glicht es einen Buildnamen zu setzen oder zu �ndern.
	 * 
	 * @param buildName
	 */
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	/**
	 * Funktion um den Forums-Link zu erhalten.
	 * 
	 * @return Gibt den Forums-Link zur�ck.
	 */
	public String getForumLink() {
		return forumLink;
	}

	/**
	 * Erm�glicht es einen neuen Forumslink zu setzen oder zu �ndern.
	 * 
	 * @param forumLink
	 */
	public void setForumLink(String forumLink) {
		this.forumLink = forumLink;
	}

	/**
	 * Funktion um den PoB-Link zu erhalten.
	 * 
	 * @return Gibt den PoB-Link zur�ck.
	 */
	public String getPobLink() {
		return pobLink;
	}

	/**
	 * Erm�glicht es einen PoB-Link zu setzen oder zu �ndern.
	 * 
	 * @param pobLink
	 */
	public void setPobLink(String pobLink) {
		this.pobLink = pobLink;
	}

}
