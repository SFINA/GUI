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