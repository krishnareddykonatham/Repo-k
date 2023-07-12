import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortenedUrlRepository extends MongoRepository<ShortenedUrl, String> {
    ShortenedUrl findByShortUrl(String shortUrl);
}