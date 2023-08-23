package online.diligence.movieslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.io.IOException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import online.diligence.movieslist.adapter.MoviesAdapter;
import online.diligence.movieslist.adapter.OnMovieListener;
import online.diligence.movieslist.models.MovieModel;
import online.diligence.movieslist.network.ApiService;
import online.diligence.movieslist.network.ApiServiceImpl;
import online.diligence.movieslist.response.MovieSearchResponse;
import online.diligence.movieslist.utils.credentials;
import online.diligence.movieslist.viewModel.MoviesViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private MoviesAdapter adapter;
    private RecyclerView recyclerView;
//    private List<MovieModel> mMovies;

//    ViewModel
    private MoviesViewModel moviesViewModel;

    Toolbar toolbar;
    public String headerImage;
    ImageView headerImg;

    private ImageView animationView;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        animationView = findViewById(R.id.animation_image);
        animationDrawable = (AnimationDrawable) animationView.getDrawable();
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.requestLayout();

        headerImg = findViewById(R.id.imageView_header);
        recyclerView = findViewById(R.id.recyclerView);



        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        searchMovieMethod();

        configureRecyclerView();
//        Calling Observers
        observeChange();

        Glide.with(this)
                .load(R.drawable.her)
                .into(headerImg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        animationDrawable.start();
    }



    //    Observing Data change
    private void observeChange(){
        moviesViewModel.getMovies().observe(this, movieModels -> {
//                Observing for any change in Data
            if(movieModels != null){
                headerImage = movieModels.get(1).getPoster_path();
                for (MovieModel movieModel: movieModels){
                    Log.v("Tag", "OnChanged: " + movieModel.getVote_average());
                    adapter.setmMovies(movieModels);
                }
            }
        });
    }

  //Configuring the Recycler View
    private void configureRecyclerView(){

//        SearchMovies
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MoviesAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
//                    Here we are calling the next page
                    moviesViewModel.searchNextPage();
                }
            }
        });
    }

    private void searchMovieMethod() {
        final androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                moviesViewModel.searchMovies(query, 1);
                animationView.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onMovieClick(int position) {

        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("movie", adapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }



}