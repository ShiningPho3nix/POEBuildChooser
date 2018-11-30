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
	 * @returnGibt den Buildnamen zurück.
	 */
	public String getBuildName() {
		return buildName;
	}

	/**
	 * Ermöglicht es einen Buildnamen zu setzen oder zu ändern.
	 * 
	 * @param buildName
	 */
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	/**
	 * Funktion um den Forums-Link zu erhalten.
	 * 
	 * @return Gibt den Forums-Link zurück.
	 */
	public String getForumLink() {
		return forumLink;
	}

	/**
	 * Ermöglicht es einen neuen Forumslink zu setzen oder zu ändern.
	 * 
	 * @param forumLink
	 */
	public void setForumLink(String forumLink) {
		this.forumLink = forumLink;
	}

	/**
	 * Funktion um den PoB-Link zu erhalten.
	 * 
	 * @return Gibt den PoB-Link zurück.
	 */
	public String getPobLink() {
		return pobLink;
	}

	/**
	 * Ermöglicht es einen PoB-Link zu setzen oder zu ändern.
	 * 
	 * @param pobLink
	 */
	public void setPobLink(String pobLink) {
		this.pobLink = pobLink;
	}

}
