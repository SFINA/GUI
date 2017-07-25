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
