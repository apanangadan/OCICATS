package OCICATS;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


public class SearchTweets {
	
	
	public List<Status> searchTweets(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("lKbAU1HGL9gvhDrrQFZJcWmPL");
		cb.setOAuthConsumerSecret("BzgQDhlTTlabeV1GpXl03r3PG8l3Dzvsz62Hf0rIF11Te0lgLw");
		cb.setOAuthAccessToken("1294010874-3ox2NoXAHJFKxWClvkRxdq1SerkOAlgU9UnkFKa");
		cb.setOAuthAccessTokenSecret("0s031kt3jyET8OKEYKNlhZNEFinPotZR0OQ3WSN7Ulcto");

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query("lax airport").lang("en");
		Query nextq = null;
		query.setCount(100);
		
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