package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

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
	
	public DrawAndGuessGUI(Controller controller) {
		FlatDarkLaf.setup();
    	// window
    	new StartGameFrame(controller);
	}
	
//	/**
//	 * the main for generating the window
//	 * @param args no args is needed
//	 */
//    public static void main(String args[ ]){	
//    	FlatDarkLaf.setup();
//    	// window
//    	new StartGameFrame();
//    } 
}