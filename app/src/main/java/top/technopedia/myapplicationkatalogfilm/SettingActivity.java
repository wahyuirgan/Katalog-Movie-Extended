package top.technopedia.myapplicationkatalogfilm;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.technopedia.myapplicationkatalogfilm.Service.DailyAlarmReceiver;
import top.technopedia.myapplicationkatalogfilm.Service.Upcoming;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.switch_daily)
    Switch setDailyReminderAlarm;
    @BindView(R.id.switch_upcoming) Switch setUpcomingReminderAlarm;

    private DailyAlarmReceiver dailyReminderMovie;
    private SettingPreference settingPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        dailyReminderMovie = new DailyAlarmReceiver();

        settingPreference = new SettingPreference(this);

        setEnableDisableNotif();
    }

    void setEnableDisableNotif(){
        if (settingPreference.isDaily()){
            setDailyReminderAlarm.setChecked(true);
        }else {
            setDailyReminderAlarm.setChecked(false);
        }

        if (settingPreference.isUpcoming()){
            setUpcomingReminderAlarm.setChecked(true);
        }else {
            setUpcomingReminderAlarm.setChecked(false);
        }
    }

    @OnClick({R.id.switch_daily,R.id.switch_upcoming,R.id.setting_local})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.switch_daily:
                boolean isDaily = setDailyReminderAlarm.isChecked();
                if (isDaily){
                    setDailyReminderAlarm.setEnabled(true);
                    settingPreference.setDaily(true);
                    dailyReminderMovie.setDailyReminderAlarm(this, DailyAlarmReceiver.TYPE_REPEATING,
                            "07:00", getString(R.string.msg_daily_reminder));
                    Toast.makeText(this, R.string.message_setup_daily, Toast.LENGTH_SHORT).show();
                }else {
                    setDailyReminderAlarm.setChecked(false);
                    settingPreference.setDaily(false);
                    dailyReminderMovie.cancelAlarm(this, DailyAlarmReceiver.TYPE_REPEATING);
                    Toast.makeText(this, R.string.message_cancel_daily, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch_upcoming:
                Upcoming mUpComingTask = new Upcoming(this);
                boolean isUpcoming = setUpcomingReminderAlarm.isChecked();
                if (isUpcoming){
                    setUpcomingReminderAlarm.setEnabled(true);
                    settingPreference.setUpcoming(true);

                    mUpComingTask.createPeriodicTask();
                    Toast.makeText(this, R.string.message_setup_upcoming, Toast.LENGTH_SHORT).show();
                }else {
                    setUpcomingReminderAlarm.setChecked(false);
                    settingPreference.setUpcoming(false);
                    mUpComingTask.cancelPeriodicTask();
                    Toast.makeText(this, R.string.message_cancel_upcoming, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.setting_local:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }

}