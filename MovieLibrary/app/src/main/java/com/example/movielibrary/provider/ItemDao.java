package com.example.movielibrary.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao
{
    @Query("select * from MovieInfo")
    LiveData<List<MovieItem>> getAllItem();

//    @Query("select * from MovieInfo where title=:name")
//    List<MovieItem> getItem(String name);

    @Insert
    void addItem(MovieItem movieItem);

//    @Query("delete from MovieInfo where title= :name")
//    void deleteItem(String name);

//    void deleteItem(String name);

    @Query("delete FROM MovieInfo")
    void deleteAllItems();

    @Query("delete from MovieInfo where year=:year")
    void deleteMovieByYear(String year);

//    List<MovieItem> getItem(String name);
}
