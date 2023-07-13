package com.example.movielibrary.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "MovieInfo")
public class MovieItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "movieID")
    int movieID;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "year")
    String year;
    @ColumnInfo(name = "country")
    String country;
    @ColumnInfo(name = "genre")
    String genre;
    @ColumnInfo(name = "cost")
    String cost;
    @ColumnInfo(name = "keywords")
    String keywords;



    public MovieItem(String title, String year, String country, String genre, String cost, String keywords) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.genre = genre;
        this.cost = cost;
        this.keywords = keywords;
    }

    public int getItemID(){return movieID;}
    public void setItemID(@NonNull int movieID){this.movieID = movieID;}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
