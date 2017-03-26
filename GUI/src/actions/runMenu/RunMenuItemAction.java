/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.runMenu;

import core.PowerFlowExperiment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        String expSeqNum = "01";
        PowerFlowExperiment exp = new PowerFlowExperiment(expSeqNum);
        exp.runExperiment();
    
    }
    
}
