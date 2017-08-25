import org.apache.http.client.utils.URIBuilder;

public class CityQuery implements Query {

    private final String city;
    private final String code;

    public CityQuery(String city){
        this.city = city;
        this.code = null;
    }

    public CityQuery(String city, String code) {
        this.city = city;
        this.code = code;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(city);
        if(code != null){
            sb.append(",").append(code);
        }
        return sb.toString();

    }

    @Override
    public void applyToBuilder(URIBuilder builder) {
        builder.addParameter("q", toString());
    }
}
