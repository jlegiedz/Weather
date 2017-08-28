import sda.code.model.WeatherModel;
import java.io.IOException;
import java.util.Optional;

// when implementing the execute method the method from OpenWeather is chosen
public interface Query {

// zwarca http baze pluz moj klucz
    Optional<WeatherModel> execute(OpenWeather api, String apiKey) throws IOException;
}
