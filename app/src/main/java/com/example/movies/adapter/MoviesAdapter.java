package com.example.movies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.viewmodel.CardViewItemViewModel;
import com.example.movies.databinding.MoviesCardViewBinding;
import com.example.movies.model.HeroClass;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private List<HeroClass> movieList = new ArrayList<>();
    private List<CardViewItemViewModel> itemMovieDetails = new ArrayList<>();;
    private List<Bitmap> images;

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public void setMovieData( List<HeroClass> movieList, List<Bitmap> image) {
        this.movieList = movieList;
        this.images = image;
        updateItemsModel();
        this.notifyDataSetChanged();
    }

    private void updateItemsModel() {
        itemMovieDetails.clear();
        for(int i = 0; i < movieList.size(); i++){
            itemMovieDetails.add(new CardViewItemViewModel(movieList.get(i).getName(), images.get(i), context.getResources()));
        }
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MoviesCardViewBinding binding =  MoviesCardViewBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {
        holder.bind(itemMovieDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private MoviesCardViewBinding binding;

        public ViewHolder(@NonNull MoviesCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.executePendingBindings();
        }

        public void bind(CardViewItemViewModel cardViewItemViewModel) {
            binding.setItemViewModal(cardViewItemViewModel);
            binding.executePendingBindings();
        }
    }

}
