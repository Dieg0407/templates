package az.demo.sourcing.twitter.service;

import twitter4j.Status;

import java.util.concurrent.atomic.AtomicLong;

public class UserTweetListenerService {
    private final AtomicLong messages = new AtomicLong();

    private final int maxToProcess;
    private final UserTweetExtractorService extractorService;

    public UserTweetListenerService(int maxToProcess, UserTweetExtractorService extractorService) {
        this.maxToProcess = maxToProcess;
        this.extractorService = extractorService;
    }

    public void onStatus(Status status) {
        if ( messages.addAndGet(1) > maxToProcess )
            return;

        extractorService.fromTwitterStatus( status );
    }

    public long getNumberOfMessages() {
        return messages.get();
    }
}
