Twitter-Transport-Stream
The process once started will remain open till system shus down
It will automatically restart if it fails in between
Log is stored in file /home/apanagadan/SpreadOfInfluence/transportTweetsLog.log

Data is stoed in MongoDD
DataBase : twitterTransportDb
Collection : tweetsCollection

Start the process
1. Got to directory /etc/init
2. Run process
	$ start twitter-transport-stream
3. to check status of the process
	$ status twitter-transport-stream
4. To stop process
	$ stop twitter-transport-stream

Need to restart the process manually if system restarts