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
package core;

import internalFrames.StatusBarSwingWorker;
import internalFrames.StatusBar;
import actions.editMenu.CreateNetworkMenuItemAction;
import actions.editMenu.EditBackendParametersMenuItemAction;
import actions.editMenu.AddEventsMenuItemAction;
import actions.editMenu.EditNetworkMenuItemAction;
import actions.editMenu.VizualizeNetworkMenuItemAction;

import actions.fileMenu.ExitMenuItemAction;
import actions.fileMenu.FileSystemConfigurationMenuItemAction;
import actions.helpMenu.AboutMenuItemAction;
import actions.helpMenu.HelpMenuItemAction;
import actions.runMenu.ConfigurationsMenuItemAction;
import actions.runMenu.RunMenuItemAction;
import junk.ShowAnalyticsMenuItemAction;
import junk.ShowExperimentExplorerMenuItemAction;
import junk.ShowLogMenuItemAction;
import junk.Analytics;
import internalFrames.LogViewer;
import internalFrames.ExperimentExplorer;
import internalFrames.NetworkEditor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import protopeer.Peer;
import protopeer.SimulatedExperiment;

/**
 *
 * @author dinesh
 */
public class SFINAGUI extends javax.swing.JFrame {
    
    private SFINAGUIExperiment exp;
    private String expSeqNum = "01";
    private boolean interDep = true;
    
    // variables                 
    private HashMap<String, String> configurations = new HashMap<String, String>();
    
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem configurationsMenuItem;
    private javax.swing.JMenuItem createNetworkMenuItem;
    private javax.swing.JMenuItem editBackendParametersMenuItem;
    private javax.swing.JMenuItem editEventsMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editNetworkMenuItem;
    private javax.swing.JMenuItem vizualizeNetworkMenuItem;
    
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu experimentMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JDesktopPane mainDesktopPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem fileSystemConfigurationMenuItem;
    private javax.swing.JMenuItem runMenuItem;
    
    private javax.swing.JInternalFrame analytics;
    private javax.swing.JInternalFrame debug;
    private javax.swing.JInternalFrame experimentExplorer;
    private javax.swing.JInternalFrame networkEditor;
            
    private Boolean  analyticsFrameVisible;
    private Boolean debugFrameVisible;
    private Boolean experimentExplorerFrameVisible;
    private Boolean networkEditorFrameVisible;
    
    private Boolean experimentRunning;
    
    private StatusBar label;
    
    private StatusBarSwingWorker sbw;
    private javax.swing.JDesktopPane explorerDesktopPane;
    private javax.swing.JDesktopPane analyticsDesktopPane;
    private javax.swing.JDesktopPane debugDesktopPane;
    private javax.swing.JDesktopPane statusPane;

    /*
    public SFINAGUI() {
        this.interDep = true;
        setTitle(this.getClass().getSimpleName());
        initComponents();
    }
    
    public SFINAGUI(SFINAGUIExperiment experiment){
        this.exp = experiment;
        this.interDep = false;
        setTitle(this.getClass().getSimpleName());
        initComponents();
    }
    */
    
