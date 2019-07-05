package top.technopedia.myapplicationkatalogfilm;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;



public class DatabaseContract {

    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns {
        public static String MOVIE_ID = "movie_id";
        public static String TITLE_MOVIE = "title";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String IMAGE_POSTER = "image_poster";
        public static String IMAGE_BACKDROP = "image_backdrop";
        public static String VOTE_AVERAGE = "vote_average";
        public static String ORIGINAL_LANGUAGE = "original_language";
    }

    public static final String AUTHORITY = "top.technopedia.myapplicationkatalogfilm";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
