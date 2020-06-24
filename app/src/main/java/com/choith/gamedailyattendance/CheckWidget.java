package com.choith.gamedailyattendance;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class CheckWidget extends AppWidgetProvider {
    public static String PENDING_ACTION="s";
    //com.choith.myapplication.pending_ACTION
    public static int widgetCount=0;
    public static String widgetAppPkg1 =null;
    public static  String widgetAppPkg2 =null;
    public static String widgetAppPkg3 =null;
    public static Bitmap widgetAppIcon1 =null;
    public static Bitmap widgetAppIcon2 =null;
    public static Bitmap widgetAppIcon3 =null;
    public static String widgetAppName1 =null;
    public static String widgetAppName2 =null;
    public static String widgetAppName3 =null;
    public static long widgetAppLastTime1 =0;
    public static long widgetAppLastTime2 =0;
    public static long widgetAppLastTime3 =0;
    public static long widgetAppHour1 =0;
    public static long widgetAppHour2 =0;
    public static long widgetAppHour3 =0;
    public static long widgetAppMinute1 =0;
    public static long widgetAppMinute2 =0;
    public static long widgetAppMinute3 =0;


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.check_widget2);

        String action = intent.getAction();

        if(widgetCount>=1){
//            long timerHour=widget_AppTime1/60;
//            long timerMinute=widget_AppTime1%60;
            rv.setTextViewText(R.id.widget_timer1,MainActivity.getLastTime(widgetAppPkg1)/60+":"+MainActivity.getLastTime(widgetAppPkg1)%60);
            rv.setImageViewBitmap(R.id.widget_appicon1, widgetAppIcon1);
            rv.setTextViewText(R.id.widget_appname1, widgetAppName1);
            rv.setTextViewText(R.id.widget_timer1, widgetAppLastTime1 /60+":"+ widgetAppLastTime1 %60);
//            if(timerHour+23.9<=widget_AppHour1){
//                rv.setImageViewResource(R.id.imageView1,R.drawable.green);
//            }else{
//                rv.setImageViewResource(R.id.imageView1,R.drawable.red);
//            }
        }
        if(widgetCount>=2){
            rv.setTextViewText(R.id.widget_timer2,MainActivity.getLastTime(widgetAppPkg2)/60+":"+MainActivity.getLastTime(widgetAppPkg2)%60);
            rv.setImageViewBitmap(R.id.widget_appicon2, widgetAppIcon2);
            rv.setTextViewText(R.id.widget_appname2, widgetAppName2);
            rv.setTextViewText(R.id.widget_timer2, widgetAppLastTime2 /60+":"+ widgetAppLastTime2 %60);
        }
        if(widgetCount>=3){
            rv.setTextViewText(R.id.widget_timer3,MainActivity.getLastTime(widgetAppPkg3)/60+":"+MainActivity.getLastTime(widgetAppPkg3)%60);
            rv.setImageViewBitmap(R.id.widget_appicon3, widgetAppIcon3);
            rv.setTextViewText(R.id.widget_appname3, widgetAppName3);
            rv.setTextViewText(R.id.widget_timer3, widgetAppLastTime3 /60+":"+ widgetAppLastTime3 %60);
        }

        rv.setOnClickPendingIntent(R.id.imageButton2, getPendingIntent(context, R.id.imageButton2));

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName cpName = new ComponentName(context, CheckWidget.class);
        appWidgetManager.updateAppWidget(cpName, rv);
        Log.d("ref","up");
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    private PendingIntent getPendingIntent(Context context, int id) {
        Intent intent = new Intent(context, CheckWidget.class);
        intent.setAction(PENDING_ACTION);
        intent.putExtra("viewId", id);

        // 중요!!! getBroadcast를 이용할 때 동일한 Action명을 이용할 경우 서로 다른 request ID를 이용해야함
        // 아래와 같이 동일한 request ID를 주면 서로 다른 값을 putExtra()하더라도 제일 처음 값만 반환됨
        // return PendingIntent.getBroadcast(context, 0, intent, 0);
        return PendingIntent.getBroadcast(context, id, intent, 0);
    }
}

