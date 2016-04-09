About
=====

TwitterScraper scrapes tweets from Twitter's infinite-scrolling pages.

There are three main phases.

1. Prompt Twitter to load additional tweets onto the page.

   * The prompt is done by scrolling the page down.
   * The average number of tweets loaded per scroll is calculated.
   * When receiving an estimated number of tweets remaining, the average tweets per scroll are used to perform multiple scrolls before rechecking the estimate.

2. Extract the tweets from the page source and estimate how many more are required. If more tweets are needed, go to 1.

   * This is not a fast step.
   * The estimates are used to avoid this step as much as possible.
   * :doc:`Rules for writing an estimator <est_rules>`

3. Apply a series of transformations to the collected tweets.

   * The series is ordered.
   * Attention may be needed to the order and datatypes.
   * :doc:`Rules for writing a processing step <proc_rules>`