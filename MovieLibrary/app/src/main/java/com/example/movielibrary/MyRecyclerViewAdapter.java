package com.example.movielibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.provider.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{
    //Lab6
    //ArrayList<MovieItem> movieInfo;

    //Lab7
    List<MovieItem> movieInfo = new ArrayList<>();

    // constructor for this line, (adapter = new MyRecyclerViewAdapter(movieInfo);)
//    public MyRecyclerViewAdapter(ArrayList<MovieItem> _data){
//        movieInfo = _data;
//    }

    public MyRecyclerViewAdapter() {

    }

    public void setMovieInfo(List<MovieItem> movieInfo) {
        this.movieInfo = movieInfo;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.myTitle.setText("Title: " + movieInfo.get(position).getTitle());
        holder.myYear.setText("Year: " + movieInfo.get(position).getYear());
        holder.myCountry.setText("Country: " + movieInfo.get(position).getCountry());
        holder.myGenre.setText("Genre: " + movieInfo.get(position).getGenre());
        holder.myCost.setText("Cost: " + movieInfo.get(position).getCost());
        holder.myKeywords.setText("Keywords: " + movieInfo.get(position).getKeywords());

        int fPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Movie No. "+(fPosition+1)+" with Title: " + movieInfo.get(fPosition).getTitle()
                        + " is selected", Toast.LENGTH_SHORT).show();
                String title = movieInfo.get(fPosition).getTitle();
                String year = movieInfo.get(fPosition).getYear();
                String country = movieInfo.get(fPosition).getCountry();
                String genre = movieInfo.get(fPosition).getGenre();
                String cost = movieInfo.get(fPosition).getCost();
                String keywords = movieInfo.get(fPosition).getKeywords();

                Main2.mItemViewModel.deleteMovieByYear(year);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieInfo.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public TextView myTitle;
        public TextView myYear;
        public TextView myCountry;
        public TextView myGenre;
        public TextView myCost;
        public TextView myKeywords;


        public ViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
            myTitle = itemView.findViewById(R.id.titleView);
            myYear = itemView.findViewById(R.id.yearView);
            myCountry = itemView.findViewById(R.id.countryView);
            myGenre = itemView.findViewById(R.id.genreView);
            myCost = itemView.findViewById(R.id.costView);
            myKeywords = itemView.findViewById(R.id.keywordsView);


        }

    }
}
