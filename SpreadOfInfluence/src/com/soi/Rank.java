package com.soi;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Rank {
	MongoClient mongoClient;
	DB db;
	DBCollection tweetsColl;
	List<DBObject> tweetList;
	private HashMap<DBObject,Integer> users;
	private HashMap<DBObject,Integer> userMentionsHash;
	public Rank(){
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = mongoClient.getDB("twitterTransportDb" );
		tweetsColl = db.getCollection("tweetsCollection");
		tweetList = new ArrayList<DBObject>();
		users = new HashMap<DBObject,Integer>();
		userMentionsHash = new HashMap<DBObject,Integer>();
	}
	public String getAllTweets(){
		BasicDBObject allQuery = new BasicDBObject();
		BasicDBObject query = new BasicDBObject();
		query.put("retweeted_status",new BasicDBObject("$exists",false));
		//Get only the original tweets, retweets are discarded.
		DBCursor cursor = tweetsColl.find(query);
		while (cursor.hasNext()) {
//			System.out.println(cursor.next());
			tweetList.add(cursor.next());
		}
//		list.add(myDoc);
//		System.out.println(myDoc);
		Gson gson = new Gson();
		Type type = new TypeToken<List<DBObject>>() {}.getType();
		String json = gson.toJson(tweetList, type);
		return json;
	}
	
	public String getTweetsByFilter(String filter){
		BasicDBObject allQuery = new BasicDBObject();
		BasicDBObject query = new BasicDBObject();
		query.put("retweeted_status",new BasicDBObject("$exists",false));
		if(filter == "hsr"){
			BasicDBObject q1 = new BasicDBObject("text", new BasicDBObject("$regex","high\\s*speed\\s*rail"));
			BasicDBObject q2 = new BasicDBObject("text", new BasicDBObject("$regex","hsr"));
			BasicDBObject q3 = new BasicDBObject("text", new BasicDBObject("$regex","bullet\\s*train"));
			BasicDBList orList = new BasicDBList();
			orList.add(q1);
			orList.add(q2);
			orList.add(q3);
			query.append("$or",orList);
					
		}else if(filter == "metro"){
			BasicDBObject q1 = new BasicDBObject("text", new BasicDBObject("$regex","expresslanes"));
			BasicDBObject q2 = new BasicDBObject("text", new BasicDBObject("$regex","fasttrack"));
			BasicDBObject q3 = new BasicDBObject("text", new BasicDBObject("$regex","transponder"));
			BasicDBList orList = new BasicDBList();
			orList.add(q1);
			orList.add(q2);
			orList.add(q3);
			query.append("$or",orList);
			
		}else if(filter == "bicycle"){
			BasicDBObject q1 = new BasicDBObject("text", new BasicDBObject("$regex","bicyclelanes"));
			BasicDBObject q2 = new BasicDBObject("text", new BasicDBObject("$regex","bicycle\\s*lanes"));
			BasicDBList orList = new BasicDBList();
			orList.add(q1);
			orList.add(q2);
			query.append("$or",orList);
		}
		
		DBCursor cursor = tweetsColl.find(query);
		while (cursor.hasNext()) {
//			System.out.println(cursor.next());
			tweetList.add(cursor.next());
		}
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<DBObject>>() {}.getType();
		String json = gson.toJson(tweetList, type);
		return json;
	}
	
	
	public List<DBObject> rankByRetweet(){
		
		for(DBObject tweet:tweetList){
			DBObject userData = (DBObject)tweet.get("user");
			BasicDBObject user = new BasicDBObject();
			String userName = (String)userData.get("name");
			user.append("name", userName).append("id_str",(String)userData.get("id_str"));
			
			// add new user to hashmap
			if(!users.containsKey(user)){
//				retweetCounts.put(status.getUser(), (long) 0);
				users.put(user, (Integer)0);
			}
			
			if((Integer)tweet.get("retweet_count") > 0){
				Integer retweetCount = users.get(user);
				users.put(user,retweetCount + (Integer)tweet.get("retweet_count"));
			}
		}
//		HashMap sortedUsers = sortByValues(users);
		List<DBObject> sortedUsers = sortByValues(users);
		return sortedUsers;
	}
	public List<DBObject> rankByMentions(){
		userMentionsHash = new HashMap<DBObject,Integer>();
//		BasicDBObject[] usersArray = new BasicDBObject[1000];
//		int i=0;
		for(DBObject tweet:tweetList){
			DBObject entities= (DBObject)tweet.get("entities");
			ArrayList<DBObject> userMentions = (ArrayList<DBObject>)entities.get("user_mentions");
			if(!userMentions.isEmpty()){
//				System.out.println(userMentions.get("id"));
				for(DBObject mention:userMentions){
					String userName = (String)mention.get("name");
					BasicDBObject user = new BasicDBObject();
					user.append("name", userName).append("id_str",(String)mention.get("id_str"));
					
					if(!userMentionsHash.containsKey(user)){
		//				retweetCounts.put(status.getUser(), (long) 0);
						userMentionsHash.put(user, (Integer)0);
					}
					Integer mentionCount = (Integer)userMentionsHash.get(user);
					userMentionsHash.put(user,mentionCount + (Integer)1);
//					i++;
				}
			}
			
			// add new user to hashmap
			
		}
//		HashMap sortedUsers = sortByValues(users);
		List<DBObject> sortedUsers = sortByValues(userMentionsHash);
		return sortedUsers;
	}
	
	private static List<DBObject> sortByValues(HashMap map){
		List<DBObject> userList = new ArrayList<DBObject>();
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
            }
		});
		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
            BasicDBObject user = (BasicDBObject)entry.getKey();
            user.append("total_retweets",entry.getValue());
            userList.add(user);
		}
//		printMap(sortedHashMap);
		for (DBObject user:userList){
			System.out.println(user.get("name") + "," +user.get("id_str")+" : " + user.get("total_retweets"));
		}
		return userList;
	}
	
	public static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
}