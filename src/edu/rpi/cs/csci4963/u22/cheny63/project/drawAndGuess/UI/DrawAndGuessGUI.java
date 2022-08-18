package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

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
 * 	<b>BattleshipGUIMain</b> is an trigger that generate a chessboard panel
 *  GUI window with all config ready
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class DrawAndGuessGUI {
	private StartGameFrame startFrame;
	private GameSessionFrame gameSessionFrame;
	private Controller controller;
	
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
	
	public void activate() {
		if (gameSessionFrame != null) gameSessionFrame.activate();
	}
	
	public void deactivate() {
		if (gameSessionFrame != null) gameSessionFrame.deactivate();
	}
	
	public void updateChat() {
		if (gameSessionFrame != null) gameSessionFrame.updateChat();
	}
	
	public void interrupt(String msg, String title) {
		startFrame.failConnection(msg, title);
	}
	
	public void startGame() {
		this.startFrame.startGame();
		this.gameSessionFrame = new GameSessionFrame(this.controller);
	}
	
	public void returnMainMenu() {
		if (this.gameSessionFrame != null) {
			this.gameSessionFrame.dispose();
			this.startFrame = new StartGameFrame(controller);
		}
			
	}
	
	/**
	 * the main for generating the window
	 * @param args no args is needed
	 */
    public static void main(String args[ ]){	
    	FlatDarkLaf.setup();
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
    	new GameSessionFrame(null);
    } 
}