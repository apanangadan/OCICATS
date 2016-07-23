package com.soi;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.DBObject;

import netscape.javascript.JSObject;

@Path("/influential")
public class Influential {
	public Influential(){
		
	}	
	
	@GET
	@Path("/retweet/{topic}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getInfluentialPeople(@PathParam("topic") String topic ){
		Rank t= new Rank();
		String tweets;
		if(topic.toLowerCase().equals("high speed rail")){
			tweets = t.getTweetsByFilter("hsr");
		}else if(topic.toLowerCase().equals("metro expresslanes")){
			tweets = t.getTweetsByFilter("metro");
		}else if(topic.toLowerCase().equals("bicycle lanes")){
			tweets = t.getTweetsByFilter("bicycle");
		}else if(topic.toLowerCase().equals("all")){
			tweets = t.getAllTweets();
		}
		List<DBObject> users = t.rankByRetweet();
		
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<DBObject>>() {}.getType();
		String json = gson.toJson(users,type);
//		new JSObject(users);
		return json;
	}
	
	@GET
	@Path("/mention/{topic}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getInfluentialPeopleByMentions(@PathParam("topic") String topic){
		Rank t= new Rank();
		String tweets;
		if(topic.toLowerCase().equals("high speed rail")){
			tweets = t.getTweetsByFilter("hsr");
		}else if(topic.toLowerCase().equals("metro expresslanes")){
			tweets = t.getTweetsByFilter("metro");
		}else if(topic.toLowerCase().equals("bicycle lanes")){
			tweets = t.getTweetsByFilter("bicycle");
		}else if(topic.toLowerCase().equals("all")){
			tweets = t.getAllTweets();
		}
		List<DBObject> users = t.rankByMentions();
		
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<DBObject>>() {}.getType();
		String json = gson.toJson(users,type);
//		new JSObject(users);
//		String tw = "{\"contributors\": null, \"truncated\": false, \"text\": \"Never thought I'd experience a lockdown due to a school shooting. Stay vigilant everyone #UCLA #UCLAshooting\", \"is_quote_status\": false, \"in_reply_to_status_id\": null, \"id\": 738056854924730369, \"favorite_count\": 4, \"entities\": {\"symbols\": [], \"user_mentions\": [], \"hashtags\": [{\"indices\": [89, 94], \"text\": \"UCLA\"}, {\"indices\": [95, 108], \"text\": \"UCLAshooting\"}], \"urls\": []}, \"retweeted\": false, \"coordinates\": null, \"source\": \"<a href=\"http://twitter.com/download/android\" rel=\"nofollow\">Twitter for Android</a>\", \"in_reply_to_screen_name\": null, \"in_reply_to_user_id\": null, \"retweet_count\": 0, \"id_str\": \"738056854924730369\", \"favorited\": false, \"user\": {\"follow_request_sent\": null, \"has_extended_profile\": true, \"profile_use_background_image\": true, \"default_profile_image\": false, \"id\": 81772689, \"profile_background_image_url_https\": \"https://pbs.twimg.com/profile_background_images/453977464234987520/EL9FA0ko.jpeg\", \"verified\": false, \"profile_text_color\": \"666666\", \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/735904144234450944/HDxRokPJ_normal.jpg\", \"profile_sidebar_fill_color\": \"252429\", \"entities\": {\"description\": {\"urls\": []}}, \"followers_count\": 119, \"profile_sidebar_border_color\": \"FFFFFF\", \"id_str\": \"81772689\", \"profile_background_color\": \"000000\", \"listed_count\": 1, \"is_translation_enabled\": false, \"utc_offset\": -25200, \"statuses_count\": 1187, \"description\": \"love & kindness are the only way\", \"friends_count\": 23, \"location\": \"Los Angeles, CA\", \"profile_link_color\": \"7FDBB6\", \"profile_image_url\": \"http://pbs.twimg.com/profile_images/735904144234450944/HDxRokPJ_normal.jpg\", \"following\": null, \"geo_enabled\": true, \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/81772689/1462653774\", \"profile_background_image_url\": \"http://pbs.twimg.com/profile_background_images/453977464234987520/EL9FA0ko.jpeg\", \"screen_name\": \"thanichan8\", \"lang\": \"en\", \"profile_background_tile\": true, \"favourites_count\": 2152, \"name\": \"Thani\", \"notifications\": null, \"url\": null, \"created_at\": \"Mon Oct 12 05:41:29 +0000 2009\", \"contributors_enabled\": false, \"time_zone\": \"Pacific Time (US & Canada)\", \"protected\": false, \"default_profile\": false, \"is_translator\": false}, \"geo\": null, \"in_reply_to_user_id_str\": null, \"lang\": \"en\", \"created_at\": \"Wed Jun 01 17:17:21 +0000 2016\", \"in_reply_to_status_id_str\": null, \"place\": null, \"metadata\": {\"iso_language_code\": \"en\", \"result_type\": \"recent\"}}";
		return json;
	}
}
