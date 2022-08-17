package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;

public class DrawBoard extends OpaqueJPanel{
	
	private static final long serialVersionUID = 1L;
	private int drawEntryWidth = 80;
	private double zoomNum = 1;
	private Color[][] currentdrawingBoardStatus;
	private int lastDragEventTriggerX = -1;
	private int lastDragEventTriggerY = -1;
	private boolean stroke = true;
	/**
	 * Construct a image from specific drawing board setting and color setting
	 *
	 * @param drawing boardStatus       the 2d array containing drawing board, 1 for alive. 0 for died
	 * @param mainThemeColor         the color for whole window color, including live color and histogram bar color
	 * @param drawing boardBorderColor  the color for edge of drawing board 
	 * @param zoomNum                the scale for drawing board size and window prefered size change, noted that 
	 *                               if it is set as -1, then constructor will automatically generate one based
	 *                               on drawing board size
	 */
	public DrawBoard(Color[][] drawingBoardStatus, double zoomNum) {
		this.zoomNum = zoomNum != -1? zoomNum:Toolkit.getDefaultToolkit().getScreenSize().height*0.80/(drawingBoardStatus.length*drawEntryWidth);
		this.currentdrawingBoardStatus = new Color[drawingBoardStatus.length][drawingBoardStatus[0].length];
		this.setBackground(new Color(251, 251, 251));
		for (int i = 0; i < drawingBoardStatus.length; i++)
			for (int j = 0; j < drawingBoardStatus[0].length; j++)
				this.currentdrawingBoardStatus[i][j] = (drawingBoardStatus[i][j] != null? drawingBoardStatus[i][j] : new Color(251, 251, 251));
		
		this.addMouseMotionListener(new MouseMotionListener() {
	        @Override
	        public void mouseMoved(MouseEvent e) {
	        	lastDragEventTriggerY = lastDragEventTriggerX = -1;
	        }
	        @Override
	        public void mouseDragged(MouseEvent e) {
	        	if (lastDragEventTriggerY != -1)
	        		connectTwoDots(e.getX(), e.getY(), lastDragEventTriggerX, lastDragEventTriggerY);
	        	setEntryColor(findPosition(e.getX(), e.getY()), Color.BLUE);
	        	lastDragEventTriggerX = e.getX();
	        	lastDragEventTriggerY = e.getY();
	        }
	    });
	}
	
	private void connectTwoDots(double x2, double y2, double x1, double y1) {	
		double slope = (x2 != x1? (y2 - y1) / (x2 - x1) : 0);
		double b = (x2*y1-x1*y2)/(x2-x1);
		// System.out.println("connect: (" + x1+", " + y1+")" + " ("+ x2 +", " + x2 + ")" + " slope: " + slope);
			
		while (y2 != y1 &&!((x1-x2>1)||(x1-x2<-1)) ) {
			setEntryColor(findPosition((int)x1, (int)y1), Color.PINK);
			y1 += (y1 < y2? 1 : (y1 == y2? 0 : -1));
		}
		while(x1 != x2) {
			setEntryColor(findPosition((int)x1, (int)y1), Color.RED);
			x1 += (x1 < x2? 1 : (x1 == x2? 0 : -1));
			y1 = x1*slope+b;
			y2 = x2*slope+b;
		}
		
	}
	
    public Dimension getPreferredSize(){
        return new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.85), 
        		             (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.85));
    }
    
	private Dimension findPosition(int x, int y) {
		int xEntry = (int)(x/(this.drawEntryWidth*this.zoomNum));
		int yEntry = (int)(y/(this.drawEntryWidth*this.zoomNum));
		if (xEntry >= this.currentdrawingBoardStatus[0].length) xEntry = this.currentdrawingBoardStatus[0].length-1;
		if (xEntry < 0) xEntry = 0;
		if (yEntry >= this.currentdrawingBoardStatus.length) yEntry = this.currentdrawingBoardStatus.length-1;
		if (yEntry < 0) yEntry = 0;
		return new Dimension(xEntry, yEntry);
	}
	private void setEntryColor(Dimension position, Color targetColor) {
		this.currentdrawingBoardStatus[position.height][position.width] = targetColor;
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * overrided paintComponent func, use to render
	 * and change the drawing board. i.e. apply the size or 
	 * rectangle change in graph
	 * @param g  the graphic going to be paint
	 */
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // draw all the rectangles in the list
        g2.setStroke(new BasicStroke((float)this.zoomNum));
        int rowNum = 0;
        int colNum = 0;
        for (Color[] rowOfBoard : currentdrawingBoardStatus) {
        	rowNum = 0;
        	for (Color piece : rowOfBoard) {
    			g2.setColor(piece);
    			g2.fillRect((int)((this.drawEntryWidth * this.zoomNum)* rowNum), 
     				   (int) ((this.drawEntryWidth * this.zoomNum)* colNum),
     				   (int) (this.drawEntryWidth * this.zoomNum),
     				   (int) (this.drawEntryWidth * this.zoomNum));
    			
    			if (stroke) {
    				g2.setColor(new Color(226, 226, 226));
        			g2.setStroke(new BasicStroke(0.00003f));
        			g2.drawRect((int)((this.drawEntryWidth * this.zoomNum)* rowNum), 
          				   (int) ((this.drawEntryWidth * this.zoomNum)* colNum),
          				   (int) (this.drawEntryWidth * this.zoomNum),
          				   (int) (this.drawEntryWidth * this.zoomNum));
    			}
    			
    			rowNum++;
        	}
        	colNum++;
        }
        
        // generate border
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(0, 0, (int)((this.drawEntryWidth * this.zoomNum)* rowNum), 
        		          (int)((this.drawEntryWidth * this.zoomNum)* colNum));
        
	}
	
	
	public static void main(String[] args) {
		JFrame testframe = new JFrame();
		// avoid image displace case,  not necessary
		Color[][] arr = new Color[180][180];
		testframe.add(new DrawBoard(arr, 0.05));
		testframe.setSize(620, 640);
		testframe.setLocationRelativeTo(null); // set window centre
		testframe.setAlwaysOnTop(true); // since it is important, let it top
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testframe.setVisible(true);
	}
}
