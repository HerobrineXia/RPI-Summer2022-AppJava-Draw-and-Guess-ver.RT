package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;


import java.awt.event.KeyListener;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.StringUtil;
import java.awt.event.KeyEvent;

/**
* Address Input Listener
* @author Kevin Xia
* @version 1.0
*/
public class AddressInputListener implements KeyListener{
    /**
     * Disable input that is not a part of integer or if the integer length reach the max length
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if(!StringUtil.validAddress(String.valueOf(e.getKeyChar()))){
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}