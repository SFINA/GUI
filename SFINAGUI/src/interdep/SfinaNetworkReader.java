/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interdep;

import edu.uci.ics.jung.graph.util.Pair;
import input.FlowLoader;
import input.TopologyLoader;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import network.FlowNetwork;
import power.backend.PowerFlowNetworkDataTypes;


/**
 *
 * @author dinesh
 */
public class SfinaNetworkReader {
    
    public SfinaNetworkReader(){
    
    }    
    
    public Vector<FlowNetwork> readNetworks(){
        Vector<FlowNetwork> fN = new Vector<FlowNetwork>();
        String path="networkFileLocation";
        JFileChooser chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Location of Peers");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
            path = chooser.getSelectedFile().getAbsolutePath();
            
            String[] peers=new File(path).list(new FilenameFilter(){
                @Override
                public boolean accept(File file, String name){
                    return new File(file, name).isDirectory();
                }
            });
            
            SelectItemComboBoxDialog dlg_peers = new SelectItemComboBoxDialog("Select any one peer to explore networks. For interdependent networks, all peers will be considered.");
            dlg_peers.setComboBoxItems(peers);
            Object[] options1 = {"OK","Cancel"};
            int cBoxDlg = JOptionPane.showOptionDialog(null, dlg_peers,"Select Peers",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options1,options1[0]);
            
            if(cBoxDlg == JOptionPane.YES_OPTION){
                String[] outputs=new File(path + File.separatorChar+peers[0]).list(new FilenameFilter(){
                    @Override
                    public boolean accept(File file, String name){
                        return new File(file, name).isDirectory();
                    }
                });
                SelectItemComboBoxDialog dlg_outputs = new SelectItemComboBoxDialog("Select whether you want to vizualize inputs or outputs");
                dlg_outputs.setComboBoxItems(outputs);
                
                int cBoxDlg_outputs = JOptionPane.showOptionDialog(null, dlg_outputs,"Select Input/Output",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options1,options1[0]);
                if(cBoxDlg_outputs == JOptionPane.YES_OPTION){
                    String[] times = new File(path + File.separatorChar + peers[0] + File.separatorChar+dlg_outputs.getSelectedItem()).list(new FilenameFilter(){
                        @Override
                        public boolean accept(File file, String name){
                            return new File(file, name).isDirectory();
                        }
                    });
                    SelectItemComboBoxDialog dlg_times = new SelectItemComboBoxDialog("Select the time step whose network you want to vizualize.");
                    dlg_times.setComboBoxItems(times);
                    
                    int cBoxDlg_times = JOptionPane.showOptionDialog(null, dlg_times,"Select Time Step",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options1,options1[0]);
                    if(cBoxDlg_times == JOptionPane.YES_OPTION){
                        
                        if((dlg_outputs.getSelectedItem()).contains("output")){
                            
                            String[] iters = new File(path + File.separatorChar + peers[0] + File.separatorChar+dlg_outputs.getSelectedItem()+File.separatorChar+dlg_times.getSelectedItem()).list(new FilenameFilter(){
                            @Override
                            public boolean accept(File file, String name){
                                return new File(file, name).isDirectory();
                            }
                            });
                            SelectItemComboBoxDialog dlg_iters = new SelectItemComboBoxDialog("Select the iteration to visualize.");
                            dlg_iters.setComboBoxItems(iters);
                            int cBoxDlg_iters = JOptionPane.showOptionDialog(null, dlg_iters,"Select Iteration Step",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options1,options1[0]);
                            if(cBoxDlg_iters == JOptionPane.YES_OPTION){
                                int networkIndex = -1;
                                for(String peerPath : peers){
                                    FlowNetwork n = new FlowNetwork();
                                    TopologyLoader topologyLoader;
                                    FlowLoader flowLoader;
                                    networkIndex = networkIndex+1;
                                    topologyLoader = new TopologyLoader(n, ",", networkIndex);
                                    flowLoader = new FlowLoader(n, ",","-",new PowerFlowNetworkDataTypes());
                                    char fSep = File.separatorChar;
                                    String time = dlg_times.getSelectedItem();
                                    String output = dlg_outputs.getSelectedItem();
                                    String iteration = dlg_iters.getSelectedItem();
                                    String net_location = path+fSep+peerPath+fSep+output+fSep+time+fSep+iteration;
                                    topologyLoader.loadNodes(net_location+"/topology/nodes.txt");
                                    topologyLoader.loadLinks(net_location+"/topology/links.txt");
                                    if (peerPath.contains("peer-0")){
                                        topologyLoader.loadLinks(net_location+"/topology/interLinks.txt");
                                    }
                                    flowLoader.loadNodeFlowData(net_location+"/flow/nodes.txt");
                                    flowLoader.loadLinkFlowData(net_location+"/flow/links.txt");
                                    if (peerPath.contains("peer-0")){
                                        flowLoader.loadInterdependentLinkFlowData(net_location+"/flow/interLinks.txt");
                                    }

                                    fN.add(networkIndex,n);
                                }
                                return fN;

                            }
                        } else {
                            int networkIndex = -1;
                            for(String peerPath : peers){
                                FlowNetwork n = new FlowNetwork();
                                TopologyLoader topologyLoader;
                                FlowLoader flowLoader;
                                networkIndex = networkIndex+1;
                                topologyLoader = new TopologyLoader(n, ",", networkIndex);
                                flowLoader = new FlowLoader(n, ",","-",new PowerFlowNetworkDataTypes());
                                char fSep = File.separatorChar;
                                String time = dlg_times.getSelectedItem();
                                String output = dlg_outputs.getSelectedItem();
                                String net_location = path+fSep+peerPath+fSep+output+fSep+time;
                                topologyLoader.loadNodes(net_location+"/topology/nodes.txt");
                                topologyLoader.loadLinks(net_location+"/topology/links.txt");
                                if (peerPath.contains("peer-0")){
                                    topologyLoader.loadLinks(net_location+"/topology/interLinks.txt");
                                }
                                flowLoader.loadNodeFlowData(net_location+"/flow/nodes.txt");
                                flowLoader.loadLinkFlowData(net_location+"/flow/links.txt");
                                if (peerPath.contains("peer-0")){
                                    flowLoader.loadInterdependentLinkFlowData(net_location+"/flow/interLinks.txt");
                                }
                                fN.add(networkIndex,n);
                            }
                            return fN;
                        }
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
        System.exit(0);
        return null;
    }
        
}