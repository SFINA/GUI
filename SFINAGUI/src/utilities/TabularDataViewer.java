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
 
package utilities;
 
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JButton;
 

public class TabularDataViewer extends JPanel {
    private String eventLocation;
    private String[] columnNames;
    private String[][] body;
    private String columnSeparator=",";
    private JTable table;
    private boolean header;
    
    public TabularDataViewer(String location, String columnSeparator, boolean header) {
        
        super(new GridLayout(1,0));
        
        this.eventLocation = location;
        this.columnSeparator = columnSeparator;
        this.header = header;
        
        updateTableData();
        
        table = new JTable(body, columnNames){
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        if(!this.header){
            table.setTableHeader(null);
        }
        
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        this.add(scrollPane, BorderLayout.CENTER);
        
    }
    
    public void updateTableData(){
        List<String> columnNamesList = new ArrayList<String>();
        List<List<String>> bodyList = new ArrayList<List<String>>();
        
        File file = new File(eventLocation);
        Scanner scr = null;
        try {
            scr = new Scanner(file);
            if(scr.hasNext()){
                StringTokenizer st = new StringTokenizer(scr.next(), columnSeparator);
                while(st.hasMoreTokens()){
                    columnNamesList.add(st.nextToken());
                }
            }
            while(scr.hasNext()){
                ArrayList<String> values = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(scr.next(), columnSeparator);
                List<String> row = new ArrayList<String>();
                while (st.hasMoreTokens()) {
                    row.add(st.nextToken());
		}
                bodyList.add(row);
            }
            
            columnNames = new String[columnNamesList.size()];
            columnNamesList.toArray(columnNames);
            body = new String[bodyList.size()][bodyList.get(0).size()];
            for(int i = 0; i < bodyList.size(); i++){
                (bodyList.get(i)).toArray(body[i]);
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
    } 
}