/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.runMenu;



import core.SFINAGUI;
import core.SFINAGUIExperiment;
import internalFrames.StatusBarSwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author dinesh
 */
public class RunMenuItemAction implements ActionListener {
    
    private SFINAGUI owner;
    
    public RunMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //SFINAGUIExperiment exp = owner.getExperiment();
        Thread t = new Thread(){
            @Override
            public void run(){
                /*
                owner.notifyStatus(true);
                exp.run();
                owner.notifyStatus(false);
                */
                owner.runExperiment();
            }
        };
        t.start();
    }
    
}