    public SFINAGUI(SFINAGUIExperiment experiment, boolean interDep){
        this.exp = experiment;
        this.interDep = interDep;
        setTitle(this.getClass().getSimpleName());
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {
        setTitle("Sfina Gui");
        explorerDesktopPane = new javax.swing.JDesktopPane();
        analyticsDesktopPane = new javax.swing.JDesktopPane();
        debugDesktopPane = new javax.swing.JDesktopPane();
        statusPane = new javax.swing.JDesktopPane();
        
        mainDesktopPane = new javax.swing.JDesktopPane();
        setPreferredSize(new Dimension(1200,800));
        mainDesktopPane.setPreferredSize(new Dimension(1200,800));
        menuBar = new javax.swing.JMenuBar();
        
        fileMenu = new javax.swing.JMenu("File");
        fileSystemConfigurationMenuItem = new javax.swing.JMenuItem("File System Configuration");
        exitMenuItem = new javax.swing.JMenuItem("Exit");
        editMenu = new javax.swing.JMenu("Edit");
        createNetworkMenuItem = new javax.swing.JMenuItem("Create Network");
        editNetworkMenuItem = new javax.swing.JMenuItem("Edit Network");
        editEventsMenuItem = new javax.swing.JMenuItem("Add Events");
        vizualizeNetworkMenuItem = new javax.swing.JMenuItem("Vizualize Network");
        editBackendParametersMenuItem = new javax.swing.JMenuItem("Edit Backend Parameters");
        experimentMenu = new javax.swing.JMenu("Experiment");
        runMenuItem = new javax.swing.JMenuItem("Run");
        configurationsMenuItem = new javax.swing.JMenuItem("Configurations");
        helpMenu = new javax.swing.JMenu("Help");
        helpMenuItem = new javax.swing.JMenuItem("SFINA Manual");
        aboutMenuItem = new javax.swing.JMenuItem("About SFINA");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // Add Action Listeners
        // File Menu
        fileSystemConfigurationMenuItem.addActionListener(new FileSystemConfigurationMenuItemAction(this));
        exitMenuItem.addActionListener(new ExitMenuItemAction(this));
        
        // Edit Menu
        createNetworkMenuItem.addActionListener(new CreateNetworkMenuItemAction(this));
        editNetworkMenuItem.addActionListener(new EditNetworkMenuItemAction(this));
        editBackendParametersMenuItem.addActionListener(new EditBackendParametersMenuItemAction(this));
        editEventsMenuItem.addActionListener(new AddEventsMenuItemAction(this));
        vizualizeNetworkMenuItem.addActionListener(new VizualizeNetworkMenuItemAction(this));

       // Experiment Menu Items
        runMenuItem.addActionListener(new RunMenuItemAction(this));
        //cancelMenuItem.addActionListener(new CancelMenuItemAction(this));
        configurationsMenuItem.addActionListener(new ConfigurationsMenuItemAction(this));

       // About Menu Items
        helpMenuItem.addActionListener(new HelpMenuItemAction(this));
        aboutMenuItem.addActionListener(new AboutMenuItemAction(this));

        fileMenu.add(fileSystemConfigurationMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        if(interDep){
            editMenu.add(vizualizeNetworkMenuItem);
        }
        editMenu.add(createNetworkMenuItem);
        editMenu.add(editNetworkMenuItem);
        editMenu.add(editEventsMenuItem);
        
        editMenu.add(editBackendParametersMenuItem);
        menuBar.add(editMenu);

        experimentMenu.add(runMenuItem);
        //experimentMenu.add(cancelMenuItem);
        experimentMenu.add(configurationsMenuItem);
        menuBar.add(experimentMenu);

        helpMenu.add(helpMenuItem);
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(explorerDesktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(debugDesktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addComponent(mainDesktopPane)))
            .addComponent(statusPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mainDesktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(debugDesktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(explorerDesktopPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPane, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        
        label = new StatusBar(this.getExperimentConfigurations().get("expSeqNum"));
        statusPane.setLayout(new BorderLayout());
        statusPane.add(label, BorderLayout.CENTER);
        
        pack();
        initInternalFrames();
        sbw=new StatusBarSwingWorker(label);
    
    }
    
    
    private void initInternalFrames(){
        
        initDebugFrame();
        
        initAnalyticsFrame();
        
        initExperimentExplorer();
        
        initNetworkEditor();
        
    };
    
    
    public void initDebugFrame(){
        debug= new LogViewer(this);
        debugDesktopPane.add(debug,BorderLayout.CENTER);
        debugFrameVisible = true;
        debug.setVisible(debugFrameVisible);
        try {
            debug.setMaximum(true);
        } catch (PropertyVetoException e) {
            // DO SOMETHING
        }
    } 
    
    public void initAnalyticsFrame(){
        
        analytics= new Analytics(this);
        analyticsDesktopPane.add(analytics);
        analyticsFrameVisible = true;
        analytics.setVisible(analyticsFrameVisible);
        
        
        try {
            analytics.setMaximum(true);
        } catch (PropertyVetoException e) {
            // DO SOMETHING
        }
        
        
    }
    
    public void resetExperimentExplorer(){
        initExperimentExplorer();
    }
    
    public void initExperimentExplorer(){
        
        experimentExplorer= new ExperimentExplorer(this);
        //this.getDesktop().add(experimentExplorer);
        explorerDesktopPane.add(experimentExplorer);
        experimentExplorerFrameVisible = true;
        experimentExplorer.setVisible(experimentExplorerFrameVisible);
        try {
            experimentExplorer.setMaximum(true);
        } catch (PropertyVetoException e) {
            // DO SOMETHING
        }
    }
    
    public void initNetworkEditor(){
        
        networkEditor= new NetworkEditor(this);
        analyticsDesktopPane.add(networkEditor);
        networkEditorFrameVisible = true;
        networkEditor.setVisible(networkEditorFrameVisible);
    }
    
    public boolean isInterDep(){
        return interDep;
    }
    /*
    public SFINAGUIExperiment getExperiment(){
        return exp;
    }
    
    public String getExpSeqNum(){
        return expSeqNum;
    }
    
    public void setExpSeqNum(String seqNum){
         expSeqNum = seqNum;
         configurations.put("expSeqNum",seqNum);
    }
    */
    public StatusBarSwingWorker getStatusBarSwingWorker(){
        return sbw;
    }
    
    public void setDebugFrameVisible(Boolean bool){
        debug.setVisible(bool);
    }
    
    public void setExperimentExplorerFrameVisible(Boolean bool){
        experimentExplorer.setVisible(bool);
    }

    public void setNetworkEditorVisible(Boolean bool){
        networkEditor.setVisible(bool);
    }

    
    public void setAnalyticFrameVisible(Boolean bool){
        analytics.setVisible(bool);
    }

    
    public JInternalFrame getDebugFrame(){
        return debug;
    }
    
    
    public JInternalFrame getExperimentExplorer(){
        return experimentExplorer;
    }

    
    public JInternalFrame getNetworkEditor(){
        return networkEditor;
    }

    
    public JInternalFrame getAnalyticFrame(){
        return analytics;
    }

    
    public javax.swing.JDesktopPane getDesktop(){
        return mainDesktopPane;
    }

    public void notifyStatus(Boolean bool){ // true means running and false means not running
        experimentRunning = bool;
        label.setStatusRunning(bool);
    }
    
    public void runExperiment(){
        notifyStatus(true);
        exp.run();
        notifyStatus(false);
    }
    
    public void setProgress(Integer i){
        if(sbw==null||!sbw.isCancelled())
        sbw.cancel(true);
        label.setValue(i);
    }
    
    public HashMap<String, String> getExperimentConfigurations(){
        return this.exp.getExperimentConfigurations();
    }
    
    public void setExperimentConfigurations(HashMap<String, String> map) {
        this.label.setCurrentExperimentName(map.get("expSeqNum"));
        this.exp.setExperimentConfigurations(map);
    } 
    
    
}
