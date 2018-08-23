
package game.xml;

import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import game.map.GameMap;

/**
 * @author Emmanuel ARDIOT
 */
public class GameConfiguration {
	
	public GameConfiguration(){ }
	
	/**
	 * Saves a map's configuration in a file (XML)
	 * @param fileName
	 * @param map
	 */
	public void saveConfigFile(String fileName, GameMap map) {
	}
	
	/**
	 * return a GameMap according a config load a file (XML)
	 * @param fileName
	 * @return GameMap
	 */
	public GameMap loadConfigFile(String fileName) {
		GameMap gMap = null;
		SAXBuilder builder = new SAXBuilder (true) ;
		try {
			Document doc = builder.build (fileName) ;
			Element root = doc.getRootElement();
			String language = root.getChild("localisation").getText();
			Localization.SINGLETON.loadLanguageFile(language);
			Element map = root.getChild("map");
			Class<?> classTemp = Class.forName(map.getChild("mapClass").getText());
			gMap = (GameMap)classTemp.newInstance();
			List<?> params = map.getChildren("param");
			String[] mapParams = new String[params.size()];
			Iterator<?> it = params.iterator();
			int i = 0;
			while(it.hasNext()) {
				Element e = (Element)it.next();
				mapParams[i++] = e.getText();
			}
			gMap.init(mapParams);
			String[][] cellsArray = this.getCellsArray(root);
			String[][] charsArray = this.getCharsArray(root);
			String[][] itemsArray = this.getItemsArray(root);
			int nbChars = Integer.parseInt(root.getChild("characters").getChild("nb").getText());
			int nbItems = Integer.parseInt(root.getChild("items").getChild("nb").getText());
			gMap.build(cellsArray, nbChars, charsArray, nbItems, itemsArray);
		} catch (JDOMException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.exit(1);
		}
		return gMap;
	}
	
	/**
	 * Returns an array of a configuration of the cells
	 * @param root
	 * @return String[][] : configuration of the cells
	 */
	private String[][] getCellsArray(Element root) {
		int idx = 0;
		List<?> cellsList = root.getChild("cells").getChildren("cellClass");
		List<?> rateList = root.getChild("cells").getChildren("rate");
		String[][] cellsArray = new String[cellsList.size()][2];
		Iterator<?> iterac = cellsList.iterator();
		Iterator<?> iteracRate = rateList.iterator();
		while(iterac.hasNext() && iteracRate.hasNext()){
			cellsArray[idx][0] = ((Element)iterac.next()).getText();
			cellsArray[idx][1] = ((Element)iteracRate.next()).getText();
			idx++;
		}
		return cellsArray;
	}
	
	/**
	 * Returns an array of a configuration of the characters
	 * @param root
	 * @return String[][] : configuration of the characters
	 */
	private String[][] getCharsArray(Element root) {
		int idx = 0;
		List<?> charsList = root.getChild("characters").getChildren("character");
		String[][] charsArray = new String[charsList.size() + 1][];
		Element hero = root.getChild("hero");
		charsArray[idx] = new String[hero.getChildren().size()];
		charsArray[idx][0] = hero.getChild("heroClass").getText();
		charsArray[idx][1] = hero.getChild("name").getText();
		charsArray[idx][2] = hero.getChild("energy").getText();
		charsArray[idx][3] = hero.getChild("strength").getText();
		charsArray[idx][4] = hero.getChild("strategy").getText();
		int i = 5;
		Iterator<?> itActsHero = hero.getChildren("action").iterator();
		while(itActsHero.hasNext()) 
			charsArray[idx][i++] = ((Element)itActsHero.next()).getText();
		idx++;
		Iterator<?> itchars = charsList.iterator();
		while(itchars.hasNext()){
			Element e = ((Element)itchars.next());
			charsArray[idx] = new String[e.getChildren().size()];
			charsArray[idx][0] = e.getChild("characterClass").getText();
			charsArray[idx][1] = e.getChild("rate").getText();
			charsArray[idx][2] = e.getChild("energy").getText();
			charsArray[idx][3] = e.getChild("strength").getText();
			charsArray[idx][4] = e.getChild("strategy").getText();
			i = 5;
			List<?> acts = e.getChildren("action");
			Iterator<?> it = acts.iterator();
			while(it.hasNext()) charsArray[idx][i++] = ((Element)it.next()).getText();
			idx++;
		}
		return charsArray;
	}
	
	/**
	 * Returns an array of a configuration of the items
	 * @param root
	 * @return String[][] : configuration of the items
	 */
	private String[][] getItemsArray(Element root) {
		int idx = 0;
		List<?> itemsList = root.getChild("items").getChildren("item");
		String[][] itemsArray = new String[itemsList.size()][2];
		Iterator<?> it = itemsList.iterator();
		while(it.hasNext()){
			Element e = (Element)it.next();
			itemsArray[idx][0] = e.getChild("itemClass").getText( );
			itemsArray[idx][1] = e.getChild("rate").getText( );
			idx++;
		}
		return itemsArray;
	}
		
}
