Processor Rules
===============

A processor may

   * Accept arguments from :py:meth:`twitter_scraper.scraper.TweetScraper.get_tweets` via :samp:`**kwargs` .

   * Return anything

A processor must

   * Accept something from a previous processor as the first argument

      * The first processor must accept a list of :py:class:`twitter_scraper.scraper.tweet_struct` as the first parameter

   * Accept a :samp:`**kwargs` parameter

      * Arguments for all processors will be contained in :samp:`**kwargs`

A processor must not

   * Share an argument name with another processor or the estimator

      * Arguments for all processors, and potentially the estimator, will be contained in :samp:`**kwargs`