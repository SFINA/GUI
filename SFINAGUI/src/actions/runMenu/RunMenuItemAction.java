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
package actions.runMenu;



import core.SFINAGUI;
import core.SFINAGUIExperiment;
import internalFrames.StatusBarSwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author dinesh
 */
public class RunMenuItemAction implements ActionListener {
    
    private SFINAGUI owner;
    
    public RunMenuItemAction(SFINAGUI owner){
        this.owner = owner;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //SFINAGUIExperiment exp = owner.getExperiment();
        Thread t = new Thread(){
            @Override
            public void run(){
                /*
                owner.notifyStatus(true);
                exp.run();
                owner.notifyStatus(false);
                */
                owner.runExperiment();
            }
        };
        t.start();
    }
    
}
