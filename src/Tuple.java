
/**
 * Simple Klasse zum Abspeichern von Tupeln.
 * 
 * @author Steffen Dworsky
 */
public class Tuple<X, Y> {
	public String x;
	public String y;

	/**
	 * Constuctor, der einen neuen Tupel erzeugt.
	 * 
	 * @param x
	 * @param y
	 */
	public Tuple(String x, String y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param setzt den ersten Wert im Tuple auf String x
	 */
	public void setX(String x) {
		this.x = x;
	}

	/**
	 * @param setzt den zweiten Wert im Tuple auf String y
	 */
	public void setY(String y) {
		this.y = y;
	}

	/**
	 * @return erster wert des Tupels
	 */
	public String getX() {
		return x;
	}

	/**
	 * @return zweiter Wert des Tupels
	 */
	public String getY() {
		return y;
	}

}
