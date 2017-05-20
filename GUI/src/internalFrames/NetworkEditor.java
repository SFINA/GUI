/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalFrames;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author dinesh
 */
public class NetworkEditor extends JInternalFrame {
    JFrame owner;
    
    public NetworkEditor(JFrame owner){
        super("Network Viewer",true,true,true,true);
        this.owner = owner;
        Point orig = owner.getLocation();
        Dimension dim = owner.getContentPane().getSize();
        super.setSize(450,400);
        //super.setLocation((int)(orig.getX()+dim.getWidth()*5/9), (int)(orig.getY()+10));
        //super.setLocation(0,0);
    }
    
}