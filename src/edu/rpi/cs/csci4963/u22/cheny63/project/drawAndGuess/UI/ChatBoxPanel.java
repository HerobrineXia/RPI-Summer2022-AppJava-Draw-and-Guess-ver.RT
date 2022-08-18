package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control.Controller;

public class ChatBoxPanel extends OpaqueJPanel{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	// GUI Component
	private JButton sendMsg;
	private JTextArea historyText;
	
	public void updateChat() {
		LinkedList<String> history = this.controller.getChat();
		this.historyText.setText("");
		for (String line : history) {
			this.historyText.append(line + "\n");
		}
	}
	
	public ChatBoxPanel(Controller controller) throws FontFormatException, IOException {
		//Set font
		Font goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		goreRegular  = goreRegular.deriveFont(Font.PLAIN, 45);
		this.controller = controller;
		
		this.sendMsg = new JButton("GO");
		JEditorPane writerPanel = new JEditorPane();
		this.historyText = new JTextArea(20, 40);
		JPanel historyPanel = new JPanel();
		JScrollPane historyScroll = new JScrollPane(historyPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		JScrollPane writerScroll = new JScrollPane(writerPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
															   JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel allContent = new JPanel();
		JPanel writerContent = new JPanel();
		
				
		Action actionSend = new AbstractAction("Send") {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
				if (!writerPanel.getText().equals("")) {
            		controller.onPlayerSentMessage(writerPanel.getText());
            		writerPanel.setText("");
            	}
			}
		};
		
		this.sendMsg.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	actionSend.actionPerformed(e);	
			}
        });
		
		writerPanel.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					actionSend.actionPerformed(null);
					e.consume();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
		writerPanel.setBackground(new Color(253, 253, 253));
		this.sendMsg.setFont(goreRegular);
		this.sendMsg.setBackground(new Color(236, 164, 145));
		this.sendMsg.setForeground(new Color(253, 253, 253));
		this.sendMsg.setBorderPainted(false);
		this.sendMsg.setFocusable(false);
		this.historyText.setEditable(false);
		historyPanel.add(historyText);
		historyPanel.setBackground(new Color(191, 191, 191));
		allContent.setLayout(new BoxLayout(allContent, BoxLayout.Y_AXIS));
		writerContent.setLayout(new BoxLayout(writerContent, BoxLayout.X_AXIS));
		historyScroll.setPreferredSize(new Dimension(500, 500));
		writerPanel.setPreferredSize(new Dimension(500, 50));
		
		writerContent.add(writerScroll);
		writerContent.add(sendMsg);
		allContent.add(historyScroll);
		allContent.add(writerContent);
		this.add(allContent);
		this.updateChat();
	}
	
	
	public static void main(String[] args) throws FontFormatException, IOException {
		JFrame testframe = new JFrame();
		// avoid image displace case,  not necessary
		testframe.add(new ChatBoxPanel(null));
		testframe.setSize(620, 640);
		testframe.setLocationRelativeTo(null); // set window centre
		testframe.setAlwaysOnTop(true); // since it is important, let it top
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testframe.setVisible(true);
	}
}
