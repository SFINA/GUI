/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalFrames;

import utilities.TabularDataViewer;
import actions.runMenu.RunMenuItemAction;
import core.SFINAGUI;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

/**
 *
 * @author dinesh
 */

// needs experiment owner
public class TreeMouseListener extends MouseAdapter{
    
        JTree tree;
        SFINAGUI owner;
        JPopupMenu pop;
        JMenuItem run;
                
        public TreeMouseListener(JTree tree, SFINAGUI owner){
            this.tree = tree;
            this.owner = owner;
            
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            int selRow = tree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
            if(selRow != -1) {
                
                if(e.getClickCount() == 2) {
                    String filePath = "";
                    for(int i =0;i<selPath.getPathCount()-1;i++){
                        filePath = filePath+selPath.getPathComponent(i)+"/";
                    }
                    filePath += selPath.getPathComponent(selPath.getPathCount()-1);
                    
                    // check the type of file, and open similar editor, viewer:
                    String columnSeperator = "=";
                    
                    // There is no header in table by default
                    boolean header = false;
                    String[] withHeader = {"nodes","links","events"};
                    for(String s : withHeader){
                        if(filePath.contains(s)){
                            header = true;
                            columnSeperator = ",";
                        }
                    }
                    
                    TabularDataViewer viewer = new TabularDataViewer(filePath, columnSeperator, header);
                    
                    JOptionPane.showConfirmDialog(null, viewer, "TabularDataViewer", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            int selRow = tree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
            String expSeqNum = "";
            if(selRow != -1) {
                
                if(e.getButton()==MouseEvent.BUTTON3){
                    pop = new JPopupMenu();
                    run = new JMenuItem("Run Experiment");
                    run.addActionListener(new RunMenuItemAction(owner));
                    pop.add(run);
                    
                    pop.show(e.getComponent(), e.getX(), e.getY());

                    for(int i =0; i<selPath.getPathCount(); i++){
                        if((selPath.getPathComponent(i)+"").contains("experiment-")){
                            expSeqNum = (selPath.getPathComponent(i)+"").replace("experiment-","");
                            HashMap<String, String> config = owner.getExperimentConfigurations();
                            config.replace("expSeqNum", expSeqNum);
                            owner.setExperimentConfigurations(config);
                            break;
                        }
                    }
                    
                }   
            }
        }
}
