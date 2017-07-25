/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkGenerator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import static java.lang.Integer.min;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author dinesh
 */
public class ValuesDialog extends JPanel{
    
    ArrayList<String> fields;
    ArrayList<String> valueList;
    
    ArrayList<PropertyField> pFields;
    
    JScrollPane scrollPane;
    JPanel contentPane;
    
    ValuesDialog(ArrayList<String> fields){
        valueList = new ArrayList<String>();
        this.fields = fields;
        pFields = new ArrayList<PropertyField>();
        
        //setPreferredSize(new Dimension(300,min(fields.size()*30+50,300)));
        for (String s:fields){
            PropertyField p = new PropertyField(s);
            pFields.add(p);
        }

        contentPane = new JPanel();
        //contentPane.setPreferredSize(new Dimension(250, min(fields.size()*30,300)));
        //contentPane.setPreferredSize(new Dimension(300, 500));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        for(PropertyField p : pFields){
            contentPane.add(p);
        }
        
        scrollPane = new JScrollPane(contentPane);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setBounds(20,20,250,250);
        //setPreferredSize(new Dimension(350,300));
        add(scrollPane);
        
        
    }
    
    public ArrayList<String> getValues(){
        
        valueList.clear();
        
        for(PropertyField p : pFields){
            valueList.add(p.getValue());
        }
        
        return valueList;
        
    }
    
    public static void main(String [] args){

        ArrayList<String> al = new ArrayList<>(Arrays.asList("v1","v2","v3","v4","v5","v6","v7","v8"));
        ValuesDialog v = new ValuesDialog(al);
        JScrollPane jsp = new JScrollPane(v,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setPreferredSize(new Dimension(320,200));
        JOptionPane.showConfirmDialog(null, jsp, "Enter Link Properties", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
    }
}
