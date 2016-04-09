from .scraper import tweet_struct
import pandas as pd


def list_to_df(l, **kwargs):
   """
   Converts a list of tweet_structs into a tweet_struct dataframe.

   Args:
      l: List of tweet_structs.
      **kwargs: Unused. Placeholder for any kwargs needed by processing functions.

   Returns:
      A dataframe containing all the tweets in l.
   """
   return pd.DataFrame.from_records(l, columns = tweet_struct._fields)

def df_add_am_pm(tweets_df, **kwargs):
   """
   Adds an AM/PM column to the dataframe, and fills in the appropriate value for each row.

   Args:
      tweets_df: The tweet_struct dataframe.
      **kwargs: Unused. Placeholder for any kwargs needed by processing functions.

   Returns:
      The dataframe in tweets_df, with the addition of an AM/PM columnm.
   """
   am_pm_series = tweets_df.apply(lambda row: pd.datetime.strftime(row["datetime"], "%p"), axis=1)
   am_pm_df = am_pm_series.to_frame(name="AM/PM")
   return tweets_df.join([am_pm_df])

def df_between_datetimes(tweets_df, pstart_datetime, pend_datetime, **kwargs):
   """
   Removes any tweets that did not occur betwen the given bounds.

   Args:
      tweets_df: The tweet_struct dataframe.
      pstart_datetime: The earliest datetime for which tweets will be retained. Inclusive.
      pend_datetime: The datetime after which tweets will be excluded. Exclusive.
      **kwargs: Unused. Placeholder for any kwargs needed by processing functions.

   Returns:
      The tweets_df dataframe with only the tweets that occured between the bounds given in pstart_datetime and pend_datetime.
   """
   return tweets_df[((tweets_df['datetime'] >= pstart_datetime) & (tweets_df['datetime'] < pend_datetime))]

def df_reindex(tweets_df, **kwargs):
   """
   Consolidates the dataframe index, so there are no missing values.

   Args:
      tweets_df: The tweet_struct dataframe.
      **kwargs: Unused. Placeholder for any kwargs needed by processing functions.

   Returns:
      The tweets_df dataframe with a consolidated index.
   """
   tweets_df.reset_index(inplace=True, drop=True)
   return tweets_df