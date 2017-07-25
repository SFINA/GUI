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
package networkGenerator;

import java.util.ArrayList;

/**
 *
 * @author dinesh
 */
public class SfinaNode{
    
    private ArrayList<String> values;
    private int id;
    
    public SfinaNode(int i){
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
