/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfinanetworkgenerator;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
        setPreferredSize(new Dimension(300,fields.size()*30+10));
        for (String s:fields){
            PropertyField p = new PropertyField(s);
            pFields.add(p);
        }

        contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(300, fields.size()*30));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        for(PropertyField p : pFields){
            contentPane.add(p);
        }
        
        
        
        scrollPane = new JScrollPane(contentPane);
        
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
//        JFrame frame = new JFrame("Values Dialog");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
//        frame.getContentPane().add(v, BorderLayout.CENTER);
//        frame.pack();
//        frame.setVisible(true);
          JOptionPane.showConfirmDialog(null, v, "Edit Values", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
//        JOptionPane.showConfirmDialog(frame,v,"",JOptionPane.INFORMATION_MESSAGE);
//        System.out.println(v.getValues());
    }
}
