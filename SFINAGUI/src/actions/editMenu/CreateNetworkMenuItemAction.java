/*
 * Copyright (C) 2017 SFINA Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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