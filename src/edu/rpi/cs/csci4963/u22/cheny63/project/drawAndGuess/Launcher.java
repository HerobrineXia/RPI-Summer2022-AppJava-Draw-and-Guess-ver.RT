package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control.Controller;

// import main.java.control.GameControl;

/**
* The launcher of the game
* @author Kevin Xia
* @version 1.0
*/
public class Launcher {
    /**
     * Launcher of the Game
     * @param args None
     */
    public static void main(String[] args){
        try{
            new Controller();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
