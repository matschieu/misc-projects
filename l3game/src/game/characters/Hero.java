
package game.characters;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.LinkedList;

import game.actions.Action;
import game.actions.Attack;
import game.actions.DisplayMap;
import game.actions.Interact;
import game.actions.MakeWish;
import game.actions.Move;
import game.actions.Options;
import game.actions.PickUp;
import game.actions.Quit;
import game.actions.Rest;
import game.actions.Status;
import game.actions.Use;
import game.actions.View;
import game.strategy.HumanStrategy;
import game.strategy.Strategy;
import game.xml.Localization;

/**
 *@author Matschieu
 */
public class Hero extends GameCharacter {

	/** the default energy of a hero */
	private static final int DEFAULT_ENERGY = 100;
	/** the default strength of a hero */
	private static final int DEFAULT_STRENGTH = 10;

	/**
	 * Constructs a new hero (human player)
	 */
	public Hero() {
		this(Localization.SINGLETON.getElement("HERO_NAME"));
	}

	/**
	 * Constructs a new hero (human player)
	 * @param name : the name of this hero
	 */
	public Hero(String name) {
		super(name, DEFAULT_ENERGY, DEFAULT_ENERGY, DEFAULT_STRENGTH, DEFAULT_STRENGTH, new HumanStrategy());
		this.actionsList = new LinkedList<Action>();
		this.actionsList.add(new Quit(this));
		this.actionsList.add(new Rest(this));
		this.actionsList.add(new Status(this));
		this.actionsList.add(new View(this));
		this.actionsList.add(new DisplayMap(this));
		this.actionsList.add(new Move(this));
		this.actionsList.add(new PickUp(this));
		this.actionsList.add(new Use(this));
		this.actionsList.add(new Interact(this));
		this.actionsList.add(new Attack(this));
		this.actionsList.add(new MakeWish(this));
		this.actionsList.add(new Options(this));
		this.checkActionsToAdd();
	}

	/**
	 * Initializes the hero with some values
	 * @param args : an array of parameters<br />
	 *		args[0]=class name<br />
	 *		args[1]=hero's name<br />
	 *		args[2]=hero's energy<br />
	 *		args[3]=hero's strength<br />
	 *		args[4]=hero's strategy<br />
	 *		args[x>4]=hero's actions
	 */
	@Override
	public void init(String[] args) {
		this.name = args[1];
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
		Class<?> heroActionClass = null;
		try {
			actionClass = Class.forName(classPackage + ".Action");
			heroActionClass = Class.forName(classPackage + ".HeroAction");
		}
		catch(ClassNotFoundException e) {
			return;
		}
		for(int i = 0; i < classFiles.length; i++) {
			try {
				if (classFiles[i].matches("[a-zA-Z0-9]*" + ext)) {
					classFiles[i] = classFiles[i].substring(0, classFiles[i].length() - ext.length());
					Class<?> cls = Class.forName(classPackage + "." + classFiles[i]);
					if (actionClass.isAssignableFrom(cls) && heroActionClass.isAssignableFrom(cls) && !cls.isInterface()) {
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
		System.out.println("* " + this.name);
		boolean finished = false;
		Action a = null;
		while(!finished) {
			System.out.println(Localization.SINGLETON.getElement("HERO_ACTION"));
			a = this.strategy.choiceAction(this.actionsList);
			if (a != null) {
				System.out.println(a.getDescription());
				finished = a.execute();
			}
		}
	}

	/**
	 * Returns a string symbole of this character
	 * @return String : the symbole of this character
	 */
	@Override
	public String getSymbole() {
		return Localization.SINGLETON.getElement("HERO_SYMBOLE");
	}

}
