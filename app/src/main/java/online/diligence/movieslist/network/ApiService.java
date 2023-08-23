package online.diligence.movieslist.network;

import online.diligence.movieslist.models.MovieModel;
import online.diligence.movieslist.response.MovieSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
//Search For Movies
//    https:api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher

    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

//Search by Movie ID
    @GET("3/movie/{movie_id}?")
        Call<MovieModel> getMovie(
                @Path("movie_id") int movie_id,
                @Query("api_key") String api_key
        );
}
