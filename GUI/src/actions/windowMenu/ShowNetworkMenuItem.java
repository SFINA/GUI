/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.windowMenu;

import core.PowerFlowCascadeGUI3;
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
        
        if(((PowerFlowCascadeGUI3)owner).getNetworkEditor().isClosed()){
            ((PowerFlowCascadeGUI3)owner).initNetworkEditor();
        }
        
        ((PowerFlowCascadeGUI3)owner).setNetworkEditorVisible(Boolean.TRUE);
        
    }
    
}
