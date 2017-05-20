/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.event.WindowEvent;
import protopeer.BasePeerlet;

/**
 *
 * @author dinesh
 */
public class GUIProtopeer extends BasePeerlet{
    
    public PowerFlowCascadeGUI3 gui;
    
    public void GUIProtopeer(PowerFlowCascadeGUI3 gui){
        this.gui = gui;
    }
    
    public void GUIProtopeer(){
        
    }
    
    
    @Override
    public void start(){
        //PowerFlowCascadeGUI3
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PowerFlowCascadeGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PowerFlowCascadeGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PowerFlowCascadeGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PowerFlowCascadeGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PowerFlowCascadeGUI3().setVisible(true);
            }
        });
        
    }
    
    @Override
    public void stop(){
        //gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
    }
    
}
