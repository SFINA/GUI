/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junk;

import core.SFINAGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author dinesh
 */
public class ShowNetworkMenuItem implements ActionListener{
    JFrame owner;
    
    public ShowNetworkMenuItem(JFrame owner){
        this.owner=owner;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(((SFINAGUI)owner).getNetworkEditor().isClosed()){
            ((SFINAGUI)owner).initNetworkEditor();
        }
        
        ((SFINAGUI)owner).setNetworkEditorVisible(Boolean.TRUE);
        
    }
    
}
