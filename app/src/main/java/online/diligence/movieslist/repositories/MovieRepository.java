package online.diligence.movieslist.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import online.diligence.movieslist.models.MovieModel;
import online.diligence.movieslist.network.MovieApiClient;

public class MovieRepository {
//    This will act as Repository

    private static MovieRepository instance;


    MovieApiClient movieApiClient;

    String mquery;
    int mPageNumber;

    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    public void searchMovies(String query, int pageNumber){
        mquery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query,pageNumber);
    }

    public void searchNextPage(){
        searchMovies(mquery,mPageNumber+1);
    }
}
