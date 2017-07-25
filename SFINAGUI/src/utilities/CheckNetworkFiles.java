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
package utilities;
import java.io.File;
import java.nio.file.Files;
/**
 *
 * @author dinesh
 */
public class CheckNetworkFiles {
    
    String root;
    
    public CheckNetworkFiles(String root){
        this.root = root;
    }
    
    public boolean checkTopologyFiles(){
        File topo_root = new File(root,"topology");
        if(topo_root.exists()){
            File node = new File(topo_root,"nodes.txt");
            File link = new File(topo_root,"links.txt");
            if(node.exists() && link.exists()){
                return true;
            }
        } else {
            return false;
        }
        return false;
    }
    
    public boolean checkFlowFiles(){
        File topo_root = new File(root,"flow");
        if(topo_root.exists()){
            File node = new File(topo_root,"nodes.txt");
            File link = new File(topo_root,"links.txt");
            if(node.exists() && link.exists()){
                return true;
            }
        } else {
            return false;
        }
        return false;    
    }
    
    public boolean checkAll(){
        if (this.checkFlowFiles() && this.checkTopologyFiles()){
            return true;
        } else {
            return false;
        }
        
    }
    
    public static void main(String args[]){
        // testing that above works
        CheckNetworkFiles check = new CheckNetworkFiles("./experiments/experiment-01/peer-0/input/time_1");
        System.out.println(check.checkAll());
    }
}
