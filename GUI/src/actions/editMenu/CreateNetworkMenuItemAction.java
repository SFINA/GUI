/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.editMenu;

import core.PowerFlowCascadeGUI3;
import internalFrames.NetworkEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import sfinanetworkgenerator.FieldsDialog;
import sfinanetworkgenerator.NetworkGenerator;

/**
 *
 * @author dinesh
 */
public class CreateNetworkMenuItemAction  implements ActionListener {
    
    JFrame owner;
    
    public CreateNetworkMenuItemAction(JFrame owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            final NetworkGenerator nG = new NetworkGenerator();
            JInternalFrame iFrame = new NetworkEditor(owner);
            ((PowerFlowCascadeGUI3)owner).getDesktop().add(iFrame);
            
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