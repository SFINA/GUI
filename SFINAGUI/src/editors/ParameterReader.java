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
package editors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author dinesh
 */
public class ParameterReader {
    
    String columnSeparator;
    
    public ParameterReader(String columnSeparator){
        this.columnSeparator=columnSeparator;
    }
    
    public HashMap<String,String> readParameters(String path){
        HashMap<String, String> parameters = new HashMap<String, String>();
        File file = new File(path);
        Scanner scr=null;
        try{
            scr = new Scanner(file);
            while(scr.hasNext()){
                StringTokenizer st = new StringTokenizer(scr.next(),columnSeparator);
                String key = st.nextToken();
                String value = st.nextToken();
                parameters.put(key,value);
            }
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        return parameters;
    }
    
}
