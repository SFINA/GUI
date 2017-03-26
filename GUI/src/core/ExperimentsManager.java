/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author dinesh
 */
public class ExperimentsManager {
    
    private File expDir;
    
    public ExperimentsManager(String expLocation){
        this.expDir = new File(expLocation);
    }
    
    // extracts list of experiments from experiment directories
    public String[] getExperimentsList(){
        
        // Lists only directories: avoids files
        String[] dirlist = expDir.list(new FilenameFilter(){
            @Override
            public boolean accept(File current, String name){
                return new File(current, name).isDirectory();
            }
        });
        
        // Remove the "experiment-" from the string
        for (int i = 0; i<dirlist.length;i++)
            dirlist[i]=dirlist[i].substring(11);
        
        return dirlist;
        
    }
    
    // generates a directory tree for experiments recursively
    public DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode((new File(curPath)).getName());
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        
        Vector ol = new Vector();
        String[] tmp = dir.list();
        
        for (int i = 0; i < tmp.length; i++)
          ol.addElement(tmp[i]);
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        
        File f;
        Vector files = new Vector();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (int i = 0; i < ol.size(); i++) {
          String thisObject = (String) ol.elementAt(i);
          String newPath;
          if (curPath.equals("."))
            newPath = thisObject;
          else
            newPath = curPath + File.separator + thisObject;
          if ((f = new File(newPath)).isDirectory())
            addNodes(curDir, f);
          else
            files.addElement(thisObject);
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < files.size(); fnum++)
          curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        return curDir;
    }
    
}
