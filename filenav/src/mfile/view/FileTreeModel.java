package mfile.view;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author Matschieu
 */
public class FileTreeModel implements TreeModel {

	private File rootFile;
	private boolean showHiddenFiles;
	
	public FileTreeModel(String rootFileName, boolean showHiddenFiles) throws FileNotFoundException {
		this.rootFile = new File(rootFileName);
		this.showHiddenFiles = showHiddenFiles;
		if (!this.rootFile.exists())
			throw new FileNotFoundException();
	}
	
	public Object getChild(Object parent, int index) {
		File parentFile = ((File)parent);
		String[] filesList = parentFile.list();
		
		List<File> directories = new LinkedList<File>();
		List<File> files = new LinkedList<File>();
		
		for(int i = 0; i < filesList.length; i++) {
			if (!showHiddenFiles && filesList[i].charAt(0) == '.')
				continue;
			File f = new File(parentFile.getAbsolutePath() + File.separator + filesList[i]);
			if (f.isDirectory())
				directories.add(f);
			else
				files.add(f);
		}
		
		Collections.sort(directories);
		Collections.sort(files);
		
		directories.addAll(files);
		if (index > directories.size() - 1)
			return null;
		
		return directories.get(index);
	}

	public int getChildCount(Object parent) {
		String[] filesList = ((File)parent).list();
		int n = 0;
		
		for(int i = 0; i < filesList.length; i++) {
			if (!showHiddenFiles && filesList[i].charAt(0) == '.')
				continue;
			n++;
		}
	
		return n;
	}

	public int getIndexOfChild(Object parent, Object child) {
		File parentFile = ((File)parent);
		String[] filesList = parentFile.list();
		
		List<File> directories = new LinkedList<File>();
		List<File> files = new LinkedList<File>();
		
		for(int i = 0; i < filesList.length; i++) {
			if (!showHiddenFiles && filesList[i].charAt(0) == '.')
				continue;
			File f = new File(parentFile.getAbsolutePath() + File.separator + filesList[i]);
			if (f.isDirectory())
				directories.add(f);
			else
				files.add(f);
		}
		
		Collections.sort(directories);
		Collections.sort(files);
		
		directories.addAll(files);
		
		int idx = 0;
		for(File f : directories) {
			if (f.equals(child))
				return idx;
			idx++;
		}
		
		return 0;
	}

	public Object getRoot() {
		return this.rootFile;
	}

	public boolean isLeaf(Object node) {
		return !((File)node).isDirectory();
	}

	public void valueForPathChanged(TreePath arg0, Object arg1) { }

	public void removeTreeModelListener(TreeModelListener arg0) { }

	public void addTreeModelListener(TreeModelListener arg0) { }

}
