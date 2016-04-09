from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary

from bs4 import BeautifulSoup

import pandas as pd

import collections
from collections import namedtuple
import datetime
import time
import math
import string
import types


tweet_struct = namedtuple('tweet_struct',
                           [
                              'tweet_id',
                              'user_id',
                              'display_name',
                              'profile_name',
                              'avatar',
                              'datetime',
                              'language',
                              'text',
                              'retweets',
                              'likes',
                              'location',
                              'promoted'
                           ])
"""
A simple structure that holds the tweet information.

It includes only the information available from the basic tweet listing page.

Attributes:
   tweet_id: String id. Not an integer because it can get very large.
   user_id: String id. Not an integer because it can get very large.
   display_name: String name. Represents the bolded name shown on the tweet. May not be a valid Twitter handle.
   profile_name: String twitter handle. Does not start with '@'.
   avatar: String url. Points to the image.
   datetime: Pandas Timestamp. Represents the time the tweet was posted. Given to the minute.
   language: String language code marked on the tweet.
   text: String text. This is the entire contents of the tweet.
   retweets: Integer. This may not be an accurate count. After a thousand retweets, it is given with at most 2 significant digits.
   likes: Integer. This may not be an accurate count. After a thousand likes, it is given with at most 2 significant digits.
   location: String location. This may take many forms. It can be as broad as a full country name or coordinates.
   promoted: Boolean. Twitter sometimes moves particular tweets to the top of the page. Such tweets are considered promoted.
"""

class TweetScraper:
   """
   Used to scrape infinite-scrolling Twitter pages.

   Attributes:
      remaining_estimator: Function (tweet_struct_list, \*args, \*\*kwargs) -> estimated number of tweets remaining to be scraped.
      driver_make: Function () -> valid selenium webdriver.
      processor_list: List of functions (tweet_struct_collection, \*\*kwargs) -> modified_tweet_struct_collection. Will be applied in order after scraping is complete.
      base_twitter_search_url: String containing the url for an unparameterized search for Twitter.
      append_search_terms: String or list of strings for terms to be added to any search.
      display_postfixes: Dictionary mapping the short alphabetic postfix used for large numbers.
      wait: Number in seconds used to set the implicit_wait value for the driver.
      sleep: Number in seconds to sleep between scrolls.
      retry_end: Maximum number of attempts to get the page to continue scrolling before giving up.
   """
   remaining_estimator = None
   driver_maker = None
   processor_list = None

   _tweets_per_scroll_est = 1
   base_twitter_search_url = "http://twitter.com/search?q="
   append_search_terms = None

   display_postfixes = {'K' : 1000, 'M' : 1000000}

   wait = None
   sleep = None
   retry_end = None


   def __init__(self,
                remaining_estimator,
                driver_maker = webdriver.Firefox,
                wait = 5,
                sleep = 3,
                retry_end = 3,
                processor_list = [],
                append_search_terms = []):
      """Inits TweetScraper to a basic state. Requires an estimation function."""
      self.remaining_estimator = remaining_estimator
      self.driver_maker = driver_maker
      self.processor_list = processor_list
      self.append_search_terms = append_search_terms
      self.wait = wait
      self.sleep = sleep
      self.retry_end = retry_end

   def _make_twitter_url(self, search_terms):
      url = self.base_twitter_search_url + search_terms

      append_search_terms = self.append_search_terms

      if(isinstance(append_search_terms, str) or not isinstance(append_search_terms, collections.Iterable)):
         append_search_terms = [append_search_terms]

      for term in append_search_terms:
         url += " " + term

      return url

   @staticmethod
   def _get_element_attribute_by_class_if_exists(e, elem_type, class_name, attribute):
      try:
         return e.find(elem_type, {"class" : class_name})[attribute]
      except:
         return None

   def _parse_display_number(self, s):
      if s == '':
         return 0
       
      if s[-1] in string.digits:
         return int(s)
       
      mult = self.display_postfixes[s[-1]]
      parts = s[:-1].split(".")
      val = int(parts[0]) * mult
      if(len(parts) > 1):
         val += int(parts[1]) * int(mult/10)
      return val

   # Pandas is only used here to convert a datetime to a pandas.Timestamp
   # A bit heavy, but easier to do it here than change the dtype later
   def _element_to_tweet_struct(self, e):
      return tweet_struct(
         tweet_id = e["data-tweet-id"],
         user_id = e["data-user-id"],
         display_name = e["data-name"],
         profile_name = e["data-screen-name"],
         avatar = e.find("img", {"class" : "avatar"})["src"],
         datetime = pd.Timestamp(datetime.datetime.strptime(e.find("a", {"class" : "tweet-timestamp"})["title"], '%I:%M %p - %d %b %Y')),
         language = e.find("p", {"class" : "TweetTextSize"})["lang"],
         text = e.find("p", {"class" : "TweetTextSize"}).text,
         retweets = self._parse_display_number(e.find("button", {"class" : "js-actionRetweet"}).find("span", {"class" : "ProfileTweet-actionCountForPresentation"}).text),
         likes = self._parse_display_number(e.find("button", {"class" : "js-actionFavorite"}).find("span", {"class" : "ProfileTweet-actionCountForPresentation"}).text),
         location = self._get_element_attribute_by_class_if_exists(e, "span", "Tweet-geo", "title"),
         promoted = e.has_attr("data-relevance-type"))

   def _process(self, initial, **kwargs):
    result = initial
    
    for f in self.processor_list:
        result = f(result, **kwargs)
    
    return result

   def get_tweets(self, search_terms, *args, **kwargs):
      """
      Scrapes tweets, and applies the processing steps.

      Args:
         search_terms: The search term to be passed to Twitter.
         *args: Extra arguments for remaining_estimator function.
         **kwargs: Extra arguments for remaining_estimator function and all processing steps.

      Returns:
         All the scraped tweets after all processing steps have been applied.
      """
      driver = self.driver_maker()
      driver.implicitly_wait(self.wait)
    
      scrolls = 1
      loads_until_check = 0
    
      tweets = []
    
      retry_end_count = 0
    
      try:
         driver.get(self._make_twitter_url(search_terms))
         time.sleep(self.sleep)
        
         while True:
            for i in range(0, loads_until_check):
               driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
               time.sleep(self.sleep)
               if driver.execute_script("return window.innerHeight + window.scrollY >= document.body.scrollHeight"):
                  if retry_end_count >= self.retry_end:
                     break
                  else:
                     retry_end_count += 1
                     driver.execute_script("window.scrollTo(0, 0);")
                     time.sleep(self.sleep)

                     # No point rechecking estimation after the first time
                     if retry_end_count > 1:
                        continue
               else:
                  scrolls += 1
                  retry_end_count = 0
            
            # Use original-tweet here because tweet attribute is shared with one or two non-tweets
            tweets = [self._element_to_tweet_struct(e) for e in BeautifulSoup(driver.page_source, "lxml").findAll("div", {"class" : "original-tweet"})]
            _tweets_per_scroll_est = math.floor(len(tweets) / scrolls) if scrolls > 0 else _tweets_per_scroll_est
            
            remaining_tweets = self.remaining_estimator(tweets, *args, **kwargs)
            
            if remaining_tweets <= 0:
               break;

            loads_until_check = math.ceil(remaining_tweets / _tweets_per_scroll_est)
            
      finally:
         driver.quit()
    
      return self._process(tweets, **kwargs)