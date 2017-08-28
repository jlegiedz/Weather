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

    private static void beIdle(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
