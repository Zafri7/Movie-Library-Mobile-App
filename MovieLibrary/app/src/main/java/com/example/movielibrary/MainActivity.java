package com.example.movielibrary;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;


import com.example.movielibrary.provider.ItemViewModel;
import com.example.movielibrary.provider.MovieItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    EditText movieTitle, movieYear, movieCountry, movieGenre, movieCost, movieKeywords;

    //Lab5
    ArrayList<String> myList = new ArrayList<>();
    ArrayAdapter myAdapter;
    DrawerLayout drawer;
    //Lab5

    //LAb 6
    //ArrayList<MovieItem> movieInfo = new ArrayList<>();

    //Lab 7
    private ItemViewModel mItemViewModel;
    MyRecyclerViewAdapter adapter;


    Gson gson = new Gson();
    /*
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;*/
    //Intent myIntent;
    //Lab6

    //Lab8
    DatabaseReference myRef;

    //Lab10
    View myFrame;
    View myConstraint;
    int x_down;
    int y_down;

    //Lab11
    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        //Lab11
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        scaleGestureDetector = new ScaleGestureDetector(this, new MyScaleGestureDetector());

        myConstraint = findViewById(R.id.constraint_id);
        myConstraint.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //gestureDetector.onTouchEvent(motionEvent);
                scaleGestureDetector.onTouchEvent(motionEvent);
                return true;
//
            }
        });


        //Lab10
        myFrame = findViewById(R.id.frame_id);
        EditText titleText = findViewById(R.id.editTextTitle);
        myFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                //scaleGestureDetector.onTouchEvent(motionEvent);
                return true;
//                int action = motionEvent.getActionMasked();
//
//                switch(action){
//                    case MotionEvent.ACTION_DOWN:
//                        x_down = (int)motionEvent.getX();
//                        y_down = (int)motionEvent.getY();
//                        if (x_down >= 600 && y_down <= 100) {
//                            movieCost = findViewById(R.id.editTextCost);
//                            int cost = Integer.parseInt(movieCost.getText().toString());
//
//                            movieCost.setText(cost+50+"");
//                        }
//                        else if(x_down <= 100 && y_down <= 100) {
//                            movieCost = findViewById(R.id.editTextCost);
//                            int cost = Integer.parseInt(movieCost.getText().toString());
//
//                            if(cost >= 50){
//                                movieCost.setText(cost-50+"");
//                            }
//                        }
//                        return true;
////                    case MotionEvent.ACTION_MOVE:
////                        return true;
//                    case MotionEvent.ACTION_UP:
//                        if (Math.abs(y_down - motionEvent.getY())<40){
//                            if (x_down - motionEvent.getX()<0){
//                                addMovie();
//                            }
//                        }
//                        else if (Math.abs(x_down - motionEvent.getX())<40){
//                            if (y_down - motionEvent.getY()<-20){
//                            ClearFields();
//                        }
//                        }
//                        return true;
//                    default:
//                        return false;
//
//
//
//                }
            }
        });

        adapter = new MyRecyclerViewAdapter();
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setMovieInfo(newData);
            adapter.notifyDataSetChanged();
            //tv.setText(newData.size() + "");
        });


        //Lab8
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Item/movie");


        //Lab5
        //setContentView(R.layout.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView listView = findViewById(R.id.lv);
        myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(myAdapter);
        //Lab5

        //Lab4
        movieTitle = findViewById(R.id.editTextTitle);
        movieYear = findViewById(R.id.editTextYear);
        movieCountry = findViewById(R.id.editTextCountry);
        movieGenre = findViewById(R.id.editTextGenre);
        movieCost = findViewById(R.id.editTextCost);
        movieKeywords = findViewById(R.id.editTextKeywords);

        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver
         */
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
        //Lab 4

        //Lab 5
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText titleText = findViewById(R.id.editTextTitle);
                String title = titleText.getText().toString();
                EditText yearText = findViewById(R.id.editTextYear);
                int year = Integer.parseInt(yearText.getText().toString());
                EditText countryText = findViewById(R.id.editTextCountry);
                String country = countryText.getText().toString();
                EditText genreText = findViewById(R.id.editTextGenre);
                String genre = genreText.getText().toString();
                EditText costText = findViewById(R.id.editTextCost);
                int cost = Integer.parseInt(costText.getText().toString());
                EditText keywordsText = findViewById(R.id.editTextKeywords);
                String keywords = keywordsText.getText().toString();

