package slava0135.fuzz3;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class UrlParser {
    public static boolean parse(String url) throws IllegalArgumentException {
        List<String> supportedSchemes = Arrays.asList("http", "https");
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            String host = uri.getHost();

            if (scheme == null || !supportedSchemes.contains(scheme)) {
                throw new IllegalArgumentException("Scheme must be one of " + supportedSchemes);
            }
            if (host == null || host.isEmpty()) {
                throw new IllegalArgumentException("Host must be non-empty");
            }

            // Do something with the URL
            return true;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format", e);
        }
    }
}
