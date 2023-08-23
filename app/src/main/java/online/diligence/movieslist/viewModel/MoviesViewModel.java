package online.diligence.movieslist.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import online.diligence.movieslist.models.MovieModel;
import online.diligence.movieslist.network.ApiService;
import online.diligence.movieslist.repositories.MovieRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends ViewModel {

//    This class is being used for VIEWMODEL

    private MovieRepository movieRepository;

//    Constructor

    public MoviesViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    public void searchMovies(String query, int pageNumber){
        movieRepository.searchMovies(query,pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
}