//                SharedPreferences sP = getPreferences(0);
//                SharedPreferences.Editor editor = sP.edit();
//                editor.putString("title", title);
//                editor.putString("country", country);
//                editor.putString("genre", genre);
//                editor.putString("keywords", keywords);
//                editor.putInt("year", year);
//                editor.putInt("cost", cost);
//                editor.apply();

                myList.add(title + " | " + year + "");
                myAdapter.notifyDataSetChanged();
                MovieItem movie = new MovieItem(title, year+"", country, genre, cost+"", keywords);
                //movieInfo.add(movie);
                mItemViewModel.insert(movie);
                myRef.push().setValue(movie);


                //Snackbar.make(view, "Item added", Snackbar.LENGTH_LONG).setAction("undo", UndoListener).show();
            }
        });
    }
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            movieCost = findViewById(R.id.editTextCost);
            int cost = Integer.parseInt(movieCost.getText().toString());
            movieCost.setText(cost+150+"");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            EditText titleText = findViewById(R.id.editTextTitle);
            EditText yearText = findViewById(R.id.editTextYear);
            EditText countryText = findViewById(R.id.editTextCountry);
            EditText genreText = findViewById(R.id.editTextGenre);
            EditText costText = findViewById(R.id.editTextCost);
            EditText keywordsText = findViewById(R.id.editTextKeywords);
            titleText.setText("Batman");
            yearText.setText(2022+"");
            countryText.setText("USA");
            genreText.setText("Action");
            costText.setText(100000+"");
            keywordsText.setText("Bat");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            EditText yearText = findViewById(R.id.editTextYear);
            int year = Integer.parseInt(yearText.getText().toString());
            year -= (int) distanceX;
            yearText.setText(Math.abs(year)+"");
            if(distanceX == 0) {
                EditText keywordsText = findViewById(R.id.editTextKeywords);
                String keywords = keywordsText.getText().toString();
                keywordsText.setText(keywords.toUpperCase());
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            ClearFields();
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(Math.abs(velocityX)>= 5000 || Math.abs(velocityY) >= 5000){
                moveTaskToBack(true);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    class MyScaleGestureDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            EditText keywordsText = findViewById(R.id.editTextKeywords);
            String keywords = keywordsText.getText().toString();
            keywordsText.setText(keywords.toLowerCase());
            return super.onScale(detector);
        }
    }





    /*View.OnClickListener UndoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            myList.remove(myList.size() - 1);
            myAdapter.notifyDataSetChanged();

        }
    };*/

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.addMov){
                EditText titleText = findViewById(R.id.editTextTitle);
                String title = titleText.getText().toString();
                EditText yearText = findViewById(R.id.editTextYear);
                int year = Integer.parseInt(yearText.getText().toString());
                EditText countryText = findViewById(R.id.editTextCountry);
                String country = countryText.getText().toString();
                EditText genreText = findViewById(R.id.editTextGenre);
                String genre = genreText.getText().toString();
                EditText costText = findViewById(R.id.editTextCost);
                int cost = Integer.parseInt(costText.getText().toString());
                EditText keywordsText = findViewById(R.id.editTextKeywords);
                String keywords = keywordsText.getText().toString();

                SharedPreferences sP = getPreferences(0);
                SharedPreferences.Editor editor = sP.edit();
                editor.putString("title", title);
                editor.putString("country", country);
                editor.putString("genre", genre);
                editor.putString("keywords", keywords);
                editor.putInt("year", year);
                editor.putInt("cost", cost);
                editor.apply();

                myList.add(title + " | " + year + "");
                myAdapter.notifyDataSetChanged();

                MovieItem movie = new MovieItem(title, year+"", country, genre, cost+"", keywords);
                //movieInfo.add(movie);
                mItemViewModel.insert(movie);
                myRef.push().setValue(movie);



            }else if (id == R.id.removeOne ){
                myList.remove(myList.size() - 1);
                myAdapter.notifyDataSetChanged();
//                movieInfo.remove(movieInfo.size() - 1);


            }else if (id==R.id.removeAll) {
                myList.removeAll(myList);
                myAdapter.notifyDataSetChanged();
//                movieInfo.removeAll(movieInfo);
                mItemViewModel.deleteAll();
                myRef.removeValue();
                }
            else if (id == R.id.close){
                finish();
            }

            else if (id == R.id.listAllMovies){
                goToNext();
            }


            drawer.closeDrawers();
            return true;

        }

    }

    public void goToNext(){
//        String dbStr = gson.toJson(movieInfo);
//
//        SharedPreferences sP = getSharedPreferences("db1",0);
//        SharedPreferences.Editor edit = sP.edit();
//        edit.putString("KEY_LIST", dbStr);
//        edit.apply();
        Intent myIntent = new Intent(this,Main2.class);
        //myIntent.putExtra("KEY_LIST",movieInfo);
        startActivity(myIntent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();
            if (id == R.id.clearFields) {
                SharedPreferences sP = getPreferences( 0);
                SharedPreferences.Editor SP = sP.edit();
                SP.clear();
                SP.apply();
                EditText titleText = findViewById(R.id.editTextTitle);
                EditText yearText = findViewById(R.id.editTextYear);
                EditText countryText = findViewById(R.id.editTextCountry);
                EditText genreText = findViewById(R.id.editTextGenre);
                EditText costText = findViewById(R.id.editTextCost);
                EditText keywordsText = findViewById(R.id.editTextKeywords);

                titleText.setText("");
                yearText.setText(0+"");
                countryText.setText("");
                genreText.setText("");
                costText.setText(0+"");
                keywordsText.setText("");
            }

            else {
                Toast.makeText(this, "Total movie(s): " + myList.size(), Toast.LENGTH_SHORT).show();
            }


        return super.onOptionsItemSelected(item);
    }//Lab 5

    //Lab4
    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */

            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */

            StringTokenizer sT = new StringTokenizer(msg, ";");
            String title = sT.nextToken();
            String year = sT.nextToken();
            String country = sT.nextToken();
            String genre = sT.nextToken();
            String cost = sT.nextToken();
            String keywords = sT.nextToken();
            String hiddenCost = sT.nextToken();
            //double newCost = ((Double.parseDouble(cost) + Double.parseDouble(hiddenCost)));
            String newCost = (Double.parseDouble(cost) + Double.parseDouble(hiddenCost)+"");
            /*
             * Now, its time to update the UI
             * */
            movieTitle.setText(title);
            movieYear.setText(year);
            movieCountry.setText(country);
            movieGenre.setText(genre);
            movieCost.setText(newCost);
            movieKeywords.setText(keywords);
        }
    }//Lab4










    public void addMovie(){
        EditText titleText = findViewById(R.id.editTextTitle);
        String title = titleText.getText().toString();
        Toast labToast = Toast.makeText(this, "Movie - " + title + " - has been updated", Toast.LENGTH_SHORT);

        labToast.show();


        EditText yearText = findViewById(R.id.editTextYear);
        int year= Integer.parseInt(yearText.getText().toString());
        EditText countryText = findViewById(R.id.editTextCountry);
        String country=countryText.getText().toString();
        EditText genreText = findViewById(R.id.editTextGenre);
        String genre=genreText.getText().toString();
        EditText costText = findViewById(R.id.editTextCost);
        int cost= Integer.parseInt(costText.getText().toString());
        EditText keywordsText = findViewById(R.id.editTextKeywords);
        String keywords=keywordsText.getText().toString();

        SharedPreferences sP = getPreferences(0);
        SharedPreferences.Editor editor = sP.edit();
        editor.putString("title",title);
        editor.putString("country",country);
        editor.putString("genre",genre);
        editor.putString("keywords",keywords);
        editor.putInt("year",year);
        editor.putInt("cost",cost);
        editor.commit();

        myList.add(title + " | " + year + "");
        myAdapter.notifyDataSetChanged();
        MovieItem movie = new MovieItem(title, year+"", country, genre, cost+"", keywords);
        //movieInfo.add(movie);
        mItemViewModel.insert(movie);
        myRef.push().setValue(movie);

    }

    public void addMov(View view){//View view so that will show method at (on click)
        EditText titleText = findViewById(R.id.editTextTitle);
        String title = titleText.getText().toString();
        Toast labToast = Toast.makeText(this, "Movie - " + title + " - has been updated", Toast.LENGTH_SHORT);

        labToast.show();


        EditText yearText = findViewById(R.id.editTextYear);
        int year= Integer.parseInt(yearText.getText().toString());
        EditText countryText = findViewById(R.id.editTextCountry);
        String country=countryText.getText().toString();
        EditText genreText = findViewById(R.id.editTextGenre);
        String genre=genreText.getText().toString();
        EditText costText = findViewById(R.id.editTextCost);
        int cost= Integer.parseInt(costText.getText().toString());
        EditText keywordsText = findViewById(R.id.editTextKeywords);
        String keywords=keywordsText.getText().toString();

        SharedPreferences sP = getPreferences(0);
        SharedPreferences.Editor editor = sP.edit();
        editor.putString("title",title);
        editor.putString("country",country);
        editor.putString("genre",genre);
        editor.putString("keywords",keywords);
        editor.putInt("year",year);
        editor.putInt("cost",cost);
        editor.commit();

        countryText.setText("I am Zaf");

        myList.add(title + " | " + year + "");
        myAdapter.notifyDataSetChanged();
        MovieItem movie = new MovieItem(title, year+"", country, genre, cost+"", keywords);
        //movieInfo.add(movie);
        mItemViewModel.insert(movie);
        myRef.push().setValue(movie);




    }
    public void ClearFields (){
        SharedPreferences sP = getPreferences( 0);
        SharedPreferences.Editor SP = sP.edit();
        SP.clear();
        SP.commit();
        EditText titleText = findViewById(R.id.editTextTitle);
        EditText yearText = findViewById(R.id.editTextYear);
        EditText countryText = findViewById(R.id.editTextCountry);
        EditText genreText = findViewById(R.id.editTextGenre);
        EditText costText = findViewById(R.id.editTextCost);
        EditText keywordsText = findViewById(R.id.editTextKeywords);

        titleText.setText("");
        yearText.setText(0+"");
        countryText.setText("");
        genreText.setText("");
        costText.setText(0+"");
        keywordsText.setText("");
    }
    public void ClearSP (View view){
        SharedPreferences sP = getPreferences( 0);
        SharedPreferences.Editor SP = sP.edit();
        SP.clear();
        SP.commit();
        EditText titleText = findViewById(R.id.editTextTitle);
        EditText yearText = findViewById(R.id.editTextYear);
        EditText countryText = findViewById(R.id.editTextCountry);
        EditText genreText = findViewById(R.id.editTextGenre);
        EditText costText = findViewById(R.id.editTextCost);
        EditText keywordsText = findViewById(R.id.editTextKeywords);

        titleText.setText("");
        yearText.setText(0+"");
        countryText.setText("");
        genreText.setText("");
        costText.setText(0+"");
        keywordsText.setText("");
    }

    public void LoadDoubleCost(View view) {

        SharedPreferences sP= getPreferences(0);
        int cost = sP.getInt("cost",0);
        EditText costText = findViewById(R.id.editTextCost);
        costText.setText(cost*2+"");
        SharedPreferences.Editor editor = sP.edit();
        editor.putInt("cost", cost * 2);
        editor.commit();
    }

    public void doubleCost(View view){
        EditText costText = findViewById(R.id.editTextCost);
        Long cost= Long.parseLong(costText.getText().toString());
        costText.setText(cost*2 + "");
    }

    public void resetAll (View view){
        EditText titleText = findViewById(R.id.editTextTitle);
        EditText yearText = findViewById(R.id.editTextYear);
        EditText countryText = findViewById(R.id.editTextCountry);
        EditText genreText = findViewById(R.id.editTextGenre);
        EditText costText = findViewById(R.id.editTextCost);
        EditText keywordsText = findViewById(R.id.editTextKeywords);

        titleText.setText("");
        yearText.setText("");
        countryText.setText("");
        genreText.setText("");
        costText.setText("");
        keywordsText.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
       // Log.i("week3App","onStart");

        SharedPreferences sP= getPreferences(0);
        String title = sP.getString("title","");
        EditText titleText = findViewById(R.id.editTextTitle);
        titleText.setText(title);
        String country = sP.getString("country","");
        EditText countryText = findViewById(R.id.editTextCountry);
        countryText.setText(country);
        String genre = sP.getString("genre","");
        EditText genreText = findViewById(R.id.editTextGenre);
        genreText.setText(genre);
        String keywords = sP.getString("keywords","");
        EditText keywordsText = findViewById(R.id.editTextKeywords);
        keywordsText.setText(keywords);
        int year = sP.getInt("year",0);
        EditText yearText = findViewById(R.id.editTextYear);
        yearText.setText(year+"");
        int cost = sP.getInt("cost",0);
        EditText costText = findViewById(R.id.editTextCost);
        costText.setText(cost+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("week3App","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("week3App","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("week3App","onStop");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("week3App","onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        //EditText titleText = findViewById(R.id.editTextTitle);
        //title=titleText.getText().toString();

        //outState.putString("KEY1",title);
        EditText genreText = findViewById(R.id.editTextGenre);
        String genre=genreText.getText().toString();
        genreText.setText(genre.toLowerCase());
        super.onSaveInstanceState(outState);
        //outState.putString("Key1",genre);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //title=savedInstanceState.getString("KEY1");
        EditText titleText = findViewById(R.id.editTextTitle);
        String title=titleText.getText().toString();
        titleText.setText(title.toUpperCase());






    }
}