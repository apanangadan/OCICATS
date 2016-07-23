package com.soi;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Path("/tweets")
public class Tweet {
	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	
	MongoClient mongoClient;
	DB db;
	DBCollection tweetsColl;
	List<DBObject> tweetList;
	
	public Tweet(){	
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = mongoClient.getDB( "twitterTransportDb" );
		tweetsColl = db.getCollection("tweetsCollection");
		tweetList = new ArrayList<DBObject>();
	}
	
	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTweets(@PathParam("userId") String userId){
		BasicDBObject allQuery = new BasicDBObject();
		BasicDBObject query = new BasicDBObject();
		query.put("user.id_str",userId);
		DBCursor cursor = tweetsColl.find(query);
		while(cursor.hasNext()) {
//			System.out.println(cursor.next());
			tweetList.add(cursor.next());
		}
		Gson gson = new Gson();
		Type type = new TypeToken<List<DBObject>>() {}.getType();
		String json = gson.toJson(tweetList,type);
		return json;
	}
	
	@GET
	@Path("/mention/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTweetsbyMention(@PathParam("userId") String userId){
		BasicDBObject query = new BasicDBObject();
		query.put("retweeted_status",new BasicDBObject("$exists",false));
		query.append("entities.user_mentions.id_str",userId);
		DBCursor cursor = tweetsColl.find(query);
		while(cursor.hasNext()) {
//			System.out.println(cursor.next());
			tweetList.add(cursor.next());
		}
		Gson gson = new Gson();
		Type type = new TypeToken<List<DBObject>>() {}.getType();
		String json = gson.toJson(tweetList,type);
		return json;
	}
}