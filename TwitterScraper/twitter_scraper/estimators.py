

def at_least_n(tweet_list, n, **kwargs):
   """
   Gathers a static number of tweets.

   Args:
      tweet_list: List of tweet_structs.
      n: Number of tweets to gather.
      **kwargs: Unused. Placeholder for any kwargs needed by processing functions.

   Returns:
      The number of tweets left until the static number is reached.
   """
   return n - len(tweet_list)


def until_datetime(tweet_list, until_datetime, **kwargs):
   """
   Gathers tweets up until a given datetime.

   Args:
      tweet_list: List of tweet_structs.
      until_datetime: Once reached, no more tweets will be gathered.
      **kwargs: Unused. Placeholder for any kwargs needed by processing functions.
   """
   # Promoted tweets don't follow strict time ordering
   latest_tweet_index = 0
   while tweet_list[latest_tweet_index].promoted == True:
      latest_tweet_index += 1

   if(tweet_list[-1].datetime < until_datetime):
      return 0

   # Assuming the rate of tweets is roughly constant
   time_sampled = tweet_list[latest_tweet_index].datetime - tweet_list[-1].datetime
   total_time = tweet_list[latest_tweet_index].datetime - until_datetime
   portion_sampled = time_sampled / total_time

   mult = 1 / portion_sampled

   # Cap the estimation based on current length
   # Prevents heavy activity early on from causing an excessively large estimate
   return min(mult * len(tweet_list), len(tweet_list))