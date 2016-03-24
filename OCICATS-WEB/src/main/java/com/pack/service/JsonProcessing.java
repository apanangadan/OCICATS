package com.pack.service;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhenglu on 3/23/16.
 */
public class JsonProcessing {

    /**
     * Store all tweets we find
     */
    static List<String> tweets = new ArrayList<String>();
    public static void main(String[] args) {


        String url = "https://twitter.com/i/search/timeline?vertical=news" +
                "&q=nba";

        getTweets(url, "nba");

        for(String tweet : tweets) {
            //System.out.println(tweet);
        }

        //System.out.println(tweets.size());
    }

    public static void getTweets(String url, String keyword) {

        String newUrl = url;

        String minid;
        String maxid = null;
        for(int i = 0; i < 2; i++) {
            System.out.println("1:"+newUrl);
            try {
                String doc = Jsoup.connect(newUrl).ignoreContentType(true).execute().body();
                doc = doc.replaceAll("u003e", ">");
                doc = doc.replaceAll("u003c", "<");
                doc = doc.replaceAll("\\\\n", "");
                doc = doc.replaceAll("\\\\", "");

                //System.out.println(doc);
                Pattern pattern = Pattern.compile("\"0\">[a-zA-Z][a-zA-Z ,.!?]+");
                Matcher matcher = pattern.matcher(doc);

                Pattern patternId = Pattern.compile("data-item-id=\"[0-9]{18}");
                Matcher matcherId = patternId.matcher(doc);


                List<String> ids = new ArrayList<String>();

                while(matcher.find()) {
                    String str = matcher.group();
                    tweets.add(str.substring(4, str.length()));

                }

                while(matcherId.find()) {
                    String strId = matcherId.group();
                    String newStrId = strId.substring(14, strId.length());
                    if(!ids.contains(newStrId)) {
                        ids.add(newStrId);
                    }
                }
                
                 minid = ids.get(i);
                if(i==0){
                	maxid = ids.get(ids.size()-1);
                }
                 
                
                

                for(String id : ids) {
                    System.out.println(id);
                }


//                System.out.println("minid= "+minid);
//                System.out.println("max= "+maxid);
                newUrl = "https://twitter.com/i/search/timeline?vertical=news" +
                        "&q=" + keyword +
                        "&max_postion=TWEET-" + maxid + "-" + minid;
                System.out.println("2:"+newUrl);
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }

        }
    }
}
