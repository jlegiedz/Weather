
import sda.code.model.WeatherModel;
import retrofit2.Response;
import java.io.IOException;
import java.util.Optional;

public class GeoQuery implements Query {
    private final double lat;
    private final double lon;

    public GeoQuery(double lat, double lon)
    {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public Optional<WeatherModel> execute(OpenWeather api, String apiKey) throws IOException {
        Response<WeatherModel> response = api.weatherByCoord(apiKey,lat, lon).execute();
        if(response.isSuccessful()){
            return Optional.ofNullable(response.body());
        }
        else{
            System.out.println(response.errorBody());
            return Optional.empty();
        }
    }
}
