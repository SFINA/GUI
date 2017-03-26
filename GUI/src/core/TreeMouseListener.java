/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 *
 * @author dinesh
 */
public class TreeMouseListener extends MouseAdapter{
    
        JTree tree;
        
        public TreeMouseListener(JTree tree){
            this.tree = tree;
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
                    
                    String columnSeperator = ",";
                    boolean header = false;
                    TabularDataEditor viewer = new TabularDataEditor(filePath, columnSeperator, header);
                    
                    JOptionPane.showConfirmDialog(null, viewer, "TabularDataViewer", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    
}
