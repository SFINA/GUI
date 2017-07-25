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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {

    JProgressBar bar;
    JLabel statLabel;
    JLabel expLabel;
    String expName;
    
    public StatusBar(String expName) {
        super();
        
        super.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        expLabel = new JLabel();
        expLabel.setText("Selected Experiment: "+expName);
        expLabel.setHorizontalAlignment(JLabel.CENTER);
        expLabel.setVerticalAlignment(JLabel.CENTER);
        
        statLabel = new JLabel();
        statLabel.setText("Experiment Status: No Experiment Running ");
        statLabel.setHorizontalAlignment(JLabel.CENTER);
        statLabel.setVerticalAlignment(JLabel.CENTER);
        
        super.setLayout(new BorderLayout());
        super.add(expLabel, BorderLayout.WEST);
        super.add(statLabel, BorderLayout.CENTER);
        
        bar = new JProgressBar();
        bar.setIndeterminate(false);
        bar.setMinimum(0);
        bar.setMaximum(100);
        
        super.add(bar, BorderLayout.EAST);
        
    }
    
    
    public void setStatusRunning(Boolean bool){
        bar.setIndeterminate(bool);
        statLabel.setText("Experiment Status: Running");
        if(!bool){
            statLabel.setText("Experiment Status: Completed");
            bar.setValue(bar.getMaximum());
        }
    }
    
    public void setValue(Integer i){
        bar.setValue(i);
    }
    
    public void setCurrentExperimentName(String expName){
        expLabel.setText("Selected Experiment:"+expName);
    }
       
}