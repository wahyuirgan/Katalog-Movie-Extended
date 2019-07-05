package top.technopedia.myapplicationkatalogfilm.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import top.technopedia.myapplicationkatalogfilm.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"+
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.TITLE_MOVIE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.RELEASE_DATE,
            DatabaseContract.MovieColumns.VOTE_AVERAGE,
            DatabaseContract.MovieColumns.IMAGE_POSTER,
            DatabaseContract.MovieColumns.IMAGE_BACKDROP,
            DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE
    );

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_MOVIE);
        onCreate(db);
    }
}
