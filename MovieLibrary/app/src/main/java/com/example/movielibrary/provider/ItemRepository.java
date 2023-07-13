package com.example.movielibrary.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository
{
    private ItemDao mItemDao;
    private LiveData<List<MovieItem>> mAllItems;

    ItemRepository(Application application)
    {
        ItemDatabase db = ItemDatabase.getDatabase(application);
        mItemDao = db.ItemDao();
        mAllItems = mItemDao.getAllItem();
    }

    LiveData<List<MovieItem>> getAllItems() {
        return mAllItems;
    }

    void insert(MovieItem item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> mItemDao.addItem(item));
    }

    void deleteAll(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteAllItems();
        });
    }

    void deleteMovieByYear(String year){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteMovieByYear(year);
        });
    }
}
