package SOI;
import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class searchingFunction {

	//private String[] negativeIdicators = {":(", "Slow", "Bad"};
	
	private String searchTweet = "metro, bad";// = "California, transportation, OR metro, OR train, OR bus, OR slow, OR bad, OR :(, OR dirty, OR dislike";
	
	public searchingFunction(String newSearch){
		searchTweet = newSearch;
		
	}
	
	public List<Status> searchTweets(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("WB5N2gKA6pjywToN69cKcdxWI");
		cb.setOAuthConsumerSecret("VTh11Zwv75JV4nhBKx3GY6xe49njqtIO05cTCnlt0ZO33WGuwE");
		cb.setOAuthAccessToken("77468340-rfizWTt4kDG1d4OIi18A8JjJkoLVEQtxqarz4SMEQ");
		cb.setOAuthAccessTokenSecret("F9D8M9YDY8kEuLtaAWJiCZfge8JmH2Nz3hBlKksJj8lvj");

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query(searchTweet).lang("en");
		Query nextq = null;
		query.setCount(100);
		query.setResultType(twitter4j.Query.ResultType.mixed);
		
		List<Status> rs = new ArrayList<Status>();

		try {
			QueryResult result = twitter.search(query);
			QueryResult nextrs = result;
			rs = nextrs.getTweets();
			
		/*	for (Status status : result.getTweets()) {
				System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			}*/

			for (int i = 0; i < 10; i++) {
				if (nextrs.nextQuery() != null) {
					nextq = nextrs.nextQuery();
					nextrs = twitter.search(nextq);
					rs.addAll(nextrs.getTweets());
					
					/*for (Status status : nextrs.getTweets()) {
						System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
					}*/
				} else {
					break;
				}
			}
			
			
		}

		catch (TwitterException te) {
			System.out.println("Couldn't connect: " + te);
		}
		return rs;
		
	}
	
	
}
