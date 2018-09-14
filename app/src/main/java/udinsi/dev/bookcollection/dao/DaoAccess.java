package udinsi.dev.bookcollection.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import udinsi.dev.bookcollection.model.Movies;

@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleMovie(Movies movies);

    @Insert
    void insertMultipleMovies(List<Movies> moviesList);

    @Query("SELECT * FROM Movies WHERE movieId = :movieId")
    Movies fetchOneMoviesbyMovieId(int movieId);

    @Query("SELECT * FROM Movies")
    List<Movies> fetchAll();

    @Update
    void updateMovie(Movies movies);

    @Delete
    void deleteMovie(Movies movies);
}
