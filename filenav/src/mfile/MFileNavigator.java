package mfile;

import javax.swing.SwingUtilities;

import mfile.view.MFileNavigatorFrame;


/**
 * @author Matschieu
 */
public class MFileNavigator implements Runnable {
	
	MFileNavigatorFrame frame;
	
	public MFileNavigator(String rootPathname) { 
		this.frame = new MFileNavigatorFrame(rootPathname);
	}
	
	public void run() {
		this.frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new MFileNavigator("/home/mathieu"));
	}

}
