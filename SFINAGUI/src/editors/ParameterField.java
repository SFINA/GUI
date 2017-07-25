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
package editors;

import java.awt.Dimension;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author dinesh
 */
public class ParameterField extends JPanel{
    
    JLabel labelField = new JLabel("");
    
    //labelField.setPreferredSize(new Dimension(200,30));
    
    JTextField valueField = new JTextField();
    
    public ParameterField(String labelTxt){
        labelField.setMaximumSize(new Dimension(150,30));
        labelField.setPreferredSize(new Dimension(150,30));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setMaximumSize(new Dimension(400,30));
        this.setPreferredSize(new Dimension(400,30));
        this.labelField.setText(labelTxt);
        //this.labelField.setEditable(false);
        this.add(labelField);
        this.add(valueField);
    }
    
    public Map.Entry<String,String> getParameterPair(){
        return new AbstractMap.SimpleEntry<String, String>(labelField.getText(),valueField.getText());
    }
    
    
    public void setValue(String txt){
        valueField.setText(txt);
    }
    
    public static void main(String [] args){
        JFrame frame = new JFrame("Text Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ParameterField jp1 = new ParameterField("Label What?1");
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().add(jp1);
        frame.pack();
        frame.setVisible(true);
    }    
}