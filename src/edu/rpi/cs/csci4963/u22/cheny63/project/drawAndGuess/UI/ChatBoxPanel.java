package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ChatBoxPanel extends OpaqueJPanel{
	private static final long serialVersionUID = 1L;
	
	
	// GUI Component
	private JButton sendMsg;
	private JEditorPane historyPanel;
	public ChatBoxPanel() throws FontFormatException, IOException {
		Font goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		goreRegular  = goreRegular.deriveFont(Font.PLAIN, 45);
		
		this.sendMsg = new JButton("GO");
		JEditorPane writerPanel = new JEditorPane();
		this.historyPanel = new JEditorPane();
		JScrollPane historyScroll = new JScrollPane(historyPanel);	
		JScrollPane writerScroll = new JScrollPane(writerPanel);
		JPanel allContent = new JPanel();
		JPanel writerContent = new JPanel();
		
		writerPanel.setBackground(new Color(253, 253, 253));
		this.sendMsg.setFont(goreRegular);
		this.sendMsg.setBackground(new Color(236, 164, 145));
		this.sendMsg.setForeground(new Color(253, 253, 253));
		this.sendMsg.setBorderPainted(false);
		this.sendMsg.setFocusable(false);
		historyPanel.setEditable(false);
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
	}
	
	
	public static void main(String[] args) throws FontFormatException, IOException {
		JFrame testframe = new JFrame();
		// avoid image displace case,  not necessary
		testframe.add(new ChatBoxPanel());
		testframe.setSize(620, 640);
		testframe.setLocationRelativeTo(null); // set window centre
		testframe.setAlwaysOnTop(true); // since it is important, let it top
		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testframe.setVisible(true);
	}
}
