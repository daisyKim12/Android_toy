package org.texchtown.fullconcentration3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    List<AppInfo> appList;

    Context context;

    //constructor for AppListAdapter
    //adding appInfos input into appList
    AppListAdapter(List<AppInfo> appInfos) {
        this.appList=appInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.app_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.appLogo.setImageDrawable(appList.get(position).appLogo);
        holder.appName.setText(appList.get(position).appName);
        if(appList.get(position).appStatus)
            holder.appStatus.setChecked(true);
        else
            holder.appStatus.setChecked(false);
    }

    @Override
    public int getItemCount() {
        //return size to notify how many card needed to be made
        return appList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //object data for the card view
        ImageView appLogo;
        TextView appName;
        CheckBox appStatus;
        public ViewHolder(@NonNull View itemView) {
            //using super connect objects with input View
            super(itemView);
            appLogo = itemView.findViewById(R.id.app_logo);
            appName = itemView.findViewById(R.id.app_name);
            appStatus = itemView.findViewById(R.id.app_status);
        }
    }

}
