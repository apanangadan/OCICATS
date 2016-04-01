package SOI;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import twitter4j.Status;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Button;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

/***
 * 
 * @author Nanwarin Chantarutai
 *
 */

public class Main 
{

	private JFrame frame;

	private JComboBox<String> listKeywords = new JComboBox<String>();
	private JComboBox<String> negativeTopicsLists = new JComboBox<String>();
	private JFileChooser directorySelect = new JFileChooser();

	private String[] keyWords = {"transit"};
	
	private String directoryFiles = "";
	
	private String[] negativeTopics = {"Please select", "Safety", "Timeliness", "Negative Tweets"};
	
	private String[] negativeSafety = {"crash", "collision", "traffic"};
	private String[] negativeTimeliness = {"slow", "minutes", "late", "traffic"};
	private String[] negativeTweets = {":(", "why", "worst", "ugh"};
	
	private String searchText = "California";
	private String searchKeyWords = "California";
	private String or = ", OR ";
	private String and = ", ";
	private JTextField directory = new JTextField(15);
	private final JPanel panel = new JPanel();
	
	private final Button searchButton = new Button("Search");
	private static Main window = new Main();
	private JTextField textField;
	private JTextField textField_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					window.frame.setVisible(true);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}
	
	public String getDirectoryPath()
	{
		return directoryFiles;
	}

	//create new search

	private void newSearch()
	{
		
		directoryFiles = directorySelect.getCurrentDirectory().toString();
			
		NewSearch newSearchObject = new NewSearch(directoryFiles, searchText);
		//newSearchObject.createFrame();
		System.out.println("SearchText" + searchText);
		newSearchObject.search();
	}
	
	private void searchText(){
		switch(negativeTopicsLists.getSelectedItem().toString()){
			case "Safety": 
				searchText = searchKeyWords;
				for(int i=0; i < negativeSafety.length; i++)
					searchText += or + negativeSafety[i];
				break;
			case "Timeliness" : 
				searchText = searchKeyWords;
				for(int i=0; i < negativeTimeliness.length; i++)
					searchText += or + negativeTimeliness[i];
				break;
			case "Negative Tweets" : 
				searchText = searchKeyWords;
				for(int i=0; i < negativeTweets.length; i++)
					searchText += or + negativeTweets[i];
				break;
			case "" :
				searchText = searchKeyWords;
				break;
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(204, 204, 255));
		frame.setBounds(100, 100, 523, 310);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int i = 0; i< negativeTopics.length; i++)
			negativeTopicsLists.addItem(negativeTopics[i]);
		
		for(int i = 0; i< keyWords.length; i++)
			listKeywords.addItem(keyWords[i]);
		searchButton.setActionCommand("newSearch\n");
		directorySelect.setMultiSelectionEnabled(true);
		directorySelect.setControlButtonsAreShown(false);
		
		directorySelect.setCurrentDirectory(new File("."));
		directorySelect.setDialogTitle("Please select directory to save files");
		directorySelect.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		directorySelect.setAcceptAllFileFilterUsed(false);
		directorySelect.setBounds(6, 205, 219, 46);
	
		directory.setText(directorySelect.getCurrentDirectory().toString());
		
		
		//panel.add(listKeywords);
		//panel.add(negativeTopicsLists);
		//panel.add(searchButton);
		//panel.add(directory);
		
		
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Please select key words");
		lblNewLabel.setBounds(6, 6, 157, 16);
		panel.add(lblNewLabel);
		
		JLabel lblKeyWords = new JLabel("Key Words");
		lblKeyWords.setBounds(6, 34, 99, 16);
		panel.add(lblKeyWords);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBounds(238, 48, 256, 69);
		textField.setEditable(false);
		textField.setText(searchText);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblSearchKeyWords = new JLabel("Search Key Words");
		lblSearchKeyWords.setBounds(240, 33, 128, 16);
		panel.add(lblSearchKeyWords);
		

		listKeywords.setBounds(6, 55, 187, 27);
		panel.add(listKeywords);
		
		JLabel lblNegativeTopics = new JLabel("Select Directory");
		lblNegativeTopics.setBounds(16, 193, 108, 16);
		panel.add(lblNegativeTopics);
		
		negativeTopicsLists.setBounds(6, 125, 187, 27);
		panel.add(negativeTopicsLists);
		
		
		//String[] keyWords = {"Metro", "California"};
		
		panel.add(directorySelect);
		
		//panel.add(button, panel);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton btnAdd = new JButton("Select");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchText();
				textField.setText(searchText);
			}
		});
		btnAdd.setBounds(6, 152, 117, 29);
		panel.add(btnAdd);
		
		JButton btnAdd_1 = new JButton("ADD"); // Add key words
		btnAdd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchKeyWords += and + listKeywords.getSelectedItem().toString();
				searchText();
				textField.setText(searchText);
			}
		});
		btnAdd_1.setBounds(6, 80, 117, 29);
		panel.add(btnAdd_1);
		
		JLabel label = new JLabel("Negative Topic");
		label.setBounds(6, 110, 108, 16);
		panel.add(label);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(238, 124, 117, 29);
		panel.add(btnSearch);

		
		listKeywords.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//text.setText((String) listKeywords.getSelectedItem());
				
			}
			
		});
		
		negativeTopicsLists.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//text.setText((String) listKeywords.getSelectedItem());
				
			}
		});
		
		btnSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				newSearch();
			}
		});
	}
}
