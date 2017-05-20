/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.runMenu;

import core.PowerFlowCascadeGUI3;
import core.PowerFlowExperiment;
import core.StatusBarSwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author dinesh
 */
public class RunMenuItemAction implements ActionListener {
    
    JFrame owner;
    
    public RunMenuItemAction(JFrame owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //expSeqNum = (String)experimentComboBox.getSelectedItem();
        //debugOutput.setText(debugOutput.getText()+"\nOption "+expSeqNum+" was selected.");
        
        HashMap<String, String> map = ((PowerFlowCascadeGUI3)owner).getExperimentConfigurations();
        String expSeqNum="01";
        if(map!=null && !(map.isEmpty())){
            expSeqNum = map.get("expSeqNum");
            System.out.println("Map Not Null");
        }
        
        //PowerFlowExperiment exp = (PowerFlowExperiment)((PowerFlowCascadeGUI3)owner).getExperiment();
        //exp.run();
        
        PowerFlowExperiment exp = new PowerFlowExperiment(expSeqNum, owner);
        Thread t = new Thread(exp);
        t.start();
        
        
        
        //exp.run();
        
        
//((PowerFlowCascadeGUI3)owner).notifyStatus(true);
        
        //StatusBarSwingWorker sbw= ((PowerFlowCascadeGUI3)owner).getStatusBarSwingWorker();
        //sbw.execute();
        //exp.run();
        //sbw.cancel(true);
        //((PowerFlowCascadeGUI3)owner).setProgress(100);
    }
    
}
