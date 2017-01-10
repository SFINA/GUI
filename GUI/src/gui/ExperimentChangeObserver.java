/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dinesh
 */
public class ExperimentChangeObserver {
    
    private List<ExperimentChangeListener> listeners = new ArrayList<ExperimentChangeListener>();
    
    public ExperimentChangeObserver(){
        
    }
    
    public void registerListener(ExperimentChangeListener ecl){
        listeners.add(ecl);
    }
    
    public void notifyChange(){
        for(ExperimentChangeListener e : listeners){
            e.experimentChanged();
        }
    }
    
    // check if directory changes:
    
    
}
