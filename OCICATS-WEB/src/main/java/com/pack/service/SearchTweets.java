package com.pack.service;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


public class SearchTweets {
	
	
	public List<Status> searchTweets(String queryContent){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("lKbAU1HGL9gvhDrrQFZJcWmPL");
		cb.setOAuthConsumerSecret("BzgQDhlTTlabeV1GpXl03r3PG8l3Dzvsz62Hf0rIF11Te0lgLw");
		cb.setOAuthAccessToken("1294010874-3ox2NoXAHJFKxWClvkRxdq1SerkOAlgU9UnkFKa");
		cb.setOAuthAccessTokenSecret("0s031kt3jyET8OKEYKNlhZNEFinPotZR0OQ3WSN7Ulcto");

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query(queryContent).lang("en");
		Query nextq = null;
		query.setCount(100);
		/*https://twitter.com/i/search/timeline?
		vertical=news
		&q=traffic
		&src=typd
		&composed_count=0
		&include_available_features=1
		&include_entities=1
		&include_new_items_bar=true
		&interval=44000
		&last_note_ts=623
		&latent_count=923
		&min_position=TWEET-710581329058988033-710649148652064768-BD1UO2FFu9QAAAAAAAAETAAAAAcAAAASAAAAAAAAAAAAAAAAAAAAgAAAAAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACAAAAAAAAAAA
		&oldest_unread_id=0*/
		
		/*https://twitter.com/i/search/timeline?
		vertical=news
		&q=traffic
		&src=typd
		&composed_count=0
		&include_available_features=1
		&include_entities=1
		&include_new_items_bar=true
		&interval=57000
		&last_note_ts=622
		&latent_count=904
		&min_position=TWEET-710581329058988033-710648700280778752-BD1UO2FFu9QAAAAAAAAETAAAAAcAAAASAAAAAAAAAAAAAAAAAAAAgAAAAAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACAAAAAAAAAAA
		&oldest_unread_id=0*/
		
		
		/*https://twitter.com/i/search/timeline?vertical=news&q=nba%20lang%3Aen%20since%3A2015-12-01%20until%3A2015-12-02&src=typd
		 * &include_available_features=1
		 * &include_entities=1
		 * &last_note_ts=672
		&max_position=TWEET-671841129940123649-671841203118305281-BD1UO2FFu9QAAAAAAAAETAAAAAcAAAASAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		 * &reset_error_state=false*/
		
		List<Status> rs = new ArrayList<Status>();

		try {
			QueryResult result = twitter.search(query);
			QueryResult nextrs = result;
			rs = nextrs.getTweets();
			
		/*	for (Status status : result.getTweets()) {
				System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			}*/

			for (int i = 0; i < 100; i++) {
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