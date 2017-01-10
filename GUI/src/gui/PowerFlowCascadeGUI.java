/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import experiment.Experiment1;
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
        
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        
        jMenu2 = new javax.swing.JMenu();
        
        jMenu3 = new javax.swing.JMenu();
        experimentComboBox = new javax.swing.JComboBox(new DefaultComboBoxModel(getExperimentsList()));
        
        runButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/playButton.png"))); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO run the experiment
                expSeqNum = (String)experimentComboBox.getSelectedItem();
                debugOutput.setText(debugOutput.getText()+"\nOption "+expSeqNum+" was selected.");
                Experiment1 exp = new Experiment1(expSeqNum);
                
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
        
        
        
        //debugScrollPane.setViewportView(fe);
        
        jMenu1.setText("File");

        jMenuItem1.setText("Import Experiment");
        jMenu1.add(jMenuItem1);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        
        jMenuItem4.setText("Edit Events");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = "experiments/experiment-"+(String)experimentComboBox.getSelectedItem()+"/peer-0/input/events.txt";
                // TODO ....................................
                TabularDataEditor panel = new TabularDataEditor(filePath, ",",true);
                panel.setPreferredSize(new Dimension(400,400));
                JOptionPane.showConfirmDialog(null, panel, "Events Editor", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                panel.writeData();
            }
        });
        
        jMenuItem5.setText("Edit Sfina Parameters");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = "experiments/experiment-"+(String)experimentComboBox.getSelectedItem()+"/peer-0/input/sfinaParameters.txt";
                TabularDataEditor panel = new TabularDataEditor(filePath, "=", false);
                panel.setPreferredSize(new Dimension(400,400));
                JOptionPane.showConfirmDialog(null, panel, "Sfina Parameters Editor", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                panel.writeData();
            }
        });
        
        jMenuItem6.setText("Edit Backend Parameters");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = "experiments/experiment-"+(String)experimentComboBox.getSelectedItem()+"/peer-0/input/backendParameters.txt";
                TabularDataEditor panel = new TabularDataEditor(filePath, "=", false);
                panel.setPreferredSize(new Dimension(400,400));
                JOptionPane.showConfirmDialog(null, panel, "Backend Parameters Editor", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                panel.writeData();
            }
        });
        
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        
        jMenu1.add(jMenuItem4);
        jMenu1.add(jMenuItem2);
        
        jMenuBar1.add(jMenu1);
        
        jMenuItem3.setText("About");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            }
        });
        
        jMenu3.add(jMenuItem3);
        jMenu3.setText("Help");
        jMenuBar1.add(jMenu3);
        
        setJMenuBar(jMenuBar1);

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
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>

        /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
               public void run() {
                   new PowerFlowCascadeGUI("Case200RandomExperiment").setVisible(true);
               }
           });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton runButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
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