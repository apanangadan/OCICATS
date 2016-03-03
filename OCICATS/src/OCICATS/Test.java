package OCICATS;

import java.io.IOException;
import java.util.List;

import twitter4j.Status;

public class Test {
	public static void main(String[] args) {
		SearchTweets s = new SearchTweets();
		List<Status>  tweets = s.searchTweets();
		
		ExportExcel e = new ExportExcel();
		e.exportExcel(tweets);
		
		Datasets d = new Datasets();
		TopicModel tm = new TopicModel();
		try {
			String path = d.tweetsFilter(tweets);
			tm.topicModeling(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
