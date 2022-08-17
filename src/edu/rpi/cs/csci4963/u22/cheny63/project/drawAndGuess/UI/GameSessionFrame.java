package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.SystemCheck;



public class GameSessionFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	// chessboard info
	private DrawBoard board;
	private Color[][] drawContent = new Color[80][80];
	// panels
	private GradientJPanel operations;   // store drawboard operations
	private GradientJPanel boardPanel;   // store drawboard all component
	private GradientJPanel chatRoom;     // store chat
	private GradientJPanel timer;        // store time
	private java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
	private void initOperations() {
		DrawBoardButton pencil = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/pencil.png"), 
								 new Dimension(90, 90))), true);
		DrawBoardButton eraser = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/eraser.png"), 
				 				 new Dimension(90, 90))), true);
		DrawBoardButton restore = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/restore.png"), 
				 				new Dimension(90, 90))), true);
		DrawBoardButton exit = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/exit.png"), 
 								new Dimension(80, 105))), true);
		exit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(GameSessionFrame.this, "Do you want to quit?", 
				    "Are you sure", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
					System.exit(0);
			}
        });
		this.operations.add(pencil);
		this.operations.add(eraser);
		this.operations.add(restore);
		this.operations.add(Box.createHorizontalGlue());
		this.operations.add(exit);
	}
	
	private void generateGUI() {
		// set full screen
		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        
        // init panel
        this.operations = new GradientJPanel();   // store drawboard operations
        this.chatRoom = new GradientJPanel();     // store chat
        this.timer = new GradientJPanel();        // store time
        this.boardPanel = new GradientJPanel();   // store all drawboard
        
        initOperations();
        
        // start arrange
     	this.setBackground(new Color(32, 130, 147));
     	GameSessionPanel allContent = new GameSessionPanel(Toolkit.getDefaultToolkit().getScreenSize());
     	allContent.setLayout(new BorderLayout());
     	this.board = new DrawBoard(drawContent, -1);
     	this.board.setOpaque(true);
     	
     	this.boardPanel.add(operations);
     	this.boardPanel.add(board);
     	allContent.add(boardPanel, BorderLayout.CENTER);
     	allContent.add(operations, BorderLayout.NORTH);
     	this.add(allContent);
     	

        //Display the window.       
		this.setLocationRelativeTo(null); // set window centre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setPreferredSize(new Dimension(600, 600));
	    this.setResizable(false);
	    if (SystemCheck.isWindows())
	    	device.setFullScreenWindow(this);
	    else
	    	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    
	    this.setVisible(true);
	}

	public GameSessionFrame() {
		super("Start a game - Draw and Guess");
		try {
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			generateGUI();
		} catch (Exception e) { // case: cannot get resource
			JOptionPane.showMessageDialog(this, 
			"Fail to load game resource: please check res folder", 
			"Oops...", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
