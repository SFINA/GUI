/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfinanetworkgenerator;

import java.util.ArrayList;

/**
 *
 * @author dinesh
 */
public class SfinaLink{
    
    private ArrayList<String> values;
    private int id;
    
    public SfinaLink(int i){
        values=new ArrayList<String>();
        this.id = i;
    }
    
    public void setValues(ArrayList<String> values){
        this.values = values;
    }
    
    public ArrayList<String> getValues(){
        return values;
    }
    
    public int getID(){
        return id;
    }
    
    public void setID(int id){
        this.id = id;
    }

    public String toString(){
        return Integer.toString(id);
    }
    
}
