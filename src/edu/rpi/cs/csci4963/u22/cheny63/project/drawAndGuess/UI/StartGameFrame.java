package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control.Controller;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.ImageUtility;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.SystemCheck;

/** 
 *  OVERVIEW: 
 * 	<b>StartGameFrame</b> is an custom frame when user start the game
 * serve as a welcome screen
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class StartGameFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	// param for setting maximum size
	private Action actionExit;
	private Action actionHost;
	private Action actionClient;
	private Controller controller;
	
	// GUI Component
	private StartGamePanel operations;
	private BeforeGameReadyFrame configFrame;	
	
	/**
	 * init all action related to this screen for further use
	 * serves as an helper function
	 */
	private void initAction() {
		this.actionExit = new AbstractAction("Exit") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(StartGameFrame.this, "Do you want to quit?", 
				    "Are you sure", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
					controller.onClose();
			}
		};
		this.actionHost = new AbstractAction("Host") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				configFrame = new BeforeGameReadyFrame(StartGameFrame.this, controller, true);
	    	}
		};
    	this.actionClient = new AbstractAction("Client") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				configFrame = new BeforeGameReadyFrame(StartGameFrame.this, controller, false);
	    	}
		};
			
	}
	
	/**
	 * helper function for customize cursor color change when enter this frame and interacting
	 */
	private void initCursorStrategy() {
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = ImageUtility.resizeIcon(toolkit.getImage("./res/gui/cursor/normal.png"), new Dimension(10, 10));
		Cursor newCursor = toolkit.createCustomCursor(image , new Point(0, 0), "");
		this.setCursor (newCursor);
	}
	
	/**
	 * helper function to change the current staet to the operations button
	 * @param enabled true if set all buttons to be enabled
	 */
	public void setAllOperationEnable(boolean enabled) {
		for (Component component : this.operations.getComponents()) {
		    if (component instanceof PixelatedButton)
		    	((PixelatedButton) component).setEnabled(enabled);	    	
		}	
	}
	
	/**
	 * helper function when there is some thing wrong with the connection and need to tell users
	 * @param msg the error message
	 * @param title the title of the notification window
	 */
	public void failConnection(String msg, String title) {
		JOptionPane.showMessageDialog(configFrame, msg, title, JOptionPane.ERROR_MESSAGE);
		this.setAllOperationEnable(true);
		if (this.configFrame != null) this.configFrame.dispose();
	}
	
	/**
	 * helper function for allowing a game session to start and enter the drawing room
	 */
	public void startGame() {
		this.dispose();
		this.configFrame.dispose();
	}
	
	/**
	 * main GUI generation function for the current GUI generation
	 * @throws FontFormatException when system is unable to find the correct format of font 
	 * @throws IOException when system is unable to find font
	 */
	private void generateGUI() throws FontFormatException, IOException {
		// set full screen
		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();
		
		initAction();
		initCursorStrategy();		
		
		// start arrange
		this.setBackground(new Color(32, 130, 147));
		this.operations = new StartGamePanel(Toolkit.getDefaultToolkit().getScreenSize());
 
		operations.setLayout(new GridBagLayout());
	    GridBagConstraints gridConstant = new GridBagConstraints();
	    gridConstant.gridy = 0;
	    gridConstant.weighty = 1;
	    operations.add(new JLabel(new ImageIcon("./res/gui/startGameScreen/logo.png")), gridConstant);
	    gridConstant.gridy = 1;
	    gridConstant.weighty = 0.2;
		operations.add(new PixelatedButton("CREATE a ROOM", this.actionHost, operations), gridConstant);
		gridConstant.gridy = 2;
		operations.add(new PixelatedButton("ENTER a ROOM", this.actionClient, operations), gridConstant);
		gridConstant.gridy = 3;
		operations.add(new PixelatedButton("EXIT", this.actionExit, operations), gridConstant);
		gridConstant.gridy = 4;
		operations.add(new JSeparator(JSeparator.VERTICAL), gridConstant);
		
		this.add(operations);
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
	
	/**
	 * constructor: will generate a init setting panel
	 *  for users to select modes
	 * @param controller the controller to be assigned in further operation
	 */
	public StartGameFrame(Controller controller) {
		super("Start a game - Draw and Guess");
		try {
			this.controller = controller;
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			generateGUI();
		} catch (Exception e) { // case: cannot get resource
			JOptionPane.showMessageDialog(this, 
			"Fail to load game resource: please check resource", 
			"Oops...", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
