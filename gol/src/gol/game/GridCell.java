
package gol.game;

import java.awt.Color;
import javax.swing.ImageIcon;

import gol.tools.*;

/**
 * @author Matschieu
 */
public interface GridCell {
	
	public static final int BORDER_SIDE = 3;

	public Position getPosition();
	public void setPosition(Position position);
	
	public char getSymbole();
	public Color getColor();
	public ImageIcon getIcon();

}