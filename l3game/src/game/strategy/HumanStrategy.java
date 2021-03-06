
package game.strategy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.actions.Action;
import game.actions.Wait;
import game.characters.GameCharacter;
import game.items.Item;
import game.map.Cell;
import game.map.Direction;
import game.xml.Localization;

/**
 *@author Matschieu
 */
public class HumanStrategy implements Strategy {

	/**
	 * Constructs a new Human strategy (choice through keyboard) used by hero
	 */
	public HumanStrategy() { }

	/**
	 * Asks a choice to player (through keyboard)
	 * @param start : the minimum number to choose
	 * @param limit : the maximum number to choose
	 * @return int : player's choice (between start and limit included)
	 */
	private int userChoice(int start, int limit) {
		Scanner sc = new Scanner(System.in);
		int choice = start - 1;
		while(choice < start || choice > limit) {
			System.out.print("\t# ");
			System.out.flush();
			try {
				choice = Integer.parseInt(sc.nextLine());
			}
			catch(NumberFormatException e) { }
		}
		sc.close();
		return choice;
	}

	/**
	 * Strategy to choose a string in a list
	 * @param values : a list of strings
	 * @return String : the string selected
	 */
	@Override
	public String choiceString(List<String> values) {
		if (values == null || values.isEmpty()) return null;
		int idx = 1;
		StringBuffer strBuf = new StringBuffer();
		Iterator<String> it = values.iterator();
		while(it.hasNext())
			strBuf.append("\t" + idx++ + ". " + it.next() + "\n");
		System.out.print(strBuf.toString());
		return values.get(userChoice(1, idx - 1) - 1);
	}

	/**
	 * Strategy to choose an action in a list
	 * @param actions : a list of actions available
	 * @return Action : the action selected
	 */
	@Override
	public Action choiceAction(List<Action> actions) {
		if (actions == null || actions.isEmpty()) return new Wait();
		int idx = 0;
		StringBuffer strBuf = new StringBuffer();
		List<Action> tmp = new LinkedList<Action>();
		Iterator<Action> it = actions.iterator();
		while(it.hasNext()) {
			Action a = it.next();
			if (a.isAvailable()) {
				strBuf.append("\t" + idx++ + ". " + a.getDescription() + "\n");
				tmp.add(a);
			}
		}
		System.out.print(strBuf.toString());
		if (tmp.isEmpty()) return null;
		return tmp.get(userChoice(0, idx - 1));
	}

	/**
	 * Strategy to choose a character in a list
	 * @param chars : a list of characters available
	 * @return GameCharacter : the character selected
	 */
	@Override
	public GameCharacter choiceGameCharacter(List<GameCharacter> chars) {
		if (chars == null || chars.isEmpty()) return null;
		int choice, idx = 0;
		StringBuffer strBuf = new StringBuffer();
		Iterator<GameCharacter> it = chars.iterator();
		String display = Localization.SINGLETON.getElement("HUMANSTRATEGY_PREVIOUS");
		strBuf.append("\t" + idx++ + ". " + display + "\n");
		while(it.hasNext()) {
			GameCharacter c = it.next();
			if (c.canBeAttacked()) {
				display = Localization.SINGLETON.getElement("HUMANSTRATEGY_CHOICECHARS");
				display = display.replace("[[CHAR_NAME]]", c.getName());
				display = display.replace("[[CHAR_ENERGY]]", "" + c.getEnergy());
				display = display.replace("[[CHAR_STRENGTH]]", "" + c.getStrength());
				strBuf.append("\t" + idx++ + ". " + display + "\n");
			}
			else
				strBuf.append("\t" + idx++ + ". " + c.getName() + "\n");
		}
		System.out.print(strBuf.toString());
		choice = this.userChoice(0, idx - 1);
		if (choice == 0) return null;
		return chars.get(choice - 1);
	}

	/**
	 * Strategy to choose an item in a list
	 * @param items : a list of items available
	 * @return Item : the item selected
	 */
	@Override
	public Item choiceItem(List<Item> items) {
		if (items == null || items.isEmpty()) return null;
		int choice, idx = 0;
		StringBuffer strBuf = new StringBuffer();
		Iterator<Item> it = items.iterator();
		String display = Localization.SINGLETON.getElement("HUMANSTRATEGY_PREVIOUS");
		strBuf.append("\t" + idx++ + ". " + display + "\n");
		while(it.hasNext()) {
			Item i = it.next();
			strBuf.append("\t" + idx++ + ". " + i.getDescription() + "\n");
		}
		System.out.print(strBuf.toString());
		choice = this.userChoice(0, idx - 1);
		if (choice == 0) return null;
		return items.get(choice - 1);
	}

	/**
	 * Strategy to choose a cell in a list
	 * @param cells : a list of cells available
	 * @return Cell<? extends Direction> : the cell selected
	 */
	@Override
	public Cell<? extends Direction> choice(Map<? extends Direction, Cell<? extends Direction>> cells) {
		if (cells == null || cells.isEmpty()) return null;
		int choice, idx = 0;
		StringBuffer strBuf = new StringBuffer();
		List<Cell<? extends Direction>> tmp = new LinkedList<Cell<? extends Direction>>();
		Iterator<? extends Direction> it = cells.keySet().iterator();
		String display = Localization.SINGLETON.getElement("HUMANSTRATEGY_PREVIOUS");
		strBuf.append("\t" + idx++ + ". " + display + "\n");
		while(it.hasNext()) {
			Direction d = it.next();
			Cell<? extends Direction> c = cells.get(d);
			if (c.isReachable()) {
				strBuf.append("\t" + idx++ + ". " + c.getDescription() + " (" + d + ")" + "\n");
				tmp.add(c);
			}
		}
		System.out.print(strBuf.toString());
		if (tmp.isEmpty()) return null;
		choice = this.userChoice(0, idx - 1);
		if (choice == 0) return null;
		return tmp.get(choice - 1);
	}

}
