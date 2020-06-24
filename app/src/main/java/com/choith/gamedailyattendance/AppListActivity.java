package com.choith.gamedailyattendance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AppListActivity extends AppCompatActivity {
    private ArrayList<NewAppList> newAppList;
    private NewAppListAdapter newAppListAdapter;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);

        activity=AppListActivity.this;
        SharedPreferences pref=getSharedPreferences("myApp",MODE_PRIVATE);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.newapplist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        newAppList =new ArrayList<>();
        newAppListAdapter =new NewAppListAdapter(newAppList);
        recyclerView.setAdapter(newAppListAdapter);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        ArrayList<String> appsPkg = new ArrayList<String>();
        List<PackageInfo> pack = getPackageManager().getInstalledPackages(0);
        Drawable appIcon = null;
        String appName="";
        for(int i = 0; i < pack.size(); i++){
            appsPkg.add(pack.get(i).packageName);
            Log.d("pkg",appsPkg.get(i));
                try {
                    appIcon = getPackageManager().getApplicationIcon(appsPkg.get(i));
                    appName = getPackageManager().getApplicationLabel
                            (getPackageManager().getApplicationInfo(appsPkg.get(i), PackageManager.GET_UNINSTALLED_PACKAGES)) .toString();
                } catch (PackageManager.NameNotFoundException e) {}
                NewAppList app=new NewAppList(appIcon,appName,appsPkg.get(i));
                newAppList.add(app);

        }
        Collections.sort(newAppList, new Comparator<NewAppList>() {
            @Override
            public int compare(NewAppList o1, NewAppList o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        newAppListAdapter.notifyDataSetChanged();


        newAppListAdapter.setOnItemClickListener(new NewAppListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent i=new Intent(AppListActivity.this,AppSettingActivity.class);
                String pkg=newAppList.get(pos).getPkg();
                i.putExtra("pkg",pkg);
                i.putExtra("activity","AppListActivity");
                startActivity(i);
            }
        });
    }
}
