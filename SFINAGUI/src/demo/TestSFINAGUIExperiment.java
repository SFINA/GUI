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
package demo;

import agents.simulation.SimulationAgent;
import agents.communication.TokenCommunicationAgent;
import agents.negotiators.PowerEventNegotiatorAgent;
import core.SFINAGUIExperiment;
import java.util.HashMap;
import org.apache.log4j.Logger;
import power.backend.InterpssFlowDomainAgent;
import protopeer.Experiment;
import protopeer.Peer;
import protopeer.PeerFactory;
import protopeer.SimulatedExperiment;
import protopeer.util.quantities.Time;

/**
 *
 * @author mcb, dinesh
 */
public class TestSFINAGUIExperiment extends SimulatedExperiment implements  SFINAGUIExperiment{
    
    private static final Logger logger = Logger.getLogger(TestSfinaExperiment.class);
    
   
    private static String expSeqNum="01";
    private static String experimentID="experiment-"+expSeqNum;
    private static String peersLogDirectory="peerlets-log/";
    //Simulation Parameters
    private  static int bootstrapTime=2000;
    private  static int runTime=1000;
    private  static int runDuration=6;
    private  static int N=3;
    
    public void run() {
        Experiment.initEnvironment();
        TestSfinaExperiment test = new TestSfinaExperiment();
        test.init();
        
        PeerFactory peerFactory=new PeerFactory() {
            public Peer createPeer(int peerIndex, Experiment experiment) {
                Peer newPeer = new Peer(peerIndex);
                newPeer.addPeerlet(new SimulationAgent(
                        experimentID));
                 //NECESSARY HELPER  AGENTS
                newPeer.addPeerlet(new TokenCommunicationAgent(Time.inMilliseconds(bootstrapTime),
                        Time.inMilliseconds(runTime),N,0));
                newPeer.addPeerlet(new InterpssFlowDomainAgent());
                newPeer.addPeerlet(new PowerEventNegotiatorAgent());
                return newPeer;
            }
        };
        test.initPeers(0,N,peerFactory);
        test.startPeers(0,N);
        
        //run the simulation
        test.runSimulation(Time.inSeconds(runDuration));
       
    }
    
    public void setExperimentConfigurations(HashMap<String, String> map){
        this.expSeqNum = map.get("expSeqNum");
        this.peersLogDirectory = map.get("peersLogDirectory");
        this.experimentID="experiment-"+this.expSeqNum;
        this.bootstrapTime = Integer.parseInt(map.get("bootstrapTime"));
        this.runDuration = Integer.parseInt(map.get("runDuration"));
        this.runTime = Integer.parseInt(map.get("runTime"));
        this.N = Integer.parseInt(map.get("N"));
    }
    
    public HashMap<String, String> getExperimentConfigurations(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("expSeqNum",this.expSeqNum);
        map.put("peersLogDirectory",this.peersLogDirectory);
        map.put("bootstrapTime",String.valueOf(this.bootstrapTime));
        map.put("runDuration",String.valueOf(this.runDuration));
        map.put("runTime",String.valueOf(this.runTime));
        map.put("N",String.valueOf(this.N));
        return map;
    }
    
    
    
}
