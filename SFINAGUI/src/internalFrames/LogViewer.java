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