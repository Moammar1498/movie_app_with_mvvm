package online.diligence.movieslist.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import online.diligence.movieslist.AppExecutors;
import online.diligence.movieslist.models.MovieModel;
import online.diligence.movieslist.response.MovieSearchResponse;
import online.diligence.movieslist.utils.credentials;
import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
//This class will work as a bridge between movieApi(remote source) and LiveData
    private static MovieApiClient instance;

    //    LIVE DATA
    private MutableLiveData<List<MovieModel>> mMovies;

//    Global Runnable Request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static  MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public void searchMoviesApi(String query, int pageNumber){

        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
//                To Cancel the retrofit call

                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);

    }

    //We will retrieve data in runnable class
 private class  RetrieveMoviesRunnable implements  Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try{
                Response response = getMovies(query, pageNumber).execute();

                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
//                    Sending data to LiveData
//                    PostValue For Background Thread
                        mMovies.postValue(list);
                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else{
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error: " + error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        //        Search query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return ApiServiceImpl.getMovieApi().searchMovie(
                    credentials.API_KEY,
                    query,
                    pageNumber
            );
        }


        private void cancelRequest(){
            Log.v("Tag", "Cancelling the request");
            cancelRequest = true;
        }

    }

}


