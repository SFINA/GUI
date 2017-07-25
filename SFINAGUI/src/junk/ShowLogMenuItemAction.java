/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junk;


import core.SFINAGUI;
import internalFrames.LogViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author dinesh
 */
public class ShowLogMenuItemAction implements ActionListener {
    
    JFrame owner;
    
    public ShowLogMenuItemAction(JFrame owner){
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((SFINAGUI)owner).getDebugFrame().isClosed()){
            ((SFINAGUI)owner).initDebugFrame();
        }
        
        ((SFINAGUI)owner).setDebugFrameVisible(Boolean.TRUE);
        
    }
    
    
    
}
