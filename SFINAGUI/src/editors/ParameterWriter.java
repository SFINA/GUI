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
