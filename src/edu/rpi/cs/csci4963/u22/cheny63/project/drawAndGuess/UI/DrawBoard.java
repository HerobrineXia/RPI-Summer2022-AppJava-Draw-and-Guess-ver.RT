package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control.Controller;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;

public class DrawBoard extends OpaqueJPanel{
	
	private static final long serialVersionUID = 1L;
	private int drawEntryWidth = 80;
	private double zoomNum = 1;
	private Color[][] currentdrawingBoardStatus;
	private int lastDragEventTriggerX = -1;
	private int lastDragEventTriggerY = -1;
	private boolean stroke = true;
	private Color strokeColor = new Color(251, 251, 251);
	private boolean isValid = false;
	private java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
	private Font goreRegular;
	private String[] prompting;
	private Controller controller;
	/**
	 * Construct a image from specific drawing board setting and color setting
	 *
	 * @param drawing boardStatus       the 2d array containing drawing board, 1 for alive. 0 for died
	 * @param mainThemeColor         the color for whole window color, including live color and histogram bar color
	 * @param drawing boardBorderColor  the color for edge of drawing board 
	 * @param zoomNum                the scale for drawing board size and window prefered size change, noted that 
	 *                               if it is set as -1, then constructor will automatically generate one based
	 *                               on drawing board size
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	public DrawBoard(Color[][] drawingBoardStatus, double zoomNum, Controller controller) throws FontFormatException, IOException {
		this.zoomNum = zoomNum != -1? zoomNum:Toolkit.getDefaultToolkit().getScreenSize().height*0.80/(drawingBoardStatus.length*drawEntryWidth);
		this.currentdrawingBoardStatus = new Color[drawingBoardStatus.length][drawingBoardStatus[0].length];
		this.goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		this.goreRegular  = goreRegular.deriveFont(Font.PLAIN, 60);
		this.controller = controller;
		this.setBackground(new Color(251, 251, 251));
		for (int i = 0; i < drawingBoardStatus.length; i++)
			for (int j = 0; j < drawingBoardStatus[0].length; j++)
				this.currentdrawingBoardStatus[i][j] = (drawingBoardStatus[i][j] != null? drawingBoardStatus[i][j] : new Color(251, 251, 251));
		
		initCursorStretegy();
		this.addMouseMotionListener(new MouseMotionListener() {
	        @Override
	        public void mouseMoved(MouseEvent e) {
	        	if(isValid)
	        		lastDragEventTriggerY = lastDragEventTriggerX = -1;
	        }
	        @Override
	        public void mouseDragged(MouseEvent e) {
	        	if(isValid) {
	        		if (lastDragEventTriggerY != -1)
		        		connectTwoDots(e.getX(), e.getY(), lastDragEventTriggerX, lastDragEventTriggerY);
	        		Dimension position = findPosition(e.getX(), e.getY());
		        	setEntryColor((int)position.getHeight(), (int)position.getWidth(), strokeColor, true);
		        	lastDragEventTriggerX = e.getX();
		        	lastDragEventTriggerY = e.getY();
	        	}
	        }
	    });
	}
	
	public void setPrompting(String[] prompts) {
		this.prompting = Arrays.copyOf(prompts, prompts.length);
		this.repaint();
		this.revalidate();
	}
	
	private void connectTwoDots(double x2, double y2, double x1, double y1) {	
		double slope = (x2 != x1? (y2 - y1) / (x2 - x1) : 0);
		double b = (x2*y1-x1*y2)/(x2-x1);
		// System.out.println("connect: (" + x1+", " + y1+")" + " ("+ x2 +", " + x2 + ")" + " slope: " + slope);
			
		while (y2 != y1 &&!((x1-x2>1)||(x1-x2<-1)) ) {
			Dimension position = findPosition((int)x1, (int)y1);
			setEntryColor((int)position.getHeight(), (int)position.getWidth(), strokeColor, true);
			y1 += (y1 < y2? 1 : (y1 == y2? 0 : -1));
		}
		while(x1 != x2) {
			Dimension position = findPosition((int)x1, (int)y1);
			setEntryColor((int)position.getHeight(), (int)position.getWidth(), strokeColor, true);
			x1 += (x1 < x2? 1 : (x1 == x2? 0 : -1));
			y1 = x1*slope+b;
			y2 = x2*slope+b;
		}
	}
	
	public void activate() {
		this.isValid = true;
		initCursorStretegy();
		this.repaint();
		this.revalidate();
	}

	public void deactivate() {
		this.isValid = false;
		initCursorStretegy();
		this.repaint();
		this.revalidate();
	}
	
	private void initCursorStretegy() {
		Image image;
		if (this.isValid)
			image = ImageUtility.resizeIcon(toolkit.getImage("./res/gui/cursor/normal.png"), new Dimension(10, 10));
		else
			image = ImageUtility.resizeIcon(toolkit.getImage("./res/gui/cursor/busy.png"), new Dimension(10, 10));
		Cursor newCursor = toolkit.createCustomCursor(image , new Point(0, 0), "");
		this.setCursor (newCursor);
		
	}
	
	public void setStroke(Color color) {
		this.strokeColor = color;
	}
	
	public void clear(boolean syncToOther) {
		for (int i = 0; i < currentdrawingBoardStatus.length; i++)
			for (int j = 0; j < currentdrawingBoardStatus[0].length; j++) {
				this.currentdrawingBoardStatus[i][j] = new Color(251, 251, 251);
				if (syncToOther) controller.onBoardDraw(i, j, new Color(251, 251, 251));
			}
				
		this.repaint();
		this.revalidate();
	}
	
    public Dimension getPreferredSize(){
        return new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.8), 
        		             (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.8));
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
	
	public void setPrompt(String secretWord, String secretHint) {
		this.prompting = new String[] {secretWord, secretHint};
		this.repaint();
		this.revalidate();
	}
	
	
	public void setEntryColor(int x, int y, Color targetColor, boolean syncToOther) {
		System.out.println("x:" +x + "y:" +y);
		this.currentdrawingBoardStatus[x][y] = targetColor;
		if (syncToOther) controller.onBoardDraw(x, y, targetColor);
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
        
        g2.setColor(Color.BLACK);
        FontMetrics metric = g.getFontMetrics(this.goreRegular);
        g.setFont(goreRegular);
        // generate prompt
        if (this.controller.isGameStart() && this.prompting != null) {
			if(!isValid) {
			    g.drawString(String.valueOf(this.prompting[0].length()) + " letters", 9, (int)(this.drawEntryWidth * this.zoomNum*(rowNum) - metric.getAscent() - 9));
			    g.drawString(this.prompting[1], 9, (int)(this.drawEntryWidth * this.zoomNum*(rowNum) - 9));
			}else {
				g.drawString("Please draw: ", 9, (int)(this.drawEntryWidth * this.zoomNum*(rowNum) - metric.getAscent() - 9));
			    g.drawString(this.prompting[0], 9, (int)(this.drawEntryWidth * this.zoomNum*(rowNum) - 9));
			}
        }  
        
	}
	
	
//	public static void main(String[] args) throws FontFormatException, IOException {
//		JFrame testframe = new JFrame();
//		// avoid image displace case,  not necessary
//		Color[][] arr = new Color[180][180];
//		testframe.add(new DrawBoard(arr, 0.05, new String[]{"Cat", "An animal"}));
//		testframe.setSize(620, 640);
//		testframe.setLocationRelativeTo(null); // set window centre
//		testframe.setAlwaysOnTop(true); // since it is important, let it top
//		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		testframe.setVisible(true);
//	}

	
}
