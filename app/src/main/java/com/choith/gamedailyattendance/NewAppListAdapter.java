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

public class NewAppListAdapter extends RecyclerView.Adapter<NewAppListAdapter.ViewHolder> {
    private ArrayList<NewAppList> app=null;

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
        protected TextView pkg;

        public ViewHolder(View view) {
            super(view);
            this.icon=(ImageView) view.findViewById(R.id.icon);
            this.name=(TextView) view.findViewById(R.id.name);
            this.pkg=(TextView) view.findViewById(R.id.pkg);

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
    public NewAppListAdapter(ArrayList<NewAppList> app){
        this.app=app;
    }

    @Override
    public NewAppListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item, parent, false) ;
        NewAppListAdapter.ViewHolder vh = new NewAppListAdapter.ViewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewAppList appList=app.get(position);
        holder.icon.setImageDrawable(appList.getIcon());
        holder.name.setText(appList.getName());
        holder.pkg.setText(appList.getPkg());
    }

    @Override
    public int getItemCount() {
        return app.size();
    }



}
