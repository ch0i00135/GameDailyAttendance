package com.choith.gamedailyattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAppListAdapter extends RecyclerView.Adapter<MyAppListAdapter.ViewHolder> {
    private ArrayList<MyAppList> app=null;

    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView icon;
        protected TextView name;
        protected TextView timer;
        protected TextView last;

        public ViewHolder(View view) {
            super(view);
            this.icon=(ImageView) view.findViewById(R.id.icon);
            this.name=(TextView) view.findViewById(R.id.name);
            this.timer=(TextView) view.findViewById(R.id.timer);
            this.last=(TextView) view.findViewById(R.id.lasttimetext);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        mListener.onItemClick(v,position);
                    }
                }
            });
        }
    }
    public MyAppListAdapter(ArrayList<MyAppList> app){
        this.app=app;
    }

    @Override
    public MyAppListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.my_item, parent, false) ;
        MyAppListAdapter.ViewHolder vh = new MyAppListAdapter.ViewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyAppList appList=app.get(position);
        holder.icon.setImageDrawable(appList.getIcon());
        holder.name.setText(appList.getName());
        holder.timer.setText(appList.getHour()+":"+appList.getMinute()+" 에 초기화");
        holder.last.setText(appList.getLast()/60/24+":"+appList.getLast()/60%24+":"+appList.getLast()%60+" 지남");
    }

    @Override
    public int getItemCount() {
        return app.size();
    }

    public void removeData(int pos){
        app.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, app.size());
    }


}
