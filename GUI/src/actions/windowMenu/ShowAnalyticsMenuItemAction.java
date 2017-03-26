/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.windowMenu;

import core.PowerFlowCascadeGUI3;
import internalFrames.Analytics;
import internalFrames.NetworkEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author dinesh
 */
public class ShowAnalyticsMenuItemAction implements ActionListener{

    JFrame owner;
    
    public ShowAnalyticsMenuItemAction(JFrame owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        JInternalFrame iFrame = new Analytics(owner);
        ((PowerFlowCascadeGUI3)owner).getDesktop().add(iFrame);
        iFrame.setVisible(true);
    }
}
