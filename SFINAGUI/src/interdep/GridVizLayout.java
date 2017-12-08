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
public class GridVizLayout extends InterdepVizLayout{
    
    public GridVizLayout(int n, int Xsize, int Ysize){
        super(n, Xsize, Ysize);
    }
    
    public void computeGridElements(){
        Vector<Integer> gridsPerRow;
        int cols = (int)Math.ceil(Math.sqrt(n));
        int rows = n/cols;
        int finalAdditionalRow = n%cols;
        gridsPerRow = new Vector(rows+(int)Math.ceil(finalAdditionalRow/cols));
        for(int i = 0; i < rows; i++){
            gridsPerRow.add(cols);
        }
        if(finalAdditionalRow>0){
            gridsPerRow.add(finalAdditionalRow);            
        }
        //System.out.println(gridsPerRow);
        gridElements = new Vector(n);
        for(int i = 0; i < gridsPerRow.size()-1; i++){
            for(int j = 0; j < gridsPerRow.get(i); j++){
                int x = j * Xsize/gridsPerRow.get(i);
                int y = i * Ysize/gridsPerRow.size();
                int height = this.Ysize/gridsPerRow.size();
                int width = Xsize/gridsPerRow.get(i);
                (gridElements).add(new Rectangle(x, y, width, height));
            }
        }
        //final row elements
        int i = gridsPerRow.size()-1;
        for(int j = 0; j < gridsPerRow.get(i); j++){
            int x = j * Xsize/gridsPerRow.get(i);
            int y = i * Ysize/gridsPerRow.size();
            (gridElements).add(new Rectangle(x, y, (gridElements).get(0).width, (gridElements).get(0).height));
        }
        
    }
    
    public static void main(String args[]){
        // Test Vizualization Layout
        GridVizLayout lay = new GridVizLayout(1,100,100);
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
