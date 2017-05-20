/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.runMenu;

import core.PowerFlowCascadeGUI3;
import editors.ExperimentConfigurationsEditor;
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
    
    JFrame owner;
    
    HashMap<String, String> map;
    ExperimentConfigurationsEditor editor;
    public ConfigurationsMenuItemAction(JFrame owner){
        this.owner = owner;
        map = new HashMap<String, String>();
        editor =  new ExperimentConfigurationsEditor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        Object[] options = {"Save","Cancel"};
        Integer i = JOptionPane.showOptionDialog(owner, editor,"Experiment Configurations",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        if(i == 0){
            System.out.println("experiment-"+editor.getExpSeqNum());
            map.put("expSeqNum",editor.getExpSeqNum());
            map.put("backendType",editor.getBackendType());
            map.put("peersLogDirectory",editor.getPeersLogDirectory());
            map.put("experimentFolder",editor.getExperimentFolder());
            map.put("bootstrapTime",editor.getBootstrapTime());
            map.put("runTime",editor.getRunTime());
            map.put("getRunDuration",editor.getRunDuration());
            map.put("N",editor.getN());
            ((PowerFlowCascadeGUI3)owner).setExperimentConfigurations(map);
        }
        System.out.println("Print Choice : "+i);
        System.out.println("experiment-"+editor.getExpSeqNum());
    }
    
    public HashMap<String, String> getConfigurations(){
        return map;
    }
    
    
}
