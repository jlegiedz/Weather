//https://square.github.io/retrofit/2.x/retrofit/retrofit2/Retrofit.Builder.html
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import sda.code.model.Weather;
import sda.code.model.WeatherModel;
import java.io.IOException;



public class Main {

    public static void main(String[] args) throws IOException {

    //The Retrofit class generates an implementation of the OpenWeather interface

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.OW_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeather api = retrofit.create(OpenWeather.class);

        Retrofit falseRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.OW_BASE + "false")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeather falseApi = falseRetrofit.create(OpenWeather.class);


        GeoQuery geoQuery = new GeoQuery(35.011667, 135.768333);
        CityQuery cityQuery = new CityQuery("Lodz");


        while (true) {
            geoQuery.execute(api,Constants.API_KEY).ifPresent(Main::printWeather);
            cityQuery.execute(api, Constants.API_KEY).ifPresent(Main::printWeather);

        }
    }

    //deserializacja
//    private static Optional<WeatherModel> parseForecastJson(String forcastJson) {
//        try {
//            //wez forcastJsona i zrob z niego instancje WeatherModel
//            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//            // tu nas
//            return Optional.ofNullable(gson.fromJson(forcastJson, WeatherModel.class));
//        } catch (JsonSyntaxException ex) {
//            System.out.println(ex.getMessage());
//            return Optional.empty();
//        }
//    }


//    private static Optional<URI> buildUri(String baseUrl, String apikey, Query query) {
//        try {
//            URIBuilder builder = new URIBuilder(baseUrl)                      // baseUrl- zmienna wyzej przechowujacata http
//                    .addParameter("appid", apikey)
//                    .addParameter("units", "metric")
//                    .addParameter("lang", "pl");
//
//            query.applyToBuilder(builder);
//
//            return Optional.of(builder.build());
//        }
//        catch(URISyntaxException e){
//            System.err.println(e.getMessage());
//            return Optional.empty();
//        }
//
//    }

//      //wysylanie requesta- zwroci Jsona - text
//    private static Optional<String> requestForecastJson(URI uri) throws IOException {
//        String responseBody; //zwraca Jsona
//        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
//        //The GET method means retrieve whatever information (in the form of an entity) is identified by the Request-URI.
//        HttpGet httpget = new HttpGet(uri);
//
//        System.out.println("Executing request " + httpget.getRequestLine());
//
//        // Create a custom response handler
//        ResponseHandler<String> responseHandler = new StringResponseHandler();
//        responseBody = httpclient.execute(httpget, responseHandler);
//        System.out.println("----------------------------------------");
//        System.out.println(responseBody);
//    }
//    //ofNullable jesli responseBody bedzie nullem to zwroci od razu Optional.empty
//        return Optional.ofNullable(responseBody);
//}

    // nothing changed:
    private static void printWeather(WeatherModel forecastCity) {
        System.out.println(forecastCity.getName());
        System.out.println("Country: " + forecastCity.getSys().getCountry());
        for (int i = 0; i < forecastCity.getWeather().size(); i++) {
            Weather w = forecastCity.getWeather().get(i);
            System.out.println(w.getDescription());
        }
        System.out.println(forecastCity.getMain().getTemp());
    }


}
