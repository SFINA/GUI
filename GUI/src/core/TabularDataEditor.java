/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package core;
 
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
 

public class TabularDataEditor extends JPanel {
    private boolean DEBUG = true;
    private String eventLocation;
    private String[] columnNames;
    private String[][] body;
    private String columnSeparator=",";
    private JTable table;
    private boolean header;
    
    public TabularDataEditor(String location, String columnSeparator, boolean header) {
        
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
        //table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        //Add the scroll pane to this panel.
        //add(scrollPane);
        //JPanel body = new JPanel();
        //body.setLayout(new BorderLayout());
        
        this.add(scrollPane, BorderLayout.CENTER);
        
//        JButton jButton = new JButton("Add Row");
//        body.add(jButton, BorderLayout.SOUTH);
        //add(body);
        
    }
    
    // load body and column names from file
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
 
 
    public void writeData() {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();
        try{
            File file = new File(eventLocation);
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(file,false));
            
            // print header
            for (int j=0; j < numCols-1; j++) {
                writer.print(model.getColumnName(j)+",");
                System.out.print(model.getColumnName(j)+",");
            }
            writer.print(model.getColumnName(numCols-1));
            System.out.print(model.getColumnName(numCols-1));
            writer.println();
            System.out.println();
            
            // print body
            for (int i=0; i < numRows; i++) {
                for (int j=0; j < numCols-1; j++) {
                    writer.print(model.getValueAt(i, j)+",");
                }
                writer.print(model.getValueAt(i, numCols-1));
                writer.println();
            }
            writer.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
 
}