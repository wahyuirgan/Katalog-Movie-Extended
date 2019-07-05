package top.technopedia.myapplicationkatalogfilm.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import top.technopedia.myapplicationkatalogfilm.Model.MovieResponse;


public interface ApiService {

//    @GET("search/movie?api_key="+ BuildConfig.API_KEY)
//    Call<MovieResponse> getItemSearch(@Query("query") String movie_name);
//
//    @GET("movie/now_playing")
//    Call<MovieResponse> getNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcoming(@Query("api_key") String apiKey);

}
