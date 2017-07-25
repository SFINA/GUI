/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.editMenu;

import utilities.CheckNetworkFiles;
import core.SFINAGUI;
import edu.uci.ics.jung.graph.util.Pair;
import internalFrames.NetworkEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import networkGenerator.FieldsDialog;
import networkGenerator.NetworkGenerator;

/**
 *
 * @author dinesh
 */
public class EditNetworkMenuItemAction  implements ActionListener {

    private SFINAGUI owner;
    
    public EditNetworkMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
            if(owner.isInterDep()){
                JOptionPane.showMessageDialog(owner, "Network Editing Feature is no implemented for Interdependent Networks!","Unsupported Feature!", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            
            final NetworkGenerator nG = new NetworkGenerator();
            JInternalFrame iFrame = new NetworkEditor(owner);
            owner.getDesktop().add(iFrame);
            
            iFrame.add(nG);
            iFrame.setVisible(true);
            
            
            JFileChooser chooser = new JFileChooser(); 
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select Network Location");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
                String path=  chooser.getSelectedFile().getAbsolutePath();
                CheckNetworkFiles check = new CheckNetworkFiles(path);
                if(!check.checkAll()){
                    JOptionPane.showMessageDialog(owner, "The specified folder does not contain necessary files.\n Make sure the path contains node and link files for both flow and topology!","Invalid Directory!", JOptionPane.PLAIN_MESSAGE);
                    iFrame.setVisible(false);
                    return;
                }
                
                nG.loadNodeFlow(path);
                HashMap<Integer, Pair<Integer>> map = nG.loadLinkTopology(path);
                nG.loadLinkFlow(path,map);
                
                nG.getVisualizationViewer().repaint();
            }       
    }    
}
