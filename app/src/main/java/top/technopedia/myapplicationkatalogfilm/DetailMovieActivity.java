package top.technopedia.myapplicationkatalogfilm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.technopedia.myapplicationkatalogfilm.Helper.MovieHelper;
import top.technopedia.myapplicationkatalogfilm.Model.MovieList;

import static top.technopedia.myapplicationkatalogfilm.BuildConfig.url_image;

public class DetailMovieActivity extends AppCompatActivity {
    ImageView posterutm,poster, imgfav;
    TextView judul,rilis,tglrilis, rating,Sinopsis, detail,jdlats, bhsasl;

    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_POSTER_PATH = "extra_poster_path";
    public static String EXTRA_ID = "extra_id";
    public static String EXTRA_RATE = "extra_rate";
    public static String EXTRA_ORIGINAL_LANGUAGE = "extra_original_language";
    public static String EXTRA_BACKDROP_PATH = "extra_backdrop_path";
    public static String EXTRA_FAVORITE = "extra_favorite";


    private MovieHelper favHelper;
    private boolean extraFav = false;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        posterutm = findViewById(R.id.posterutm);
        poster = findViewById(R.id.poster);
        judul =  findViewById(R.id.judul);
        rilis = findViewById(R.id.rilis);
        tglrilis = findViewById(R.id.tglrilis);
        rating = findViewById(R.id.rating);
        Sinopsis = findViewById(R.id.sinopsis);
        detail = findViewById(R.id.detail);
        jdlats = findViewById(R.id.jdlats);
        bhsasl =  findViewById(R.id.bhsasl);
        imgfav =  findViewById(R.id.imgfav);


        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String rate = getIntent().getStringExtra(EXTRA_RATE);
        String orilangu = getIntent().getStringExtra(EXTRA_ORIGINAL_LANGUAGE);
        String poster_path = getIntent().getStringExtra(EXTRA_POSTER_PATH);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String backdrop_path = getIntent().getStringExtra(EXTRA_BACKDROP_PATH);

        Picasso.with(this).load(url_image + backdrop_path).placeholder(this.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).error(this.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).into(posterutm);
        Picasso.with(this).load(url_image + poster_path).into(poster);
        judul.setText(title);
        jdlats.setText(title);

        if (getSupportActionBar() != null)

            getSupportActionBar().setTitle(title);


        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatOfDate = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = formatOfDate.parse(release_date);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormatDate =  new SimpleDateFormat("EEEE, dd MMM yyyy");
            String releaseDate = newFormatDate.format(date);
            tglrilis.setText(releaseDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        rating.setText(rate + "/10");
        detail.setText(overview);
        bhsasl.setText(orilangu);


        favHelper = new MovieHelper(this);
        favHelper.open();

        int fav = getIntent().getIntExtra(EXTRA_FAVORITE, 0);
        if (fav == 1){
            extraFav = true;
            imgfav.setImageResource(R.drawable.ic_favorite_red_24dp);
        }

        imgfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!extraFav){
                    addFavorit();
                    Toast.makeText(DetailMovieActivity.this, getString(R.string.toast_add), Toast.LENGTH_LONG).show();
                }else {
                    deleteFavorite();
                    Toast.makeText(DetailMovieActivity.this, getString(R.string.toast_remove), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }


    private void addFavorit(){
        MovieList favItems = new MovieList();
        favItems.setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        favItems.setOverview(getIntent().getStringExtra(EXTRA_OVERVIEW));
        favItems.setRelease_date(getIntent().getStringExtra(EXTRA_RELEASE_DATE));
        favItems.setPoster_path(getIntent().getStringExtra(EXTRA_POSTER_PATH));
        favItems.setBackdrop_path(getIntent().getStringExtra(EXTRA_BACKDROP_PATH));
        favItems.setVote_average(getIntent().getStringExtra(EXTRA_RATE));
        favItems.setOriginal_language(getIntent().getStringExtra(EXTRA_ORIGINAL_LANGUAGE));
        favHelper.insert(favItems);

        imgfav.setImageResource(R.drawable.ic_favorite_red_24dp);

    }

    private void deleteFavorite(){
        favHelper.delete(getIntent().getIntExtra(EXTRA_ID, 0));

        imgfav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

}
