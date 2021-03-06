/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
package sfinanetworkgenerator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotationControls;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import java.lang.Integer;
import javax.swing.JScrollPane;
/**
 * Shows how  to create a graph editor with JUNG.
 * Mouse modes and actions are explained in the help text.
 The application version of NetworkGenerator provides a
 File menu with an option to save the visible graph as
 a jpeg file.
 * 
 * @author Tom Nelson
 * 
 */
public class NetworkGenerator extends JApplet{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2023243689258876709L;
        private static ArrayList<String> nodeFields = new ArrayList<String>();
        private static ArrayList<String> linkFields = new ArrayList<String>();
        private static String path = "TopologyFiles/input/";
        private static final Logger logger = Logger.getLogger(SfinaNetworkGenerator.class);
	Factory<SfinaNode> vertexFactory;
        Factory<SfinaLink> edgeFactory;
        
        /**
     * the graph
     */
    Graph<SfinaNode,SfinaLink> graph;
    
    AbstractLayout<SfinaNode,SfinaLink> layout;

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<SfinaNode,SfinaLink> vv;
    
    
    /**
     * create an instance of a simple graph with popup controls to
     * create a graph.
     * 
     */
    public NetworkGenerator() {
        
        // create a simple graph for the demo
        graph = new DirectedSparseMultigraph<SfinaNode,SfinaLink>();

        this.layout = new FRLayout<SfinaNode,SfinaLink>(graph, 
        	new Dimension(600,600));
        
        vv =  new VisualizationViewer<SfinaNode,SfinaLink>(layout);
        vv.setBackground(Color.white);

        vv.getRenderContext().setVertexLabelTransformer(MapTransformer.<SfinaNode,String>getInstance(
        		LazyMap.<SfinaNode,String>decorate(new HashMap<SfinaNode,String>(), new ToStringLabeller<SfinaNode>())));
        
        vv.getRenderContext().setEdgeLabelTransformer(MapTransformer.<SfinaLink,String>getInstance(
        		LazyMap.<SfinaLink,String>decorate(new HashMap<SfinaLink,String>(), new ToStringLabeller<SfinaLink>())));

        vv.setVertexToolTipTransformer(vv.getRenderContext().getVertexLabelTransformer());

        Container content = getContentPane();
        //final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);
        vertexFactory = new VertexFactory();
        edgeFactory = new EdgeFactory();
        
        final SfinaModalGraphMouse<SfinaNode,SfinaLink> graphMouse = 
        	new SfinaModalGraphMouse<SfinaNode,SfinaLink>(vv.getRenderContext(), vertexFactory, edgeFactory);
        
        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());
        
        graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
        
        final ScalingControl scaler = new CrossoverScalingControl();
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });

        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });

        JButton export = new JButton("Export Network");
        export.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
                
                JFileChooser chooser = new JFileChooser(); 
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Select Output Location");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
                    path=  chooser.getSelectedFile().getAbsolutePath();
                    
                    writeLinkFlow(path);
                    writeNodeFlow(path);
                    writeLinkTopology(path);
                    writeNodeTopology(path);
                }
                
           } 
        });
        
//        JButton loadNet = new JButton("Load Network");
//        loadNet.addActionListener(new ActionListener(){
//            // TODO, check if the location is invalid
//            public void actionPerformed(ActionEvent e){
//                JFileChooser chooser = new JFileChooser(); 
//                chooser.setCurrentDirectory(new java.io.File("."));
//                chooser.setDialogTitle("Select Network Location");
//                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//                chooser.setAcceptAllFileFilterUsed(false);
//                
//                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
//                    path=  chooser.getSelectedFile().getAbsolutePath();
//                    //loadLinkFlow(path);
//                    loadNodeFlow(path);
//                    loadLinkTopology(path);
//                    loadNodeTopology(path);
//                }
//            }
//        });
        

        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(modeBox);
