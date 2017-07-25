/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: LICENSE,v 1.8 2004/02/09 03:33:38 ian Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java
 * language and environment is gratefully acknowledged.
 *
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */

package internalFrames;

import core.SFINAGUI;
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
 * @author modification by dinesh
 */
public class ExperimentExplorer extends JInternalFrame{
    
    SFINAGUI owner;
    
    String expLocation = "experiments/";
    private javax.swing.JScrollPane expTreeScrollPane;
    private DefaultTreeModel expTreeModel;
    private javax.swing.JTree expTree;
    
    public ExperimentExplorer(SFINAGUI owner){
        super("Experiments",false,false,false,false);
        this.owner = owner;
        Point orig = owner.getLocation();
        Dimension dim = owner.getContentPane().getSize();
        super.setSize(300,500);
        super.setLocation((int)orig.getX(), (int)orig.getY());
        initComponents();
    }
    
    public void initComponents(){
        
        expTreeScrollPane= new javax.swing.JScrollPane();
        
        expTree = new javax.swing.JTree(populateTree(null,new File(expLocation)));
	expTree.addMouseListener(new TreeMouseListener(expTree, owner));
        
	expTreeScrollPane.setViewportView(expTree);
        //this.getContentPane().add(expTreeScrollPane);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(expTreeScrollPane,BorderLayout.CENTER);
        this.getContentPane().add(panel);
    }
    
    private DefaultMutableTreeNode populateTree(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode((new File(curPath)).getName());
        if (curTop != null) {
            curTop.add(curDir);
        }
        
        Vector ol = new Vector();
        String[] tmp = dir.list();
        
        if(tmp.length<=0){
            //System.out.println("No Experiment Files Found. ");
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
            populateTree(curDir, f);
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
        expTree = new JTree(populateTree(null, new File(expLocation)));
        expTreeScrollPane.setViewportView(expTree);
    }
    
    
}