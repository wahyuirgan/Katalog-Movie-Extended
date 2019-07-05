package top.technopedia.myapplicationkatalogfilm.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import top.technopedia.myapplicationkatalogfilm.Model.MovieList;

import static android.provider.BaseColumns._ID;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.IMAGE_BACKDROP;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.IMAGE_POSTER;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.OVERVIEW;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.RELEASE_DATE;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.TABLE_MOVIE;



public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<MovieList> query(){
        ArrayList<MovieList> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        MovieList favItems;
        if (cursor.getCount()>0){
            do {

                favItems = new MovieList();
                favItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_MOVIE)));
                favItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favItems.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favItems.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_POSTER)));
                favItems.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_BACKDROP)));
                favItems.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE)));
                favItems.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                arrayList.add(favItems);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieList favItems) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE_MOVIE, favItems.getTitle());
        initialValues.put(OVERVIEW, favItems.getOverview());
        initialValues.put(RELEASE_DATE, favItems.getRelease_date());
        initialValues.put(IMAGE_POSTER, favItems.getPoster_path());
        initialValues.put(IMAGE_BACKDROP, favItems.getBackdrop_path());
        initialValues.put(ORIGINAL_LANGUAGE, favItems.getOriginal_language());
        initialValues.put(VOTE_AVERAGE, favItems.getVote_average());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(MovieList favItems) {
        ContentValues args = new ContentValues();
        args.put(TITLE_MOVIE, favItems.getTitle());
        args.put(OVERVIEW, favItems.getOverview());
        args.put(RELEASE_DATE, favItems.getRelease_date());
        args.put(IMAGE_POSTER, favItems.getPoster_path());
        args.put(IMAGE_BACKDROP, favItems.getBackdrop_path());
        args.put(ORIGINAL_LANGUAGE, favItems.getOriginal_language());
        args.put(VOTE_AVERAGE, favItems.getVote_average());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + favItems.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + "= '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null,_ID+"=?",new String[]{id},null,null,null,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE, null,null,null,null,null,_ID+" DESC");
    }

    public long insertProvider(ContentValues contentValues){
        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public int updateProvider(String id, ContentValues contentValues){
        return database.update(DATABASE_TABLE, contentValues,_ID+"=?",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID+"=?",new String[]{id});
    }

}
