from pymongo import MongoClient
from tweepy import Stream
from tweepy import OAuthHandler
from tweepy.streaming import StreamListener
import datetime
import time
import json

## authentication
consumer_key="WB5N2gKA6pjywToN69cKcdxWI"
consumer_secret="VTh11Zwv75JV4nhBKx3GY6xe49njqtIO05cTCnlt0ZO33WGuwE"
access_token="77468340-rfizWTt4kDG1d4OIi18A8JjJkoLVEQtxqarz4SMEQ"
access_token_secret="F9D8M9YDY8kEuLtaAWJiCZfge8JmH2Nz3hBlKksJj8lvj"

class Listener(StreamListener):
    tweetCount = 0
    client = MongoClient('localhost', 27017)
    db = client['twitterTransportDb']
    collection = db['tweetsCollection']
    def on_data(self,data):
        try:
            Listener.tweetCount+=1
            tweet = json.loads(data)
            Listener.collection.insert(tweet)
            if tweet.get('retweeted_status'):               #check if it is a retweet 
                Listener.collection.find_and_modify({"$and":[{"id":{"$eq":tweet["retweeted_status"]["id"]}},
                    { "retweet_count":{"$lt":tweet["retweeted_status"]["retweet_count"]}}]},
                    {"$set":{"retweet_count":tweet["retweeted_status"]["retweet_count"]}}, 
                    upsert=False) 
            if tweet["coordinates"] != None:
                print Listener.tweetCount,". ",tweet["created_at"]," : ", tweet["user"]["name"].encode("utf-8")," tweeted at ",tweet["coordinates"]["coordinates"], ", User's Location: ",tweet["user"]["location"]
            else:
                print Listener.tweetCount,". ",tweet["created_at"]," : ", tweet["user"]["name"].encode("utf-8")," tweeted, User's Location: ",tweet["user"]["location"] 
            return True
            
        except BaseException,e:
            print datetime.datetime.now(),': failed ondata',str(e)
            time.sleep(5)
            
    def on_error(self,status):
        print status

auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

print "started listening to Transport tweets"
print "Keywords: '#caltrans','caltrans','California transportation','California traffic','California cars','California rail','California trains','California freight','high speed rail California','#hsr California','bullet train California','expresslanes California','#expresslanes California','fastrak California','transponder California','#bicyclelanes California','bicycle lanes California'"
twitterStream=Stream(auth,Listener())
twitterStream.filter(track=['#caltrans','caltrans','California transportation','California traffic','California cars','California rail','California trains','California freight'
,'high speed rail California','#hsr California','bullet train California','expresslanes California','#expresslanes California','fastrak California','transponder California',
'#bicyclelanes California','bicycle lanes California'])

# Run python code in background
# $ nohup python -u twitter-transport-stream.py >> transportTweetsLog.out &
# Check process number to kill
# $ ps -ef|grep twitter-transport-stream
# Kill Process
# $ kill process_number