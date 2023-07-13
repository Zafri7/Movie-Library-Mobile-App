package com.example.movielibrary.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MovieItem.class}, version = 2)
public abstract class ItemDatabase extends RoomDatabase{


        public static final String Movie_DATABASE_NAME = "movie_database";

        public abstract ItemDao ItemDao();

        // marking the instance as volatile to ensure atomic access to the variable
        private static volatile ItemDatabase INSTANCE;
        private static final int NUMBER_OF_THREADS = 4;
        static final ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        static ItemDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (ItemDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                ItemDatabase.class, Movie_DATABASE_NAME)
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

    }

