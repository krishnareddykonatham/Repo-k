import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shortenedUrls")
public class ShortenedUrl {
    @Id
    private String id;
    private String originalUrl;
    private String shortUrl;
    private long createdAt;
    private long expiresIn;

    
}