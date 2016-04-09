Future Work
===========

The next step in this work is decoupling the page scrolling from the Python program.

This should be possible by moving the scrolling code into the browser with Javascript. One way to do this is

.. code-block:: python
   
   driver.execute_script("return window.setInterval(function(){window.scrollTo(0, document.body.scrollHeight);}, ms_poll_time)")

This has been explored briefly and seems quite promising. Care needs to be taken to keep the poll time slow enough that the browser is able to produce the current page source in a reasonable time.

Additionally, there needs to be some handling of the situation where no more tweets load. Although this may be caused by Twitter simply being unwilling to serve any more tweets, it can also result from the page simply getting stuck and not recognizing the scroll down. This mostly seems to happen when the scroll distance is low, and tweets are being loaded slowly.

When Twitter won't load any more tweets, the scraper needs to recognize this and stop
 Scrolling up and then back down resolves the issue. When the page simply got stuck, it can be fixed by scrolling back up and then down again.

If this extra logic is also implemented in Javascript, then it needs to have a way to signal Python that it is done. If it is done in Python it will be somewhat slower, although as long as it is done in parallel with the page source parsing it shouldn't be significant.

The benefits of this will be two-fold. The first and most important is that it should provide a significant speedup. Secondly, it will remove the need for estimations. Instead, similar functions can take their place that merely need to return a boolean, indicating if scraping should continue or not.

In the mean time, pushing for more aggressive :py:attr:`twitter_scraper.scraper.TweetScraper.sleep` times may help reduce the need for this extra work. There is some significant time spent communicating the requests, so effective values may be somewhat higher than what is set.