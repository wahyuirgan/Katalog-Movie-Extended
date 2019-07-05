package top.technopedia.myapplicationkatalogfilm.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.technopedia.myapplicationkatalogfilm.Api.ApiBuilder;
import top.technopedia.myapplicationkatalogfilm.Api.ApiService;
import top.technopedia.myapplicationkatalogfilm.DetailMovieActivity;
import top.technopedia.myapplicationkatalogfilm.Model.MovieList;
import top.technopedia.myapplicationkatalogfilm.Model.MovieResponse;
import top.technopedia.myapplicationkatalogfilm.R;

import static top.technopedia.myapplicationkatalogfilm.BuildConfig.API_KEY;

public class UpcomingAlarmReceiver extends GcmTaskService {

    public static String TAG_TASK_UPCOMING = "upcoming movies";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)) {
            loadData();
        }

        return result;
    }

    private void loadData() {
        ApiService apiService = ApiBuilder.getClient().create(ApiService.class);
        Call<MovieResponse> call = apiService.getUpcoming(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
//                final List<MovieModel> movies = response.body().getResults();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<MovieList> items = response.body().getResults();
                    int index = new Random().nextInt(items.size());

                    MovieList item = items.get(index);
                    String title = items.get(index).getTitle();
                    String message = items.get(index).getOverview();
                    int notifId = 200;

                    showNotification(getApplicationContext(), title, message, notifId, item);

                } else loadFailed();
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {

            }
        });

    }

    private void loadFailed() {
        Toast.makeText(this, R.string.err_load_failed, Toast.LENGTH_SHORT).show();
    }

    private void showNotification(Context context, String title, String message, int notifId, MovieList item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String time = item.getRelease_date();
        String poster = item.getPoster_path();
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_TITLE, title);
        intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, message);
        intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE, time);
        intent.putExtra(DetailMovieActivity.EXTRA_POSTER_PATH, poster);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}