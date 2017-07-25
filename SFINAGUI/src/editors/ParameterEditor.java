/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.Box;

/**
 *
 * @author dinesh
 */
public class ParameterEditor extends JPanel{
    
    HashMap<String, String> parameters;
    ArrayList<ParameterField> pFields;
    JPanel contentPane;
    JScrollPane scrollPane;
    String columnSeparator="=";
    
    public ParameterEditor(HashMap<String, String> pMap){
        parameters = pMap;
        initComponents();
        populateFields(pMap);
    }
    
    public ParameterEditor(String file, String columnSeparator){
        parameters = new HashMap<String,String>();
        initComponents();
        populateFields(file, columnSeparator);
    }
    
    private void initComponents(){
        pFields = new ArrayList<ParameterField>();
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(contentPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(410,250));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        setLayout(new BorderLayout());
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void populateFields(HashMap<String, String> pMap){
        for(String s:pMap.keySet()){
            String key = s;
            String value=pMap.get(s);
            ParameterField pF = new ParameterField(key);
            pF.setValue(value);
            pFields.add(pF);
            contentPane.add(pF);
            contentPane.add(Box.createVerticalStrut(7));
            scrollPane.revalidate();
            scrollPane.repaint();
            revalidate();
            repaint();
        }
    }
    
    private void populateFields(String path, String columnSeparator){
        ParameterReader pR = new ParameterReader("=");
        parameters = pR.readParameters(path);
        populateFields(parameters);
    }
    
    public HashMap<String,String> getParameters(){
       for(ParameterField pF : pFields){
           parameters.put(pF.getParameterPair().getKey(),pF.getParameterPair().getValue());
       }
       return parameters;
    }
    
    public static void main(String [] args){
        JFrame frame = new JFrame("Text Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ParameterEditor jp1 = new ParameterEditor("conf/fileSystem.conf","=");
        frame.getContentPane().add(jp1, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
