package top.technopedia.myapplicationkatalogfilm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class MovieResponse {
    @SerializedName("results")
    private List<MovieList> results;

    public List<MovieList> getResults() {
        return results;
    }

}
