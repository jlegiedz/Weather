import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import sda.code.model.StringResponseHandler;
import sda.code.model.Weather;
import sda.code.model.WeatherModel;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;


// wersja z Optional java 6
public class Main {

    private static final String API_KEY  ="9699c91f2a9d4e70fe910f0fe8a9c385";
    private static final String BASE_URL ="http://api.openweathermap.org/data/2.5/weather";


    public static void main(String[] args) throws IOException {

//        URI lodz = buildUri(BASE_URL, API_KEY, new CityQuery("Lodz"));
//        System.out.println(lodz);
//        URI japan = buildUri(BASE_URL, API_KEY, new GeoQuery(35.011667, 135.768333));
//        System.out.println(japan);

//        if(japan == null || lodz == null){
//            return;
//        }
        // powuzsza wersja przerobiona na wersje z Optional
        while (true) {
            Optional<URI> japan = buildUri(BASE_URL, API_KEY, new GeoQuery(35.011667, 135.768333));
            Optional<URI> lodz = buildUri(BASE_URL, API_KEY, new GeoQuery(35.011667, 135.768333));
            if (!japan.isPresent()) {
                return;
            }
            System.out.println(japan);
            if (!lodz.isPresent()) {
                return;
            }
            System.out.println(lodz);

            // forecastJson musi zwracac cos typu Optional<String> bo japan jest typu Optional
            // zebym mogla uzyc get na japan lub lodz najpierw musze sprawdzic czy
            Optional<String> forcastJsonJapan = requestForecastJson(japan.get());
            if(forcastJsonJapan.isPresent()) {
             Optional<WeatherModel> forecastJapan = parseForecastJson(forcastJsonJapan.get());
                if(forecastJapan.isPresent())
                    printWeather(forecastJapan.get());
            }

            Optional<String> forcastJsonLodz = (requestForecastJson(lodz.get()));
            if(forcastJsonLodz.isPresent()){
                Optional<WeatherModel> forcastLodz = parseForecastJson(forcastJsonLodz.get());
                if(forcastLodz.isPresent()){
                    printWeather(forcastLodz.get());
                }

            }
        }
    }

    private static Optional<WeatherModel> parseForecastJson(String forcastJson) {
       try {
           Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
           return Optional.ofNullable(gson.fromJson(forcastJson, WeatherModel.class));
       }
       catch(JsonSyntaxException ex){
           System.out.println(ex.getMessage());
           return Optional.empty();
       }
    }


    private static void printWeather(WeatherModel forecastCity) {
        System.out.println(forecastCity.getName());
        System.out.println("Country: " + forecastCity.getSys().getCountry());
        for (int i = 0; i < forecastCity.getWeather().size() ; i++) {
            Weather w = forecastCity.getWeather().get(i);
            System.out.println(w.getDescription());
        }
        System.out.println(forecastCity.getMain().getTemp());
    }

    private static Optional<URI> buildUri(String baseUrl, String apikey, Query query) {
        try {
            URIBuilder builder = new URIBuilder(baseUrl)                      // baseUrl- zmienna wyzej przechowujacata http
                    .addParameter("appid", apikey)
                    .addParameter("units", "metric")
                    .addParameter("lang", "pl");

            query.applyToBuilder(builder);

            return Optional.of(builder.build());
        }
        catch(URISyntaxException e){
            System.err.println(e.getMessage());
            return Optional.empty();
        }

    }

    private static Optional<String> requestForecastJson(URI uri) throws IOException {
        String responseBody;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            //The GET method means retrieve whatever information (in the form of an entity) is identified by the Request-URI.
            HttpGet httpget = new HttpGet(uri);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new StringResponseHandler();
            responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
        //ofNullable jesli responseBody bedzie nullem to zwroci od razu Optional.empty
        return Optional.ofNullable(responseBody);
    }
}
