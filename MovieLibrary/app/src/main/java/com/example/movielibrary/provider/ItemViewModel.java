package com.example.movielibrary.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository mRepository;
    private LiveData<List<MovieItem>> mAllItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    public LiveData<List<MovieItem>> getAllItems() {
        return mAllItems;
    }

    public void insert(MovieItem item) {
        mRepository.insert(item);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteMovieByYear(String year){
        mRepository.deleteMovieByYear(year);
    }

}
