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

import core.SFINAGUI;
import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author dinesh
 */
public class NetworkEditor extends JInternalFrame {
    SFINAGUI owner;
    
    public NetworkEditor(SFINAGUI owner){
        super("Network Viewer",true,true,true,true);
        this.owner = owner;
        Point orig = owner.getLocation();
        Dimension dim = owner.getContentPane().getSize();
        super.setSize(450,400);
        //super.setLocation((int)(orig.getX()+dim.getWidth()*5/9), (int)(orig.getY()+10));
        //super.setLocation(0,0);
    }
    
}