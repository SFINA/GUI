/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.editMenu;

import editors.EventsEditor;
import editors.SfinaParameterEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author dinesh
 */
public class EditSfinaParametersMenuItemAction  implements ActionListener {

    JFrame owner;
    
    public EditSfinaParametersMenuItemAction(JFrame owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        SfinaParameterEditor editor = new SfinaParameterEditor();
        Object[] options = {"Save","Cancel"};
        JOptionPane.showOptionDialog(owner, editor,"Edit Events",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
    }
    
}
