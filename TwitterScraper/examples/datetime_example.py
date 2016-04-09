from twitter_scraper import scraper
import twitter_scraper.estimators as est
import twitter_scraper.processors as proc
from datetime import datetime

some_term = "some term"
start_date = datetime(2016, 4, 6)
end_date = datetime(2016, 4, 9)


ts = scraper.TweetScraper(est.until_datetime,
                          append_search_terms=["lang:en", "include:retweets"],
                          processor_list=[proc.list_to_df, proc.df_add_am_pm, proc.df_between_datetimes, proc.df_reindex])

tweets = ts.get_tweets(some_term, until_datetime = start_date, pstart_datetime = start_date, pend_datetime = end_date)