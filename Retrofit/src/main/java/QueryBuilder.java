import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.Query;
import sda.code.model.WeatherModel;
import static java.util.Objects.requireNonNull;


public class QueryBuilder {
    private final OpenWeather api;
    private final String apiKey;
    private GeoQuery geoQuery;
    private CityQuery cityQuery;
    private String units = "metric";
    private String langCode = "pl";

    public QueryBuilder(OpenWeather api, String apiKey) {
        this.api = requireNonNull(api);
        this.apiKey = requireNonNull(apiKey);
    }

    public QueryBuilder withQuery(GeoQuery geoQuery) {
        this.geoQuery = requireNonNull(geoQuery);
        return this;
    }
    public QueryBuilder withQuery(CityQuery cityQuery) {
        this.cityQuery = requireNonNull(cityQuery);
        return this;
    }

    public QueryBuilder withUnits(String units) {
        this.units = requireNonNull(units);
        return this;
    }

    public QueryBuilder withLangCode(String langCode) {
        this.langCode = requireNonNull(langCode);
        return this;
    }

    public void validate(){
        if(cityQuery != null && geoQuery != null){
            throw new IllegalStateException("Cannot pass two queries at once");
        }
        if(cityQuery == null && geoQuery == null){
            throw new IllegalArgumentException("At least one query has to be passed");
        }
    }

    public Call<WeatherModel> build(){
        // api-object type OpenWeather created in Main, weather-HTTP API from interface OpenWeather
        return api.weather(
                apiKey,
                cityQuery != null? cityQuery.toString() :null,
                geoQuery != null? geoQuery.getLat() : null,
                geoQuery != null? geoQuery.getLon() : null,
                units,
                langCode
        );

    }
}
