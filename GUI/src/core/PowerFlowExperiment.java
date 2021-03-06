
/*
 * Copyright (C) 2015 SFINA Team
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
package core;

import replayer.BenchmarkLogReplayer;
import agent.PowerCascadeAgent;
import java.io.File;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import protopeer.Experiment;
import protopeer.Peer;
import protopeer.PeerFactory;
import protopeer.SimulatedExperiment;
import protopeer.util.quantities.Time;

/**
 * Reduces capacity of Links successively in each time step. Up to a total of 50% reduction. Goal is to trigger cascades.
 * @author Ben
 */
public class PowerFlowExperiment extends SimulatedExperiment implements Runnable{
    
    private static final Logger logger = Logger.getLogger(PowerFlowExperiment.class);
    
    private static String expSeqNum="Case30RateReduction";
    private final static String peersLogDirectory="peerlets-log/";
    private static String experimentID="experiment-"+expSeqNum;
    
    //Simulation Parameters
    private final static int bootstrapTime=2000;
    private final static int runTime=1000;
    private final static int runDuration=29;
    private final static int N=1;
    
    private JFrame owner;
    
    public PowerFlowExperiment(String expSeqNum, JFrame owner){
        this.expSeqNum = expSeqNum;
        this.experimentID = "experiment-"+expSeqNum;
        this.owner = owner;
    }
    
//    public void 
    
    public void run(){
        double totCapacityChange = 0.5;
        double steps = runDuration-bootstrapTime/runTime;
        double relCapacityChangePerStep = Math.pow(totCapacityChange, 1/steps);
        
        Experiment.initEnvironment();
        final PowerFlowExperiment test = new PowerFlowExperiment(expSeqNum, null);
        test.init();
        final File folder = new File(peersLogDirectory+experimentID);
        clearExperimentFile(folder);
        folder.mkdir();
   
        PeerFactory peerFactory=new PeerFactory() {
            public Peer createPeer(int peerIndex, Experiment experiment) {
                Peer newPeer = new Peer(peerIndex);
//                if (peerIndex == 0) {
//                   newPeer.addPeerlet(null);
//                }
                newPeer.addPeerlet(new PowerCascadeAgent(
                        experimentID, 
                        Time.inMilliseconds(bootstrapTime),
                        Time.inMilliseconds(runTime),
                        relCapacityChangePerStep));
                return newPeer;
            }
        };
        
        test.initPeers(0,N,peerFactory);
        test.startPeers(0,N);
        //run the simulation
        ((PowerFlowCascadeGUI3)owner).notifyStatus(true);
        test.runSimulation(Time.inSeconds(runDuration));
        ((PowerFlowCascadeGUI3)owner).notifyStatus(false);
    }

    public final void clearExperimentFile(File experiment){
        File[] files = experiment.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    clearExperimentFile(f);
                } else {
                    f.delete();
                }
            }
        }
        experiment.delete();
    }
    
    
    public void clear(){
        
    }
    
    
    public static void main(String args[]){
        final PowerFlowExperiment exp = new PowerFlowExperiment("Case30RateReduction", null);
        exp.run();
        
    }
}
