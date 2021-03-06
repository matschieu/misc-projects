
package game.characters;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.LinkedList;

import game.actions.Action;
import game.actions.Attack;
import game.actions.Interact;
import game.actions.Move;
import game.actions.PickUp;
import game.actions.Rest;
import game.actions.Use;
import game.strategy.RandomStrategy;
import game.strategy.Strategy;
import game.xml.Localization;

/**
 *@author Matschieu
 */
public class Monster extends GameCharacter {

	/** To generate a name for a monster */
	private static int cpt = 0;

	/**
	 * Constructs a new monster
	 */
	public Monster() {
		this(Localization.SINGLETON.getElement("MONSTER_NAME") + cpt++);
	}

	/**
	 * Constructs a new monster
	 * @param name : the name of this monster
	 */
	public Monster(String name) {
		super(name, 0, (int)(Math.random() * 201), 0, (int)(Math.random() * 11), new RandomStrategy());
		this.setEnergy(this.getMaxEnergy());
		this.setStrength(this.getMaxStrength());
		this.setGold((int)(Math.random() * 1000));
		this.actionsList = new LinkedList<Action>();
		this.actionsList.add(new Rest(this));
		this.actionsList.add(new Move(this));
		this.actionsList.add(new PickUp(this));
		this.actionsList.add(new Use(this));
		this.actionsList.add(new Interact(this));
		this.actionsList.add(new Attack(this));
		this.checkActionsToAdd();
	}

	/**
	 * Initializes the monster with some values
	 * @param args : an array of parameters<br />
	 *		args[0]=class name<br />
	 *		args[1]=rate<br />
	 *		args[2]=monster's energy<br />
	 *		args[3]=monster's strength<br />
	 *		args[x>3]=monster's actions
	 */
	@Override
	public void init(String[] args) {
		this.name = Localization.SINGLETON.getElement("MONSTER_NAME") + cpt++;
		try {
			this.maxEnergy = this.energy = Integer.parseInt(args[2]);
			this.maxStrength = this.strength = Integer.parseInt(args[3]);
			this.strategy = (Strategy)Class.forName(args[4]).newInstance();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
		this.actionsList = new LinkedList<Action>();
		for(int i = 5; i < args.length; i++) {
			try {
				Constructor<?> cst = Class.forName(args[i]).getConstructor(game.characters.GameCharacter.class);
				this.actionsList.add((Action)cst.newInstance(this));
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * Checks if there are actions in the package game.actions to add to the list
	 */
	private void checkActionsToAdd() {
		String[] classFiles = (new File("classes/game/actions")).list();
		String classPackage = "game.actions";
		String ext = ".class";
		Class<?> actionClass = null;
		Class<?> monsterActionClass = null;
		try {
			actionClass = Class.forName(classPackage + ".Action");
			monsterActionClass = Class.forName(classPackage + ".MonsterAction");
		}
		catch(ClassNotFoundException e) {
			return;
		}
		for(int i = 0; i < classFiles.length; i++) {
			try {
				if (classFiles[i].matches("[a-zA-Z0-9]*" + ext)) {
					classFiles[i] = classFiles[i].substring(0, classFiles[i].length() - ext.length());
					Class<?> cls = Class.forName(classPackage + "." + classFiles[i]);
					if (actionClass.isAssignableFrom(cls) && monsterActionClass.isAssignableFrom(cls) && !cls.isInterface()) {
						Constructor<?> cst = cls.getConstructor(game.characters.GameCharacter.class);
						Action act = (Action)cst.newInstance(this);
						boolean toAdd = true;
						Iterator<Action> it = this.actionsList.iterator();
						while(it.hasNext()) {
							Action act_tmp = it.next();
							if (act_tmp.getDescription().equals(act.getDescription())) {
								toAdd = false;
								break;
							}
						}
						if (toAdd) this.actionsList.add(act);
					}
				}
			}
			catch(ClassNotFoundException e) { }
			catch(Exception e) { }
		}
	}

	/**
	 * Checks if this character can be attacked
	 * @return true
	 */
	@Override
	public boolean canBeAttacked() {
		return true;
	}

	/**
	 * Actions this character can do on a tour
	 */
	@Override
	public void action() {
		System.out.println("* " + this.name + " : ");
		boolean finished = false;
		Action a = null;
		while(!finished) {
			a = this.strategy.choiceAction(this.actionsList);
			if (a != null) finished = a.execute();
		}
	}

	/**
	 * Returns a string symbole of this character
	 * @return String : the symbole of this character
	 */
	@Override
	public String getSymbole() {
		return Localization.SINGLETON.getElement("MONSTER_SYMBOLE");
	}

}
