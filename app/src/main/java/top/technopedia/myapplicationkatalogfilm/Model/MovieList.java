package top.technopedia.myapplicationkatalogfilm.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.IMAGE_BACKDROP;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.IMAGE_POSTER;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.OVERVIEW;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.RELEASE_DATE;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.getColumnInt;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.getColumnString;

public class MovieList implements Parcelable {
    private int id;
    private String vote_average;
    private String title;
    private String poster_path;
    private String original_language;
    private String backdrop_path;
    private String overview;
    private String release_date;

    public MovieList(JSONObject object) {

        try {
            String title = object.getString("title");
            String overview = object.getString("overview");
            String rlsdate = object.getString("release_date");
            String poster = object.getString("poster_path");
            String rating = object.getString("vote_average");
            int id = object.getInt("id");
            String language = object.getString("original_language");
            String backdrop = object.getString("backdrop_path");

            this.title = title;
            this.overview = overview;
            this.release_date = rlsdate;
            this.poster_path = poster;
            this.vote_average = rating;
            this.id = id;
            this.original_language = language;
            this.backdrop_path = backdrop;


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MovieList(Cursor cursor) {
        this.id = getColumnInt(cursor,_ID);
        this.vote_average = getColumnString(cursor, VOTE_AVERAGE);
        this.title = getColumnString(cursor, TITLE_MOVIE);
        this.poster_path = getColumnString(cursor, IMAGE_POSTER);
        this.original_language = getColumnString(cursor, ORIGINAL_LANGUAGE);
        this.overview =  getColumnString(cursor, OVERVIEW);
        this.release_date = getColumnString(cursor, RELEASE_DATE);
        this.backdrop_path = getColumnString(cursor, IMAGE_BACKDROP);
    }

    public MovieList() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average){this.vote_average = vote_average;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path){this.backdrop_path = backdrop_path;}

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path){this.poster_path = poster_path;}

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language){this.original_language = original_language;}

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview){this.overview = overview;}

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date){this.release_date = release_date;}


    //constructor Movie
    public MovieList(int id, String vote_average, String title, String poster_path, String original_language, String overview, String release_date, String backdrop_path) {

        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.overview = overview;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
    }



    private MovieList(Parcel in) {
        this.id = in.readInt();
        this.vote_average = in.readString();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.original_language = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.backdrop_path = in.readString();
    }

    public static final Creator<MovieList> CREATOR = new Creator<MovieList>() {
        @Override
        public MovieList createFromParcel(Parcel source) {
            return new MovieList(source);
        }

        @Override
        public MovieList[] newArray(int size) {
            return new MovieList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.vote_average);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.original_language);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.backdrop_path);
    }
}

