package udinsi.dev.bookcollection.rxandroid;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import udinsi.dev.bookcollection.R;
import udinsi.dev.bookcollection.dao.MovieDatabase;
import udinsi.dev.bookcollection.model.Movies;

public class RxLatihan2 extends AppCompatActivity {
    public static final String TAG = "RxLatihan2";

    private CompositeDisposable disposable = new CompositeDisposable();
    Observable<String> animalsObservable = getAnimalsObservable();
    DisposableObserver<String> allAnimal = getAnimalsObserver();
    DisposableObserver<String> allAnimalAllCaps = getAnimalsAllCapsObserver();

    private static final String DATABASE_NAME = "movies_db";
    public MovieDatabase movieDatabase;

    public List<Movies> moviesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_latihan2);

        moviesList = new ArrayList<>();

        movieDatabase = Room
                .databaseBuilder(getApplicationContext(), MovieDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Movies movie =new Movies();
                movie.setMovieName("The Prestige");
                movieDatabase.daoAccess()
                        .insertOnlySingleMovie(movie);
            }
        }) .start();

        new LoadData().execute();

//        disposable.add(animalsObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        return s.toLowerCase().startsWith("b");
//                    }
//                })
//                .subscribeWith(allAnimal));
//
//        disposable.add(animalsObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        return s.toLowerCase().startsWith("f");
//                    }
//                })
//                .map(new Function<String, String>() {
//                    @Override
//                    public String apply(String s) throws Exception {
//                        return s.toUpperCase();
//                    }
//                })
//                .subscribeWith(allAnimalAllCaps)
//        );
    }

    private Observable<String> getAnimalsObservable(){
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "Butterfly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog");
    }

    private DisposableObserver<String> getAnimalsObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    private DisposableObserver<String> getAnimalsAllCapsObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    public class LoadData extends AsyncTask<Void, Void, List<Movies>>{

        @Override
        protected List<Movies> doInBackground(Void... voids) {
            for (int i = 0; i<movieDatabase.daoAccess().fetchAll().size(); i++){
                moviesList.add(movieDatabase.daoAccess().fetchAll().get(i));
            }

            return moviesList;
        }

        @Override
        protected void onPostExecute(List<Movies> movies) {
            super.onPostExecute(movies);
            for (Movies movie : movies){
                Log.e(TAG, "MOVIE NAME "+movie.getMovieName());
                Log.e(TAG, "MOVIE ID "+movie.getMovieId());
            }
        }
    }
}
