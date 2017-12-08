/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interdep;

import java.awt.Rectangle;
import java.util.Vector;

/**
 *
 * @author dinesh
 */
public abstract class InterdepVizLayout {
    protected int n;  // number of total elements
    protected int Xsize;  // length of visualization panel
    protected int Ysize;  // height of visualization panel
    protected Vector<Rectangle> gridElements; // data structures that stores start, end and 
    
    public InterdepVizLayout(int n, int Xsize, int Ysize){
        this.n=n;
        this.Xsize = Xsize;
        this.Ysize = Ysize;
        computeGridElements();
    }
    
    public void setElementCount(int n){
        this.n = n;
        computeGridElements();
    }
    
    public void setLayoutDimension(int Xsize, int Ysize){
        this.Xsize = Xsize;
        this.Ysize = Ysize;
        computeGridElements();
    }
    
    public Vector<Rectangle> getElements(){
        return gridElements;
    }
    
    abstract void computeGridElements();
}
