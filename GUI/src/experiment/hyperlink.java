/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

/**
 *
 * @author dinesh
 */
public class hyperlink {
    public static void main(String[] args) throws URISyntaxException {
    final URI uri = new URI("http://java.sun.com");
    
    
    class OpenUrlAction implements ActionListener {
      @Override 
      public void actionPerformed(ActionEvent e) {
        if (Desktop.isDesktopSupported()) {
            try {
                  Desktop.getDesktop().browse(uri);
            } 
            catch (IOException io) { 
            /* TODO: error handling */ 
            }
        } else 
        { 
            /* TODO: error handling */ 
        }
      }
    }
    
    
    JFrame frame = new JFrame("Links");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(100, 400);

    Container container = frame.getContentPane();
    container.setLayout(new GridBagLayout());

    JButton button = new JButton();
    button.setText("<HTML>Click the <FONT color=\"#000099\"><U>link</U></FONT>"
        + " to go to the Java website.</HTML>");
    button.setHorizontalAlignment(SwingConstants.LEFT);
    button.setBorderPainted(false);
    button.setOpaque(false);
    button.setBackground(Color.WHITE);
    button.setToolTipText(uri.toString());
    button.addActionListener((ActionListener) new OpenUrlAction());
    container.add(button);
    frame.setVisible(true);
  }

}
