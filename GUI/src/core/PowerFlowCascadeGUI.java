/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import actions.editMenu.*;
import actions.fileMenu.*;
import actions.windowMenu.*;
import actions.runMenu.*;
import actions.helpMenu.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author dinesh
 */
public class PowerFlowCascadeGUI extends javax.swing.JFrame {
    /**
     * Creates new form NewJFrame
     */
    public PowerFlowCascadeGUI(String expSeqNum) {
        this.expSeqNum = expSeqNum;
        this.experimentID = "experiment-"+expSeqNum;
        
        initComponents();
        
        debugOutput.setText("Experiments Output.");
        
    }

    private void initComponents() {
        
        treeScrollPane = new javax.swing.JScrollPane();
//        DefaultMutableTreeNode n = addNodes(null, new File(expLocation));
//        jTree1 = new javax.swing.JTree(n);
//        treeModel = (DefaultTreeModel)jTree1.getModel();
//        treeModel.reload(n);
//        jTree1.setModel(treeModel);
        
        // Load Flow Information:
        
        
        jTree1 = new javax.swing.JTree(addNodes(null,new File(expLocation)));
        jTree1.addMouseListener(new TreeMouseListener(jTree1));
        
        jPanel1 = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        debugScrollPane = new javax.swing.JScrollPane();
        debugOutput = new javax.swing.JTextArea();
        
        debugOutput.setForeground(debugColor);
        debugOutput.setEditable(false);
        
        menuBar = new javax.swing.JMenuBar();
        
        // Menus
        fileMenu = new javax.swing.JMenu("File");
        editMenu = new javax.swing.JMenu("Edit");
        experimentMenu = new javax.swing.JMenu("Experiments");
        windowMenu = new javax.swing.JMenu("Windows");
        helpMenu = new javax.swing.JMenu("Help");
        
        // File Menu Items
        preferencesMenuItem = new javax.swing.JMenuItem("Preferences");
        exitMenuItem = new javax.swing.JMenuItem("Exit");

        // Edit Menu Items
        createNetworkMenuItem = new javax.swing.JMenuItem("Create New Network");
        editNetworkMenuItem = new javax.swing.JMenuItem("Edit Current Network");
        editBackendParameterMenuItem = new javax.swing.JMenuItem("Edit Backend Parameters");
        editEventsMenuItem = new javax.swing.JMenuItem("Edit Events");
        editSfinaParametersMenuItem = new javax.swing.JMenuItem("Edit Sfina Parameters");

        // Experiment Menu Items
        runMenuItem = new javax.swing.JMenuItem("Run Experiment");
        cancelMenuItem = new javax.swing.JMenuItem("Stop Experiment");
        configurationsMenuItem = new javax.swing.JMenuItem("Experiment Configurations");

        // Window Menu Items
        showExperimentExplorerMenuItem = new javax.swing.JMenuItem("Experiments");
        showAnalyticsMenuItem = new javax.swing.JMenuItem("Analysis");
        showLogMenuItem = new javax.swing.JMenuItem("Log");

        // About Menu Items
        helpMenuItem = new javax.swing.JMenuItem("Help");
        aboutMenuItem = new javax.swing.JMenuItem("About");
        
        // Add menu items to corresponding Menus
        fileMenu.add(preferencesMenuItem);
        fileMenu.add(exitMenuItem);
        
        editMenu.add(createNetworkMenuItem);
        editMenu.add(editNetworkMenuItem);
        editMenu.add(editBackendParameterMenuItem);
        editMenu.add(editEventsMenuItem);
        editMenu.add(editSfinaParametersMenuItem);
        
        experimentMenu.add(runMenuItem);
        experimentMenu.add(cancelMenuItem);
        experimentMenu.add(configurationsMenuItem);
        
        windowMenu.add(showExperimentExplorerMenuItem);
        windowMenu.add(showAnalyticsMenuItem);
        windowMenu.add(showLogMenuItem);
        
        helpMenu.add(helpMenu);
        helpMenu.add(aboutMenuItem);
        
        
        experimentComboBox = new javax.swing.JComboBox(new DefaultComboBoxModel(getExperimentsList()));
        
        //runButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("./src/gui/playButton.png"))); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO run the experiment
                expSeqNum = (String)experimentComboBox.getSelectedItem();
                debugOutput.setText(debugOutput.getText()+"\nOption "+expSeqNum+" was selected.");
                PowerFlowExperiment exp = new PowerFlowExperiment(expSeqNum);
                exp.runExperiment();
            }
        });
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setResizable(false);

        treeScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Experiments"));
        treeScrollPane.setViewportView(jTree1);
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(experimentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(201, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(experimentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        debugScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Debug"));
        debugOutput.setToolTipText("");
        debugScrollPane.setViewportView(debugOutput);

        // Add Action Listeners
        
        // Edit Menu
        createNetworkMenuItem.addActionListener(new CreateNetworkMenuItemAction(this));
        editNetworkMenuItem.addActionListener(new EditNetworkMenuItemAction(this));
        editBackendParameterMenuItem.addActionListener(new EditBackendParametersMenuItemAction(this));
        editEventsMenuItem.addActionListener(new EditEventsMenuItemAction(this));
        editSfinaParametersMenuItem.addActionListener(new EditSfinaParametersMenuItemAction(this));

       // Experiment Menu Items
        runMenuItem.addActionListener(new RunMenuItemAction(this));
        cancelMenuItem.addActionListener(new CancelMenuItemAction(this));
        configurationsMenuItem.addActionListener(new ConfigurationsMenuItemAction(this));

       // Window Menu Items
        showExperimentExplorerMenuItem.addActionListener(new ShowExperimentExplorerMenuItemAction(this));
        showAnalyticsMenuItem.addActionListener(new ShowAnalyticsMenuItemAction(this));
        showLogMenuItem.addActionListener(new ShowLogMenuItemAction(this));

       // About Menu Items
        helpMenuItem.addActionListener(new HelpMenuItemAction(this));
        aboutMenuItem.addActionListener(new AboutMenuItemAction(this));

        
        // 
        
        
        editEventsMenuItem.setText("Edit Events");
        editEventsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = "experiments/experiment-"+(String)experimentComboBox.getSelectedItem()+"/peer-0/input/events.txt";
                // TODO ....................................
                TabularDataEditor panel = new TabularDataEditor(filePath, ",",true);
                panel.setPreferredSize(new Dimension(400,400));
                JOptionPane.showConfirmDialog(null, panel, "Events Editor", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                panel.writeData();
            }
        });
        
        editSfinaParametersMenuItem.setText("Edit Sfina Parameters");
        editSfinaParametersMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = "experiments/experiment-"+(String)experimentComboBox.getSelectedItem()+"/peer-0/input/sfinaParameters.txt";
                TabularDataEditor panel = new TabularDataEditor(filePath, "=", false);
                panel.setPreferredSize(new Dimension(400,400));
                JOptionPane.showConfirmDialog(null, panel, "Sfina Parameters Editor", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                panel.writeData();
            }
        });
        
        editBackendParameterMenuItem.setText("Edit Backend Parameters");
        editBackendParameterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = "experiments/experiment-"+(String)experimentComboBox.getSelectedItem()+"/peer-0/input/backendParameters.txt";
                TabularDataEditor panel = new TabularDataEditor(filePath, "=", false);
                panel.setPreferredSize(new Dimension(400,400));
                JOptionPane.showConfirmDialog(null, panel, "Backend Parameters Editor", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                panel.writeData();
            }
        });
        
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        
        editNetworkMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editNetworkMenuItemActionPerformed(evt);
            }
        });
        
        
        
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            }
        });
        
        
        // Add all menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(experimentMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(treeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(debugScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(debugScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(treeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(null);
    }
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt){
        System.exit(0);
    }

    private void editNetworkMenuItemActionPerformed(java.awt.event.ActionEvent evt){
        
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        // get File Location and Import to Current Location
        updateSfinaParameterLocation();
        updateBackendParameterLocation();
        updateEventLocation();
        updateNetworkLocation();
        copyFilesToLocalDirectory();
        // update folder tree;
    }                                                           
    
    private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode((new File(curPath)).getName());
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        
        Vector ol = new Vector();
        String[] tmp = dir.list();
        
        if(tmp.length<=0){
            // TODO null pointer exception has to be handled
            System.out.println("No Experiment Files Found. ");
            return curDir;
        }
        
        for (int i = 0; i < tmp.length; i++)
          ol.addElement(tmp[i]);
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        
        File f;
        Vector files = new Vector();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (int i = 0; i < ol.size(); i++) {
          String thisObject = (String) ol.elementAt(i);
          String newPath;
          if (curPath.equals("."))
            newPath = thisObject;
          else
            newPath = curPath + File.separator + thisObject;
          if ((f = new File(newPath)).isDirectory())
            addNodes(curDir, f);
          else
            files.addElement(thisObject);
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < files.size(); fnum++)
          curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        return curDir;
    }
    
    private void updateSfinaParameterLocation(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,0));
        JButton browseButton = new JButton("Browse");
        JTextArea pathTextPane = new JTextArea();
        panel.add(new JLabel("Select Sfina Parameters File:"));
        panel.add(new JPanel());
        panel.add(pathTextPane);
        panel.add(browseButton);
        panel.add(pathTextPane);
        panel.add(browseButton);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser fileChooser = new JFileChooser(new File("../../"));
                fileChooser.showOpenDialog(jPanel1);
                String path = fileChooser.getSelectedFile().getPath();
                pathTextPane.setText(path);
            }
        });
        JOptionPane.showConfirmDialog(null, panel, "Sfina Parameter", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        sfinaParameterLocation = pathTextPane.getText();
        debugOutput.setText(debugOutput.getText()+"\nFile selected: "+pathTextPane.getText());
        //System.out.println(sfinaParameterLocation);
    }
    
    private void updateBackendParameterLocation(){
        JPanel panel = new JPanel(new GridLayout(2,0));
        JButton browseButton = new JButton("Browse");
        JTextArea pathTextPane = new JTextArea();
        panel.add(new JLabel("Select Backend Parameters File:"));
        panel.add(new JPanel());
        panel.add(pathTextPane);
        panel.add(browseButton);
        panel.add(pathTextPane);
        panel.add(browseButton);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                try{
                    JFileChooser fileChooser = new JFileChooser(new File("../../"));
//                } catch{
//                    
//                }
                fileChooser.showOpenDialog(jPanel1);
                String path = fileChooser.getSelectedFile().getPath();
                pathTextPane.setText(path);
            }
        });
        
        JOptionPane.showConfirmDialog(null, panel, "Backend Parameter File Location", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        backendParameterLocation = pathTextPane.getText();
        debugOutput.setText(debugOutput.getText()+"\nFile selected: "+pathTextPane.getText());
    }
    
    private void updateEventLocation(){
        JPanel panel = new JPanel(new GridLayout(2,0));
        JButton browseButton = new JButton("Browse");
        JTextArea pathTextPane = new JTextArea();
        panel.add(new JLabel("Select Events File:"));
        panel.add(new JPanel());
        panel.add(pathTextPane);
        panel.add(browseButton);
        panel.add(pathTextPane);
        panel.add(browseButton);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser fileChooser = new JFileChooser(new File("../../"));
                fileChooser.showOpenDialog(jPanel1);
                String path = fileChooser.getSelectedFile().getPath();
                pathTextPane.setText(path);
            }
        });
        JOptionPane.showConfirmDialog(null, panel, "Event File Location", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        eventsLocation = pathTextPane.getText();
        debugOutput.setText(debugOutput.getText()+"\nFile selected: "+pathTextPane.getText());
        
    }
    
    private void updateNetworkLocation(){
        //HashMap<Integer, String> networkLocationMap = new HashMap<Integer, String>();
        String directoryPath = "";
        Integer time = 0;
        boolean moreNetworks = true;
        
        while(moreNetworks){
            JPanel panel = new JPanel(new GridLayout(0,2));
            JButton browseButton = new JButton("Browse");
            JTextArea pathTextPane = new JTextArea();
            JTextArea timeTextPane = new JTextArea();
            panel.add(new JLabel("Select Network Files Location:"));
            panel.add(new JPanel());
            panel.add(pathTextPane);
            panel.add(browseButton);
            panel.add(new JLabel("Select Time:"));
            panel.add(new JPanel());
            panel.add(timeTextPane);

            browseButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    JFileChooser fileChooser = new JFileChooser(new File("../../"));
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.showOpenDialog(jPanel1);
                    String path = fileChooser.getSelectedFile().getPath();
                    pathTextPane.setText(path);
                }
            });
            
            JOptionPane.showConfirmDialog(null, panel, "Network File Location", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
            directoryPath = pathTextPane.getText();
            time = Integer.parseInt(timeTextPane.getText());
            debugOutput.setText(debugOutput.getText()+"\nFile selected: "+pathTextPane.getText());
            networkLocations.put(time,directoryPath);
            moreNetworks = (JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(null, "Load network files location for more time values?","Message", JOptionPane.YES_NO_OPTION));
        }
    }

    private void copyFilesToLocalDirectory(){
        String currentFolder = expLocation+experimentID+"/peer-0/input/";
        // check if directory is present:
        // jTree1 = new javax.swing.JTree(addNodes(null,new File(expLocation)), false);
        
        File directory = new File(currentFolder);
        if (! directory.exists()){
            directory.mkdirs();
        }
        
        // copy sfina files
        try{
            Files.copy(Paths.get(sfinaParameterLocation),Paths.get(currentFolder+"/sfinaParameters.txt"));
        } catch(IOException ex){
            // TODO has to be edited
            System.out.println (ex.toString());
            System.out.println("Could not find file " + sfinaParameterLocation);
        }
        
        // copy events files
        try{
            Files.copy(Paths.get(eventsLocation),Paths.get(currentFolder+"/eventParameters.txt"));
        } catch(IOException ex){
            // TODO has to be edited
            System.out.println (ex.toString());
            System.out.println("Could not find file " + eventsLocation);
        }
        
        try{
            Files.copy(Paths.get(backendParameterLocation),Paths.get(currentFolder+"/backendParameters.txt"));
        } catch(IOException ex){
            // TODO has to be edited
            System.out.println (ex.toString());
            System.out.println("Could not find file " + backendParameterLocation);
        }
        
        // copy network files
        for(Map.Entry<Integer,String> entry:networkLocations.entrySet()){
            try{
                FileUtils.copyDirectory(new File(entry.getValue()), new File(currentFolder+"/time_"+entry.getKey()));
            } catch(IOException ex){
                // TODO has to be edited
                System.out.println (ex.toString());
                System.out.println("Could not find file " + entry.getValue());
            }
        }
        notifyExperimentsChange();

        
    }
    
    public String[] getExperimentsList(){
        File dir = new File(expLocation);
        String [] dirlist = dir.list();
        for (int i = 0; i<dirlist.length;i++){
            dirlist[i]=dirlist[i].substring(11);
        }
        return dirlist;
    }
    
    public void notifyExperimentsChange(){
        
        // Update jTree Values
        jTree1 = new JTree(addNodes(null, new File(expLocation)));
        treeScrollPane.setViewportView(jTree1);
        
        // Update comboBox Values
        experimentComboBox.setModel(new DefaultComboBoxModel(getExperimentsList()));
    }
    
    /////////////////// Getters and setters:
    public String getExperimentID(){
        return this.experimentID;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PowerFlowCascadeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PowerFlowCascadeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PowerFlowCascadeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PowerFlowCascadeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
               public void run() {
                   new PowerFlowCascadeGUI("Case200RandomExperiment").setVisible(true);
               }
           });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton runButton;
    
    private javax.swing.JMenuBar menuBar;
    
    // List of menus
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu experimentMenu;
    private javax.swing.JMenu windowMenu;
    
    // List of menu items
    // File Menu Items
    private javax.swing.JMenuItem preferencesMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    
    // Edit Menu Items
    private javax.swing.JMenuItem createNetworkMenuItem;
    private javax.swing.JMenuItem editNetworkMenuItem;
    private javax.swing.JMenuItem editBackendParameterMenuItem;
    private javax.swing.JMenuItem editEventsMenuItem;
    private javax.swing.JMenuItem editSfinaParametersMenuItem;
    
    // Experiment Menu Items
    private javax.swing.JMenuItem runMenuItem;
    private javax.swing.JMenuItem cancelMenuItem;
    private javax.swing.JMenuItem configurationsMenuItem;
    
    // Window Menu Items
    private javax.swing.JMenuItem showExperimentExplorerMenuItem;
    private javax.swing.JMenuItem showAnalyticsMenuItem;
    private javax.swing.JMenuItem showLogMenuItem;
    
    // About Menu Items
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JMenuItem aboutMenuItem;
    
    
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane treeScrollPane;
    private javax.swing.JScrollPane debugScrollPane;
    private javax.swing.JTextArea debugOutput;
    private javax.swing.JTree jTree1;
    private javax.swing.JComboBox experimentComboBox;
    private DefaultTreeModel treeModel;
    // End of variables declaration                   
    
    private String expLocation="experiments/";
    private String sfinaParameterLocation;
    private String backendParameterLocation;
    private String eventsLocation;
    private HashMap<Integer,String> networkLocations = new HashMap<Integer,String>();
    private String expSeqNum = null;
    private String peersLogDirectory = "peerlets-log/";
    private String experimentID = "experiment-"+expSeqNum;
    // simulation parameters
    private final static int bootstrapTime=2000;
    private final static int runTime=1000;
    private final static int runDuration=34;
    private final static int N=1;
    private final static Color debugColor = new Color(107,162,181);
}