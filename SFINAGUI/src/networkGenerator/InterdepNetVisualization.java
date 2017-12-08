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
import static java.lang.Math.min;
import java.util.Vector;
import network.FlowNetwork;
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
    public InterdepNetVisualization(SFINAGUI owner){
       this.start();
       this.owner = owner;
    }

    public void start(){
        Graph<ExtendedNode, ExtendedLink> graph = new SparseMultigraph<ExtendedNode, ExtendedLink>();
        
        SfinaNetworkReader snr = new SfinaNetworkReader();
        FlowNetGraphConverter sn2j = new FlowNetGraphConverter();
        Vector<FlowNetwork> fn = snr.readNetworks();
        int totalNetworks = fn.size();
        graph = sn2j.createJungGraph(fn);
        
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
           protected final Stroke DASH = new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
 BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
           protected final Stroke THICK = new BasicStroke((float)1.5);
            public Stroke apply(ExtendedLink e){
               boolean inter = e.getLink().isInterdependent();
               if(inter)
                   return DASH;
               else 
                   return THICK;
           }
        });
        
        DefaultModalGraphMouse<ExtendedNode, ExtendedLink> gm = new DefaultModalGraphMouse<ExtendedNode, ExtendedLink>();
        vv.setGraphMouse(gm);
        colorLayout(layout);
        totalNetworks=3;
        arrangeLayoutCircle(layout, totalNetworks);
        vv.validate();
        arrangeLayoutCircle(layout,totalNetworks);
        vv.repaint();
                
        JButton layoutCircle = new JButton("Layout Circle");
        layoutCircle.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                arrangeLayoutCircle(layout,3);
                vv.repaint();
            }
        });
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        Container content = getContentPane();
        content.add(new GraphZoomScrollPane(vv));
        layoutCircle.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.add(layoutCircle);
        layoutCircle.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(buttons);
        //p.add(new DrawToolBox());
        content.add(p, BorderLayout.SOUTH);
    }

    public void arrangeLayoutCircle(AggregateLayout<ExtendedNode, ExtendedLink> layout, int totalNetworks){
        //int totalNetworks = 3;
        GridVizLayout rl = new GridVizLayout(totalNetworks, this.getSize().height-120, this.getSize().width-120);
        Vector<Graph<ExtendedNode, ExtendedLink>> g_array = new Vector<Graph<ExtendedNode, ExtendedLink>>(3);
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
            Layout<ExtendedNode, ExtendedLink> l = new CircleLayout<ExtendedNode, ExtendedLink>(g_array.get(i));
            Dimension d = ((rl.getElements()).get(i)).getSize();
            Point2D corner = ((rl.getElements()).get(i)).getLocation();
            Point2D center = new Point2D.Double(corner.getX()+d.width/2+40, corner.getY()+d.height/2-40);
            l.setSize(d);
            layout.put(l, center);
        }
        vv.repaint();
    }


    public void arrangeLayoutSingle(AggregateLayout<ExtendedNode, ExtendedLink> layout){
        int h = this.getSize().height;
        int w = this.getSize().width;
        Point2D center1 = new Point2D.Double(w/2,h/2+50);
        Dimension d = new Dimension(2*w/3,2*h/3);
        Graph<ExtendedNode, ExtendedLink> g = layout.getGraph();
        layout.removeAll();
        Layout<ExtendedNode,ExtendedLink> sl2 = new FRLayout(g);
        sl2.setSize(d);
        layout.put(sl2, center1);
        ((FRLayout)sl2).step();
        ((FRLayout)sl2).initialize();
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
        
        JFrame jf = new JFrame();
        jf.getContentPane().add(tal);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
}
