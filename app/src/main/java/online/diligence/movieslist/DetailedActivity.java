package online.diligence.movieslist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import online.diligence.movieslist.models.MovieModel;

public class DetailedActivity extends AppCompatActivity {

//    Widgets
    private TextView titleDetails, overviewDetails;
    ImageView coverDetails;
    RatingBar ratingBarDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        titleDetails = findViewById(R.id.textView_title);
        overviewDetails = findViewById(R.id.textView_overview);
        coverDetails = findViewById(R.id.movie_Cover_bg);
        ratingBarDetails = findViewById(R.id.detailed_ratingBar);

        getDataFromIntent();
    }

    private void getDataFromIntent() {
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getTitle());
            overviewDetails.setText(movieModel.getMovie_overview());
            ratingBarDetails.setRating(movieModel.getVote_average()/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original"+movieModel.getPoster_path())
                    .into(coverDetails);
        }
    }
}