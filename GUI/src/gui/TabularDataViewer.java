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
 
package gui;
 
/*
 * SimpleTableDemo.java requires no other files.
 */
 
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
 

public class TabularDataViewer extends JPanel {
    private boolean DEBUG = true;
    private String eventLocation;
    private String[] columnNames;
    private String[][] body;
    private String columnSeparator=",";
    private JTable table;
    private boolean header = false;
    
    public TabularDataViewer(String location, String columnSeparator, boolean header) {
        super(new GridLayout(1,0));
        this.eventLocation = location;
        this.columnSeparator = columnSeparator;
        this.header = header;
        
        loadTableData();
        table = new JTable(body, columnNames);
        // make cells uneditable
        /*
        DefaultTableModel model = new DefaultTableModel(body, columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table.setModel(model);
        */
        table.setEnabled(false);

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        
        if(!header){
            table.setTableHeader(null);
        }
    }
    
    public void loadTableData(){
        List<String> columnNamesList = new ArrayList<String>();
        List<List<String>> bodyList = new ArrayList<List<String>>();
        
        File file = new File(eventLocation);
        Scanner scr = null;
        try {
            scr = new Scanner(file);
            if(header){
                if(scr.hasNext()){
                    StringTokenizer st = new StringTokenizer(scr.next(), columnSeparator);
                    while(st.hasMoreTokens()){
                        columnNamesList.add(st.nextToken());
                    }
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
            //System.out.println(columnNames);
            body = new String[bodyList.size()][bodyList.get(0).size()];
            for(int i = 0; i < bodyList.size(); i++){
                (bodyList.get(i)).toArray(body[i]);
            }
            //System.out.println(body);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
 
}