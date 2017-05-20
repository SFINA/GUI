/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.windowMenu;

import core.PowerFlowCascadeGUI3;
import internalFrames.Analytics;
import internalFrames.ExperimentExplorer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author dinesh
 */
public class ShowExperimentExplorerMenuItemAction implements ActionListener{

    JFrame owner;
    
    public ShowExperimentExplorerMenuItemAction(JFrame owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(((PowerFlowCascadeGUI3)owner).getExperimentExplorer().isClosed()){
            ((PowerFlowCascadeGUI3)owner).initExperimentExplorer();
        }
        
        

        ((PowerFlowCascadeGUI3)owner).setExperimentExplorerFrameVisible(Boolean.TRUE);
        
    }
    
}
