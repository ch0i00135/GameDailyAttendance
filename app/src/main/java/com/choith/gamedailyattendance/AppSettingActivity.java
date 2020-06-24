package com.choith.gamedailyattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class AppSettingActivity extends AppCompatActivity {

    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);


        Button button = (Button) findViewById(R.id.button2);
        Button button2 = (Button) findViewById(R.id.button3);
        TextView textView = (TextView) findViewById(R.id.textView);
        timePicker=(TimePicker)findViewById(R.id.timepicker);

        final MainActivity ma = (MainActivity) MainActivity.activity;
        final AppListActivity la = (AppListActivity) AppListActivity.activity;

        Intent getI = getIntent();

        final String pkg = getI.getExtras().getString("pkg");
        String activityString = getI.getExtras().getString("activity");

        switch (activityString) {
            case "AppListActivity":
                button2.setText("취소");
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                break;
            case "MainActivity":
                button2.setText("목록에서 제거");
                final int pos = getI.getExtras().getInt("pos");
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(AppSettingActivity.this, MainActivity.class);
                        Intent getI=getIntent();
                        MainActivity.dbDelete(getI.getExtras().getString("Pkg"));
                        startActivity(i);
                        finish();
                    }
                });
                break;
        }
        timePicker.setHour(0);
        timePicker.setMinute(0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour=timePicker.getHour();
                int minute=timePicker.getMinute();
                ma.finish();
                la.finish();
                Intent i = new Intent(AppSettingActivity.this, MainActivity.class);
                ma.save_values(pkg,hour,minute);
                startActivity(i);
                finish();
            }
        });
    }
}
