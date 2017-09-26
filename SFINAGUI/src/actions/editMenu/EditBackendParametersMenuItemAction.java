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
import editors.BackendParameterEditor;
import editors.ParameterEditor;
import editors.ParameterReader;
import editors.ParameterWriter;
import editors.SfinaParameterEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dinesh
 */
public class EditBackendParametersMenuItemAction  implements ActionListener {

    private SFINAGUI owner;
    
    public EditBackendParametersMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // show a dialog box with experimentDirectory:
        String expSeqNum = ((SFINAGUI)owner).getExperimentConfigurations().get("expSeqNum");
        //expSeqNum="01";
        File expFolder = new File("experiments/experiment-"+expSeqNum);
        String[] peers=expFolder.list(new FilenameFilter(){
            @Override
            public boolean accept(File file, String name){
                return new File(file, name).isDirectory();
            }
        });
        SelectPeerComboBoxDialog dlg = new SelectPeerComboBoxDialog();
        dlg.setComboBoxItems(peers);
        Object[] options1 = {"OK","Cancel"};
        int cBoxDlg = JOptionPane.showOptionDialog(owner, dlg,"Select Peerlet",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options1,options1[0]);
        
        ParameterReader pr = new ParameterReader("=");
        HashMap<String,String> parameters=pr.readParameters("conf/fileSystem.conf");
        String filePath = parameters.get("configurationFilesLocation")+"experiment-"+expSeqNum+"/"+dlg.getSelectedPeer()+"/"+parameters.get("inputDirectoryName")+parameters.get("backendParamFileName");
        
        if(cBoxDlg == JOptionPane.YES_OPTION){
            ParameterReader reader = new ParameterReader("=");
            HashMap<String, String> map = reader.readParameters(filePath);

            ParameterEditor editor = new ParameterEditor(map);
            
            Object[] options2 = {"Save","Cancel"};
            int editorChoice=JOptionPane.showOptionDialog(owner, editor,"Edit Events",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options2,options2[0]);
            // if user changed values, update them:
            if(editorChoice==JOptionPane.YES_OPTION){
                map = editor.getParameters();
                ParameterWriter writer = new ParameterWriter("=");
                writer.write(map,filePath);
            }
            
        }
        owner.resetExperimentExplorer();
    }
    
    
}
