/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.editMenu;

import core.PowerFlowCascadeGUI3;
import edu.uci.ics.jung.graph.util.Pair;
import internalFrames.NetworkEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import sfinanetworkgenerator.FieldsDialog;
import sfinanetworkgenerator.NetworkGenerator;

/**
 *
 * @author dinesh
 */
public class EditNetworkMenuItemAction  implements ActionListener {

    JFrame owner;
    
    public EditNetworkMenuItemAction(JFrame owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
                    final NetworkGenerator nG = new NetworkGenerator();
            JInternalFrame iFrame = new NetworkEditor(owner);
            ((PowerFlowCascadeGUI3)owner).getDesktop().add(iFrame);
            
            iFrame.add(nG);
            iFrame.setVisible(true);
            
            
            JFileChooser chooser = new JFileChooser(); 
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select Network Location");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
                String path=  chooser.getSelectedFile().getAbsolutePath();
                nG.loadNodeFlow(path);
                HashMap<Integer, Pair<Integer>> map = nG.loadLinkTopology(path);
                nG.loadLinkFlow(path,map);
                
                //demo.setLayout(new );
                //demo.layout = new StaticLayout<SfinaNode,SfinaLink>(demo.graph, new Dimension(600,600));
                //demo.vv.doLayout();
                //demo.vv.setLayout();
                nG.getVisualizationViewer().repaint();
            }
            
    }
    
}
