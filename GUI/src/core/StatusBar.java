package core;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {

    JProgressBar bar;
    JLabel label;
    
    /** Creates a new instance of StatusBar */
    public StatusBar() {
        super();
        
        super.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        label = new JLabel();
        label.setText("Experiment Status: No Experiment Running ");
        super.setLayout(new FlowLayout(FlowLayout.RIGHT,50,0));
        super.add(label);
        
        bar = new JProgressBar();
        bar.setIndeterminate(false);
        bar.setMinimum(0);
        bar.setMaximum(100);
        super.add(bar);
        
    }
    
    
    public void setStatusRunning(Boolean bool){
        bar.setIndeterminate(bool);
        label.setText("Experiment Status: Running");
        if(!bool){
            label.setText("Experiment Status: Stopped");
            bar.setValue(bar.getMaximum());
        }
    }
    
    
    
    public void setValue(Integer i){
        bar.setValue(i);
    }
    
}
