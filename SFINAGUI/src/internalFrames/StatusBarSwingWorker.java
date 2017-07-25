package internalFrames;

import internalFrames.StatusBar;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;

public class StatusBarSwingWorker extends SwingWorker<Object, Object> {

    StatusBar bar;
    
    /** Creates a new instance of StatusBar */
    public StatusBarSwingWorker(StatusBar bar) {
        this.bar = bar;
    }
    
    @Override
        public Object doInBackground() throws Exception {

            for (int i = 0; i < 100; i++) {        
                bar.setValue(i);
                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    
    /*
    public void setStatusRunning(Boolean bool){
        bar.setIndeterminate(bool);
        label.setText("Status: Running");
        if(!bool){
            label.setText("Status: Stopped");
            bar.setValue(bar.getMaximum());
        }
    }
    */
}
