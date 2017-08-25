import org.apache.http.client.utils.URIBuilder;

public class GeoQuery implements Query {
    private final double lat;
    private final double lon;

    public GeoQuery(double lat, double lon)
    {
        this.lat = lat;
        this.lon = lon;
    }
    public void applyToBuilder(URIBuilder builder){
           builder.addParameter("lat", String.valueOf(lat))
                .addParameter("lon", String.valueOf(lon));
    }
}
