from pymongo import MongoClient
import datetime
import time
import json

client = MongoClient('localhost', 27017)
db = client['twitterTransportDb']  #contains updated db in which original tweets have correct retweet count
collection = db['tweetsCollection']
n=0;
for tweet in collection.find():
    if tweet.get('retweeted_status'):               #check if it is a retweet 
        collection.find_and_modify({"$and":[{"id":{"$eq":tweet["retweeted_status"]["id"]}},
        { "retweet_count":{"$lt":tweet["retweeted_status"]["retweet_count"]}}]},
        {"$set":{"retweet_count":tweet["retweeted_status"]["retweet_count"]}}, 
        upsert=False)
        n=n+1;   
        print "updated ", tweet["retweeted_status"]["id"]
print "Updated ", n, "tweets";

print "values updated successfully!!"