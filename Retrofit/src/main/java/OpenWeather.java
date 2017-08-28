import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sda.code.model.WeatherModel;

public interface OpenWeather {
    //the value of that parameter is bound to the specific replacement block:@Path
//    @GET("/data/2.5/weather?appid={key}&q={query}")
//    Call<WeatherModel> weatherByQuery(
//            @Path("key") String apiKey,
//            @Path("query") String query);

    @GET(Constants.OW_WEATHER)
    Call<WeatherModel> weatherByQuery(
            @Query("appid") String apiKey,
            @Query("q") String query
    );

    //Query parameters are added with the @Query annotation on a method parameter.
    // They are automatically added at the end of the URL.
    @GET(Constants.OW_WEATHER)
    Call<WeatherModel> weatherByCoord(
            @Query("appid") String apiKey,
            @Query("lat") double latitude,
            @Query("lon") double longitude
    );

    @GET(Constants.OW_WEATHER)
    Call<WeatherModel> weather(
            @Query("appid") String apiKey,
            @Query("q") String query,
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("units") String units,
            @Query("lang") String langCode);

}
