/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOI;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author Diana L
 */
public class retweetRankingFunction {
    
    private HashSet<Status> uniqueTweets = new HashSet<Status>();
    private HashMap<User,Long> retweetCounts = new HashMap<User,Long>();
    Map<User,Long> retweetRanking;
    
    public retweetRankingFunction(List<Status> tweets) {        
        filterUniqueTweets(tweets);
        filterUniqueUsers();
    }
    
    public Map<User,Long> retweetRankCount() {
        // list of users mapped to number of retweet counts, sorted according to retweet ranking in non-increasing order
        retweetRanking = sortByValues(retweetCounts);
        
        return retweetRanking;
    }
    
    public List<User> retweetRankList() {
        // list of users sorted according to retweet ranking in non-increasing order
        List<User> userRanking = new ArrayList<User>(retweetRanking.keySet());
        return userRanking;
    }
    
    /**
     * Filters out duplicate (re)tweets from specified list of tweets.
     * 
     * @param tweets 
     */
    private void filterUniqueTweets(List<Status> tweets) {
        for (Status stat : tweets) {
            if (stat.isRetweet()) {
                stat = stat.getRetweetedStatus();   // original tweet of retweet
            }
            uniqueTweets.add(stat);
        }
    }
    
    private void filterUniqueUsers() {
        for (Status status : uniqueTweets) {
            if(!retweetCounts.containsKey(status.getUser())) {
                retweetCounts.put(status.getUser(), (long) 0);
            }
            if(status.getRetweetCount() > 0) {
                long retweetCount = retweetCounts.get(status.getUser());
                retweetCounts.put(status.getUser(), retweetCount + status.getRetweetCount());
            }
        }
        
        
    }
    
    private static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
        List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
     
        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (-1)*(o1.getValue().compareTo(o2.getValue()));
            }
        });
     
        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on non-increasing order
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
     
        for(Map.Entry<K,V> entry : entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
     
        return sortedMap;
    }
}
