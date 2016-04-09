Estimator Rules
===============

An estimator may

   * Accept arguments from :py:meth:`twitter_scraper.scraper.TweetScraper.get_tweets` via :samp:`*args` or :samp:`**kwargs`.

An estimator must

   * Accept a list of :py:class:`twitter_scraper.scraper.tweet_struct` as the first parameter
   * Accept :samp:`**kwargs` as a parameter
      * Processors also receive arguments via :samp:`**kwargs` from :py:meth:`twitter_scraper.scraper.TweetScraper.get_tweets`
   * Return a number
   * Eventually return a number less than or equal to zero
      * Scraping will continue until this occurs, or no more tweets are being loaded

An estimator should

   * Be as accurate as possible, but error on the high side

      * Low estimates will incur significant extra costs with needless parsing of the source

      * High estimates will spend extra time loading unneeded tweets

See :py:mod:`twitter_scraper.estimators` for concrete examples.