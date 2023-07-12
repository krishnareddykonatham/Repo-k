import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class ShortenedUrlController {
    @Autowired
    private ShortenedUrlRepository shortenedUrlRepository;

    @PostMapping
    public ResponseEntity<ShortenedUrl> createShortenedUrl(@RequestBody ShortenedUrl shortenedUrl) {
        

        // Set expiration time to 5 minutes from the current time

        long currentTime = System.currentTimeMillis();
        shortenedUrl.setCreatedAt(currentTime);
        shortenedUrl.setExpiresIn(currentTime + 5 * 60 * 1000);

        ShortenedUrl savedUrl = shortenedUrlRepository.save(shortenedUrl);
        return ResponseEntity.ok(savedUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable("shortUrl") String shortUrl) {
        ShortenedUrl shortenedUrl = shortenedUrlRepository.findByShortUrl(shortUrl);
        if (shortenedUrl == null) {
            return ResponseEntity.notFound().build();
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime > shortenedUrl.getExpiresIn()) {
            return ResponseEntity.badRequest().body("URL has expired");
        }

        return ResponseEntity.ok(shortenedUrl.getOriginalUrl());
    }
}