//        controls.add(loadNet);
        controls.add(export);
        content.add(controls, BorderLayout.SOUTH);
    }
            
    class VertexFactory implements Factory<SfinaNode> {
    	int i=0;
		public SfinaNode create() {
                        i++;
                        SfinaNode l = new SfinaNode(i);
                        if(nodeFields.size()>0){
                            ValuesDialog v = new ValuesDialog(nodeFields);
                            
                            JScrollPane jsp = new JScrollPane(v,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                            jsp.setPreferredSize(new Dimension(320,300));
                            JOptionPane.showConfirmDialog(null, jsp, "Enter Node Properties: ", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                            l.setValues(v.getValues());
                        }
			return l;
		}
                
                public void increaseIndex(){
                    i++;
                };
    }
    
    class EdgeFactory implements Factory<SfinaLink> {
    	int i=0;
		public SfinaLink create() {
                        i++;
                        SfinaLink l = new SfinaLink(i);
                        
                        if(linkFields.size()>0){
                            
                            ValuesDialog v = new ValuesDialog(linkFields);
                            JScrollPane jsp = new JScrollPane(v,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                            jsp.setPreferredSize(new Dimension(320,300));
                            JOptionPane.showConfirmDialog(null, jsp, "Enter Link Properties", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
                            l.setValues(v.getValues());
                        }
                        return l;
		}
                
                public void increaseIndex(){
                    i++;
                };
    }
    
    //======================================================================================        
    //===================================== Exporters ======================================        
    //======================================================================================        
    public void writeNodeFlow(String path){
        String location = path+"/flow/Node.txt";
        String columnSeparator = ",";
        try{
            File file = new File(location);
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs())
                logger.debug("Couldn't create output folder");
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(file,false));
            
            // print header
            if(nodeFields.size()>0){
                writer.print("id");
                for(int i = 0;i<nodeFields.size();i++){
                    writer.print(columnSeparator+nodeFields.get(i));
                }
                writer.print("\n");

                for (SfinaNode node : graph.getVertices()){
                    writer.print(Integer.toString(node.getID()));
                    ArrayList<String> v = node.getValues();
                    for(int i = 0; i<v.size(); i++){
                        String s = ((v.get(i)).equals(""))?"-":v.get(i);
                        writer.print(columnSeparator+s);
                    }
                    writer.print("\n");
                }
            }
            
            writer.close();
            
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void writeLinkFlow(String path){
        String location = path+"/flow/Link.txt";
        String columnSeparator = ",";
        try{
            File file = new File(location);
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs())
                logger.debug("Couldn't create output folder");
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(file,false));
            
            // print header
            if(linkFields.size()>0){
                writer.print("id");
                for(int i = 0;i<linkFields.size();i++){
                    writer.print(columnSeparator+linkFields.get(i));
                }
                writer.print("\n");

                for (SfinaLink link : graph.getEdges()){
                    writer.print(Integer.toString(link.getID()));
                    ArrayList<String> v = link.getValues();
                    for(int i = 0; i<v.size(); i++){
                        String s = (v.get(i).equals(""))?"-":v.get(i);
                        writer.print(columnSeparator+s);
                    }
                    writer.print("\n");
                }
            }
            
            writer.close();
            
        }
        catch(IOException ex){
            ex.printStackTrace();
        }        
    }
    
    public void writeNodeTopology(String path){
        String location = path+"/topology/nodes.txt";
        String columnSeparator = ",";
        try{
            File file = new File(location);
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs())
                logger.debug("Couldn't create output folder");
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(file,false));
            writer.println("id" + columnSeparator + "status");
            for (SfinaNode node : graph.getVertices()){
                String status = "1";
                writer.println(node.getID() + columnSeparator + status);
            }
            writer.close();
            
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void writeLinkTopology(String path){
        String location = path+"/topology/links.txt";
        String columnSeparator = ",";
        try{
            File file = new File(location);
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs())
                logger.debug("Couldn't create output folder");
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(file,false));
            writer.println("id" + columnSeparator+"from_node_id"+columnSeparator+"to_node_id" +columnSeparator+ "status");
            for (SfinaLink link : graph.getEdges()){
                String status = "1";
                String from_node_id = Integer.toString(((SfinaNode)graph.getSource(link)).getID());
                String to_node_id = Integer.toString(((SfinaNode)graph.getDest(link)).getID());
                writer.println(link.getID() + columnSeparator + from_node_id+columnSeparator+to_node_id +columnSeparator+ status);
            }
            writer.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    //====================================================================================
    //===================================== Loaders ======================================
    //====================================================================================
    public HashMap<Integer,Pair<Integer>> loadLinkTopology(String path){
        String location = path+"/topology/links.txt";
        String columnSeparator = ",";
        
        HashMap<Integer, Pair<Integer>> map = new HashMap<Integer,Pair<Integer>>();
        
        File file = new File(location);
        Scanner scr = null;
        try {
            scr = new Scanner(file);
            // disregard the header
            scr.next();
            
            while(scr.hasNext()){
                ArrayList<String> values = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(scr.next(), columnSeparator);
                // get id and construct the Link
                int id = Integer.parseInt(st.nextToken());
                int src = Integer.parseInt(st.nextToken());
                int dest = Integer.parseInt(st.nextToken());
                // firs values is linkID and then rest are source and destination
                map.put(id, new Pair(src,dest));
                // disregard the status
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return map;
    }
    
//    public void loadNodeTopology(String path){
//    }
    
    public void loadLinkFlow(String path, HashMap<Integer, Pair<Integer>> map){
        // First Load headings and set Fields
        //System.out.print(map);
        String location = path+"/flow/links.txt";
        String columnSeparator = ",";
        linkFields = new ArrayList<String>();
        
        File file = new File(location);
        Scanner scr = null;
        try {
            scr = new Scanner(file);
            if(scr.hasNext()){
                StringTokenizer st = new StringTokenizer(scr.next(), columnSeparator);
                // Discard the first token as it is id.
                st.nextToken();
                while(st.hasMoreTokens()){
                    String stateName = st.nextToken();
                    // add all the fields to array;
                    linkFields.add(stateName);
                }
            }
    
            while(scr.hasNext()){
                ArrayList<String> values = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(scr.next(), columnSeparator);
                // get id and construct the Link
                String id = st.nextToken();
                SfinaLink l = new SfinaLink(Integer.parseInt(id));
                while (st.hasMoreTokens()) {
                    values.add(st.nextToken());
		}
                l.setValues(values);
                SfinaNode source = nodeFromId(map.get(Integer.parseInt(id)).getFirst());
                SfinaNode dest = nodeFromId(map.get(Integer.parseInt(id)).getSecond());
                graph.addEdge(l, source, dest, EdgeType.DIRECTED);
                ((EdgeFactory)edgeFactory).increaseIndex();
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public SfinaNode nodeFromId(int id){
        for(SfinaNode n:graph.getVertices()){
            if(n.getID()==id){
                //System.out.println(id);
                return n;
                
            }
        }
        return null;
    }
    
    public void loadNodeFlow(String path){
        // First Load headings and set fields
        String location = path+"/flow/nodes.txt";
        String columnSeparator = ",";
        File file = new File(location);
        Scanner scr = null;
        nodeFields = new ArrayList<String>();
        try {
            scr = new Scanner(file);
            if(scr.hasNext()){
                StringTokenizer st = new StringTokenizer(scr.next(), columnSeparator);
                st.nextToken();
                while(st.hasMoreTokens()){
                    String stateName = st.nextToken();
                    nodeFields.add(stateName);
                }
            }
    
            while(scr.hasNext()){
                ArrayList<String> values = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(scr.next(), columnSeparator);
                // get id and construct the Link
                String id = st.nextToken();
                SfinaNode n = new SfinaNode(Integer.parseInt(id));
                while (st.hasMoreTokens()) {
                    values.add(st.nextToken());
		}
                n.setValues(values);
                
                // add node to the graph
                graph.addVertex(n);
                ((VertexFactory)vertexFactory).increaseIndex();
            }   
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setNodeFields(ArrayList<String> nodeFields){
        this.nodeFields = nodeFields;
    }
    
    
    public void setLinkFields(ArrayList<String> linkFields){
        this.linkFields = linkFields;
    }
    
    public VisualizationViewer getVisualizationViewer(){
        return vv;
    }
    
    
     /**
     * a driver for this demo
     */
    @SuppressWarnings("serial")
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sfina Network Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final NetworkGenerator demo = new NetworkGenerator();
        frame.getContentPane().add(demo);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        
        // give option either to load or create new network
        Object[] opt = {"Load Existing Data","Start From Scratch"};
        int result = JOptionPane.showOptionDialog(null,"Edit Mode Selection","Select method to proceed network creation",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,opt,null);
        if(result==JOptionPane.NO_OPTION){
                    
            FieldsDialog nF = new FieldsDialog();
            JOptionPane.showConfirmDialog(null, nF, "Enter Node Fields", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
            nodeFields = nF.getFields();

            FieldsDialog lF = new FieldsDialog();
            JOptionPane.showConfirmDialog(null, lF, "Enter Link Fields", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
            linkFields = lF.getFields();
        } else {
            // TODO, check if the location is invalidYES
            JFileChooser chooser = new JFileChooser(); 
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select Network Location");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
                path=  chooser.getSelectedFile().getAbsolutePath();
                demo.loadNodeFlow(path);
                HashMap<Integer, Pair<Integer>> map = demo.loadLinkTopology(path);
                demo.loadLinkFlow(path,map);
                
                //demo.setLayout(new );
                //demo.layout = new StaticLayout<SfinaNode,SfinaLink>(demo.graph, new Dimension(600,600));
                //demo.vv.doLayout();
                //demo.vv.setLayout();
                demo.vv.repaint();
            }

        }

    }
    
}
// TODO Reformat Topology Loader and Flow Loader and corresponding Exporters