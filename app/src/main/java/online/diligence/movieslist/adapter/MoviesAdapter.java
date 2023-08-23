package online.diligence.movieslist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import online.diligence.movieslist.R;
import online.diligence.movieslist.models.MovieModel;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;
    public MoviesAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    public void setmMovies(List<MovieModel> movieList){
        this.mMovies = movieList;
        notifyDataSetChanged();
    }



@NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row,
                parent, false);
        return new MyViewHolder(view,onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder holder, int position) {

        float remoteRatingValue = (mMovies.get(position).getVote_average())/2;
        holder.ratingBar.setRating(remoteRatingValue);


//        ImageView
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/original"+mMovies.get(position).getPoster_path())
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        if(mMovies!= null){
            return mMovies.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView category, duration ;
        ImageView cover;
        RatingBar ratingBar;
        OnMovieListener onMovieListener;
        public MyViewHolder(View itemView, OnMovieListener movieListener){
            super(itemView);

            onMovieListener = movieListener;

            ratingBar = itemView.findViewById(R.id.rating_bar);

            cover = itemView.findViewById(R.id.movieCover);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMovieListener.onMovieClick(getAdapterPosition());
        }

    }
    public MovieModel getSelectedMovie(int position){
        if(mMovies != null){
            if(mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return  null;
    }
}
