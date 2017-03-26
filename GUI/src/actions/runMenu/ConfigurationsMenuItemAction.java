/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.runMenu;

import editors.ExperimentConfigurationsEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dinesh
 */
public class ConfigurationsMenuItemAction implements ActionListener{
    
    JFrame owner;
    
    public ConfigurationsMenuItemAction(JFrame owner){
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ExperimentConfigurationsEditor editor = new ExperimentConfigurationsEditor();
        Object[] options = {"Save","Cancel"};
        JOptionPane.showOptionDialog(owner, editor,"Experiment Configurations",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
    }
    
    
}
