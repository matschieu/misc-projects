
package game.map;

import game.xml.Localization;

/**
 *@author Matschieu
 */
public class Wood implements CellType {
	
	/** String description of this type */
	public static final String DESCRIPTION = Localization.SINGLETON.getElement("WOOD_DESCRIPTION");
	
	/**
	 * Constructs a new wood type 
	 */	
	public Wood() { }

	/**
	 * Checks if this type of cell is reachable
	 * @return true
	 */
	public boolean isReachable() {
		return true;
	}
	
	/**
	 * Returns the energy lost by the character who cross over a field of this type
	 * @return int : the energy lost by the character who cross over a field of this type
	 */
	public int energyLost() {
		return 2;
	}

	/**
	 * Returns a string description of this cell
	 * @return String : the description of this cell
	 */
	public String getDescription() {
		return DESCRIPTION;
	}

	/**
	 * Returns a string symbole of this cell
	 * @return String : the symbole of this cell
	 */
	public String getSymbole() {
		return Localization.SINGLETON.getElement("WOOD_SYMBOLE");
	}

}
