/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interdep;

import dsutil.generic.state.State;
import network.Node;

/**
 *
 * @author dinesh
 */
public class ExtendedNode implements Comparable{
    private Node no;
    private Number network;
    public ExtendedNode(Node no, Number network){
        this.no = no;
        this.network = network;
    }
    public Node getNode(){
        return no;
    }

    public Number getNetwork(){
        return network;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(Integer.parseInt(this.getNode().getIndex()),Integer.parseInt(((ExtendedNode)o).getNode().getIndex()));
    }
        
}