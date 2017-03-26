/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.helpMenu;

import dialogs.AboutDialog;
import editors.ExperimentConfigurationsEditor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    JFrame owner;
    
    public AboutMenuItemAction(JFrame owner){
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // Create Panel with Formatting
        
        AboutDialog dialog = new AboutDialog();
        JOptionPane.showMessageDialog(owner, dialog,"Experiment Configuration",JOptionPane.PLAIN_MESSAGE);
        
        /*
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(200,200));
        panel.setLayout(new GridBagLayout());
        
        JLabel label1 = new JLabel("SFINA");
        JLabel label2 = new JLabel("COSS");
        panel.add(label1);
        panel.add(label2);
        
        // Add to doalog box and exit
        JDialog dialog= new JDialog(owner, "About");
        dialog.add(panel);
        */
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
