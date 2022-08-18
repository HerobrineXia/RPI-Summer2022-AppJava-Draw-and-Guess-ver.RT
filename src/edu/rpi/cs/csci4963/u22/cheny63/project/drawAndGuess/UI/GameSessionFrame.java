package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control.Controller;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.SystemCheck;

public class GameSessionFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	// chessboard info
	private DrawBoard board;
	private Color[][] drawContent = new Color[80][80];
	
	// statusInfo
	private boolean isHost;
	private boolean isStart = false;
	
	// panels
	private OpaqueJPanel operations;   // store drawboard operations
	private OpaqueJPanel boardPanel;   // store drawboard all component
	private OpaqueJPanel chatRoom;     // store chat
	private OpaqueJPanel timer;        // store time
	private java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
	private ChatBoxPanel chat;
	
	public void updateChat() {
		this.chat.updateChat();
	}
	
	public void activate() {
		this.board.activate();
	}
	
	public void deactivate() {
		this.board.deactivate();
	}
	
	private void initOperations(Controller controller) {
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		this.operations.setLayout(new GridBagLayout());
		GridBagConstraints gridBagCons = new GridBagConstraints();
		this.operations.setOpaque(true);
		DrawBoardButton pencil = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/pencil.png"), 
								 new Dimension(90, 90))), true);
		DrawBoardButton eraser = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/eraser.png"), 
				 				 new Dimension(90, 90))), true);
		DrawBoardButton restore = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/restore.png"), 
				 				new Dimension(90, 90))), true);
		DrawBoardButton exit = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/exit.png"), 
 								new Dimension(80, 105))), true);
		
		// set action to button
		exit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(GameSessionFrame.this, "Do you want to quit?", 
				    "Are you sure", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
					controller.onClose();
			}
        });
		pencil.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				GameSessionFrame.this.board.setStroke(Color.BLACK);
			}
        });
		eraser.addActionListener(new java.awt.event.ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				GameSessionFrame.this.board.setStroke(new Color(251, 251, 251));
			}
        });
		restore.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (JOptionPane.showConfirmDialog(GameSessionFrame.this, "Do you want to clear the board?", 
    				    "Are you sure", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
            			GameSessionFrame.this.board.clear();
			}
        });
		
		gridBagCons.gridx = 1;
		this.operations.add(pencil);
		gridBagCons.gridx = 2;
		this.operations.add(eraser);
		gridBagCons.gridx = 3;
		this.operations.add(restore);
		gridBagCons.gridx = 4;
		gridBagCons.anchor = GridBagConstraints.EAST;
		gridBagCons.weightx = 10;
		this.operations.add(exit);
		
	}
	
	private void generateGUI(Controller controller) throws FontFormatException, IOException {
		// set full screen
		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        
        // init panel
        this.operations = new OpaqueJPanel();                   // store drawboard operations
        this.chatRoom = new OpaqueJPanel();                     // store chat
        this.timer = new OpaqueJPanel();                        // store time
        OpaqueJPanel chessboardAndTools = new OpaqueJPanel();   // store drawboard operations
		this.chat = new ChatBoxPanel(this.controller);
        initOperations(controller);
        VerticalTimerPanel timerContent = new VerticalTimerPanel();
        this.board = new DrawBoard(drawContent, -1);
        
        // start arrange
     	this.setBackground(new Color(32, 130, 147));
     	GameSessionPanel allContent = new GameSessionPanel(Toolkit.getDefaultToolkit().getScreenSize());
     	allContent.setLayout(new BorderLayout());
     	chessboardAndTools.setLayout(new GridBagLayout());
     	GridBagConstraints gridBagCons = new GridBagConstraints();
     	
     	this.chatRoom.add(chat);
     	this.timer.add(timerContent);
     	gridBagCons.gridx = 0;
     	gridBagCons.gridy = 1;
     	chessboardAndTools.add(board, gridBagCons);
     	gridBagCons.gridy = 0;
     	gridBagCons.anchor = GridBagConstraints.WEST;
     	chessboardAndTools.add(operations, gridBagCons);
     	allContent.add(chessboardAndTools, BorderLayout.CENTER);
     	allContent.add(this.chatRoom, BorderLayout.EAST);
     	// allContent.add(this.timer, BorderLayout.WEST);
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

	public GameSessionFrame(Controller controller) {
		super("Start a game - Draw and Guess");
		this.controller = controller;
		this.isHost = this.controller.isServer();
		try {
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			generateGUI(controller);
		} catch (Exception e) { // case: cannot get resource
			JOptionPane.showMessageDialog(this, 
			"Fail to load game resource: please check res folder", 
			"Oops...", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
