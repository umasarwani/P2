package com.udacity.webcrawler;

import com.udacity.webcrawler.json.CrawlResult;
import com.udacity.webcrawler.parser.PageParserFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A concrete implementation of {@link WebCrawler} that runs multiple threads on a
 * {@link ForkJoinPool} to fetch and process multiple web pages in parallel.
 */
final class ParallelWebCrawler implements WebCrawler {
  private final Clock clock;
  private final Duration timeout;
  private final int popularWordCount;
  private final ForkJoinPool pool;
  private final PageParserFactory parserFactory;
  private final int maxDepth;
  private final List<Pattern> ignoredUrls;

    @Inject
  ParallelWebCrawler(
          Clock clock,
          PageParserFactory parserFactory,
          @Timeout Duration timeout,
          @PopularWordCount int popularWordCount,
          @TargetParallelism int threadCount,
          @MaxDepth int maxDepth,
          @IgnoredUrls List<Pattern> ignoredUrls) {
    this.clock = clock;
    this.parserFactory=parserFactory;
    this.timeout = timeout;
    this.popularWordCount = popularWordCount;
    this.pool = new ForkJoinPool(Math.min(threadCount, getMaxParallelism()));
    this.maxDepth=maxDepth;
    this.ignoredUrls=ignoredUrls;
  }

  @Override
  public CrawlResult crawl(List<String> startingUrls) {
     System.out.println("============, within ParallelWebcrawler.crawl(...), tmeout=" + timeout);
      Instant deadline=clock.instant().plus(timeout);
   Map<String, Integer> counts = Collections.synchronizedMap(new HashMap<>());
   Set<String> visitedUrls =Collections.synchronizedSet(new HashSet<>());
   for(String url:startingUrls){
       CrawlSearchRecursiveAction crawlSearchRecursiveAction =new CrawlSearchRecursiveAction(
               url,clock,deadline,maxDepth,parserFactory,ignoredUrls,counts,visitedUrls);
       pool.invoke(crawlSearchRecursiveAction);
   }

//    return new CrawlResult.Builder().build();
      if (counts.isEmpty()){
          return new CrawlResult.Builder().setWordCounts(counts).setUrlsVisited(visitedUrls.size()).build();
      }else{
          return new CrawlResult.Builder().setWordCounts(WordCounts.sort(counts,popularWordCount)).setUrlsVisited(visitedUrls.size()).build();
      }

  }

  @Override
  public int getMaxParallelism() {
    return Runtime.getRuntime().availableProcessors();
  }
}
