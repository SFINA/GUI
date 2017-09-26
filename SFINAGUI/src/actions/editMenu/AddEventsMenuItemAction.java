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
import editors.EventsEditor;
import editors.ParameterReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author dinesh
 */
public class AddEventsMenuItemAction  implements ActionListener  {

    private SFINAGUI owner;
    private static final Logger logger = Logger.getLogger("AddEventsActionListener");
    
    public AddEventsMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // get fileSystemInformation
        ParameterReader pR = new ParameterReader("=");
        HashMap<String, String> parameters = pR.readParameters("conf/fileSystem.conf");
        
        // show a dialog box with experimentDirectory:
        String expSeqNum = owner.getExperimentConfigurations().get("expSeqNum");
        File expFolder = new File(parameters.get("configurationFilesLocation")+"experiment-"+expSeqNum);
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
        
        String filePath = parameters.get("configurationFilesLocation")+"experiment-"+expSeqNum+"/"+dlg.getSelectedPeer()+"/"+parameters.get("inputDirectoryName")+parameters.get("eventsFileName");
        
        if(cBoxDlg == JOptionPane.YES_OPTION){
            EventsEditor editor = new EventsEditor();
            Object[] options = {"Save","Cancel"};
            int editorChoice=JOptionPane.showOptionDialog(owner, editor,"Add Events",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            
            if(editorChoice==JOptionPane.YES_OPTION){
                try{
                    File file = new File(filePath);
                    File parent = file.getParentFile();
                    if(!parent.exists() && !parent.mkdirs()){
                        logger.debug("Couldn't create output folder");
                    }
                    
                    file.createNewFile();
                    PrintWriter writer = new PrintWriter(new FileWriter(file,true));
                    
                    // if file is empty, write header:
                    String columnSeparator = parameters.get("columnSeparator");
                    
                    // check if the file is empty:
                    BufferedReader br = new BufferedReader(new FileReader(filePath));
                    if (br.readLine() == null) {
                        writer.write("time"+columnSeparator+"feature"+columnSeparator+"component"+columnSeparator+"id"+columnSeparator+"parameter"+columnSeparator+"value");
                    }
                    
                    // write the event information
                    writer.write("\n");
                    writer.write(editor.getTime()+columnSeparator);
                    writer.write(editor.getFeature()+columnSeparator);
                    writer.write(editor.getNetworkComponent()+columnSeparator);
                    writer.write(editor.getId()+columnSeparator);
                    writer.write(editor.getParameter()+columnSeparator);
                    writer.close();
                    
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
                
                    
            }
            
        }
        
        owner.resetExperimentExplorer();
    }
}
