/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.windowMenu;

import core.PowerFlowCascadeGUI3;
import internalFrames.Analytics;
import internalFrames.Debug;
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
        if(((PowerFlowCascadeGUI3)owner).getDebugFrame().isClosed()){
            ((PowerFlowCascadeGUI3)owner).initDebugFrame();
        }
        
        ((PowerFlowCascadeGUI3)owner).setDebugFrameVisible(Boolean.TRUE);
        
    }
    
    
    
}
