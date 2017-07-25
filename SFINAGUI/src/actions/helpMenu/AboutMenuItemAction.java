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
import editors.ExperimentConfigurationsEditor;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author dinesh
 */
public class AboutMenuItemAction implements ActionListener{
    
    private SFINAGUI owner;
    
    public AboutMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Create Panel with Formatting
        
        AboutDialog dialog = new AboutDialog();
        JOptionPane.showMessageDialog(owner, dialog,"SFINA Manual",JOptionPane.PLAIN_MESSAGE);
        
    }
    
    public static void main(String[] args){
        /*
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(200,200));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        JLabel label1 = new JLabel("SFINA");
        JLabel label2 = new JLabel("COSS");
        panel.add(label1);
        panel.add(label2);
        
        JFrame frame = new JFrame();
        // Add to doalog box and exit
        JDialog dialog= new JDialog(frame, "About");
        dialog.setSize(new Dimension(200,200));
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setVisible(true);
        */
    }
    
}
