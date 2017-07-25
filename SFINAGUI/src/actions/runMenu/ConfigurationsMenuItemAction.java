/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.runMenu;

import core.SFINAGUI;
import editors.ExperimentConfigurationsEditor;
import editors.ParameterEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dinesh
 */
public class ConfigurationsMenuItemAction implements ActionListener{
    
    private SFINAGUI owner;
    private HashMap<String, String> map;
    private ParameterEditor editor;
    
    public ConfigurationsMenuItemAction(SFINAGUI owner){
        this.owner = owner;
        map = new HashMap<String, String>();
        editor = new ParameterEditor(owner.getExperimentConfigurations());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        Object[] options = {"Save","Cancel"};
        
        Integer i = JOptionPane.showOptionDialog(owner, editor, "Experiment Configurations",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        
        if(i == 0){
            map = editor.getParameters();
            owner.setExperimentConfigurations(map);
        }
        
    }
    
    public HashMap<String, String> getConfigurations(){
        return map;
    }
    
}
