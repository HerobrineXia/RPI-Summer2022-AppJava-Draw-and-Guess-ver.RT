package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control.Controller;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.SystemCheck;

public class ChatBoxPanel extends OpaqueJPanel{
	private static final long serialVersionUID = 1L;
	private java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
	private Controller controller;
	private Font goreRegular;
	
	// GUI Component
	private JButton sendMsg;
	private JEditorPane historyText;
	private JPanel chatContent;
	private JPanel titleContent;
	private DrawBoard board;
	private GameSessionFrame parent;
	
	// statusInfo
	private boolean isHost;
	private boolean isStart = false;
	private double scale = SystemCheck.isWindows()? 0.7 : 0.6;
	
	public void updateChat() {
		LinkedList<String> history = this.controller.getChat();
		this.historyText.setText(parsingText(history));
	}
	
	private String parsingText(LinkedList<String> history) {
		String res  = "";
		for (int i = 0; i < history.size(); i++) {
			res += history.get(i) + "\n";
			if (i % 2 == 1) res += "\n";
		}
		return res;
	}

	private void initTitle() {
		Font goreRegularTitleLarge  = goreRegular.deriveFont(Font.PLAIN, 70);
		
		titleContent = new JPanel();
		JTextPane currentDrawing = new JTextPane();
		JTextPane guessCandidate = new JTextPane();
		
		titleContent.setLayout(new BorderLayout());
		currentDrawing.setText("Currently drawing:");
		currentDrawing.setEditable(false);
		currentDrawing.setBackground(Color.WHITE);
		titleContent.setBackground(Color.WHITE);
		Font goreRegularTitleSmall  = goreRegular.deriveFont(Font.PLAIN, 22);
		currentDrawing.setFont(goreRegularTitleSmall);
		currentDrawing.setAlignmentY(Component.LEFT_ALIGNMENT);
		JButton startGame = new JButton("Click to Start");
		titleContent.add(currentDrawing, BorderLayout.NORTH);
		if (isHost && !isStart) {
			startGame.addActionListener(new java.awt.event.ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	controller.onStartGameServer();
	            	isStart = true;
	            	// titleContent.removeAll();
	            	initTitle();
	            	parent.timer.start();
				}
	        });
			startGame.setHorizontalAlignment(SwingConstants.LEFT);
			goreRegularTitleLarge  = goreRegular.deriveFont(Font.PLAIN, 50);
			startGame.setForeground(new Color(192, 0, 0));
			startGame.setLayout(null);
			startGame.setFont(goreRegularTitleLarge);
			startGame.setBackground(Color.WHITE);
			startGame.setBorderPainted(false);
			startGame.setFocusable(false);			
			titleContent.add(startGame, BorderLayout.CENTER);
		}else {
			titleContent.removeAll();
			guessCandidate.setText(!isStart ? "HOLD" : controller.getDrawerName());
			guessCandidate.setForeground(Color.BLACK);
			guessCandidate.setEditable(false);
			guessCandidate.setFont(goreRegularTitleLarge);
			guessCandidate.setBackground(Color.WHITE);
			guessCandidate.setAlignmentY(Component.LEFT_ALIGNMENT);
			titleContent.add(guessCandidate, BorderLayout.CENTER);
		}
	}
	
	private void initChat() {
		chatContent = new JPanel();
		JPanel writerContent = new JPanel();
		JEditorPane writerPanel = new JEditorPane();
		this.historyText = new JEditorPane();
		this.sendMsg = new JButton("GO");
		
		JScrollPane historyScroll = new JScrollPane(historyText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		JScrollPane writerScroll = new JScrollPane(writerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			   JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
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
		
		//writerPanel.setBackground(new Color(191, 191, 191));
		writerPanel.setBackground(Color.WHITE);
		writerPanel.setCaretColor(new Color(236, 164, 145));
		this.sendMsg.setFont(goreRegular);
		this.sendMsg.setBackground(new Color(236, 164, 145));
		this.sendMsg.setForeground(new Color(253, 253, 253));
		this.sendMsg.setBorderPainted(false);
		this.sendMsg.setFocusable(false);
		this.historyText.setEditable(false);
		historyScroll.setAutoscrolls(true);
		historyScroll.setBorder(BorderFactory.createEmptyBorder());
		writerScroll.setBorder(BorderFactory.createEmptyBorder());
		
		writerContent.setLayout(new BorderLayout());
		chatContent.setLayout(new BorderLayout());
		writerContent.setBackground(new Color(236, 164, 145));
		this.sendMsg.setPreferredSize(new Dimension(100, 100));
		historyScroll.setPreferredSize(new Dimension(500, (int)(toolkit.getScreenSize().height*scale)));
		historyText.setPreferredSize(new Dimension(500, 500));
		writerPanel.setPreferredSize(new Dimension(500, 100));
		writerContent.setPreferredSize(new Dimension(600, 100));
		
		writerContent.add(writerScroll, BorderLayout.WEST);
		writerContent.add(sendMsg, BorderLayout.EAST);
		chatContent.add(historyScroll, BorderLayout.NORTH);
		chatContent.add(writerContent, BorderLayout.SOUTH);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, toolkit.getScreenSize().height);
	}
	
	public ChatBoxPanel(Controller controller, GameSessionFrame parent) throws FontFormatException, IOException {
		//Set font
		this.goreRegular = Font.createFont(Font.TRUETYPE_FONT, new File("./res/gui/font/Gore Regular.otf"));
		goreRegular  = goreRegular.deriveFont(Font.PLAIN, 27);
		this.controller = controller;
		this.isHost = this.controller.isServer();
		
		JPanel allContent = new JPanel();
		allContent.setLayout(new BoxLayout(allContent, BoxLayout.Y_AXIS));
		
		initChat();
		initTitle();
				
		allContent.add(titleContent);
		allContent.add(chatContent);
		allContent.setBackground(Color.WHITE);
		this.add(allContent);
		this.updateChat();
	}

//	public static void main(String[] args) throws FontFormatException, IOException {
//		JFrame testframe = new JFrame();
//		// avoid image displace case,  not necessary
//		testframe.add(new ChatBoxPanel(null));
//		testframe.setSize(620, 640);
//		testframe.setLocationRelativeTo(null); // set window centre
//		testframe.setAlwaysOnTop(true); // since it is important, let it top
//		testframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		testframe.setVisible(true);
//	}
}
