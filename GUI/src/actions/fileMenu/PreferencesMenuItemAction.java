/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.fileMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author dinesh
 */
public class PreferencesMenuItemAction implements ActionListener {

    JFrame owner;
    
    public PreferencesMenuItemAction(JFrame owner){
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        // show an editable JMenu Item
        
    }
    
    
    
}
