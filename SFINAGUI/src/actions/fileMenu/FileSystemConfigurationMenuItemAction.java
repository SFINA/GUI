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
package actions.fileMenu;

import core.SFINAGUI;
import editors.ParameterEditor;
import editors.ParameterWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dinesh
 */
public class FileSystemConfigurationMenuItemAction implements ActionListener {

    private SFINAGUI owner;
    private HashMap<String, String> map;
    private ParameterEditor editor;
    public FileSystemConfigurationMenuItemAction(SFINAGUI owner){
        this.owner = owner;
        map = new HashMap<String, String>();
        editor = new ParameterEditor("conf/fileSystem.conf","=");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object[] options = {"Save","Cancel"};
        
        Integer i = JOptionPane.showOptionDialog(owner, editor, "Experiment Configurations",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        
        if(i == 0){
            map = editor.getParameters();
            ParameterWriter pW = new ParameterWriter("=");
            pW.write(map,"conf/fileSystem.conf");
        }

        
    }
    
    
    
}
