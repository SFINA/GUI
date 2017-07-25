/*
 * Copyright (C) 2017 SFINA Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package networkGenerator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author dinesh
 */
public class FieldsDialog extends JPanel{
    
    ArrayList<String> fields;
    ArrayList<PropertyField> pFields;
    public int countFields=0;
    JPanel contentPane;
    JButton addField;
    JButton removeField;
    JScrollPane scrollPane;
    
    public FieldsDialog(){
        setPreferredSize(new Dimension(320,200));
        fields = new ArrayList<String>();
        pFields = new ArrayList<PropertyField>();
        addField = new JButton("Add Field");
        removeField = new JButton("Remove Field");
        contentPane = new JPanel();
        //contentPane.setPreferredSize(new Dimension(300,100));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(contentPane);
        scrollPane.setPreferredSize(new Dimension(310,200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        addField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                countFields++;
                PropertyField p = new PropertyField("Field "+Integer.toString(countFields)+" : ");
                p.setValue("Field"+Integer.toString(countFields));
                pFields.add(p);
                contentPane.add(p);
                scrollPane.revalidate();
                scrollPane.repaint();
                validate();
//                    contentPane.repaint();
//                    scrollPane.repaint();
                repaint();
            }
        });
        
        removeField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(countFields>0){
                    countFields--;
                    PropertyField p = pFields.get(pFields.size()-1);
                    contentPane.remove(p);
                    pFields.remove(p);
                    scrollPane.revalidate();
                    scrollPane.repaint();
                    validate();
//                    contentPane.repaint();
//                    scrollPane.repaint();
                    repaint();
                }
                
            }
        });
        
  
        setLayout(new BorderLayout());
        
        add(scrollPane, BorderLayout.CENTER);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        addField.setPreferredSize(new Dimension(190,30));
        removeField.setPreferredSize(new Dimension(190,30));
        p.add(addField);
        p.add(removeField);
        add(p,BorderLayout.SOUTH);
               
    }
    
    public ArrayList<String> getFields(){
       for(PropertyField p: pFields){
           fields.add(p.getValue());
       } 
       return fields; 
    }
    
    public static void main(String [] args){
        JFrame frame = new JFrame("Text Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FieldsDialog jp1 = new FieldsDialog();
        //frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().add(jp1, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
