/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalFrames;

import core.TreeMouseListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author dinesh
 */
public class ExperimentExplorer extends JInternalFrame{
    
    JFrame owner;
    
    String expLocation = "experiments/";
    private javax.swing.JScrollPane expTreeScrollPane;
    private DefaultTreeModel expTreeModel;
    private javax.swing.JTree expTree;
    
    public ExperimentExplorer(JFrame owner){
        super("Experiments",true,true,false,true);
        this.owner = owner;
        Point orig = owner.getLocation();
        Dimension dim = owner.getContentPane().getSize();
        super.setSize(200,400);
        super.setLocation((int)orig.getX(), (int)orig.getY());
        initComponents();
    }
    
    public void initComponents(){
        
        expTreeScrollPane= new javax.swing.JScrollPane();
        
        expTree = new javax.swing.JTree(addNodes(null,new File(expLocation)));
	expTree.addMouseListener(new TreeMouseListener(expTree));
        
	expTreeScrollPane.setViewportView(expTree);
        //this.getContentPane().add(expTreeScrollPane);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(expTreeScrollPane,BorderLayout.CENTER);
        this.getContentPane().add(panel);
    }
    
    private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode((new File(curPath)).getName());
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        
        Vector ol = new Vector();
        String[] tmp = dir.list();
        
        if(tmp.length<=0){
            // TODO null pointer exception has to be handled
            System.out.println("No Experiment Files Found. ");
            return curDir;
        }
        
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


    public String[] getExperimentsList(){
        File dir = new File(expLocation);
        String [] dirlist = dir.list();
        for (int i = 0; i<dirlist.length;i++){
            dirlist[i]=dirlist[i].substring(11);
        }
        return dirlist;
    }

    public void notifyExperimentsChange(){
        
        // Update jTree Values
        expTree = new JTree(addNodes(null, new File(expLocation)));
        expTreeScrollPane.setViewportView(expTree);
        
        // Update comboBox Values
    }
    
    
}