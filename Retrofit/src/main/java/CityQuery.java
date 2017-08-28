import retrofit2.Response;
import sda.code.model.WeatherModel;
import java.io.IOException;
import java.util.Optional;

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

        return city + (code != null ? "," + code : "");
        }


    // api - OpenWeather object created in Main
    // apiKey- constant
    // Class retorfit2.Response<T> - an HTTP response

    @Override
    public Optional<WeatherModel> execute(OpenWeather api, String apiKey) throws IOException {
        Response<WeatherModel> response = api.weatherByQuery(apiKey, toString()).execute();
        if(response.isSuccessful()){
            //body() : The deserialized(from json to java) response body of a successful response.
            return Optional.ofNullable(response.body());
        }
        else{
            System.out.println(response.errorBody());
            return Optional.empty();
        }
    }
}
