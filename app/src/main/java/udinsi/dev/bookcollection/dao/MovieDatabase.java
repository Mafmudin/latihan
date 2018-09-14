package udinsi.dev.bookcollection.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import udinsi.dev.bookcollection.model.Movies;

@Database(entities = {Movies.class}, version = 2, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}