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
package actions.helpMenu;

import core.SFINAGUI;
import dialogs.AboutDialog;
import dialogs.HelpDialog;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author dinesh
 */
public class HelpMenuItemAction implements ActionListener {
    
    private SFINAGUI owner;
    
    public HelpMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Show a dialog box with manual link
        HelpDialog dialog = new HelpDialog();
        String[] options = {" Visit Repository "};
         int optionPane = JOptionPane.showOptionDialog(owner, dialog,"About SFINA",JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        if(optionPane==JOptionPane.OK_OPTION){
            try{
                Desktop.getDesktop().browse(new URL("https://github.com/SFINA/Manual").toURI());
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
}
