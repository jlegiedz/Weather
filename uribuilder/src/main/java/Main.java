import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class Main {

    private static final String API_KEY  ="9699c91f2a9d4e70fe910f0fe8a9c385";
    private static final String BASE_URL ="http://api.openweathermap.org/data/2.5/weather";


    public static void main(String[] args) throws IOException {

        // dokumentacja OpenWeather: mozna uzyc URIBulildera zamiast url:

//        final String url = new StringBuilder()
//                .append(baseUrl)
//                .append("?appid=").append(apikey)
//                .append("&q=").append("Lodz")
//                .append("&units=").append("metric")
//                .append("&lang=").append("pl")
//                .toString();


        URI lodz = buildUri(BASE_URL, API_KEY, new CityQuery("Lodz"));
        System.out.println(lodz);
        URI japan = buildUri(BASE_URL, API_KEY, new GeoQuery(35.011667, 135.768333));
        if (japan == null) {
            return;
        }

        // jak sie url nie da zbudowac to strzelam focha i wychodze z programu
        if(japan == null || lodz == null){
            return;
        }
        String forcastJson = requestForecastJson(japan);
        WeatherModel forecastJapan = parseForecastJson(forcastJson);
        printWeather(forecastJapan);
        forcastJson = requestForecastJson(lodz);
        WeatherModel forecastLodz = parseForecastJson(forcastJson);
        printWeather(forecastLodz);
    }

    //deserialiacja- z jsona na obiekt javy
    private static WeatherModel parseForecastJson(String forcastJson) {
        //tworzenie parsera
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        //parsowanie z jsona do modelu za pomoca gsona: reader- to co zciaga dane ze strony, skonwertuj na obiek WeatherModel.class
        return gson.fromJson(forcastJson, WeatherModel.class);
    }

// przyklad Consumera: przyjmuje WeatherModel i nie zwraca nic
    private static void printWeather(WeatherModel forecastCity) {
        System.out.println(forecastCity.getName());
        System.out.println("Country: " + forecastCity.getSys().getCountry());
        for (int i = 0; i < forecastCity.getWeather().size() ; i++) {
            Weather w = forecastCity.getWeather().get(i);
            System.out.println(w.getDescription());
        }
        System.out.println(forecastCity.getMain().getTemp());
    }

    private static URI buildUri(String baseUrl, String apikey, Query query) {
        try {
            URIBuilder builder = new URIBuilder(baseUrl)                      // baseUrl- zmienna wyzej przechowujacata http
                    .addParameter("appid", apikey)
                    .addParameter("units", "metric")
                    .addParameter("lang", "pl");

            query.applyToBuilder(builder);

            return builder.build();
        }
        catch(URISyntaxException e){
            System.err.println(e.getMessage());
            return null;
        }

    }
    //wysylanie requesta- zwroci Jsona - text
    private static String requestForecastJson(URI uri) throws IOException {
        String responseBody; //json
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
        return responseBody;
    }
}

