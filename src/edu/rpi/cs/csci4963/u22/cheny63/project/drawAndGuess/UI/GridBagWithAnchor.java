package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridBagWithAnchor {

  public static void main(String[] args) {
    JFrame f = new JFrame(
        "Demonstrates the use of anchor constraints");
    JPanel p = new JPanel(new GridBagLayout());

    p.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(2, 2, 2, 2);
    c.weighty = 1.0;
    c.weightx = 1.0;
    c.gridx = 0;
    c.gridy = 0;
    c.gridheight = 2;
    c.anchor = GridBagConstraints.NORTH; // place component on the North
    p.add(new JButton("Java"), c);
    c.gridx = 1;
    c.gridheight = 1;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.SOUTHWEST;
    p.add(new JButton("Source"), c);
    c.gridy = 1;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.CENTER; // remember to rest to center
    p.add(new JButton("and"), c);
    c.gridx = 2;
    p.add(new JButton("Support !!!"), c);

    WindowListener wndCloser = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };
    f.addWindowListener(wndCloser);

    f.getContentPane().add(p);
    f.setSize(600, 200);
    f.show();
  }
}