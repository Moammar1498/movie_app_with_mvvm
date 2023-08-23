package online.diligence.movieslist.response;

//This is for single movie request

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import online.diligence.movieslist.models.MovieModel;

public class MovieResponse {
//    1- finding the movie object

    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }

}
