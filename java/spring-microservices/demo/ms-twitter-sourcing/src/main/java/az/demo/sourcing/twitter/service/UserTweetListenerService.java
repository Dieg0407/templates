package az.demo.sourcing.twitter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class UserTweetListenerService implements StatusListener {
    private final AtomicLong messages = new AtomicLong();

    private final int maxToProcess;
    private final UserTweetExtractorService extractorService;
    private final IUserTweetPublisherService publisherService;

    public UserTweetListenerService(@Value("${service.max-to-process}") int maxToProcess,
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
            log.trace( "message arrived: {}", status.getId() );
            publisherService.publish( extractorService.fromTwitterStatus( status ) );
        }
        catch (Exception e) {
            log.error("Failed to publish tweet", e);
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {

    }

    public long getNumberOfMessages() {
        return messages.get();
    }

    @Override
    public void onException(Exception e) {
        log.error("Failed on listen", e);
    }
}
