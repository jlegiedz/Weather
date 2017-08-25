import org.apache.http.client.utils.URIBuilder;

public interface Query {

    void applyToBuilder(URIBuilder builder);
}
