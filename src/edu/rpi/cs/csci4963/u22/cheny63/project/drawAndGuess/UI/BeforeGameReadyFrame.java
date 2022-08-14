package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.NumberFormatter;


public class BeforeGameReadyFrame extends JDialog {
	private static final long serialVersionUID = 1L;
	
	// IP info
	private String IPAdress;
    private JPanel IPInfo;
	private JLabel ipAddressLabel;
	private JTextField ipAddressInfo;
	private JFormattedTextField portAddressInfo;

	// game info
	private String inputFilePath;
	private JPanel gameInfo;
	private JFileChooser dictionaryFilechooser;
	
	
	/**
	 * helper function: init the large dot separator for IP setting panel
	 */
	private void initIPInfo() {
		this.IPInfo.setLayout(new GridBagLayout());
		this.IPInfo.setBorder(BorderFactory.createCompoundBorder( // set border
				 BorderFactory.createTitledBorder("IP info:"),
				 BorderFactory.createEmptyBorder(10,10,10,10)));
		this.ipAddressInfo = new JTextField();
		// global gridbag const for this func
		GridBagConstraints gridBag = new GridBagConstraints();
		
		// IP Address
		JPanel ipPanel = new JPanel();
		JPanel allPanel = new JPanel();
		ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.X_AXIS));
		allPanel.setLayout(new BoxLayout(allPanel, BoxLayout.Y_AXIS));
		this.ipAddressLabel = new JLabel("Your IP Address: ");		
		
		JCheckBox host = new JCheckBox("Use localhost");
		host.addItemListener(new ItemListener() {    
			public void itemStateChanged(ItemEvent e) {                 
				if (e.getStateChange()==1) {
					ipAddressInfo.setEnabled(false);
					try {
						BeforeGameReadyFrame.this.IPAdress = InetAddress.getLocalHost().toString().split("/")[1];
					} catch (UnknownHostException e1) {
						JOptionPane.showMessageDialog(BeforeGameReadyFrame.this, "Failed to find localhost info", "Oops...", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					ipAddressInfo.setEnabled(true);
				}
			}    
         });
		ipPanel.add(ipAddressInfo);
		allPanel.add(host);
		allPanel.add(ipPanel);
		allPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// port
	    JLabel portAddressLabel = new JLabel("The port: ");
	    NumberFormat portFormat = NumberFormat.getInstance();
	    portFormat.setGroupingUsed(false);
	    NumberFormatter portFormatter = new NumberFormatter(portFormat);
	    portFormatter.setValueClass(Integer.class);
	    portFormatter.setMinimum(0);
	    portFormatter.setMaximum(65535);
	    portFormatter.setAllowsInvalid(false);
	    portAddressInfo = new JFormattedTextField(portFormatter);
	    
	    // auto assign default
	    try {
	    	portAddressInfo.setText("8189");
			portAddressInfo.commitEdit();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "Failed to assign a port", "Oops...", JOptionPane.ERROR_MESSAGE);
		}
	    
	    this.portAddressInfo.setHorizontalAlignment(JFormattedTextField.CENTER);    
	    this.ipAddressInfo.setHorizontalAlignment(JFormattedTextField.CENTER);  
	    // construction
	    gridBag.fill = GridBagConstraints.HORIZONTAL;
	    gridBag.ipady = 15;
	    gridBag.ipadx = 120;
	    gridBag.weightx = 10;
	    gridBag.gridx = 0;
	    gridBag.gridy = 0;
	    this.IPInfo.add(this.ipAddressLabel, gridBag);  
	    gridBag.gridy = 1;
	    this.IPInfo.add(allPanel, gridBag);
	    gridBag.gridy = 2;   
	    this.IPInfo.add(portAddressLabel, gridBag);
	    gridBag.gridy = 3;
	    this.IPInfo.add(portAddressInfo, gridBag);
	}
	
	/**
	 * action function: used when file input action is triggered
	 */
	private void getInputFile() {
		if (dictionaryFilechooser == null) { // open file chooser
        	dictionaryFilechooser = new JFileChooser();
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
	
	
	private void initGameSetting() {
		this.gameInfo.setLayout(new BoxLayout(this.gameInfo, BoxLayout.Y_AXIS));
		this.gameInfo.setBorder(BorderFactory.createCompoundBorder( // set border
				 BorderFactory.createTitledBorder("Game info:"),
				 BorderFactory.createEmptyBorder(10,10,10,10)));
		
		
		JPanel dictionaryPanel = new JPanel();
		JPanel usernamePanel = new JPanel();
		JLabel usernameLabel = new JLabel("Username: ");	
		JLabel dictionaryPathLabel = new JLabel("Dictionary path: ");
		JButton selectFile = new JButton("Select...");
		JTextField dictLocatioinField = new JTextField();
		JTextField usernameField = new JTextField();
		dictLocatioinField.setPreferredSize(new Dimension(320, 30));
		usernameField.setPreferredSize(new Dimension(380, 30));
		dictLocatioinField.setEditable(false);
		
		dictLocatioinField.setHorizontalAlignment(JFormattedTextField.CENTER);    
		usernameField.setHorizontalAlignment(JFormattedTextField.CENTER); 
		
		selectFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
    			getInputFile();
    			if (inputFilePath != null) 
    				dictLocatioinField.setText(inputFilePath);
            }
        });
		
		dictionaryPanel.add(dictionaryPathLabel);
		dictionaryPanel.add(dictLocatioinField);
		dictionaryPanel.add(selectFile);
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		this.gameInfo.add(usernamePanel);
		this.gameInfo.add(dictionaryPanel);
	}
	
	public BeforeGameReadyFrame(JFrame parent) {
		super(parent, "Input some necessary info as a host...");
		this.IPInfo = new JPanel();    // store IP Info
		this.gameInfo = new JPanel();  // store Game Info
		JPanel allContent = new JPanel();
		allContent.setLayout (new BoxLayout (allContent, BoxLayout.Y_AXIS));    
		
		initIPInfo();
		allContent.add(IPInfo);
		initGameSetting();
		allContent.add(gameInfo);
		this.add(allContent);
		//Display the window.       
		this.setSize(600, 400);
		this.setLocationRelativeTo(null); // set window centre
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		
	}

	public static void main(String args[ ]){	
		 new BeforeGameReadyFrame(null);
	 } 
}
