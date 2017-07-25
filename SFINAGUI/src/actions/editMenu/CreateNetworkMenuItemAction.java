/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.editMenu;

import core.SFINAGUI;
import internalFrames.NetworkEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import networkGenerator.FieldsDialog;
import networkGenerator.NetworkGenerator;

/**
 *
 * @author dinesh
 */
public class CreateNetworkMenuItemAction  implements ActionListener {
    
    private SFINAGUI owner;
    
    public CreateNetworkMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
            if(owner.isInterDep()){
                JOptionPane.showMessageDialog(owner, "Network Creation Feature is no implemented for Interdependent Networks!","Unsupported Feature!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
        
            final NetworkGenerator nG = new NetworkGenerator();
            JInternalFrame iFrame = new NetworkEditor(owner);
            owner.getDesktop().add(iFrame);
            
            iFrame.add(nG);
            iFrame.setVisible(true);
            
            FieldsDialog nF = new FieldsDialog();
            JOptionPane.showConfirmDialog(null, nF, "Enter Node Fields", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
            ArrayList<String> nodeFields = nF.getFields();

            FieldsDialog lF = new FieldsDialog();
            JOptionPane.showConfirmDialog(null, lF, "Enter Link Fields", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
            ArrayList<String> linkFields = lF.getFields();

            nG.setNodeFields(nodeFields);
            nG.setLinkFields(linkFields);

    }
    
}