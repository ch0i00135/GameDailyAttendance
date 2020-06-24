package com.choith.gamedailyattendance;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{
    private static File file;
    private ArrayList<MyAppList> myAppList;
    private MyAppListAdapter myAppListAdapter;
    public static Activity activity;
    private static UsageStatsManager usageStatsManager;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File(getFilesDir(), "MyAppList.db") ;
        usageStatsManager= (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        activity=MainActivity.this;
        db=init_database();
        init_tables();

        Button button=(Button)findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            }
        });

        Button listButton=(Button)findViewById(R.id.button);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, AppListActivity.class);
                startActivity(i);
            }
        });


        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.myapplist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        myAppList =new ArrayList<>();
        myAppListAdapter =new MyAppListAdapter(myAppList);
        recyclerView.setAdapter(myAppListAdapter);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        final String selectTbl = "SELECT * FROM AppList" ;
        Cursor cursor = null ;

        cursor = db.rawQuery(selectTbl, null) ;

        for (int i=0;i<dbCount();i++){
            Drawable appIcon = null;
            String appName="";
            String pkg = null;
            int hour=0;
            int minute=0;
            if (cursor.moveToNext()) {
                pkg = cursor.getString(0) ;
                hour = cursor.getInt(1);
                minute = cursor.getInt(2);
            }
            try {
                appIcon = getPackageManager().getApplicationIcon(pkg);
                appName = getPackageManager().getApplicationLabel
                        (getPackageManager().getApplicationInfo(pkg, PackageManager.GET_UNINSTALLED_PACKAGES)) .toString();
            } catch (PackageManager.NameNotFoundException e) {
            }
            MyAppList app=new MyAppList(appIcon,appName, hour, minute, getLastTime(pkg));
            myAppList.add(app);
            if(i<3) setWidget(pkg,appIcon,appName,getLastTime(pkg),hour,minute,i+1);
        }
        myAppListAdapter.notifyDataSetChanged();

        myAppListAdapter.setOnItemClickListener(new MyAppListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Cursor itemCursor = db.rawQuery(selectTbl, null) ;
                for(int i=-1;i<pos;i++){
                    itemCursor.moveToNext();
                }
                Intent intent=new Intent(MainActivity.this,AppSettingActivity.class);
                Log.d("pkg",itemCursor.getString(0));
                intent.putExtra("Pkg",itemCursor.getString(0));
                intent.putExtra("activity","MainActivity");
                startActivity(intent);
            }
        });

        Button resetButton=(Button)findViewById(R.id.button4);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDelete("");
                myAppList.clear();
                myAppListAdapter.notifyDataSetChanged();
                for(int i=1;i<=3;i++){
                    setWidget(null,null,null,0,0,0,i);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAppListAdapter.notifyDataSetChanged();
    }
    private static SQLiteDatabase init_database(){
        SQLiteDatabase db = null ;

        try {
            db = SQLiteDatabase.openOrCreateDatabase(file, null) ;
        } catch (SQLiteException e) {
            e.printStackTrace() ;
        }
        return db ;
    }

    private static void init_tables() {
        SQLiteDatabase db=init_database();
        if (db != null) {
            String createTable = "CREATE TABLE IF NOT EXISTS AppList (" +
                    "pkg TEXT," +
                    "hour INTEGER," +
                    "minute INTEGER" +
                    ")" ;


            db.execSQL(createTable) ;
        }
    }
    private static int dbCount(){
        SQLiteDatabase db=init_database();
        Cursor cursor = db.rawQuery("SELECT * FROM AppList", null) ;
        return cursor.getCount();
    }
    public void save_values(String pkg, int timer, int minute) {
        SQLiteDatabase db=init_database();
        if (db != null) {

            String sqlInsert = "INSERT INTO AppList VALUES (" +
                    "'" + pkg + "'," +
                    "'" + timer + "'," +
                    "'" + minute + "'" +
                    ")" ;

            db.execSQL(sqlInsert) ;
        }
    }
    public static void dbDelete(String pkg){
        SQLiteDatabase db=init_database();
        String sqlDelete = null;
        if (db != null) {
            if(pkg!="") {
                sqlDelete = "DELETE FROM AppList WHERE pkg='" + pkg + "';";
            }else{
                sqlDelete="DELETE FROM AppList";
            }
            db.execSQL(sqlDelete);
        }
    }
    public static long getLastTime(String pkg){
        long time=System.currentTimeMillis();
        long lastTime = 0;
        List<UsageStats> usageStats=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,0,time);
        for(UsageStats it:usageStats){
            if(it.getPackageName().equals(pkg)){
                lastTime=it.getLastTimeUsed();
            }
        }
        return ((time-lastTime)/1000/60);
    }
    public static void setWidget(String pkg,Drawable icon,String name,long time,long hour,long minute,int id){
        switch (id){
            case 1:
                CheckWidget.widgetAppPkg1 =pkg;
                //CheckWidget.widget_AppIcon1=((BitmapDrawable)icon).getBitmap();
                CheckWidget.widgetAppName1 =name;
                CheckWidget.widgetAppLastTime1 =time;
                CheckWidget.widgetAppHour1 =hour;
                CheckWidget.widgetAppMinute1 =minute;
                break;
            case 2:
                CheckWidget.widgetAppPkg2 =pkg;
                //CheckWidget.widget_AppIcon2=((BitmapDrawable)icon).getBitmap();
                CheckWidget.widgetAppName2 =name;
                CheckWidget.widgetAppLastTime2 =time;
                CheckWidget.widgetAppHour2 =hour;
                CheckWidget.widgetAppMinute2 =minute;
                break;
            case 3:
                CheckWidget.widgetAppPkg3 =pkg;
                //CheckWidget.widget_AppIcon3=((BitmapDrawable)icon).getBitmap();
                CheckWidget.widgetAppName3 =name;
                CheckWidget.widgetAppLastTime3 =time;
                CheckWidget.widgetAppHour3 =hour;
                CheckWidget.widgetAppMinute3 =minute;
                break;
        }
        CheckWidget.widgetCount=id;
    }
}


