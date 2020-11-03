package com.example.movies.viewmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.adapter.MoviesAdapter;
import com.example.movies.model.HeroClass;
import com.example.movies.services.ApiClient;
import com.example.movies.services.ApiService;
import com.example.movies.view.MainActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainViewModel implements LifecycleObserver {

    private List<HeroClass> movieList;
    private ProgressDialog progressDialog;
    private List<Bitmap> image = new ArrayList<>();
    public Context context;
    public RecyclerView.LayoutManager layoutManager;
    public MoviesAdapter adapter;
    Observable<List<HeroClass>> observable;

    public MainViewModel(MainActivity mainActivity) {
        this.context = mainActivity;
        layoutManager = new LinearLayoutManager(context);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init() {


        adapter = new MoviesAdapter(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        observable = apiService.getMoviesList()
        .observeOn(Schedulers.newThread())
        .subscribeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<List<HeroClass>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<HeroClass> heroClasses) {
                movieList = new ArrayList<>();
                for(int i=0;i<heroClasses.size();i++){
                    HeroClass heroClass = new HeroClass();
                    heroClass.setName(heroClasses.get(i).getName());
                    heroClass.setCreatedby(heroClasses.get(i).getCreatedby());
                    heroClass.setFirstappearance(heroClasses.get(i).getFirstappearance());
                    heroClass.setTeam(heroClasses.get(i).getTeam());
                    heroClass.setImageurl(heroClasses.get(i).getImageurl());
                    movieList.add(heroClass);
                }
                new ProcessBitMaps().execute("");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Error: ",""+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        /*call.enqueue(new Callback<List<HeroClass>>(){
            @Override
            public void onResponse(Call<List<HeroClass>> call, Response<List<HeroClass>> response) {
                movieList = response.body();
                System.out.println("RESPONSE ----------- " + response.body().toString());
                new ProcessBitMaps().execute("");
            }

            @Override
            public void onFailure(Call<List<HeroClass>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private class ProcessBitMaps extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String[] url) {
            for (int i = 0; i < movieList.size(); i++) {
                try {
                    image.add(BitmapFactory.decodeStream((InputStream) new URL(movieList.get(i).getImageurl()).getContent()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            adapter.setMovieData(movieList, image);
            progressDialog.hide();

        }
    }
}

