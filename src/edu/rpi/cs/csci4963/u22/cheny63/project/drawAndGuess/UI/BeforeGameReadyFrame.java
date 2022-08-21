package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.NumberFormatter;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control.Controller;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.StringUtil;

/**
 * BeforeGameReadyFrame for setting all information for the window when clicking "start the game"
 * all variable will be send to the next screen also using this dialog
 * 
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class BeforeGameReadyFrame extends JDialog {
	private static final long serialVersionUID = 1L;
	private boolean isHost;
	private Controller controller;
	
	// IP info
    private JPanel IPInfo;
	private JLabel ipAddressLabel;
	private JTextField ipAddressInfo;
	private JFormattedTextField portAddressInfo;

	// game info
	String inputFilePath;
	private JPanel gameInfo;
	private JFileChooser dictionaryFilechooser;
	private JTextField dictLocatioinField;
	private JTextField usernameField;
	
	
	/**
	 * helper function: init the large dot separator for IP setting panel
	 */
	private void initIPInfo() {
		this.IPInfo.setLayout(new BoxLayout(this.IPInfo, BoxLayout.Y_AXIS));
		this.IPInfo.setBorder(BorderFactory.createCompoundBorder( // set border
				 BorderFactory.createTitledBorder("IP info:"),
				 BorderFactory.createEmptyBorder(10,10,10,10)));
		this.ipAddressInfo = new JTextField();
		
		// IP Address
		JPanel ipPanel = new JPanel();
		JPanel ipPanelAll = new JPanel();
		JPanel portPanel = new JPanel();
		ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.Y_AXIS));
		this.ipAddressLabel = new JLabel((isHost? "Your IP address: " : "Host's IP address: "));		
		this.ipAddressInfo.setPreferredSize(new Dimension(380, 60));
		this.ipAddressInfo.setText(this.controller.getAddress());
		JCheckBox host = new JCheckBox("Use localhost");
		host.addItemListener(new ItemListener() {    
			public void itemStateChanged(ItemEvent e) {                 
				if (e.getStateChange()==1) {
					ipAddressInfo.setEnabled(false);
					ipAddressInfo.setText(StringUtil.getInetAddress());
				}else {
					ipAddressInfo.setEnabled(true);
				}
			}    
         });
		host.setSelected(isHost);
		host.setEnabled(!isHost);
		
		// port
	    JLabel portAddressLabel = new JLabel("The port:              ");
	    NumberFormat portFormat = NumberFormat.getInstance();
	    portFormat.setGroupingUsed(false);
	    NumberFormatter portFormatter = new NumberFormatter(portFormat);
	    portFormatter.setValueClass(Integer.class);
	    portFormatter.setMinimum(0);
	    portFormatter.setMaximum(65535);
	    portFormatter.setAllowsInvalid(false);
	    portAddressInfo = new JFormattedTextField(portFormatter);
	    portAddressInfo.setPreferredSize(new Dimension(380, 60));
	    portAddressInfo.setText(this.controller.getPort());
	    // auto assign default
	    try {
	    	portAddressInfo.setText(controller.getPort());
			portAddressInfo.commitEdit();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "Failed to assign a port", "Oops...", JOptionPane.ERROR_MESSAGE);
		}
	    
	    this.portAddressInfo.setHorizontalAlignment(JFormattedTextField.CENTER);    
	    this.ipAddressInfo.setHorizontalAlignment(JFormattedTextField.CENTER);
	    
	    
	    ipPanel.add(host);
		ipPanel.add(ipAddressInfo);
		ipPanelAll.add(ipAddressLabel);
		ipPanelAll.add(ipPanel);
	    portPanel.add(portAddressLabel);
	    portPanel.add(portAddressInfo);
	    
	    this.IPInfo.add(ipPanelAll);
	    this.IPInfo.add(portPanel);
	}
	
	/**
	 * action function: used when file input action is triggered
	 * @param controller the controller to be assigned in further operation
	 */
	private void getInputFile(Controller controller) {
		if (dictionaryFilechooser == null) { // open file chooser
        	dictionaryFilechooser = new JFileChooser(controller.getFileConfig());
        	dictionaryFilechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        	dictionaryFilechooser.setAcceptAllFileFilterUsed(false);
        	dictionaryFilechooser.addChoosableFileFilter(new FileFilter() { // only txt is allowed
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
                }

                @Override
                public String getDescription() {
                    return "CSV dictionary file (*.csv)";
                }
            });
        }
		// case: get correct file
        if (dictionaryFilechooser.showOpenDialog(BeforeGameReadyFrame.this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(dictionaryFilechooser.getSelectedFile()))) {
            	this.inputFilePath = dictionaryFilechooser.getSelectedFile().getAbsolutePath();
            	// checkFileLegalAndUpdate(this.inputFilePath);
            } catch (IOException exp) {
                exp.printStackTrace();
                JOptionPane.showMessageDialog(BeforeGameReadyFrame.this, "Failed to read file", "Oops...", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
	
	/**
	 * Helper function for constructor when init the basic game setting panel
	 * @param controller the controller to be assigned in further operation
	 */
	private void initGameSetting(Controller controller) {
		this.gameInfo.setLayout(new BoxLayout(this.gameInfo, BoxLayout.Y_AXIS));
		this.gameInfo.setBorder(BorderFactory.createCompoundBorder( // set border
				 BorderFactory.createTitledBorder("Game info:"),
				 BorderFactory.createEmptyBorder(10,10,10,10)));		
		
		JPanel dictionaryPanel = new JPanel();
		JPanel usernamePanel = new JPanel();
		JLabel usernameLabel = new JLabel("Username:            ");	
		JLabel dictionaryPathLabel = new JLabel("Dictionary path:               ");
		JButton selectFile = new JButton("Select...");
		dictLocatioinField = new JTextField();
		usernameField = new JTextField(controller.getNameConfig());
		dictLocatioinField.setPreferredSize(new Dimension(320, 60));
		usernameField.setPreferredSize(new Dimension(380, 60));
		dictLocatioinField.setEditable(false);
		dictLocatioinField.setHorizontalAlignment(JFormattedTextField.CENTER);  
		File file = new File(controller.getFileConfig());
		dictLocatioinField.setText(this.inputFilePath = (file.isFile()? controller.getFileConfig() : ""));	
		usernameField.setHorizontalAlignment(JFormattedTextField.CENTER); 
		
		selectFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
    			getInputFile(controller);
    			if (inputFilePath != null) 
    				dictLocatioinField.setText(inputFilePath);
            }
        });
		
		usernameField.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            if (usernameField.getText().length() >= 32 ) // limit to 32 characters
	                e.consume();
	        }
	    });
		
		// construct the window
		selectFile.setEnabled(isHost);
		dictionaryPanel.add(dictionaryPathLabel);
		dictionaryPanel.add(dictLocatioinField);
		dictionaryPanel.add(selectFile);
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		this.gameInfo.add(usernamePanel);
		this.gameInfo.add(dictionaryPanel);
	}
	
	/**
	 * Constructor for building the before game ready frame, set all variable and UI ready here
	 * @param parent the start game frame for transimitting all info from there
	 * @param controller the controller to be assigned in further operation
	 * @param isHost true if current window is for host
	 */
	public BeforeGameReadyFrame(StartGameFrame parent, Controller controller, boolean isHost) {
		super(parent, String.format("Input some necessary info as a %s...", (isHost? "host" : "client")));		
		parent.setAllOperationEnable(false);
		this.isHost = isHost;
		this.controller = controller;
		this.IPInfo = new JPanel();    // store IP Info
		this.gameInfo = new JPanel();  // store Game Info
		JPanel allContent = new JPanel();
		JPanel buttonContent = new JPanel();
		// allContent.setLayout (new BoxLayout (allContent, BoxLayout.Y_AXIS));    
		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() { // action for confirming the setting
            public void actionPerformed(ActionEvent e) {
            	if (isHost && (inputFilePath == null || inputFilePath == "")) {
            		JOptionPane.showMessageDialog(BeforeGameReadyFrame.this, "You need to select a dictionary file", 
            				                      "Oops...", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	
            	if (BeforeGameReadyFrame.this.usernameField.getText().equals("")) {
            		JOptionPane.showMessageDialog(BeforeGameReadyFrame.this, "You need to have a non-empty username", 
		                      "Oops...", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            		
            	if (isHost)
					try {
						controller.onStartServer(BeforeGameReadyFrame.this.usernameField.getText(), 
							                 Integer.parseInt(BeforeGameReadyFrame.this.portAddressInfo.getText()), inputFilePath);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(BeforeGameReadyFrame.this, "Fail to read the dictionary.", "Oops...", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				else
            		controller.onClientStart(BeforeGameReadyFrame.this.usernameField.getText(), 
            				BeforeGameReadyFrame.this.ipAddressInfo.getText(), 
            				Integer.parseInt(BeforeGameReadyFrame.this.portAddressInfo.getText()));
            }
        });
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	parent.setAllOperationEnable(true);
            	BeforeGameReadyFrame.this.dispose();
            }
        });
		buttonContent.add(confirm);
		buttonContent.add(cancel);
		
		this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
            	parent.setAllOperationEnable(true);
                e.getWindow().dispose();
            }
        });
		
		initIPInfo();
		this.IPInfo.setPreferredSize(new Dimension(780, 250));
		allContent.add(IPInfo);
		initGameSetting(controller);
		this.gameInfo.setPreferredSize(new Dimension(780, 250));
		allContent.add(gameInfo);
		
		
		allContent.add(buttonContent);
		this.add(allContent);
		//Display the window.       
		this.setSize(800, 640);
		this.setLocationRelativeTo(null); // set window centre
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		
	}
}
