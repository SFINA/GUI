/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import org.apache.log4j.Logger;


/**
 *
 * @author dinesh
 */
public class ParameterWriter {
    private static final Logger logger = Logger.getLogger(ParameterWriter.class);
    String columnSeparator;
    public ParameterWriter(String columnSeparator){
        this.columnSeparator = columnSeparator;
    };
    
    public void write(HashMap<String, String> map, String path){
        try{
            File file = new File(path);
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs()){
                logger.debug("Couldn't create output folder");
            }
            
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(file,false));
            
            for(String s:map.keySet()){
                writer.print(s+columnSeparator+map.get(s));
                writer.print("\n");
            }
            writer.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
}
