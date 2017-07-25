/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
