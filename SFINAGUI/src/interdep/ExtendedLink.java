/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interdep;

import network.Link;
import network.LinkInterface;

/**
 *
 * @author dinesh
 */
    public class ExtendedLink{
        private LinkInterface l;
        private Number network;
        public ExtendedLink(LinkInterface l, Number network){
            this.l = l;
            this.network = network;
        }
        public LinkInterface getLink(){
            return l;
        }
        
        public Number getNetwork(){
            return network;
        }
        @Override
        public boolean equals(Object o){
            if (this==o){
                return true;
            }
            if(o instanceof ExtendedLink){
                ExtendedLink e = (ExtendedLink)o;
                return (l.equals(e.getLink()) && network.equals(e.getNetwork())) || (l==null && e.getLink()==null && network==null && e.getNetwork()==null);
            }
            return false;
        }
        @Override
        public String toString(){
            return l.getIndex();
        }
    }    

