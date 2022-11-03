package com.udacity.webcrawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParserFactory;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Pattern;

public class CrawlSearchRecursiveAction extends RecursiveAction {
    private final String url;
    private final Clock clock;
    private final Instant deadline;
    private final int maxDepth;
    private final PageParserFactory parserFactory;
    private final List<Pattern> ignoredUrls;
    private final Map<String, Integer> counts;
    private final Set<String> visitedUrls;

    public CrawlSearchRecursiveAction (String url, Clock clock, Instant deadline, int maxDepth,
                                       PageParserFactory parserFactory, List<Pattern> ignoredUrls,
                                               Map<String, Integer> counts, Set<String> visitedUrls){
        this. url=url;
        this.clock=clock;
        this.deadline=deadline;
        this.maxDepth=maxDepth;
        this.parserFactory=parserFactory;
        this.ignoredUrls=ignoredUrls;
        this.counts=counts;
        this.visitedUrls=visitedUrls;

    }
    @Override
    protected void compute() {
        if(maxDepth > 0 &&clock.instant().isBefore(deadline)){
            List<String> subLinks = processingAndGetSubUrls(url);
            List<CrawlSearchRecursiveAction> crawlSearchRecursiveActionList= createSubTasks(subLinks);
            ForkJoinTask.invokeAll(crawlSearchRecursiveActionList);

        }

    }

    private List<String> processingAndGetSubUrls(String urlLink) {
        List<String> subLinks = new ArrayList<>();

        if (maxDepth == 0 || clock.instant().isAfter(deadline)) {
            return subLinks;
        }
        for (Pattern pattern : ignoredUrls) {
            if (pattern.matcher(urlLink).matches()) {
                return subLinks;
            }
        }
       /* if (visitedUrls.contains(urlLink)) {
            return subLinks;
        }
        visitedUrls.add(urlLink);*/
        if(!visitedUrls.add(urlLink)){
            return subLinks;
        }
        PageParser.Result result = parserFactory.get(urlLink).parse();
        for (Map.Entry<String, Integer> e : result.getWordCounts().entrySet()) {
            if (counts.containsKey(e.getKey())) {
                counts.put(e.getKey(), e.getValue() + counts.get(e.getKey()));
            } else {
                counts.put(e.getKey(), e.getValue());
            }
        }
        subLinks.addAll(result.getLinks());
        return subLinks;
    }
    private List<CrawlSearchRecursiveAction> createSubTasks(List<String> subLinks) {

        List<CrawlSearchRecursiveAction>crawlSearchRecursiveActionList=new ArrayList<>();

        for(String subUrl:subLinks){
            CrawlSearchRecursiveAction crawlSearchRecursiveAction=new CrawlSearchRecursiveAction(subUrl,clock,deadline,maxDepth-1,parserFactory,
                                                                                                    ignoredUrls,counts,visitedUrls);
            crawlSearchRecursiveActionList.add(crawlSearchRecursiveAction);

            }   return crawlSearchRecursiveActionList;
        }
    }

