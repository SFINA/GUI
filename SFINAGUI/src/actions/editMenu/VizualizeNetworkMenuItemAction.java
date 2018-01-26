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

import utilities.CheckNetworkFiles;
import core.SFINAGUI;
import edu.uci.ics.jung.graph.util.Pair;
import internalFrames.NetworkEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import networkGenerator.FieldsDialog;
import networkGenerator.InterdepNetVisualization;
import networkGenerator.NetworkGenerator;
import org.apache.log4j.Logger;

/**
 *
 * @author dinesh
 */
public class VizualizeNetworkMenuItemAction  implements ActionListener {

    private SFINAGUI owner;
    
    public VizualizeNetworkMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
            if(!owner.isInterDep()){
                JOptionPane.showMessageDialog(owner, "Use Edit Network to view networks that are not interdependent.","Unsupported Feature!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            final InterdepNetVisualization nV = new InterdepNetVisualization(owner);
            JInternalFrame iFrame = new NetworkEditor(owner, "Network Visualization");
            owner.getDesktop().add(iFrame);
            iFrame.add(nV);
            iFrame.setVisible(true);
            nV.start();
    }
}
