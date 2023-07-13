package com.example.movielibrary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.provider.ItemViewModel;
import com.example.movielibrary.provider.MovieItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Main2  extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //RecyclerView.Adapter adapter;
    MyRecyclerViewAdapter adapter;

//    ArrayList<MovieItem> movieInfo = new ArrayList<>();
    static ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);


        recyclerView =  findViewById(R.id.recycler_View);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //movieInfo = (ArrayList<String>) getIntent().getSerializableExtra("KEY_LIST");

//        SharedPreferences sP = getSharedPreferences("db1",0);
//        String dbStr = sP.getString("KEY_LIST", "");
//        Type type = new TypeToken<ArrayList<MovieItem>>() {}.getType();
//        Gson gson = new Gson();
//        movieInfo = gson.fromJson(dbStr,type);
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setMovieInfo(newData);
            adapter.notifyDataSetChanged();
//            tv.setText(newData.size() + "");
        });

        adapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void back (View v)
    {
        finish();
    }
}
