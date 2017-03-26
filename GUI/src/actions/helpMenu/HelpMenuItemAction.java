/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.helpMenu;

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
    
    JFrame owner;
    
    public HelpMenuItemAction(JFrame owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Show a dialog box with manual link
        HelpDialog dialog = new HelpDialog();
        JOptionPane.showMessageDialog(owner, dialog,"Experiment Configuration",JOptionPane.PLAIN_MESSAGE);
        /*
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(200,200));
        panel.setLayout(new BorderLayout());
        JLabel emptyTxt = new JLabel("");
        JLabel txt = new JLabel("Visit Sfina-net.org for manual.");
        JButton btn = new JButton("Visit Webpage");
        
        panel.add(txt,BorderLayout.CENTER);
        
        panel.add(btn,BorderLayout.SOUTH);
        btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                final URI uri;
                try {
                    uri = new URI("https://github.com/SFINA/Manual");
                    
                    if (Desktop.isDesktopSupported()) {
                        try {
                              Desktop.getDesktop().browse(uri);
                        } 
                        catch (IOException io) { 
                        // TODO Error handling
                        }
                    } else 
                    { 
                        // TODO Error handling
                    }
                } catch (URISyntaxException ex) {
                    Logger.getLogger(HelpMenuItemAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        JDialog dialog = new JDialog(owner, "Help");
        dialog.add(panel);
        dialog.setSize(new Dimension(200,200));
        dialog.setVisible(true);
        */
    }
    
}
