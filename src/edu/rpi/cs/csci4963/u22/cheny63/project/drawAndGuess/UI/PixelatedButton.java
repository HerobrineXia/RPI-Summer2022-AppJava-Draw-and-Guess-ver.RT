package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class PixelatedButton extends JButton{
	
	public Font goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
	
    public PixelatedButton() throws FontFormatException, IOException {
    	// set font
    	goreRegular  = goreRegular.deriveFont(Font.PLAIN, 40);
    	
    	// basic info filling
        this.setFont(goreRegular);
        this.setText("Text test");
        this.setForeground(Color.WHITE);
        PixelatedButton.this.setContentAreaFilled(false);
        this.setPreferredSize(new Dimension(500, 50));
        
        // activate action
        this.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.setUI(new ModifButtonUI());

        this.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isRollover()) {
                	PixelatedButton.this.setContentAreaFilled( true );
                	PixelatedButton.this.setForeground(new Color(32, 130, 147));
                	PixelatedButton.this.setBackground(Color.WHITE);
                } else {
                	PixelatedButton.this.setForeground(Color.WHITE);
                	PixelatedButton.this.setContentAreaFilled(false);
                }
                if (model.isPressed()) {
                	PixelatedButton.this.setContentAreaFilled(false);
                	PixelatedButton.this.setForeground(Color.GRAY);
                }
            }
        });
    }

    public static void main(String args[]) {
        JFrame test = new JFrame();
        JPanel panel = new JPanel();
        try {
        	panel.add(new PixelatedButton());
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        test.add(panel);
        test.setBackground(new Color(32, 130, 147));
        test.setSize(500, 250);
        test.setLocationRelativeTo(null); // set window centre
        test.setVisible(true);
    }
}

class OldRoundedBorderLine extends AbstractBorder {

    private final static int MARGIN = 5;
    private static final long serialVersionUID = 1L;
    private Color color;

    OldRoundedBorderLine(Color clr) {
        color = clr;
    }

    public void setColor(Color clr) {
        color = clr;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        ((Graphics2D) g).setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = MARGIN;
        insets.top = MARGIN;
        insets.right = MARGIN;
        insets.bottom = MARGIN;
        return insets;
    }
}

class ModifButtonUI extends MetalButtonUI {

    private static final ModifButtonUI buttonUI = new ModifButtonUI();

    public static ComponentUI createUI(JComponent c) {
        return new ModifButtonUI();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        final Color color1 = new Color(230, 255, 255, 0);
        final Color color2 = new Color(255, 230, 255, 64);
        final Color alphaColor = new Color(200, 200, 230, 64);
        final Color color3 = new Color(
            alphaColor.getRed(), alphaColor.getGreen(), alphaColor.getBlue(), 0);
        final Color color4 = new Color(
            alphaColor.getRed(), alphaColor.getGreen(), alphaColor.getBlue(), 64);
        super.paint(g, c);
        Graphics2D g2D = (Graphics2D) g;
        GradientPaint gradient1 = new GradientPaint(
            0.0F, (float) c.getHeight() / (float) 2, color1, 0.0F, 0.0F, color2);
        Rectangle rec1 = new Rectangle(0, 0, c.getWidth(), c.getHeight() / 2);
        g2D.setPaint(gradient1);
        g2D.fill(rec1);
        GradientPaint gradient2 = new GradientPaint(
            0.0F, (float) c.getHeight() / (float) 2, color3, 0.0F, c.getHeight(), color4);
        Rectangle rec2 = new Rectangle(0, c.getHeight() / 2, c.getWidth(), c.getHeight());
        g2D.setPaint(gradient2);
        g2D.fill(rec2);
    }

    @Override
    public void paintButtonPressed(Graphics g, AbstractButton b) {
        paintText(g, b, b.getBounds(), b.getText());
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, b.getSize().width, b.getSize().height);
    }
}