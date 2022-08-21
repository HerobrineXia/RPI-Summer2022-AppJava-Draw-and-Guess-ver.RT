package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control.Controller;

/** 
 *  OVERVIEW: 
 * 	<b>DrawAndGuessGUI</b> is an trigger that generate a start game welcome panel
 *  GUI window with all config ready
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class DrawAndGuessGUI {
	private StartGameFrame startFrame;
	private GameSessionFrame gameSessionFrame;
	private Controller controller;
	
	/**
	 * Constructor for init the welcome screen for the overall game
	 * this is the trigger of the all GUI component
	 * @param controller the controller to be assigned in further operation
	 */
	public DrawAndGuessGUI(Controller controller) {
		FlatDarkLaf.setup();
		// setup default font
		this.controller = controller;
		try {	  
		    Font font = Font.createFont(Font.TRUETYPE_FONT,
		                                new File("./res/gui/font/windows_command_prompt.ttf"));
			Font plain = font.deriveFont(Font.PLAIN, 25);
			UIManager.getLookAndFeelDefaults().put("defaultFont", plain);
		} catch (FontFormatException | IOException e) {
			JOptionPane.showMessageDialog(null, 
					"Fail to load game resource: please check resource", 
					"Oops...", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
		}
		
    	// window
    	this.startFrame = new StartGameFrame(controller);
	}
	
	/**
	 * helper function: triggered when there is a time sync needed
	 * @param timeInterval the current time to be synced
	 */
	public void timerUpdate(int timeInterval) {
		if (gameSessionFrame != null) gameSessionFrame.timerStart(timeInterval);
	}
	
	/**
	 * helper function for activating the drawing function of the drawboard
	 */
	public void activate() {
		if (gameSessionFrame != null) gameSessionFrame.activate();
	}
	
	/**
	 * helper function for deactivating the drawing function of the drawboard
	 */
	public void deactivate() {
		if (gameSessionFrame != null) gameSessionFrame.deactivate();
	}
	
	/**
	 * helper function for updateing the current chat history info
	 */
	public void updateStats() {
		if (gameSessionFrame != null) gameSessionFrame.updateStats();
	}
	
	/**
	 * helper function when there is some thing wrong with the connection and need to tell users
	 * @param msg the error message
	 * @param title the title of the notification window
	 */
	public void interrupt(String msg, String title) {
		startFrame.failConnection(msg, title);
	}
	
	/**
	 * helper function for set the specific entry color of the drawboard
	 * @param x the x position of the entry
	 * @param y the y position of the entry
	 * @param targetColor the color to be assigned at (x, y)
	 */
	public void setEntryColor(int x, int y, Color targetColor) {
		if (gameSessionFrame != null) gameSessionFrame.setEntryColor(x, y, targetColor);
	}
	
	/**
	 * helper function for allowing a game session to start and enter the drawing room
	 */
	public void startGame() {
		this.startFrame.startGame();
		this.gameSessionFrame = new GameSessionFrame(this.controller);
	}
	
	/**
	 * helper function: set the prompting at left corner of the drawboard
	 * @param secretWord the first real answer of the word
	 * @param secretHint the second category of the word
	 */
	public void setPrompt(String secretWord, String secretHint) {
		if (gameSessionFrame != null) gameSessionFrame.setPrompt(secretWord, secretHint);
	}
	
	/**
	 * helper function when we need to go back to the main menu when
	 * 1. a server left the game and
	 * 2. game cannot continue for some reason
	 */
	public void returnMainMenu() {
		if (this.gameSessionFrame != null) {
			this.gameSessionFrame.dispose();
			this.startFrame = new StartGameFrame(controller);
		}
			
	}
	
	/**
	 * helper function for clearing the chessboard operation is set from remote
	 */
	public void clear() {
		if (gameSessionFrame != null) gameSessionFrame.clear();
	}
	
//	/**
//	 * the main for generating the window
//	 * @param args no args is needed
//	 */
//    public static void main(String args[ ]){	
//    	FlatDarkLaf.setup();
//    	try {	  
//		    Font font = Font.createFont(Font.TRUETYPE_FONT,
//		                                new File("./res/gui/font/windows_command_prompt.ttf"));
//			Font plain = font.deriveFont(Font.PLAIN, 25);
//			UIManager.getLookAndFeelDefaults().put("defaultFont", plain);
//		} catch (FontFormatException | IOException e) {
//			JOptionPane.showMessageDialog(null, 
//					"Fail to load game resource: please check resource", 
//					"Oops...", JOptionPane.ERROR_MESSAGE);
//					e.printStackTrace();
//		}
//    	
//    	// window
//    	new GameSessionFrame(null);
//    } 
}