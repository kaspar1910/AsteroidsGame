package dk.sdu.cbse.scoreservice;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreController {

    private final AtomicInteger score = new AtomicInteger(0);

    @GetMapping("/score")
    public int getScore() {
        return score.get();
    }

    @GetMapping("/score/add")
    public int addScore(@RequestParam(value = "points", defaultValue = "1") int points) {
        return score.addAndGet(points);
    }

    @GetMapping("/score/reset")
    public int resetScore() {
        score.set(0);
        return score.get();
    }
}