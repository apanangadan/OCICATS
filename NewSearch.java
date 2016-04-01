package SOI;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import twitter4j.Status;

public class NewSearch {

	public String timeStamp;  //For checking time when the search is created. 
	public String path;
	public String searchText;
	private JFrame newFrame;
	
	public NewSearch(String newPath, String newSearchText)
	{
		path = newPath;
		searchText = newSearchText;
	}
	
	public void createFrame()
	{
		Color bgColor = new Color(204, 204, 255);
		newFrame = new JFrame("New Search");
		newFrame.getContentPane().setForeground(new Color(0, 102, 102));
		newFrame.getContentPane().setBackground(new Color(204, 204, 255));
		newFrame.setBackground(new Color(0, 153, 51));
		newFrame.setBounds(500, 100, 514, 322);
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		newFrame.setVisible(true);
	
	}
	
	public void search()
	{
		searchingFunction search = new searchingFunction(searchText);
		List<Status>  tweets = search.searchTweets();
		
		ExportExcel e = new ExportExcel(path);
		e.exportExcel(tweets);
		
		Datasets d = new Datasets(path);
		TopicModel tm = new TopicModel(path);
		
		System.out.println(path);
		
		try {
			String path = d.tweetsFilter(tweets);
			tm.topicModeling(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
