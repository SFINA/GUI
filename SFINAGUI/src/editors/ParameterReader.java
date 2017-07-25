/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
