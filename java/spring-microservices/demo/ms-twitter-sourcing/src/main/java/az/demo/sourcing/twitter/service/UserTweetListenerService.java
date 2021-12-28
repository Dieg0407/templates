package az.demo.sourcing.twitter.service;

import lombok.extern.slf4j.Slf4j;
import twitter4j.Status;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class UserTweetListenerService {
    private final AtomicLong messages = new AtomicLong();

    private final int maxToProcess;
    private final UserTweetExtractorService extractorService;
    private final IUserTweetPublisherService publisherService;

    public UserTweetListenerService(int maxToProcess,
                                    UserTweetExtractorService extractorService,
                                    IUserTweetPublisherService publisherService) {
        this.maxToProcess = maxToProcess;
        this.extractorService = extractorService;
        this.publisherService = publisherService;
    }

    public void onStatus(Status status) {
        if ( messages.addAndGet(1) > maxToProcess )
            return;
        try {
            publisherService.publish( extractorService.fromTwitterStatus( status ) );
        }
        catch (Exception e) {
            log.error("Failed to publish tweet", e);
        }
    }

    public long getNumberOfMessages() {
        return messages.get();
    }
}
