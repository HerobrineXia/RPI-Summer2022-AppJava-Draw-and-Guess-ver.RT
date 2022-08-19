package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.IOException;

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
	
	// panels
	private OpaqueJPanel operations;           // store drawboard operations
	private OpaqueJPanel chatRoom;             // store chat
	protected VerticalTimerPanel timer;        // store time
	private java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
	private ChatBoxPanel chat;
	private DrawBoardButton exit;
	
	// special param
	private double scale = SystemCheck.isWindows() ? 0.06 : 0.03;
	
	public void updateStats() {
		this.chat.updateChat();
	}
	
	public void timerStart(int timeInterval) {
		this.timer.updateTime(timeInterval);
	}
	
	public void setPrompt(String secretWord, String secretHint) {
		this.board.setPrompt(secretWord, secretHint);
		this.chat.updateCurrentGuessing();
	}
	
	public void activate() {
		this.board.activate();
		for (Component button: operations.getComponents())
			button.setEnabled(true);
	}
	
	public void deactivate() {
		this.board.deactivate();
		for (Component button: operations.getComponents())
			button.setEnabled(false);
	}
	
	public void clear() {
		this.board.clear(false);
	}
	
	public void setEntryColor(int x, int y, Color targetColor) {
		this.board.setEntryColor(x, y, targetColor, false);
	}
	
	private void initCursorStrategy() {
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = ImageUtility.resizeIcon(toolkit.getImage("./res/gui/cursor/normal.png"), new Dimension(10, 10));
		Cursor newCursor = toolkit.createCustomCursor(image , new Point(0, 0), "");
		this.setCursor (newCursor);
	}
	
	
	
	private void initOperations(Controller controller) {
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		this.operations.setLayout(new GridBagLayout());
		GridBagConstraints gridBagCons = new GridBagConstraints();
		this.operations.setOpaque(true);
		int buttonWidth = (int)(toolkit.getScreenSize().width * scale);
		DrawBoardButton pencil = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/pencil.png"), 
								 new Dimension(buttonWidth, buttonWidth))), true);
		DrawBoardButton eraser = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/eraser.png"), 
				 				 new Dimension(buttonWidth, buttonWidth))), true);
		DrawBoardButton restore = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/restore.png"), 
				 				new Dimension(buttonWidth, buttonWidth))), true);
		this.exit = new DrawBoardButton(new ImageIcon(ImageUtility.resizeIcon(toolkit.getImage("./res/gui/gameSession/exit.png"), 
 								new Dimension((int)(toolkit.getScreenSize().width * scale/1741*1321), (int)(toolkit.getScreenSize().width *scale)))), true);
		
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
            			GameSessionFrame.this.board.clear(true);
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
		// deactivate all
		for (Component button: operations.getComponents())
			button.setEnabled(false);		
	}
	
	private void generateGUI(Controller controller) throws FontFormatException, IOException {
		// set full screen
		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
        initCursorStrategy();
        
        // init panel
        this.operations = new OpaqueJPanel();                   // store drawboard operations
        OpaqueJPanel allOperations = new OpaqueJPanel();        // store drawboard operations
        this.chatRoom = new OpaqueJPanel();                     // store chat
        this.timer = new VerticalTimerPanel();                        // store time
        OpaqueJPanel chessboardAndTools = new OpaqueJPanel();   // store drawboard operations
		this.chat = new ChatBoxPanel(this.controller, this);
        initOperations(controller);
        this.board = new DrawBoard(drawContent, -1, controller);
        allOperations.add(operations);
        allOperations.add(exit);
        
        // start arrange
     	this.setBackground(new Color(32, 130, 147));
     	GameSessionPanel allContent = new GameSessionPanel();
     	allContent.setLayout(new BorderLayout());
     	chessboardAndTools.setLayout(new GridBagLayout());
     	GridBagConstraints gridBagCons = new GridBagConstraints();
     	
     	this.chatRoom.add(chat);
     	gridBagCons.gridx = 0;
     	gridBagCons.gridy = 1;
     	chessboardAndTools.add(board, gridBagCons);
     	gridBagCons.gridy = 0;
     	gridBagCons.anchor = GridBagConstraints.WEST;
     	chessboardAndTools.add(allOperations, gridBagCons);
     	chessboardAndTools.setPreferredSize(new Dimension((int)(toolkit.getScreenSize().width * 0.5), 
     			                                          (int)(toolkit.getScreenSize().width * 0.5)));
     	// this.timer.setBounds(0, 0, 50, 1000);
     	// this.timer.add(chessboardAndTools);
     	allContent.add(new VerticalTimerPanel(), BorderLayout.WEST);
     	allContent.add(chessboardAndTools, BorderLayout.CENTER);
     	allContent.add(this.chatRoom, BorderLayout.EAST);
     	
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