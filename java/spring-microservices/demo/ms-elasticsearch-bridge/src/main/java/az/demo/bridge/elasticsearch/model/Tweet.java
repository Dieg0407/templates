package az.demo.bridge.elasticsearch.model;


public record Tweet(
        long id,
        String user,
        String email,
        String text,
        boolean retweet,
        int favoriteCount
) {
}
