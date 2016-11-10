/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfinanetworkgenerator;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author dinesh
 */
public class PropertyField extends JPanel{
    
    JTextField labelField = new JTextField(50);
    JTextField valueField = new JTextField(50);
    
    public PropertyField(String labelTxt){
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setMaximumSize(new Dimension(300,30));
        this.setPreferredSize(new Dimension(300,30));
        this.labelField.setText(labelTxt);
        this.labelField.setEditable(false);
        this.add(labelField);
        this.add(valueField);
    }
    
    public String getValue(){
        return valueField.getText();
    }
    
    public void setValue(String txt){
        valueField.setText(txt);
    }
    
    public static void main(String [] args){
        JFrame frame = new JFrame("Text Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PropertyField jp1 = new PropertyField("Label What?1");
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().add(jp1);
        frame.pack();
        frame.setVisible(true);
    }    
}