package dk.sdu.cbse.collision;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ScoreClient {

    private static final String SCORE_SERVICE_URL = "http://localhost:8080";

    private final HttpClient client = HttpClient.newHttpClient();

    public void addPoints(int points) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SCORE_SERVICE_URL + "/score/add?points=" + points))
                .timeout(Duration.ofMillis(500))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .exceptionally(exception -> {
                    System.out.println("ScoreService is not running.");
                    return null;
                });
    }
}