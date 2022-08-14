package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/** 
 *  OVERVIEW: 
 * 	<b>BattleshipGUIMain</b> is an trigger that generate a chessboard panel
 *  GUI window with all config ready
 *
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class DrawAndGuessGUI {
	/**
	 * the main for generating the window
	 * @param args no args is needed
	 */
    public static void main(String args[ ]){	 	
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (InstantiationException|
       		 UnsupportedLookAndFeelException|
       		 ClassNotFoundException|
       		 IllegalAccessException exceptionFromStyle) {
       	    exceptionFromStyle.printStackTrace();
        }
    	
    	// window
    	// new StudioChessboardFrame();
    	new StartGameFrame();
    	//new holdConnection();
    } 
}