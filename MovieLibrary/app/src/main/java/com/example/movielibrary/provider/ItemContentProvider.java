package com.example.movielibrary.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ItemContentProvider extends ContentProvider {

    private static final String CONTENT_AUTHORITY = "fit2081.app.ZAFRI.provider";
    private static final Uri CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);
    ItemDatabase db;
    private final String tableName = "MovieInfo";
    public ItemContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deletionCount;

        deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(tableName, selection, selectionArgs);

        return deletionCount;
    }


    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = db
                .getOpenHelper()// open library for android to access database
                .getWritableDatabase()
                .insert(tableName, 0, values);

        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public boolean onCreate() {
        db = ItemDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);

        final Cursor cursor = db
                .getOpenHelper()// open library for android to access database
                .getReadableDatabase()
                .query(query, selectionArgs);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

            int updateCount;
            updateCount = db
                    .getOpenHelper()
                    .getWritableDatabase()
                    .update(tableName, 0, values, selection, selectionArgs);

            return updateCount;
        }
    }
