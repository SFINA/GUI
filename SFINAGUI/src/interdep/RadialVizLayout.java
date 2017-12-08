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
public class RadialVizLayout extends InterdepVizLayout{
    
    public RadialVizLayout(int n, int Xsize, int Ysize){
        super(n, Xsize, Ysize);
    }
    
    public void computeGridElements(){

        super.gridElements = new Vector(n);
        for(int i = 0; i < n; i++){
            int x = (int)(((i+1.0)/(2.0*(1.0+n)))*Xsize);
            int y = (int)((i+1.0)/(2.0*(1.0+n))*Ysize);
            int height = (int)((n-i)/(n+1.0)*Ysize);
            int width = (int)((n-i)/(n+1.0)*Xsize);
            gridElements.add(new Rectangle(x, y, height, width));
        }
    }
    
    public static void main(String args[]){
        // Test Vizualization Layout
        RadialVizLayout lay = new RadialVizLayout(1,100,100);
        Vector<Rectangle> el = lay.getElements();
        System.out.println(el);
        // change number of elements
        lay.setElementCount(2);
        el = lay.getElements();
        System.out.println(el);
        // change dimension of panel
        lay.setElementCount(3);
        el = lay.getElements();
        System.out.print(el);
    }

    
}