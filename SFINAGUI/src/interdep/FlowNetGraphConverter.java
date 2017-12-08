/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interdep;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import network.FlowNetwork;
import network.InterdependentLink;
import network.Link;
import network.LinkInterface;
import network.Node;

/**
 *
 * @author dinesh
 */

public class FlowNetGraphConverter{
    
    public FlowNetGraphConverter(){
    
    }
    
    private int encodeNetworkID(String nodeId, int netId, int [] totalNets){
        int startId = 0;
        for(int i = 0; i < netId; i++){
            startId = totalNets[i] + startId;
        }
        return Integer.parseInt(nodeId)+startId;
    }
    
    public Graph<ExtendedNode, ExtendedLink> createJungGraph(Vector<FlowNetwork> fN){
        Graph<ExtendedNode, ExtendedLink> graph = new SparseMultigraph<ExtendedNode, ExtendedLink>();
        HashMap<Integer, ExtendedNode> nodes = new HashMap<Integer, ExtendedNode>();
        int[]totalNodes = new int[fN.size()];
        int totN = 0;
        for(int i = 0; i < fN.size(); i++){
            totalNodes[i]=((fN.get(i)).getNodes()).size();
            totN = totN + totalNodes[i];
        }

        for(int i = 0; i < fN.size(); i++){
            for(Node n : (fN.get(i)).getNodes()){
                nodes.put(encodeNetworkID(n.getIndex(), i, totalNodes), new ExtendedNode(n,i));
            }
        }
        for(int i=0; i<fN.size(); i++){
            for(LinkInterface n : (fN.get(i)).getLinksAll()){
                if(n.isInterdependent()){
                    int nodeIdStart = encodeNetworkID(n.getStartNode().getIndex(), ((InterdependentLink)n).getThisNetworkIndex(), totalNodes);
                    int nodeIdEnd = encodeNetworkID(n.getEndNode().getIndex(), ((InterdependentLink)n).getRemoteNetworkIndex(), totalNodes);
                    graph.addEdge(new ExtendedLink(n,i), nodes.get(nodeIdStart), nodes.get(nodeIdEnd));
                } else{
                    int nodeIdStart = encodeNetworkID(n.getStartNode().getIndex(), i, totalNodes);
                    int nodeIdEnd = encodeNetworkID(n.getEndNode().getIndex(), i, totalNodes);
                    graph.addEdge(new ExtendedLink(n,i), nodes.get(nodeIdStart), nodes.get(nodeIdEnd));                    
                }
            }
        }
        return graph;
    }
    
    public Vector<FlowNetwork> createFlowNet(Graph<ExtendedNode, ExtendedLink> jG){
        int totalNetworks = 0;
        for(ExtendedNode n : jG.getVertices()){
            totalNetworks = max(totalNetworks, (int)n.getNetwork());
        }
        Vector<FlowNetwork> fN = new Vector<FlowNetwork>(totalNetworks);
        for(int i = 0; i < fN.size(); i++){
            fN.add(i,new FlowNetwork());
        }
        
        for(ExtendedNode n : jG.getVertices()){
            (fN.get((int)n.getNetwork())).addNode(n.getNode());
        }
        for(ExtendedLink e : jG.getEdges()){
            (fN.get((int)e.getNetwork())).addLink(e.getLink());
        }
        return fN;
    }
}