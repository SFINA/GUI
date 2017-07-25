/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalFrames;

import utilities.CheckLogChange;
import core.SFINAGUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;


/**
 *
 * @author dinesh
 */
public class LogViewer extends JInternalFrame{
    SFINAGUI owner;
    
    public LogViewer(SFINAGUI owner){
        super("Log",false,false,false,false);
        this.owner = owner;
        Point orig = owner.getLocation();
        Dimension dim = owner.getContentPane().getSize();
        super.setSize(600,250);
        super.setLocation((int)(orig.getX()+dim.getWidth()*1/5), (int)(orig.getY()+dim.getHeight()*3/5));
        
        JTextArea textpane = new JTextArea(10,50);
        textpane.setEditable(false);
        textpane.setLineWrap(true);
        textpane.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(textpane);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        textpane.setText(" ");
        panel.add(scroll,BorderLayout.CENTER);
        this.getContentPane().add(panel);
        
        
        TimerTask task = new CheckLogChange("./log/debug.log") {
            protected void updateLog( File file ) {
                // here we code the action on a change
                
                try{
                BufferedReader in = new BufferedReader(new FileReader(file));
                
                String line = in.readLine();
                
                while(line != null){
                  textpane.append(line + "\n");
                  line = in.readLine();
                  textpane.setCaretPosition(textpane.getDocument().getLength());
                }
                } catch (IOException io){
                    //TODO log output error using logger
                }
                
            }
          };

        Timer timer = new Timer();
        // repeat the check every second
        timer.schedule( task , new Date(), 1000 );

    }
       
}