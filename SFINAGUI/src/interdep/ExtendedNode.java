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
public class ExtendedNode {
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
    public boolean equals(Object o){
        if (this==o){
            return true;
        }
        if(o instanceof ExtendedNode){
            ExtendedNode e = (ExtendedNode)o;
            return (no.equals(e.getNode()) && network.equals(e.getNetwork())) || (no==null && e.getNode()==null && network==null && e.getNetwork()==null);
        }
        return false;
    }
        
        
    }