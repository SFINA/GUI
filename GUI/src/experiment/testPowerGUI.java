/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import core.PowerFlowCascadeGUI;

/**
 *
 * @author dinesh
 */
public class testPowerGUI {
    public static void main(String agrs[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PowerFlowCascadeGUI("Case200RandomExperiment").setVisible(true);
            }
        });
    }
}