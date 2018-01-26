package networkGenerator;


import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Supplier;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import core.SFINAGUI;
import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.util.ArrowFactory;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import interdep.ExtendedLink;
import interdep.ExtendedNode;
import interdep.GridVizLayout;
import interdep.RadialVizLayout;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import interdep.SfinaNetworkReader;
import interdep.FlowNetGraphConverter;
import java.io.File;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import network.FlowNetwork;
import network.Link;
import power.input.PowerLinkState;
import power.input.PowerNodeState;
import power.input.PowerNodeType;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dinesh
 */
public class InterdepNetVisualization extends JApplet{
    
    VisualizationViewer<ExtendedNode, ExtendedLink> vv;
    LoadingCache<ExtendedNode, Paint> vertexPaints =
			CacheBuilder.newBuilder().build(
					CacheLoader.from(Functions.<Paint>constant(Color.white)));
    LoadingCache<ExtendedLink, Paint> edgePaints =
			CacheBuilder.newBuilder().build(
					CacheLoader.from(Functions.<Paint>constant(Color.blue)));     
    
    public final Color slack = new Color(62, 74, 155);
    public final Color bus = new Color(155, 62, 85);
    public final Color generator = new Color(73, 155, 62);
    public final Color activeLink = new Color(30, 200, 200);
    public final Color inActiveLink = new Color(181, 5, 11);
    public final Color interdepVertexColor =new Color(255, 255, 0);
    private SFINAGUI owner;
    private double maxPower = 0.;
    private double minPower = 10000.;
    
    public InterdepNetVisualization(SFINAGUI owner){
       this.owner = owner;
    }

    public void start(){
        Graph<ExtendedNode, ExtendedLink> graph = new SparseMultigraph<ExtendedNode, ExtendedLink>();
        SfinaNetworkReader snr = new SfinaNetworkReader();
        FlowNetGraphConverter sn2j = new FlowNetGraphConverter();
        Vector<FlowNetwork> fn = snr.readNetworks();
        if (fn==null){
            return;
        }
        final int totalNetworks = fn.size();
        graph = sn2j.createJungGraph(fn);
        for(ExtendedLink l:graph.getEdges()){
            if (!(l.getLink()).isInterdependent()){
                maxPower = max(maxPower, (double)((Link)(l.getLink())).getProperty(PowerLinkState.CURRENT));
                minPower = min(minPower, (double)((Link)(l.getLink())).getProperty(PowerLinkState.CURRENT));
            }
        }
        final AggregateLayout<ExtendedNode, ExtendedLink> layout = new AggregateLayout<ExtendedNode, ExtendedLink>(new FRLayout<ExtendedNode, ExtendedLink>(graph));
        vv = new VisualizationViewer<ExtendedNode, ExtendedLink>(layout);
        vv.setBackground(Color.white);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaints);
        vv.getRenderContext().setVertexDrawPaintTransformer(new Function<ExtendedNode,Paint>() {
                public Paint apply(ExtendedNode v) {
                        if(vv.getPickedVertexState().isPicked(v)) {
                                return Color.cyan;
                        } else {
                                return Color.BLACK;
                        }
                }
        });
        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaints);
        vv.getRenderContext().setEdgeStrokeTransformer(new Function<ExtendedLink, Stroke>(){
           float dash[] = {10.f};
            public Stroke apply(ExtendedLink e){
               
               boolean inter = e.getLink().isInterdependent();
               if(inter)
                   return new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
 BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
               else {
                   return new BasicStroke((float) 1.5);
               }
           }
        });
        
        DefaultModalGraphMouse<ExtendedNode, ExtendedLink> gm = new DefaultModalGraphMouse<ExtendedNode, ExtendedLink>();
        vv.setGraphMouse(gm);
        colorLayout(layout);
        arrangeLayoutCircle(layout, totalNetworks);
        vv.validate();
        vv.repaint();
                
        JPanel buttons = new JPanel();
        /*JButton saveGraph = new JButton("Save Visible");
        saveGraph.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(); 
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Select Location To Save File");
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "svg"));
                //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                int option = chooser.showSaveDialog(vv);
                if(option == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    saveGraphics(file);
                }
            }
        });
        buttons.add(saveGraph);
        */
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        Container content = getContentPane();
        content.add(new GraphZoomScrollPane(vv));
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(buttons);
        content.add(p, BorderLayout.SOUTH);
        
    }
//        public void saveGraphics(File file){
//        try{
//            VectorGraphics g = new SVGGraphics2D(file, vv);
//            g.startExport();
//            vv.print(g);
//            g.endExport();
//        } catch(Exception e){
//            e.printStackTrace();
//        }        
//    }
    public void arrangeLayoutCircle(AggregateLayout<ExtendedNode, ExtendedLink> layout, int totalNetworks){
        int dim = min(this.getSize().height,this.getSize().width) + 100;
        GridVizLayout rl = new GridVizLayout(totalNetworks, dim, dim);
        Vector<Graph<ExtendedNode, ExtendedLink>> g_array = new Vector<Graph<ExtendedNode, ExtendedLink>>(totalNetworks);
        Graph<ExtendedNode, ExtendedLink> g = layout.getGraph();
        layout.removeAll();
        
        for (int i = 0; i < totalNetworks; i++){
            Graph<ExtendedNode, ExtendedLink> g_new = SparseMultigraph.<ExtendedNode, ExtendedLink>getFactory().get();
            g_array.add(g_new);
        }
        
        for (ExtendedNode t : g.getVertices()){
            g_array.get((int)(t.getNetwork())).addVertex(t);
        }
        for (int i = 0; i < totalNetworks; i++){
            Collection<ExtendedNode> c= g_array.get(i).getVertices();
            List lst = new ArrayList(c);
            Collections.sort(lst);
            CircleLayout<ExtendedNode, ExtendedLink> l = new CircleLayout<ExtendedNode, ExtendedLink>(g_array.get(i));
            l.setVertexOrder(lst);

            Dimension d = ((rl.getElements()).get(i)).getSize();
            Point2D corner = ((rl.getElements()).get(i)).getLocation();
            Point2D center = new Point2D.Double(corner.getX()+d.width/2+60, corner.getY()+d.height/2);
            l.setSize(d);
            
            layout.put(l, center);
        }
        vv.repaint();
    }
    
    public void colorLayout(AggregateLayout<ExtendedNode, ExtendedLink> layout){
        for(ExtendedNode v: layout.getGraph().getVertices()){
            PowerNodeType t = (PowerNodeType)v.getNode().getProperty(PowerNodeState.TYPE);
            if(t==PowerNodeType.SLACK_BUS){
                vertexPaints.put(v,slack);
            } else if(t==PowerNodeType.GENERATOR){
                vertexPaints.put(v,generator);
            } else {
                vertexPaints.put(v,bus);
            }
            }
        for(ExtendedLink t:vv.getGraphLayout().getGraph().getEdges()){
            if (t.getLink().isActivated()){
                edgePaints.put(t, activeLink);
            } else {
                edgePaints.put(t, inActiveLink);
            }
        }
    }
    
    public static void main(String[] args){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterdepNetVisualization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterdepNetVisualization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterdepNetVisualization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterdepNetVisualization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        InterdepNetVisualization tal = new InterdepNetVisualization(null);
        tal.start();
        JFrame jf = new JFrame();
        jf.getContentPane().add(tal);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
        
    }
}
