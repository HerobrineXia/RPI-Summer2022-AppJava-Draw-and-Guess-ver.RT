package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawingBoard extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<Map.Entry<Color, Rectangle>>> drawingBoard;
	private int chessPieceWidth = 80;
	private double zoomNum = 1;
	private Color[][] currentdrawingBoardStatus;
	
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
	public DrawingBoard(Color[][] drawingBoardStatus) {
		this.currentdrawingBoardStatus = drawingBoardStatus;
		this.drawingBoard = new ArrayList<>(drawingBoardStatus.length);
		for (int i = 0; i < drawingBoardStatus.length; i++) { // start get value from input drawing board
			drawingBoard.add(new ArrayList<Map.Entry<Color, Rectangle>>(drawingBoardStatus[0].length));
			for (int j = 0; j < drawingBoardStatus[0].length; j++) {
				drawingBoard.get(i).add(new AbstractMap.SimpleEntry<Color, Rectangle>(drawingBoardStatus[i]!= null? drawingBoardStatus[i][j]:Color.WHITE, 
						                                           new Rectangle(j*this.chessPieceWidth, i*this.chessPieceWidth, 
						                                           this.chessPieceWidth, this.chessPieceWidth)));
			}
		}
		this.setOpaque(true);
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
        for (ArrayList<Map.Entry<Color, Rectangle>> rowOfBoard : drawingBoard) {
        	for (Map.Entry<Color, Rectangle> piece : rowOfBoard) {
    			g2.setColor(piece.getKey());
    			g2.fillRect((int)(piece.getValue().getX()), 
     				   (int) (piece.getValue().getY()),
     				   (int) (piece.getValue().getWidth()*this.zoomNum),
     				   (int) (piece.getValue().getWidth()*this.zoomNum));
        		g2.setColor(Color.BLACK);
        		g2.drawRect((int)(piece.getValue().getX()*this.zoomNum), 
        				    (int) (piece.getValue().getY()*this.zoomNum),
        				    (int)(piece.getValue().getWidth()*this.zoomNum),
        				    (int) (piece.getValue().getWidth()*this.zoomNum));

        	}
        }
        
        // generate border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(0, 0, (int)(this.drawingBoard.get(0).get(0).getValue().getWidth()*this.zoomNum*this.drawingBoard.get(0).size()), 
        		          (int)(this.drawingBoard.get(0).get(0).getValue().getWidth()*this.zoomNum*this.drawingBoard.size()));
        
	}
	/**
	 * Observer: get current zoom parameter
	 * @return this zoom parameter
	 */
	public double getZoomPara() {
		return this.zoomNum;
	}
	
	
	public static void main(String[] args) {
		JFrame testframe = new JFrame();
		// avoid image displace case,  not necessary
		Color[][] arr = new Color[512][512];
		testframe.add(new DrawingBoard(arr));
		testframe.setSize(620, 640);
		testframe.setLocationRelativeTo(null); // set window centre
		testframe.setAlwaysOnTop(true); // since it is important, let it top
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testframe.setVisible(true);
	}
}
